package sustech.edu.phantom.dboj.response;

/**
 * @author Lori
 */
public class SuccessResponse extends GenericResponse {
    private static final GlobalResponseCode success = GlobalResponseCode.SUCCESS;

    public SuccessResponse() {
        super(success.getCode(), success.getMessage());
    }

    public SuccessResponse(int code, String message) {
        super(code, message);
    }
}
