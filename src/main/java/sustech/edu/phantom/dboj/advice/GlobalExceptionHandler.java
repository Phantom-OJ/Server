package sustech.edu.phantom.dboj.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename JsonParseExceptionHandling
 * @date 2020/12/10 11:01
 */
@RestControllerAdvice(basePackageClasses = {
        sustech.edu.phantom.dboj.controller.AdvancedModifyInfoController.class,
        sustech.edu.phantom.dboj.controller.RequireController.class,
        sustech.edu.phantom.dboj.controller.HomeController.class,
        sustech.edu.phantom.dboj.controller.AvatarController.class,
        sustech.edu.phantom.dboj.controller.QueryController.class,
        sustech.edu.phantom.dboj.controller.StatController.class,
        sustech.edu.phantom.dboj.controller.UploadController.class,
        sustech.edu.phantom.dboj.controller.UserController.class,
        sustech.edu.phantom.dboj.controller.AccountController.class,
        sustech.edu.phantom.dboj.controller.BeaconController.class})
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理前端传回后端数据反射异常
     *
     * @param request   http 请求
     * @param exception HttpMessageNotReadableException 反射异常
     * @return 全局回应
     * @throws HttpMessageNotReadableException exception
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalResponse<String>> jsonParseHandler(HttpServletRequest request, HttpMessageNotReadableException exception) throws HttpMessageNotReadableException {
        exception.printStackTrace();
        log.error(exception.getMessage());
        log.error("Request from " + request.getRemoteAddr() + " has wrong format of request POST data.");
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(ResponseMsg.PARSE_EXCEPTION.getMsg()).build(), ResponseMsg.PARSE_EXCEPTION.getStatus());
    }

    /**
     * 请求路径中的类型异常处理
     *
     * @param request http请求
     * @param e       异常
     * @return 全局回应
     * @throws MethodArgumentTypeMismatchException 请求路径中的类型异常
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalResponse<String>> pathVarHandler(HttpServletRequest request, MethodArgumentTypeMismatchException e) throws MethodArgumentTypeMismatchException {
        e.printStackTrace();
        log.error(e.getMessage());
        log.error("Request from " + request.getRemoteAddr() + " has wrong format of request path data.");
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(ResponseMsg.PATH_VAR_EXCEPTION.getMsg()).build(), ResponseMsg.PATH_VAR_EXCEPTION.getStatus());
    }

    /**
     * 处理不存在的URL的情况
     *
     * @param request http请求
     * @param e       NoHandlerFoundException
     * @return 404 响应信息
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<GlobalResponse<String>> error404(HttpServletRequest request, NoHandlerFoundException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        log.error("Request from " + request.getRemoteAddr() + " has no such path.");
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(ResponseMsg.NOT_EXIST_URL.getMsg()).build(), ResponseMsg.NOT_EXIST_URL.getStatus());
    }
}
