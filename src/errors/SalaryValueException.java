package errors;

public class SalaryValueException extends IllegalArgumentException {
    public SalaryValueException(String message) {
        super(message);
    }
}
