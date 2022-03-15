package travelcompany.eshop.domain.exception;

public class AirportCodeNotExistingException extends BusinessException {
    public AirportCodeNotExistingException(String errorMessage) {
        super(errorMessage);
    }
}
