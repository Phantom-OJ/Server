package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.basicJudge.PollingMessage;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.Code;
import sustech.edu.phantom.dboj.entity.po.Group;
import sustech.edu.phantom.dboj.entity.po.Problem;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.home.CodeForm;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;
import sustech.edu.phantom.dboj.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    ProblemMapper problemMapper;

    @ApiOperation("重新判题")
    @RequestMapping(value = "/rejudge/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> rejudge(
            HttpServletRequest request,
            @PathVariable Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        if (!user.containPermission(PermissionEnum.REJUDGE)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                judgeService.rejudge(id);
                res = ResponseMsg.OK;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    /**
     * 根据id查询具体的problem
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
            p = problemService.getOneProblem(id, user.getId(), isAdmin, user.getGroupList().stream().map(Group::getId).collect(Collectors.toList()));
            res = ResponseMsg.OK;
        } catch (ClassCastException e) {
            try {
                List<Integer> i = new ArrayList<>();
                i.add(1);
                p = problemService.getOneProblem(id, false,i);
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
        long currentTime = System.currentTimeMillis();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long endTime = problemService.getSimpleProblem(id);
        boolean flag = user.containPermission(PermissionEnum.TEST_CODE);
        List<Integer> tmp = problemMapper.problemGroups(id);
        List<Integer> tmp2 = user.getGroupList().stream().map(Group::getId).collect(Collectors.toList());
        tmp2.retainAll(tmp);
        if (tmp2.size() == 0) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").build(), HttpStatus.FORBIDDEN);
        }
        //管理員或在結束之前可以提交
        ResponseMsg res;
        Integer codeId = 0;
        if (flag || currentTime < endTime) {
            codeId = judgeService.judgeCode(id, codeForm, user.getId());
            res = ResponseMsg.OK;
        } else {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(String.valueOf(codeId)).build(), res.getStatus());

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