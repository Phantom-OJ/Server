package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename JudgeController
 * @date 2020/12/13 03:27
 */
@RestController
@Slf4j
@RequestMapping(value = "/api")
@Api(tags = {"判题"})
public class JudgeController {
    //TODO:rejudge
    @ApiOperation("重新判题")
    @RequestMapping(value = "/rejudge/{id}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> rejudge(
            HttpServletRequest request,
            @PathVariable Integer id) {
        return null;
    }
}
