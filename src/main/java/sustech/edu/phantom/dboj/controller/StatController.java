package sustech.edu.phantom.dboj.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.UserGrade;
import sustech.edu.phantom.dboj.form.stat.AssignmentScore;
import sustech.edu.phantom.dboj.form.stat.AssignmentStat;
import sustech.edu.phantom.dboj.form.stat.HomeStat;
import sustech.edu.phantom.dboj.form.stat.ProblemStatSet;
import sustech.edu.phantom.dboj.service.SchedulingService;
import sustech.edu.phantom.dboj.service.StatService;
import sustech.edu.phantom.dboj.utils.PreLoadUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/7 16:41
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
@Api(tags = {"All statistics fetch"})
public class StatController {
    @Autowired
    StatService statService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    SchedulingService schedulingService;

    /**
     * 个人信息界面overview
     * 这里不需要权限，因为别人也可以随时看
     *
     * @param request http request
     * @param id      string id
     * @return 结果集
     */
    @ApiOperation("获取用户数据信息")
    @RequestMapping(value = "/user/{id}/statistics", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<ProblemStatSet>> getOneUserStatistics(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "问题id", required = true, type = "int") String id) {
        ResponseMsg res;
        ProblemStatSet p = null;
        try {
            int idx = Integer.parseInt(id);
            p = statService.getOneUserStat(idx);
            if (p == null) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
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


    /**
     * 一个Problem的所有数据
     * 这里也不需要权限
     *
     * @param id Problem id
     * @return ProblemStatisticsSet对象，包含result结果和语言结果
     */
    @ApiOperation("获取问题数据信息")
    @RequestMapping(value = "/problem/{id}/statistics", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<ProblemStatSet>> getOneProblemStatistics(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "问题id", required = true, type = "int") String id) {
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

    @ApiOperation("获取用户得分")
    @RequestMapping(value = "/user/{id}/grade", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<UserGrade>>> getUserGrade(
            HttpServletRequest request,
            @PathVariable
            @ApiParam(name = "用户id", required = true, type = "java.lang.Integer") Integer id) {
        ResponseMsg res;
        List<UserGrade> userGrades = null;
        try {
            userGrades = statService.getUserGrade(id);
            res = ResponseMsg.OK;
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
            log.error("Some errors happens in the internal server to the request " + request.getRemoteAddr());
        }
        return new ResponseEntity<>(GlobalResponse.<List<UserGrade>>builder().msg(res.getMsg()).data(userGrades).build(), res.getStatus());
    }

    @ApiOperation("作业的数据")
    @RequestMapping(value = "/assignment/{id}/statistics", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<List<AssignmentStat>>> getAssignmentStat(
            HttpServletRequest request,
            @PathVariable
            @ApiParam(name = "作业id", required = true, type = "java.lang.Integer") Integer id) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AssignmentStat> assignmentStats = null;
        if (!user.containPermission(PermissionEnum.VIEW_SUBMISSIONS)) {
            res = ResponseMsg.FORBIDDEN;
            log.error("Low privilege from the request " + request.getRemoteAddr());
        } else {
            try {
                assignmentStats = statService.getOneAssignmentStat(id);
                res = ResponseMsg.OK;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal errors when getting assignment statistics from " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<List<AssignmentStat>>builder().msg(res.getMsg()).data(assignmentStats).build(), res.getStatus());
    }

    @ApiOperation("获取主页数据")
    @RequestMapping(value = "/home/statistics", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<HomeStat>>> getHomeStat(HttpServletRequest request) {
        ResponseMsg res;
        List<HomeStat> homeStats = null;
        String s;
        try {
            s = (String) redisTemplate.opsForValue().get(PreLoadUtil.homeStatistics);
            homeStats = Arrays.asList(new Gson().fromJson(s, HomeStat[].class).clone());
            res = ResponseMsg.OK;
            log.info("success getting info from " + request.getRemoteAddr());
        } catch (Exception e) {
//            res = ResponseMsg.INTERNAL_SERVER_ERROR;
//            log.error("Internal server error happens in getting statistics.");
            homeStats = schedulingService.getHomeStat();
            redisTemplate.opsForValue().set(PreLoadUtil.homeStatistics, new Gson().toJson(homeStats), 1, TimeUnit.DAYS);
            res = ResponseMsg.OK;
        }
        return new ResponseEntity<>(GlobalResponse.<List<HomeStat>>builder().msg(res.getMsg()).data(homeStats).build(), res.getStatus());
    }

    @ApiOperation("获取一个作业中所有用户得分")
    @RequestMapping(value = "/assignment/{id}/scores", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<List<AssignmentScore>>> getOneAssignmentScores(HttpServletRequest request, @PathVariable Integer id) {
        ResponseMsg res;
        List<AssignmentScore> assignmentScores = null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_SUBMISSIONS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                assignmentScores = statService.getOneAssignmentScore(id);
                res = ResponseMsg.OK;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error(Arrays.toString(e.getStackTrace()));
            }
        }
        return new ResponseEntity<>(GlobalResponse.<List<AssignmentScore>>builder().msg(res.getMsg()).data(assignmentScores).build(), res.getStatus());
    }
}
