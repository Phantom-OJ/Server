package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.service.JudgeService;

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

}
