package sustech.edu.phantom.dboj;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sustech.edu.phantom.dboj.form.CodeForm;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.service.AssignmentService;
import sustech.edu.phantom.dboj.service.UserService;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("sustech.edu.phantom.dboj")
public class DbojApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Test
    public void test1() {
//        CodeForm codeForm = CodeForm.builder().code("ssss").submitTime(new Timestamp(7892749L)).build();
//        System.out.println(codeForm.getSubmitTime());
    }
    @Test
    public void test2(){
        Pagination pagination = new Pagination();
        pagination.setStart(1);
        pagination.setEnd(2);
        pagination.setParameters();
        Gson gson = new Gson();
        String json = gson.toJson(assignmentService.getAssignmentList(pagination));
        System.out.println(json);
    }
}
