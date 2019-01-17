package yu.idgen.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yu.idgen.service.api.IdService;

import java.util.List;

/**
 * Created by zsp on 2019/1/17.
 */
@RestController
public class IdFacadeImpl {

    @Autowired
    private IdService idService;

    @PostMapping("/id/generate")
    public Long generate() {
        return idService.generateId();
    }

    @PostMapping("/id/generate/more")
    public List<Long> generateMore(@RequestBody Integer size) {
        if(size == null) {
            return null;
        }
        return idService.generateId(size);
    }

}
