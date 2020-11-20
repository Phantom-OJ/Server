package sustech.edu.phantom.dboj.response;

public class ErrorResponse extends GenericResponse{
    public ErrorResponse(GlobalResponseCode globalResponseCode) {
        super(globalResponseCode.getCode(), globalResponseCode.getMessage());
    }

    public ErrorResponse(int code, String message) {
        super(code, message);
    }
}
