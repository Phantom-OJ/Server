package sustech.edu.phantom.dboj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sustech.edu.phantom.dboj.form.CodeForm;
import sustech.edu.phantom.dboj.service.UserService;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("sustech.edu.phantom.dboj")
public class DbojApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void test1() {
        CodeForm codeForm = CodeForm.builder().code("ssss").submitTime(new Timestamp(7892749L)).build();
        System.out.println(codeForm.getSubmitTime());
    }
}
