package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.RecordDetail;
import sustech.edu.phantom.dboj.service.AssignmentService;
import sustech.edu.phantom.dboj.service.RecordService;
import sustech.edu.phantom.dboj.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@Api(tags = "Query functions")
public class QueryController {
    @Autowired
    RecordService recordService;

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    /**
     * 具体的查询某个record
     * 需要权限认证
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
            log.error("The request URL from " + request.getRemoteAddr() + " has wrong format.");
            res = ResponseMsg.BAD_REQUEST;
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
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
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<User>> userInfo(HttpServletRequest request, @PathVariable String id) {
        ResponseMsg res;
        User data = null;
        int idx;
        try {
            idx = Integer.parseInt(id);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (idx == user.getId() || user.containPermission(PermissionEnum.GRANT)) {
                data = user;
                res = ResponseMsg.OK;
            } else {
                log.info("Here shows the basic information of the user " + id + " from the visiting of " + request.getRemoteAddr());
                data = userService.find(idx, false);
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
    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Assignment>> getOneAssignment(HttpServletRequest request, @PathVariable String id) {
        int idx;
        ResponseMsg res;
        Assignment assignment = null;
        try {
            idx = Integer.parseInt(id);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean isAdmin = user.containPermission(PermissionEnum.VIEW_ASSIGNMENTS);
            Assignment a = assignmentService.getOneAssignment(idx, true, user.getId(), isAdmin);
            if (a == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                res = ResponseMsg.OK;
                assignment = a;
            }
        } catch (NumberFormatException e) {
            res = ResponseMsg.BAD_REQUEST;
            log.error("The visit from the " + request.getRemoteAddr() + " has wrong URL.");
        } catch (ClassCastException e) {
            idx = Integer.parseInt(id);
            try {
                Assignment a = assignmentService.getOneAssignment(idx, false, 0, false);
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
}
