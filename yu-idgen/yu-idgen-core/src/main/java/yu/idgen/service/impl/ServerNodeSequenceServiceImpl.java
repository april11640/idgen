package yu.idgen.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import yu.idgen.domain.IdGenerationConfig;
import yu.idgen.domain.IdGenerationException;
import yu.idgen.domain.ServerNodeSequence;
import yu.idgen.manager.api.ServerNodeSequenceManager;
import yu.idgen.service.api.ServerNodeSequenceService;

import java.security.SecureRandom;
import java.util.*;

import static yu.idgen.domain.ServerNodeSequence.EMPTY_NODE_VALUE;

/**
 * Created by zsp on 2019/1/16.
 */
@Service
public class ServerNodeSequenceServiceImpl implements ServerNodeSequenceService, InitializingBean {

    private int fetchSize;
    private final SecureRandom random = new SecureRandom();

    @Autowired
    private ServerNodeSequenceManager serverNodeSequenceManager;

    @Autowired(required = false)
    private IdGenerationConfig idGenerationConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        float factor;
        if(idGenerationConfig == null) {
            factor = IdGenerationConfig.DEFAULT_EMPTY_NODE_FETCH_FACTOR;
        } else {
            factor = idGenerationConfig.getEmptyNodeFetchFactor();
        }
        fetchSize = Math.round(Runtime.getRuntime().availableProcessors() * factor);
    }

    @Override
    public void initServerNodeSequence(Collection<String> nodeCollection) {
        List<ServerNodeSequence> serverNodeSequenceList = new ArrayList<>();
        ServerNodeSequence serverNodeSequence;
        int size = CollectionUtils.isEmpty(nodeCollection) ? 0 : nodeCollection.size();
        if (size > 0) {
            Set<String> nodeSet = new HashSet<>(nodeCollection);
            Iterator<String> it = nodeSet.iterator();
            for (int i = 0; i < size; i++) {
                serverNodeSequence = new ServerNodeSequence();
                serverNodeSequence.setId(i);
                serverNodeSequence.setNode(it.next());
                serverNodeSequenceList.add(serverNodeSequence);
            }
        }
        int maxSize =(idGenerationConfig == null)
                ? 1 << IdGenerationConfig.DEFAULT_SERVER_NODE_SEQUENCE_BITS
                : 1 << idGenerationConfig.getServerNodeSequenceBits();
        for (int i = size; i < maxSize; i++) {
            serverNodeSequence = new ServerNodeSequence();
            serverNodeSequence.setId(i);
            serverNodeSequence.setNode(EMPTY_NODE_VALUE);
            serverNodeSequenceList.add(serverNodeSequence);
        }
        serverNodeSequenceManager.saveAllServerNodeSequence(serverNodeSequenceList);
    }

    @Override
    public void initServerNodeSequenceByIp(Collection<String> ipCollection) {
        initServerNodeSequence(ipCollection);
    }

    @Override
    public void initServerNodeSequenceByIpAndPort(Map<String, Set<Integer>> ipAndPortMap) {
        List<String> nodeList = new ArrayList<>();
        for(Map.Entry<String, Set<Integer>> entry : ipAndPortMap.entrySet()) {
            for(Integer port : entry.getValue()) {
                nodeList.add(entry.getKey() + ":" + port);
            }
        }
        initServerNodeSequence(nodeList);
    }

    @Override
    public ServerNodeSequence getOrUpdateEmptyServerNodeSequence(String node) {
        ServerNodeSequence serverNodeSequence = serverNodeSequenceManager.getServerNodeSequence(node);
        if (serverNodeSequence == null) {
            List<ServerNodeSequence> emptyServerNodeSequenceList;
            int index, affectedRows = 0;
            Set<Integer> indexSet = new HashSet<>();
            Outter: for(int i = 0; i < 3; i++) {
                emptyServerNodeSequenceList = serverNodeSequenceManager.listEmptyServerNodeSequence(
                        EMPTY_NODE_VALUE, fetchSize);
                for (int j = 0; j < 3; j++) {
                    do {
                        index = random.nextInt(emptyServerNodeSequenceList.size() + 1) - 1;
                        if (index < 0) {
                            index = 0;
                        }
                    } while (indexSet.contains(index));
                    indexSet.add(index);
                    serverNodeSequence = emptyServerNodeSequenceList.get(index);
                    serverNodeSequence.setNode(node);
                    affectedRows = serverNodeSequenceManager.updateEmptyServerNodeSequence(
                            EMPTY_NODE_VALUE, serverNodeSequence);
                    if (affectedRows > 0) {
                        break Outter;
                    }
                }
                indexSet.clear();
            }
            if(affectedRows < 1) {
                throw new IdGenerationException("抢占服务节点序号失败");
            }
        }
        return serverNodeSequence;
    }

    @Override
    public ServerNodeSequence getOrUpdateEmptyServerNodeSequenceByIp(String ip) {
        return getOrUpdateEmptyServerNodeSequence(ip);
    }

    @Override
    public ServerNodeSequence getOrUpdateEmptyServerNodeSequenceByIpAndPort(String ip, int port) {
        return getOrUpdateEmptyServerNodeSequence(ip + ":" + port);
    }

    @Override
    public ServerNodeSequence getServerNodeSequence(String node) {
        return serverNodeSequenceManager.getServerNodeSequence(node);
    }

    @Override
    public ServerNodeSequence getServerNodeSequenceByIp(String ip) {
        return getServerNodeSequence(ip);
    }

    @Override
    public ServerNodeSequence getServerNodeSequenceByIpAndPort(String ip, int port) {
        return getServerNodeSequence(ip + ":" + port);
    }

    @Override
    public List<ServerNodeSequence> listServerNodeSequence(boolean allocatedOnly, int size) {
        return serverNodeSequenceManager.listServerNodeSequence(
                allocatedOnly ? EMPTY_NODE_VALUE : null,
                size);
    }

}
