package sustech.edu.phantom.dboj.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/24 22:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponse<T> {
    public transient final static String SUCCESS = "success";
    public transient final static String FAIL = "not found";
    public transient final static String USERNAME_OR_PASSWORD_ERROR = "Username or password error";
    public transient final static String ACCESS_FORBIDDEN = "forbidden";
    public transient final static String USERNAME_NOT_FOUND = "username not found";
    public transient final static String LOGIN_FAILED = "logging in failed";
    public transient final static String VERIFICATION_CODE_NOT_MATCHED = "Verification code is not matched";
    public transient final static String SERVER_ERROR = "Internal Server Error";

    private String msg;
    private T data;
}
