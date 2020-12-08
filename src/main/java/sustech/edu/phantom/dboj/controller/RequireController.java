package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Group;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.service.GroupService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/require")
@Slf4j
public class RequireController {

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<Group>>> getGroups() {
        ResponseMsg msg;
        List<Group> data = null;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.containPermission(PermissionEnum.VIEW_GROUPS)) {
                try {
                    data = groupService.getAllGroups();
                    msg = ResponseMsg.OK;
                } catch (Exception e) {
                    //TODO:log报错信息
                    log.error("");
                    msg = ResponseMsg.INTERNAL_SERVER_ERROR;
                }
            } else {
                msg = ResponseMsg.FORBIDDEN;
            }
        } catch (ClassCastException e) {
            //TODO:log.error
            msg = ResponseMsg.UNAUTHORIZED;
        }
        return new ResponseEntity<>(GlobalResponse.<List<Group>>builder().data(data).msg(msg.getMsg()).build(), msg.getStatus());
    }
}
