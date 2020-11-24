package sustech.edu.phantom.dboj.response;

/**
 * @author Lori
 */
public class SuccessResponse extends GenericResponse {
    private static final GlobalResponseMsg SUCCESS = GlobalResponseMsg.SUCCESS;

    public SuccessResponse() {
        super(SUCCESS.getMessage());
    }

    public SuccessResponse(String message) {
        super(message);
    }
}
