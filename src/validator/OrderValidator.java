package validator;

public interface OrderValidator {
    String ORDER_REGEX = "([A-Z][A-Z\\s]+;[A-Z]+;([1-9]\\d*;){2}0\\.\\d{1,2}\\|)*[A-Z][A-Z\\s]+;[A-Z]+;([1-9]\\d*;){2}0\\.\\d{1,2}";

    static boolean validate(String expression) {
        return expression != null && expression.matches(ORDER_REGEX);
    }
}