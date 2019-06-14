package jp.co.edi_java.app.util.validate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        if (!value.contains("@")) return false;
        if(value.split("@").length != 2) return false;
        return true;
    }

}