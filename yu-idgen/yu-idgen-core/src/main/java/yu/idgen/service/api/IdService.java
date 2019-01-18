package yu.idgen.service.api;

import java.util.List;

/**
 * Created by zsp on 2018/12/4.
 */
public interface IdService {

    /**
     * 获取id
     *
     * @return
     */
    Long generateId();

    /**
     * 获取id
     *
     * @param size
     * @return
     */
    List<Long> generateId(int size);

}
