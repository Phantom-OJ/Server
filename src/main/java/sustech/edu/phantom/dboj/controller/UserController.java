package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.Code;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.entity.vo.RecordDetail;
import sustech.edu.phantom.dboj.form.CodeForm;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.service.*;

import javax.servlet.http.HttpServletRequest;

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
     * 得到具体的assignment
     *
     * @param id assignment id
     * @return assignment的对象
     */
    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Assignment>> getOneAssignment(HttpServletRequest request, @PathVariable String id) {
        int idx;
        ResponseMsg res;
        Assignment assignment = null;
        try {
            idx = Integer.parseInt(id);
            Assignment a = assignmentService.getOneAssignment(idx);
            if (a == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                res = ResponseMsg.OK;
                assignment = a;
            }
        } catch (NumberFormatException e) {
            res = ResponseMsg.BAD_REQUEST;
            log.error("The visit from the " + request.getRemoteAddr() + " has wrong URL.");
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
            log.error("The visit from the " + request.getRemoteAddr() + " has internal server error.");
        }
        return new ResponseEntity<>(GlobalResponse.<Assignment>builder().msg(res.getMsg()).data(assignment).build(), res.getStatus());
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

//        try {
        judgeService.judgeCode(id, codeForm, 1);
        return true;
//        } catch (Exception e) {
//            return false;
////            throw new Exception("You have not signed in.");
//        }
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
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<RecordDetail>> getOneRecord(HttpServletRequest request, @PathVariable String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User " + user.getUsername() + " from " + request.getRemoteAddr() + " wants to fetch the code " + id);
        int idx;
        ResponseMsg res;
        RecordDetail record = null;
        try {
            idx = Integer.parseInt(id);
            record = recordService.getOneRecord(idx, user.getId());
            if (record == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                res = ResponseMsg.OK;
            }
        } catch (NumberFormatException e) {
            res = ResponseMsg.BAD_REQUEST;
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<RecordDetail>builder().msg(res.getMsg()).data(record).build(), res.getStatus());
    }

    /**
     * 针对某个code id 进行详细查询
     * 最低需要student 的权限
     * 是否是该student的代码在方法中实现
     *
     * @param id code id
     * @return Code对象
     */
    @RequestMapping(value = "/code/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<Code>> getOneCode(@PathVariable String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int idx;
        ResponseMsg res;
        Code code = null;
        try {
            idx = Integer.parseInt(id);
            Code c = codeService.queryCode(idx);
            if (c == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                if (!user.getId().equals(recordService.getUserIdByCodeId(idx))) {
                    res = ResponseMsg.FORBIDDEN;
                } else {
                    code = c;
                    res = ResponseMsg.OK;
                }
            }
        } catch (NumberFormatException e) {
            res = ResponseMsg.BAD_REQUEST;
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<Code>builder()
                .msg(res.getMsg())
                .data(code)
                .build(),
                res.getStatus());
    }
}