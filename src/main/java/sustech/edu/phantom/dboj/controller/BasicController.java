package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.service.AnnouncementService;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping(value = "/api")
public class BasicController {

    @Autowired
    AnnouncementService announcementService;

    @RequestMapping(value = "/checkstate", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<User>> checkState(HttpServletRequest request){
        User user = null;
        ResponseMsg res;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
}
