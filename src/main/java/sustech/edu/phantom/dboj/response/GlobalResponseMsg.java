package sustech.edu.phantom.dboj.response;

/**
 * @author Lori
 */

public enum GlobalResponseMsg implements IErrorMsg {
    //success
    SUCCESS("Success"),

    //账户相关
    USERNAME_OR_PASSWORD_ERROR("Username or password error"),
    ACCOUNT_LOCKED_ERROR("Account is locked error"),
    CREDENTIALS_EXPIRED_ERROR("Credentials expires error"),
    ACCOUNT_EXPIRED_ERROR("Account expires error"),
    ACCOUNT_DISABLED_ERROR("Account is disabled error"),
    VERIFICATION_CODE_NOT_MATCHED_ERROR("Verification code is not matched error"),
    LOGIN_FAILED_ERROR("Logging in failed"),
    USERNAME_NOT_FOUND_ERROR("Username not found"),
    //权限
    ACCESS_FORBIDDEN_ERROR("Forbidden"),

    //服务器出错
    SERVER_ERROR("Internal server error");

    private String message;

    GlobalResponseMsg(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
