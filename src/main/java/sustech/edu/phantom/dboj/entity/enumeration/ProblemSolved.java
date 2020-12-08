package sustech.edu.phantom.dboj.entity.enumeration;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/8 20:25
 */
public enum ProblemSolved {
    WA(0, "wa or tle or mle or se"),
    AC(1, "accepted"),
    NO_SUBMISSION(2, "no submission for this problem"),;


    private final Integer code;
    private final String msg;

    ProblemSolved(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
