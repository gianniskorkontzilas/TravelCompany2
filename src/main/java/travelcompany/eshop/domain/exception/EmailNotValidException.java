package travelcompany.eshop.domain.exception;

public class EmailNotValidException extends BusinessException {
    public EmailNotValidException(String errorMessage) {
        super(errorMessage);
    }
}
