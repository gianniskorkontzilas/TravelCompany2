package travelcompany.eshop.domain.exception;

public class InvalidCodeException extends BusinessException{
    public InvalidCodeException(String errorMessage){
        super(errorMessage);
    }
}
