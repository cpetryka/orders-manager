package service.exception;

public class OrderServiceException extends RuntimeException {
    public OrderServiceException(String message) {
        super(message);
    }
}
