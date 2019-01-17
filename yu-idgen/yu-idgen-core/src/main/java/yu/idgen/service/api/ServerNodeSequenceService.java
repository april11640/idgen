package yu.idgen.service.api;

import yu.idgen.domain.ServerNodeSequence;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zsp on 2019/1/16.
 */
public interface ServerNodeSequenceService {

    /**
     * 初始化服务节点的序列号，用于数据的初始化，仅执行一次。
     * 例如：支持1024个服务节点，则初始化1024个节点数据（未分配的节点为空节点）。
     *
     * @param nodeCollection
     */
    void initServerNodeSequence(Collection<String> nodeCollection);

    /**
     * 初始化服务节点的序列号，用于数据的初始化，仅执行一次。
     * 例如：支持1024个服务节点，则初始化1024个节点数据（未分配的节点为空节点）。
     *
     * @param ipCollection
     */
    void initServerNodeSequenceByIp(Collection<String> ipCollection);

    /**
     * 初始化服务节点的序列号，用于数据的初始化，仅执行一次。
     * 例如：支持1024个服务节点，则初始化1024个节点数据（未分配的节点为空节点）。
     *
     * @param ipAndPortMap
     */
    void initServerNodeSequenceByIpAndPort(Map<String, Set<Integer>> ipAndPortMap);

    /**
     * 获取指定服务节点的序列号；若未分配，则更新一个空节点然后返回。
     *
     * @param node
     * @return
     */
    ServerNodeSequence getOrUpdateEmptyServerNodeSequence(String node);

    /**
     * 获取指定服务节点的序列号；若未分配，则更新一个空节点然后返回。
     *
     * @param ip
     * @return
     */
    ServerNodeSequence getOrUpdateEmptyServerNodeSequenceByIp(String ip);

    /**
     * 获取指定服务节点的序列号；若未分配，则更新一个空节点然后返回。
     *
     * @param ip
     * @param port
     * @return
     */
    ServerNodeSequence getOrUpdateEmptyServerNodeSequenceByIpAndPort(String ip, int port);

    /**
     * 获取指定服务节点的序列号。
     *
     * @param node
     * @return
     */
    ServerNodeSequence getServerNodeSequence(String node);

    /**
     * 获取指定服务节点的序列号。
     *
     * @param ip
     * @return
     */
    ServerNodeSequence getServerNodeSequenceByIp(String ip);

    /**
     * 获取指定服务节点的序列号。
     *
     * @param ip
     * @param port
     * @return
     */
    ServerNodeSequence getServerNodeSequenceByIpAndPort(String ip, int port);

    /**
     * 查询所有服务节点的序列号
     *
     * @param allocatedOnly     仅分配占用的节点
     * @param size              返回的记录个数
     * @return
     */
    List<ServerNodeSequence> listServerNodeSequence(boolean allocatedOnly, int size);

}
