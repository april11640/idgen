package yu.idgen.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yu.idgen.domain.ServerNodeSequence;
import yu.idgen.service.api.ServerNodeSequenceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zsp on 2019/1/16.
 */
@RestController
public class ServerNodeSequenceFacadeImpl {

    @Autowired
    private ServerNodeSequenceService serverNodeSequenceService;

    @PostMapping("/server-node/sequence/init")
    public void initServerNodeSequence(@RequestBody String[] nodeCollection) {
        if(nodeCollection == null || nodeCollection.length == 0) {
            serverNodeSequenceService.initServerNodeSequence(null);
        } else {
            serverNodeSequenceService.initServerNodeSequence(Arrays.asList(nodeCollection));
        }
    }

    @PostMapping("/server-node/sequence/get-or-update")
    public ServerNodeSequence getOrUpdateEmptyServerNodeSequence(@RequestBody String node) {
        return serverNodeSequenceService.getOrUpdateEmptyServerNodeSequence(node);
    }

    @PostMapping("/server-node/sequence/get")
    public ServerNodeSequence getServerNodeSequence(@RequestBody String node) {
        return serverNodeSequenceService.getServerNodeSequence(node);
    }

    @PostMapping("/server-node/sequence/list")
    public List<ServerNodeSequence> listServerNodeSequence() {
        return serverNodeSequenceService.listServerNodeSequence();
    }

    @PostMapping("/server-node/sequence/test/get-or-update")
    public void testGetOrUpdateEmptyServerNodeSequence() {
        List<String> users = new ArrayList<>();
        for(int i = 0; i < 500; i++) {
            users.add("u" + i);
        }
        users.parallelStream()
                .forEach(e -> serverNodeSequenceService.getOrUpdateEmptyServerNodeSequence(e));
    }

}
