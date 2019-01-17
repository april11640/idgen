package yu.idgen.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import yu.idgen.dao.api.ServerNodeSequenceDao;
import yu.idgen.domain.ServerNodeSequence;
import yu.idgen.domain.converter.ServerNodeSequenceConverter;
import yu.idgen.domain.po.ServerNodeSequencePO;
import yu.idgen.manager.api.ServerNodeSequenceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zsp on 2019/1/16.
 */
@Service
public class ServerNodeSequenceManagerImpl implements ServerNodeSequenceManager {

    @Autowired
    private ServerNodeSequenceDao serverNodeSequenceDao;

    @Override
    public void saveAllServerNodeSequence(Collection<ServerNodeSequence> serverNodeSequenceCollection) {
        if(CollectionUtils.isEmpty(serverNodeSequenceCollection)) {
            return;
        }
        List<ServerNodeSequence> serverNodeSequenceList = new ArrayList<>(serverNodeSequenceCollection);
        int endIndex;
        for(int i = 0, len = serverNodeSequenceList.size(); i < len; i += 300) {
            endIndex = i + 300 > len ? len : i + 300;
            List<ServerNodeSequence> subList = serverNodeSequenceList.subList(i, endIndex);
            serverNodeSequenceDao.saveAllServerNodeSequence(ServerNodeSequenceConverter.toPO(subList));
        }
    }

    @Override
    public int updateEmptyServerNodeSequence(String emptyNode, ServerNodeSequence serverNodeSequence) {
        return serverNodeSequenceDao.updateEmptyServerNodeSequence(serverNodeSequence.getId(),
                emptyNode, serverNodeSequence.getNode());
    }

    @Override
    public ServerNodeSequence getServerNodeSequence(String node) {
        ServerNodeSequencePO serverNodeSequencePO = serverNodeSequenceDao.getServerNodeSequence(node);
        return ServerNodeSequenceConverter.fromPO(serverNodeSequencePO);
    }

    @Override
    public List<ServerNodeSequence> listServerNodeSequence(String emptyNode, int size) {
        List<ServerNodeSequencePO> serverNodeSequencePOList
                = serverNodeSequenceDao.listServerNodeSequence(emptyNode, size);
        return ServerNodeSequenceConverter.fromPO(serverNodeSequencePOList);
    }

    @Override
    public List<ServerNodeSequence> listEmptyServerNodeSequence(String emptyNode, int size) {
        List<ServerNodeSequencePO> serverNodeSequencePOList
                = serverNodeSequenceDao.listEmptyServerNodeSequence(emptyNode, size);
        return ServerNodeSequenceConverter.fromPO(serverNodeSequencePOList);
    }
}
