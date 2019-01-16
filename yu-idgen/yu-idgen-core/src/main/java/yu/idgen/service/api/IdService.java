package yu.idgen.service.api;

import java.util.List;

/**
 * Created by zsp on 2018/12/4.
 */
public interface IdService {

    Long generateId();

    List<Long> generateId(int size);

}
