package sustech.edu.phantom.dboj.entity.enumeration;

import org.springframework.http.HttpStatus;
public enum ResponseMsg {
    OK("Success",HttpStatus.OK),
    UNAUTHORIZED("Not authorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Forbidden", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("Not found", HttpStatus.NOT_FOUND),
    BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST),
    USERNAME_OR_PASSWORD_ERROR("Username or password error", HttpStatus.BAD_REQUEST),
    FAIL("Not found", HttpStatus.NOT_FOUND),
    VERIFICATION_CODE_NOT_MATCHED("Verification code is not matched",HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAILED("Authentication fails", HttpStatus.BAD_REQUEST),
    EMPTY_FILE("The file is empty", HttpStatus.BAD_REQUEST),;

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
