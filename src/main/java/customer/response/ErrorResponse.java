package customer.response;

/**
 * className: ErrorResponse
 * package: customer.response
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/10/15 21:03
 */
public class ErrorResponse {
    private String errorCode;
    private String message;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}

