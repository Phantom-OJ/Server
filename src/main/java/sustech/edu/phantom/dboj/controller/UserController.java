package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.*;
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
    public List<Announcement> getAnnouncement(@RequestBody Pagination pagination) {
        return announcementService.getAnnouncementList(pagination);
    }

    /**
     * 所有的problem，是public的，没有权限控制
     * problem id, problem name, problem tag
     *
     * @param pagination 前端传回的分页筛选信息 这个类有待完善
     * @return list of problems
     */
    @RequestMapping(value = "/problem", method = RequestMethod.POST)
    public List<Problem> getProblemList(@RequestBody Pagination pagination) {
//        List<Problem> p = problemService.getProblemList(pagination);
        System.out.println(pagination);
        return problemService.getProblemList(pagination);
    }

    /**
     * 根据id查询具体的problem，这里存在权限控制，因为有recentCode
     *
     * @param id problem id
     * @return 查询的problem的对象
     */
    @RequestMapping(value = "/problem/{id}", method = RequestMethod.GET)
    public Problem getOneProblem(@PathVariable int id, @AuthenticationPrincipal User user) {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getDetails();
        System.out.println(principle);
//        httpServletResponse.addCookie(new Cookie("Set-Cookie","1111111"));
//        System.out.println(principle);
//        System.out.println(user);
        return user != null ? problemService.getOneProblem(id, user.getId()) : problemService.getOneProblem(id);
    }

    /**
     * 得到assignment的list
     *
     * @param pagination 前端传回的分页筛选信息 这个类有待完善
     * @return list of assignments
     */
    @RequestMapping(value = "/assignment", method = RequestMethod.POST)
    public List<Assignment> getAllAssignments(@RequestBody Pagination pagination) {
        return assignmentService.getAssignmentList(pagination);
    }

    /**
     * 得到具体的assignment
     *
     * @param id assignment id
     * @return assignment的对象
     */
    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET)
    public Assignment getOneAssignment(@PathVariable int id) {
        return assignmentService.getOneAssignment(id);
    }

    /**
     * 提交代码api，这里需要权限认证
     *
     * @param id       problem id
     * @param codeForm 提交code的表单
     * @return 这份代码提交的record, 还未实现
     */
    @RequestMapping(value = "/problem/{id}/submit", method = RequestMethod.POST)
    public Record submitCode(@PathVariable int id, @RequestBody CodeForm codeForm, @AuthenticationPrincipal User user) throws Exception {
        //这个方法要用到消息队列

        try {
            judgeService.judgeCode(id, codeForm, user.getId());
        } catch (NullPointerException e) {
            throw new Exception("You have not signed in.");
        }
        return null;
    }

    /**
     * 所有record记录
     * assignment, problem title, username/nickname
     *
     * @param pagination 前端传回的分页筛选信息 这个类有待完善
     * @return list of records
     */
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public List<Record> getRecords(@RequestBody Pagination pagination) {
        return recordService.getRecordList(pagination);
    }

    /**
     * 具体的查询某个record，需要权限认证
     *
     * @param id record id
     * @return 查询的record的类
     */
    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    public Record getOneRecord(@PathVariable int id, @AuthenticationPrincipal User user) {
        Record record = null;
        try {
            record = recordService.getOneRecord(id, user.getId());
        } catch (NullPointerException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            log.error("Error: {}", (Object) e.getStackTrace());
        }
        return record;
    }

    /**
     * 一个Problem的所有数据
     *
     * @param id Problem id
     * @return ProblemStatisticsSet对象，包含result结果和语言结果
     */
    @RequestMapping(value = "/problem/{id}/statistics/", method = RequestMethod.GET)
    public ProblemStatSet getOneProblemStatistics(@PathVariable int id) {
        return recordService.getOneProblemStat(id);
    }

    @RequestMapping(value = "/code/{id}", method = RequestMethod.GET)
    public Code getOneCode(@PathVariable int id, @AuthenticationPrincipal User user) {
        log.info("user information is {}", user);
        if (user == null) {
            return null;
        }
        return codeService.queryCode(id);
    }
}