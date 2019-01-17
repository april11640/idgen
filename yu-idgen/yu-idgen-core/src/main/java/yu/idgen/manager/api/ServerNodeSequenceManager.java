package yu.idgen.manager.api;

import yu.idgen.domain.ServerNodeSequence;

import java.util.Collection;
import java.util.List;

/**
 * Created by zsp on 2019/1/16.
 */
public interface ServerNodeSequenceManager {

    void saveAllServerNodeSequence(Collection<ServerNodeSequence> serverNodeSequenceCollection);

    int updateEmptyServerNodeSequence(String emptyNode, ServerNodeSequence serverNodeSequence);

    ServerNodeSequence getServerNodeSequence(String node);

    List<ServerNodeSequence> listServerNodeSequence(String emptyNode, int size);

    List<ServerNodeSequence> listEmptyServerNodeSequence(String emptyNode, int size);

}
