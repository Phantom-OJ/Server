package sustech.edu.phantom.dboj.response;

/**
 * @author Lori
 */
public class ErrorResponse extends GenericResponse {
    public ErrorResponse(GlobalResponseMsg globalResponseCode) {
        super(globalResponseCode.getMessage());
    }

    public ErrorResponse(String message) {
        super(message);
    }
}
