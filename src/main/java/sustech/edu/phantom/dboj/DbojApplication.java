package sustech.edu.phantom.dboj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
@MapperScan("sustech.edu.phantom.dboj.mapper")
public class DbojApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbojApplication.class, args);
    }

}
