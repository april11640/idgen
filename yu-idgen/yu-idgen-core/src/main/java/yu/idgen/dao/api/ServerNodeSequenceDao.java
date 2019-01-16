package yu.idgen.dao.api;

import yu.idgen.domain.po.ServerNodeSequencePO;

/**
 * Created by zsp on 2018/12/4.
 */
public interface ServerNodeSequenceDao {

    int saveServerNodeSequence(ServerNodeSequencePO serverNodeSequence);

    ServerNodeSequencePO getServerNodeSequence(String node);

}
