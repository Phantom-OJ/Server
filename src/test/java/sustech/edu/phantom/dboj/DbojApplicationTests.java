package sustech.edu.phantom.dboj;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sustech.edu.phantom.dboj.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("sustech.edu.phantom.dboj")
class DbojApplicationTests {

    @Autowired
    UserService userService;
}
