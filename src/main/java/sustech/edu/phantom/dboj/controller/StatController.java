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
import sustech.edu.phantom.dboj.form.stat.ProblemStatSet;
import sustech.edu.phantom.dboj.service.StatService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/7 16:41
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class StatController {
    @Autowired
    StatService statService;

    /**
     * 个人信息界面overview
     * @param request http request
     * @param id string id
     * @return 结果集
     */
    @RequestMapping(value = "/user/{id}/statistics")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<ProblemStatSet>> getOneUserStatistics(HttpServletRequest request, @PathVariable String id) {
        ResponseMsg res;
        ProblemStatSet p = null;
        try {
            int idx = Integer.parseInt(id);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getId() != idx) {
                res = ResponseMsg.FORBIDDEN;
            } else {
                p = statService.getOneUserStat(idx);
                if (p == null) {
                    res = ResponseMsg.INTERNAL_SERVER_ERROR;
                } else {
                    res = ResponseMsg.OK;
                }
            }
        } catch (NumberFormatException e) {
            log.error("Wrong URL visiting from " + request.getRemoteAddr());
            res = ResponseMsg.NOT_FOUND;
        } catch (Exception e) {
            log.error("Internal server error visiting from " + request.getRemoteAddr());
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<ProblemStatSet>builder().msg(res.getMsg()).data(p).build(), res.getStatus());
    }


    /**
     * 一个Problem的所有数据
     *
     * @param id Problem id
     * @return ProblemStatisticsSet对象，包含result结果和语言结果
     */
    @RequestMapping(value = "/problem/{id}/statistics/", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<ProblemStatSet>> getOneProblemStatistics(HttpServletRequest request, @PathVariable String id) {
        ResponseMsg res;
        ProblemStatSet p = null;
        try {
            int idx = Integer.parseInt(id);
            p = statService.getOneProblemStat(idx);
            if (p == null) {
                res = ResponseMsg.NOT_FOUND;
            } else {
                res = ResponseMsg.OK;
            }
        } catch (NumberFormatException e) {
            log.error("Wrong URL visiting from " + request.getRemoteAddr());
            res = ResponseMsg.NOT_FOUND;
        } catch (Exception e) {
            log.error("Internal server error visiting from " + request.getRemoteAddr());
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<ProblemStatSet>builder().msg(res.getMsg()).data(p).build(), res.getStatus());
    }

}
