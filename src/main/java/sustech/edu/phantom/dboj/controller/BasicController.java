package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.service.AnnouncementService;
import sustech.edu.phantom.dboj.service.BasicService;
import sustech.edu.phantom.dboj.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping(value = "/api")
public class BasicController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    UserService userService;

    @Autowired
    BasicService basicService;

    /**
     * return the state of the user
     * @param request http request
     * @return 统一的包含user的回应
     */
    @RequestMapping(value = "/checkstate", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<User>> checkState(HttpServletRequest request){
        User user = null;
        ResponseMsg res;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setState(basicService.getLastState(user.getId()));
            res = ResponseMsg.OK;
        } catch (ClassCastException e) {
            log.error("The request from " + request.getRemoteAddr() + " client has not been logged in.");
            res = ResponseMsg.UNAUTHORIZED;
        }
        return new ResponseEntity<>(GlobalResponse.<User>builder().msg(res.getMsg()).data(user).build(), res.getStatus());
    }

    /**
     * 所有的公告，是可以对所有人public的，没有权限限制
     *
     * @param pagination 前端传回的分页筛选信息 这个类有待完善
     * @return 公告list 这里就不设置 /announcement/{id} 这种api了，直接缓存
     */
    @RequestMapping(value = "/announcement", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<EntityVO<Announcement>>> getAnnouncement(HttpServletRequest request, @RequestBody Pagination pagination) {
        ResponseMsg res;
        EntityVO<Announcement> list = null;
        try {
            log.info("进入公告页面");
            list = announcementService.announcementEntityVO(pagination);
            res = ResponseMsg.OK;
        } catch (Exception e) {
            res = ResponseMsg.NOT_FOUND;
            log.error("Internal server error, sent to " + request.getRemoteAddr());
        }
        return new ResponseEntity<>(GlobalResponse.<EntityVO<Announcement>>builder()
                .msg(res.getMsg())
                .data(list)
                .build(), res.getStatus());
    }


    /**
     * 获取用户信息
     * 这里包含查看自己的和查看别人的
     * 查看别人的隐藏了重要信息
     *
     * @param id 用户的id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<User>> userInfo(HttpServletRequest request, @PathVariable String id) {
        ResponseMsg res;
        User data = null;
        int idx;
        try {
            idx = Integer.parseInt(id);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (idx == user.getId()) {
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
            data = userService.find(idx,false);
            if (data == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                res = ResponseMsg.OK;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<User>builder().data(data).msg(res.getMsg()).build(), res.getStatus());
    }

}
