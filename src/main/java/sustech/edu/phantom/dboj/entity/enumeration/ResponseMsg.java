package sustech.edu.phantom.dboj.entity.enumeration;

import org.springframework.http.HttpStatus;

public enum ResponseMsg {
    //总体
    OK("Success", HttpStatus.OK),
    INTERNAL_SERVER_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("Not found", HttpStatus.NOT_FOUND),
    BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST),

    //关于用户注册，变更信息
    REGISTER_FAIL("Register fails", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST("The account already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST("The account does not exist", HttpStatus.NOT_FOUND),
    USERNAME_OR_PASSWORD_ERROR("Username or password error", HttpStatus.BAD_REQUEST),
    VERIFICATION_CODE_NOT_MATCHED("Verification code is not matched", HttpStatus.BAD_REQUEST),
    PASSWORD_WRONG("The password is wrong", HttpStatus.BAD_REQUEST),
    RESET_PASSWORD_WRONG("Fail to reset the password", HttpStatus.BAD_REQUEST),

    //权限
    UNAUTHORIZED("Not authorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Forbidden", HttpStatus.FORBIDDEN),
    AUTHENTICATION_FAILED("Authentication fails", HttpStatus.BAD_REQUEST),

    //上传文件
    EMPTY_FILE("The file is empty", HttpStatus.BAD_REQUEST),
    FAIL("Not found", HttpStatus.NOT_FOUND),

    //全局异常处理
    PARSE_EXCEPTION("Wrong format of post data", HttpStatus.BAD_REQUEST),
    PATH_VAR_EXCEPTION("Wrong format of path variable data", HttpStatus.BAD_REQUEST),

    ;

    private final String msg;
    private final HttpStatus status;

    ResponseMsg(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
