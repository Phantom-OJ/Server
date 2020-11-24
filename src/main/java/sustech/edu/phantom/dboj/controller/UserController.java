package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.*;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.entity.vo.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.RecordDetail;
import sustech.edu.phantom.dboj.form.CodeForm;
import sustech.edu.phantom.dboj.form.LoginForm;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.form.RegisterForm;
import sustech.edu.phantom.dboj.form.stat.ProblemStatSet;
import sustech.edu.phantom.dboj.service.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lori
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ProblemService problemService;
    @Autowired
    AssignmentService assignmentService;
    @Autowired
    RecordService recordService;
    @Autowired
    AnnouncementService announcementService;
    @Autowired
    CodeService codeService;
    @Autowired
    JudgeService judgeService;

    /**
     * 注册api 还没写好
     *
     * @param registerForm 注册表单
     * @return 刚注册的user对象
     */
//    @RequestMapping(value = "/signup", method = RequestMethod.POST)
//    public User signup(@RequestBody RegisterForm registerForm) throws Exception {
//        return userService.register(registerForm);
//    }

//    /**
//     * 登录api，还没写好
//     *
//     * @param loginForm 登录表单
//     * @return 登录的user对象
//     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public UserDetails login(@RequestBody LoginForm loginForm) {
//        return userService.loadUserByUsername(loginForm.getUsername());
//    }

    /**
     * 所有的公告，是可以对所有人public的，没有权限限制
     *
     * @param pagination 前端传回的分页筛选信息 这个类有待完善
     * @return 公告list 这里就不设置 /announcement/{id} 这种api了，直接缓存
     */
    @RequestMapping(value = "/announcement", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<EntityVO<Announcement>>> getAnnouncement(@RequestBody Pagination pagination) {
        log.info("进入公告页面");
        return new ResponseEntity<>(GlobalResponse.<EntityVO<Announcement>>builder()
                .msg("Success")
                .data(announcementService.announcementEntityVO(pagination))
                .build(), HttpStatus.OK);
    }

    /**
     * 所有的problem，是public的，没有权限控制
     * problem id, problem name, problem tag
     *
     * @param pagination 前端传回的分页筛选信息 这个类有待完善
     * @return list of problems
     */
    @RequestMapping(value = "/problem", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<EntityVO<Problem>>> getProblemList(@RequestBody Pagination pagination) {
        try {
            EntityVO<Problem> entityVO = problemService.problemEntityVO(pagination);
            return new ResponseEntity<>(GlobalResponse.<EntityVO<Problem>>builder()
                    .msg("sucess")
                    .data(problemService.problemEntityVO(pagination))
                    .build(), HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>
                    (GlobalResponse.<EntityVO<Problem>>builder()
                            .msg("Number format exception.")
                            .build(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 根据id查询具体的problem，这里存在权限控制，因为有recentCode
     *
     * @param id problem id
     * @return 查询的problem的对象
     */
    @RequestMapping(value = "/problem/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Problem>> getOneProblem(@PathVariable int id, @AuthenticationPrincipal User user) {
        try {
            Problem p = problemService.getOneProblem(id, user.getId());
            return new ResponseEntity<>(GlobalResponse.<Problem>builder().msg("success").data(p).build(), HttpStatus.OK);
        } catch (NullPointerException e) {
            Problem p = problemService.getOneProblem(id);
            return new ResponseEntity<>(GlobalResponse.<Problem>builder().msg("success").data(p).build(), HttpStatus.OK);
        }
    }

    /**
     * 得到assignment的list
     *
     * @param pagination 前端传回的分页筛选信息 这个类有待完善
     * @return list of assignments
     */
    @RequestMapping(value = "/assignment", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<EntityVO<Assignment>>> getAllAssignments(@RequestBody Pagination pagination) {
        try {
            EntityVO<Assignment> a = assignmentService.assignmentEntityVO(pagination);
            return new ResponseEntity<>(GlobalResponse.<EntityVO<Assignment>>builder().msg("success").data(a).build(), HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(GlobalResponse.<EntityVO<Assignment>>builder().msg("Number Format error.").build(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 得到具体的assignment
     *
     * @param id assignment id
     * @return assignment的对象
     */
    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Assignment>> getOneAssignment(@PathVariable int id) {
        Assignment a = assignmentService.getOneAssignment(id);
        if (a == null) {
            return new ResponseEntity<>(GlobalResponse.<Assignment>builder().msg("No such assignment.").build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(GlobalResponse.<Assignment>builder().msg("success").data(a).build(), HttpStatus.OK);
    }

    /**
     * 提交代码api，这里需要权限认证
     *
     * @param id       problem id
     * @param codeForm 提交code的表单
     * @return 这份代码提交的record, 还未实现
     */
    @RequestMapping(value = "/problem/{id}/submit", method = RequestMethod.POST)
    public Boolean submitCode(@PathVariable int id, @RequestBody CodeForm codeForm/*, @AuthenticationPrincipal User user*/) throws Exception {
        //这个方法要用到消息队列

        try {
            judgeService.judgeCode(id, codeForm, 1);
            return true;
        } catch (NullPointerException e) {
            throw new Exception("You have not signed in.");
        }
    }

    /**
     * 所有record记录
     * assignment, problem title, username/nickname
     *
     * @param pagination 前端传回的分页筛选信息 这个类有待完善
     * @return list of records
     */
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<EntityVO<RecordDetail>>> getRecords(@RequestBody Pagination pagination) {
//        try {
        return new ResponseEntity<>(GlobalResponse.<EntityVO<RecordDetail>>builder()
                .data(recordService.getRecordDetailList(pagination))
                .msg("success")
                .build(),
                HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("error is {}", e.getStackTrace());
//            return new ResponseEntity<>(GlobalResponse.<EntityVO<RecordDetail>>builder()
//                    .msg("Internal server error.")
//                    .build(),
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    /**
     * 具体的查询某个record，需要权限认证
     *
     * @param id record id
     * @return 查询的record的类
     */
    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<RecordDetail>> getOneRecord(@PathVariable int id, @AuthenticationPrincipal User user) {
        RecordDetail record = null;
        try {
            record = recordService.getOneRecord(id, user.getId());
            log.info("The permission is {}", user.getPermissionList());
            return new ResponseEntity<>(GlobalResponse.<RecordDetail>builder()
                    .msg("success")
                    .data(record)
                    .build(),
                    HttpStatus.OK);
        } catch (NullPointerException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            log.error("Error: {}", (Object) e.getStackTrace());
            return new ResponseEntity<>(GlobalResponse.<RecordDetail>builder()
                    .msg("you have not signed in.")
                    .build(),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 一个Problem的所有数据
     *
     * @param id Problem id
     * @return ProblemStatisticsSet对象，包含result结果和语言结果
     */
    @RequestMapping(value = "/problem/{id}/statistics/", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<ProblemStatSet>> getOneProblemStatistics(@PathVariable int id) {
        try {
            return new ResponseEntity<>(GlobalResponse.<ProblemStatSet>builder().msg("success").data(recordService.getOneProblemStat(id)).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(GlobalResponse.<ProblemStatSet>builder().msg("error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/code/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Code>> getOneCode(@PathVariable int id, @AuthenticationPrincipal User user) {
        log.info("user information is {}", user);
        if (user == null) {
            return new ResponseEntity<>(GlobalResponse.<Code>builder()
                    .msg("you have not signed in.")
                    .build(),
                    HttpStatus.UNAUTHORIZED);
        }
        Code c = codeService.queryCode(id);
        if (!user.getId().equals(recordService.getUserIdByCodeId(id))) {
            return new ResponseEntity<>(GlobalResponse.<Code>builder()
                    .msg("you have not such authorizations.")
                    .build(),
                    HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(GlobalResponse.<Code>builder()
                .msg("success")
                .data(c)
                .build(),
                HttpStatus.OK);
    }
}