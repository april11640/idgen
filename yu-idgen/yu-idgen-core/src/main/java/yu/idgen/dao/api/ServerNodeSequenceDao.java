package yu.idgen.dao.api;

import yu.idgen.domain.po.ServerNodeSequencePO;

import java.util.Collection;
import java.util.List;

/**
 * Created by zsp on 2018/12/4.
 */
public interface ServerNodeSequenceDao {

    int saveAllServerNodeSequence(Collection<ServerNodeSequencePO> serverNodeSequencePOCollection);

    int updateEmptyServerNodeSequence(Integer id, String emptyNode, String newNode);

    ServerNodeSequencePO getServerNodeSequence(String node);

    List<ServerNodeSequencePO> listServerNodeSequence();

    List<ServerNodeSequencePO> listEmptyServerNodeSequence(String emptyNode, int size);

}
