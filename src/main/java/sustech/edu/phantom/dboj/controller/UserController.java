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
import sustech.edu.phantom.dboj.basicJudge.JudgeResultMessage;
import sustech.edu.phantom.dboj.basicJudge.PollingMessage;
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
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:05
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

    //TODO:rejudge
    @ApiOperation("重新判题")
    @RequestMapping(value = "/rejudge/{id}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> rejudge(
            HttpServletRequest request,
            @PathVariable Integer id) {
        judgeService.rejudge(id);
        String responsedata = "Rejudge Success";
        ResponseMsg res = ResponseMsg.OK;
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(responsedata).build(), res.getStatus());
    }

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
            @PathVariable @ApiParam(name = "问题id", required = true, type = "int") Integer id) {
        User user;
        Problem p = null;
        ResponseMsg res;
        boolean isAdmin = false;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isAdmin = user.containPermission(PermissionEnum.VIEW_ASSIGNMENTS);
            p = problemService.getOneProblem(id, user.getId(), isAdmin);
            res = ResponseMsg.OK;
        } catch (ClassCastException e) {
            try {
                p = problemService.getOneProblem(id, isAdmin);
                res = ResponseMsg.OK;
            } catch (Exception e2) {
                log.error("No user signed in and occurs internal server error to " + request.getRemoteAddr());
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }

        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<Problem>builder().msg(res.getMsg()).data(p).build(), res.getStatus());
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
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> submitCode(
            @PathVariable @ApiParam(name = "问题id", required = true, type = "int") Integer id,
            @RequestBody @ApiParam(name = "代码表单", required = true, type = "CodeForm对象") CodeForm codeForm) throws Exception {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (user.isInGroup())
//
        ResponseMsg res = ResponseMsg.OK;
        //这个方法要用到消息队列

//        try {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer codeId = judgeService.judgeCode(id, codeForm, user.getId());
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(String.valueOf(codeId)).build(), res.getStatus());
//        } catch (Exception e) {
//            return false;
////            throw new Exception("You have not signed in.");
//        }
    }


    @RequestMapping(value = "/polling/{recordId}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<PollingMessage>> polling(@PathVariable String recordId) {
        ResponseMsg res = ResponseMsg.OK;
        PollingMessage pollingMessage = judgeService.getJudgeStatus(recordId);
        return new ResponseEntity<>(GlobalResponse.<PollingMessage>builder()
                .msg(res.getMsg())
                .data(pollingMessage)
                .build(),
                res.getStatus());

    }


    /**
     * 针对某个code id 进行详细查询
     * 最低需要student 的权限
     * 是否是该student的代码在方法中实现//TODO:老师权限
     *
     * @param id code id
     * @return Code对象
     */
    @ApiOperation("对具体的code进行查询")
    @RequestMapping(value = "/code/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<Code>> getOneCode(
            HttpServletRequest request,
            @PathVariable
            @ApiParam(name = "代码id", required = true)
                    Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        Code code = null;
        try {
            Code c = codeService.queryCode(id);
            if (c == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                if (user.getId().equals(recordService.getUserIdByCodeId(id))
                        && user.containPermission(PermissionEnum.VIEW_CODES)) {
                    code = c;
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FORBIDDEN;
                }
            }
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