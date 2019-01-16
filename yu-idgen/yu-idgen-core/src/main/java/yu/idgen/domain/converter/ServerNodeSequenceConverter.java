package yu.idgen.domain.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import yu.idgen.domain.ServerNodeSequence;
import yu.idgen.domain.po.ServerNodeSequencePO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsp on 2019/1/16.
 */
public class ServerNodeSequenceConverter {

    public static ServerNodeSequence fromPO(ServerNodeSequencePO serverNodeSequencePO) {
        if(serverNodeSequencePO == null) {
            return null;
        }
        ServerNodeSequence serverNodeSequence = new ServerNodeSequence();
        BeanUtils.copyProperties(serverNodeSequencePO, serverNodeSequence);
        return serverNodeSequence;
    }

    public static List<ServerNodeSequence> fromPO(List<ServerNodeSequencePO> serverNodeSequencePOList) {
        List<ServerNodeSequence> serverNodeSequenceList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(serverNodeSequencePOList)) {
            for(ServerNodeSequencePO serverNodeSequencePO : serverNodeSequencePOList) {
                if(serverNodeSequencePO == null) {
                    continue;
                }
                ServerNodeSequence serverNodeSequence = new ServerNodeSequence();
                BeanUtils.copyProperties(serverNodeSequencePO, serverNodeSequence);
                serverNodeSequenceList.add(serverNodeSequence);
            }
        }
        return serverNodeSequenceList;
    }

    public static List<ServerNodeSequencePO> toPO(List<ServerNodeSequence> serverNodeSequenceList) {
        List<ServerNodeSequencePO> serverNodeSequencePOList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(serverNodeSequenceList)) {
            for(ServerNodeSequence serverNodeSequence : serverNodeSequenceList) {
                if(serverNodeSequence == null) {
                    continue;
                }
                ServerNodeSequencePO serverNodeSequencePO = new ServerNodeSequencePO();
                BeanUtils.copyProperties(serverNodeSequence, serverNodeSequencePO);
                serverNodeSequencePOList.add(serverNodeSequencePO);
            }
        }
        return serverNodeSequencePOList;
    }

}
