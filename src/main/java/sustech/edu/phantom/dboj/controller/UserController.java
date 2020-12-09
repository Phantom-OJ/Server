package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.Code;
import sustech.edu.phantom.dboj.entity.po.Problem;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.home.CodeForm;
import sustech.edu.phantom.dboj.service.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lori
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
@Api(tags = "user basic operations")
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
    @ApiOperation("获取具体问题")
    @RequestMapping(value = "/problem/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Problem>> getOneProblem(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "问题id", required = true, type = "int") String id) {
        User user;
        Problem p = null;
        ResponseMsg res;
        int idx;
        boolean isAdmin = false;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            idx = Integer.parseInt(id);
            isAdmin = user.containPermission(PermissionEnum.VIEW_ASSIGNMENTS);
            p = problemService.getOneProblem(idx, user.getId(), isAdmin);
            res = ResponseMsg.OK;
        } catch (ClassCastException e) {
            try {
                idx = Integer.parseInt(id);
                p = problemService.getOneProblem(idx, false);
                res = ResponseMsg.OK;
            } catch (NumberFormatException e1) {
                log.error("No user signed in and occurs number format exception from " + request.getRemoteAddr());
                res = ResponseMsg.BAD_REQUEST;
            } catch (Exception e2) {
                log.error("No user signed in and occurs internal server error to " + request.getRemoteAddr());
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }

        } catch (NumberFormatException e) {
            res = ResponseMsg.BAD_REQUEST;
            log.error("Bad request from the " + request.getRemoteAddr());
        }

        return new ResponseEntity<>(GlobalResponse.<Problem>builder().msg(res.getMsg()).data(p).build(), res.getStatus());
//        try {
//            Problem p = problemService.getOneProblem(id, user.getId());
//            return new ResponseEntity<>(GlobalResponse.<Problem>builder().msg("success").data(p).build(), HttpStatus.OK);
//        } catch (NullPointerException e) {
//            Problem p = problemService.getOneProblem(id);
//            return new ResponseEntity<>(GlobalResponse.<Problem>builder().msg("success").data(p).build(), HttpStatus.OK);
//        }
    }


    /**
     * 提交代码api，这里需要权限认证
     *
     * @param id       problem id
     * @param codeForm 提交code的表单
     * @return 这份代码提交的record, 还未实现
     */
    @RequestMapping(value = "/problem/{id}/submit", method = RequestMethod.POST)
    @ApiOperation("提交代码")
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
     * 针对某个code id 进行详细查询
     * 最低需要student 的权限
     * 是否是该student的代码在方法中实现//TODO:老师权限
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