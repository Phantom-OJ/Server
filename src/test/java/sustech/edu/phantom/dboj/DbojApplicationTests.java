package sustech.edu.phantom.dboj;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import sustech.edu.phantom.dboj.controller.FileController;
import sustech.edu.phantom.dboj.entity.po.Grade;
import sustech.edu.phantom.dboj.form.home.CodeForm;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.form.search.GroupRoleForm;
import sustech.edu.phantom.dboj.mapper.GradeMapper;
import sustech.edu.phantom.dboj.mapper.RecordMapper;
import sustech.edu.phantom.dboj.mapper.UserMapper;
import sustech.edu.phantom.dboj.service.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("sustech.edu.phantom.dboj")
public class DbojApplicationTests {
    //    @Autowired
//    TestService testService;
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    JudgeService judgeService;

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    TagService tagService;

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FileController fileController;
    @Autowired
    RecordMapper recordMapper;

    @Test

    public void testGradeMapper() {
        Grade grade = Grade.builder().userId(1).problemId(1).score(100).build();
        gradeMapper.saveOneGrade(grade);
        Grade grade1 = gradeMapper.getOneGrade(1, 1);
        System.out.println("查询结果" + grade1.toString());
        //grade1.setScore(3);
        gradeMapper.updateOneGrade(1, 1, 123);
    }


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
//        String json = gson.toJson(assignmentService.getAssignmentList(pagination));
//        System.out.println(json);
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
//        System.out.println(assignmentService.getAssignmentList(pagination));
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

    @Test
    public void test6() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
        BCrypt bCrypt = new BCrypt();

        System.out.println(encoder.matches("123456", "$2a$10$I/1HP/qBQlpI/A.UGEwwA.2NI04JOZ97Wya1HHT4mHA4SiqYK6h2m"));
        System.out.println(encoder.encode("123456"));
        System.out.println(encoder.encode("123456"));
        System.out.println(encoder.encode("123456"));
        System.out.println(encoder.encode("123456"));
    }


    static String answer = "SELECT title, country, year_released FROM movies WHERE country <>'us' AND year_released = 1991 AND title LIKE 'The%'";

    @Test
    public void judgeCode() {
        CodeForm codeForm = new CodeForm();
        codeForm.setCode(answer);
        codeForm.setDialect("pgsql");
        codeForm.setSubmitTime(12345L);
        System.out.println(judgeService);
    }

    @Test
    public void getAnn() {
        Pagination pagination = new Pagination();
        pagination.setStart(1);
        pagination.setEnd(9);
        pagination.setFilter(new HashMap<String, Object>());
        pagination.setParameters();
        System.out.println(announcementService.announcementEntityVO(pagination));
//        ResponseEntity<>(GlobalResponse.<EntityVO<Announcement>>builder()
//                .msg("Success")
//                .data(announcementService.announcementEntityVO(pagination))
//                .build(), HttpStatus.OK);
    }

    @Test
    public void test1111() {
//        System.out.println(fileController.getOfficeHome());
//        assert recordMapper.getProblemResultSet(5) != null;
//        assert userMapper.findUserById(66)== null;
//        System.out.println(recordMapper.getProblemResultSet(5));
        System.out.println(userMapper.findUserByFilterMixed(GroupRoleForm.builder().group(0).notGroup(1).notRole("").role("").username("").build()));
    }
}
