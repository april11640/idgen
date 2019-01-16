package yu.idgen.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zsp on 2018/12/4.
 */
@SpringBootApplication(scanBasePackages = {"yu.idgen"})
@MapperScan(basePackages = {"yu.idgen.dao.api"})
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
