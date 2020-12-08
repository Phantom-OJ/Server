package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.RecordDetail;
import sustech.edu.phantom.dboj.service.RecordService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class QueryController {
    @Autowired
    RecordService recordService;

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
            res = ResponseMsg.BAD_REQUEST;
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<RecordDetail>builder().msg(res.getMsg()).data(record).build(), res.getStatus());
    }
}
