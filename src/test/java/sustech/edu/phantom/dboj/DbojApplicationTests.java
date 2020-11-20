package sustech.edu.phantom.dboj;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.mapper.GroupMapper;
import sustech.edu.phantom.dboj.mapper.UserMapper;
import sustech.edu.phantom.dboj.service.AssignmentService;
import sustech.edu.phantom.dboj.service.GroupService;
import sustech.edu.phantom.dboj.service.TagService;
import sustech.edu.phantom.dboj.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("sustech.edu.phantom.dboj")
public class DbojApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    TagService tagService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserMapper userMapper;


    @Test
    public void test1() {
//        CodeForm codeForm = CodeForm.builder().code("ssss").submitTime(new Timestamp(7892749L)).build();
//        System.out.println(codeForm.getSubmitTime());
    }

    @Test
    public void test2() {
        Pagination pagination = new Pagination();
        pagination.setStart(1);
        pagination.setEnd(2);
        pagination.setParameters();
        Gson gson = new Gson();
        String json = gson.toJson(assignmentService.getAssignmentList(pagination));
        System.out.println(json);
    }

    @Test
    public void test3() {
        Map<String, Integer> h = new HashMap<>();
        h.put("k1", 1);
        h.put("k2", 2);
        h.put("k3", 3);
        h.put("k4", 4);
        h.put("k5", 5);
//        assert h.equals(tagService.getTagMapper());
    }

    @Test
    public void test4() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
//        System.out.println(groupService.getGroupList(a));
        Pagination pagination = new Pagination();
        pagination.setStart(1);
        pagination.setEnd(4);
        pagination.setParameters();
        System.out.println(assignmentService.getAssignmentList(pagination));
    }

    @Test
    public void Test5() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(userMapper.register(User.builder()
//                .username("11811499@mail.sustech.edu.cn")
//                .password(encoder.encode("11811499"))
//                .build()));
//        System.out.println(userMapper.login(User.builder().username("11811499@mail.sustech.edu.cn").password(encoder.encode("11811499")).build()));
        System.out.println(userService.loadUserByUsername("11811499@mail.sustech.edu.cn"));
    }
}
