package sustech.edu.phantom.dboj.enumeration;

import org.springframework.http.HttpStatus;

public enum ResponseMsg {
    UNAUTHORIZED("Not authenticated", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Forbidden", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("Not found", HttpStatus.NOT_FOUND),
    BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST);

    ResponseMsg(String message, HttpStatus status) {
    }

}
