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

    void initServerNodeSequence(Collection<String> nodeCollection);

    void initServerNodeSequenceByIp(Collection<String> ipCollection);

    void initServerNodeSequenceByIpAndPort(Map<String, Set<Integer>> ipAndPortMap);

    ServerNodeSequence getOrUpdateEmptyServerNodeSequence(String node);

    ServerNodeSequence getOrUpdateEmptyServerNodeSequenceByIp(String ip);

    ServerNodeSequence getOrUpdateEmptyServerNodeSequenceByIpAndPort(String ip, int port);

    ServerNodeSequence getServerNodeSequence(String node);

    ServerNodeSequence getServerNodeSequenceByIp(String ip);

    ServerNodeSequence getServerNodeSequenceByIpAndPort(String ip, int port);

    List<ServerNodeSequence> listServerNodeSequence();

}
