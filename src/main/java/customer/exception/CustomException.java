package customer.exception;

/**
 * className: CustomException
 * package: customer.exception
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/10/15 20:53
 */
public class CustomException extends RuntimeException {
    private String errorCode;

    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
