package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.Assignment;
import sustech.edu.phantom.dboj.entity.po.Group;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.RecordDetail;
import sustech.edu.phantom.dboj.service.AssignmentService;
import sustech.edu.phantom.dboj.service.ModificationService;
import sustech.edu.phantom.dboj.service.RecordService;
import sustech.edu.phantom.dboj.service.UserService;

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
@Api(tags = {"Query functions"})
public class QueryController {
    @Autowired
    RecordService recordService;

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    ModificationService modificationService;


    /**
     * 具体的查询某个record
     * 需要权限认证
     *
     * @param id record id
     * @return 查询的record的类
     */
    @ApiOperation("获取具体的record记录")
    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<RecordDetail>> getOneRecord(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "记录id", required = true, type = "int") Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User " + user.getUsername() + " from " + request.getRemoteAddr() + " wants to fetch the code " + id);
        ResponseMsg res;
        RecordDetail record = null;
        try {
            boolean isAdmin = user.containPermission(PermissionEnum.VIEW_CODES);
            record = recordService.getOneRecord(id, user.getId(), isAdmin);
            if (record == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                res = ResponseMsg.OK;
            }
        } catch (Exception e) {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<RecordDetail>builder().msg(res.getMsg()).data(record).build(), res.getStatus());
    }

    /**
     * 获取用户信息
     * <br></br>这里包含查看自己的和查看别人的
     * <br></br>查看别人的隐藏了重要信息
     *
     * @param id 用户的id
     * @return 返回用户信息
     */
    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<User>> userInfo(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "作业id", required = true, type = "int") String id) {
        ResponseMsg res;
        User data = null;
        int idx;
        try {
            idx = Integer.parseInt(id);
            log.debug("请求的user id是" + id);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.debug("user的id是" + id);
            if (idx == user.getId() || user.containPermission(PermissionEnum.GRANT)) {
                data = user;
                res = ResponseMsg.OK;
            } else {
                log.info("Here shows the basic information of the user " + id + " from the visiting of " + request.getRemoteAddr());
                data = userService.find(idx, false);
                log.debug("这里的user是" + data);
                if (data == null) {
                    log.error("Not exist user " + id + " from the request of " + request.getRemoteAddr());
                    res = ResponseMsg.NOT_FOUND;
                } else {
                    log.info("Successfully view the basic information of " + id + " from the request of " + request.getRemoteAddr());
                    res = ResponseMsg.OK;
                }
            }
        } catch (NumberFormatException e) {
            log.error("Wrong URL visiting from " + request.getRemoteAddr());
            res = ResponseMsg.NOT_FOUND;
        } catch (ClassCastException e) {
            idx = Integer.parseInt(id);
            log.info("Here shows the basic information of the user " + id + " from the visiting of " + request.getRemoteAddr());
            data = userService.find(idx, false);
            if (data == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                res = ResponseMsg.OK;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<User>builder().data(data).msg(res.getMsg()).build(), res.getStatus());
    }

    /**
     * 得到具体的assignment<br></br>
     * 包含problem, tags<br></br>
     * 如果有登录用户, 则显示是否AC
     *
     * @param id assignment id
     * @return assignment的对象
     */
    @ApiOperation("获取具体的作业信息")
    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Assignment>> getOneAssignment(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "作业id", required = true, type = "int") Integer id) {
        ResponseMsg res;
        Assignment assignment = null;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean isAdmin = user.containPermission(PermissionEnum.VIEW_ASSIGNMENTS);
            Assignment a = assignmentService.getOneAssignment(id, true, user.getId(), isAdmin, user.getGroupList().stream().map(Group::getId).collect(Collectors.toList()));
            if (a == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                res = ResponseMsg.OK;
                assignment = a;
            }
        } catch (ClassCastException e) {
            try {
                List<Integer> i = new ArrayList<>();
                i.add(1);
                Assignment a = assignmentService.getOneAssignment(id, false, 0, false, i);
                if (a == null) {
                    res = ResponseMsg.NOT_FOUND;
                } else {
                    res = ResponseMsg.OK;
                    assignment = a;
                }
            } catch (Exception e1) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("The visit from the " + request.getRemoteAddr() + " has internal server error.");
            }
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
            log.error("The visit from the " + request.getRemoteAddr() + " has internal server error.");
        }
        return new ResponseEntity<>(GlobalResponse.<Assignment>builder().msg(res.getMsg()).data(assignment).build(), res.getStatus());
    }
//
//    @ApiOperation("获取一个问题所有的判题点")
//    @RequestMapping(value = "/judgepoint/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_STUDENT')")
//    public ResponseEntity<GlobalResponse<List<JudgePoint>>> getOneProblemJudgePoint(
//            HttpServletRequest request,
//            @PathVariable @ApiParam(name = "问题id") Integer id){
//        ResponseMsg res;
//        List<JudgePoint> judgePoints = null;
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
//            try {
//                judgePoints = modificationService.getOneProblemJudgePoint(id);
//                res = ResponseMsg.OK;
//            } catch (Exception e) {
//                res = ResponseMsg.INTERNAL_SERVER_ERROR;
//            }
//        } else {
//            res = ResponseMsg.FORBIDDEN;
//        }
//        return new ResponseEntity<>(GlobalResponse.<List<JudgePoint>>builder().msg(res.getMsg()).data(judgePoints).build(), res.getStatus());
//    }
}
