package jp.co.edi_java.app.util.validate;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class EmailConfirmValidator implements ConstraintValidator<EmailConfirm, Object> {

	private String field1;
    private String field2;

    @Override
    public void initialize(EmailConfirm constraintAnnotation) {
    	field1 = "email";
        field2 = "emailConfirm";
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object field1Value = beanWrapper.getPropertyValue(field1);
        Object field2Value = beanWrapper.getPropertyValue(field2);

        if (Objects.equals(field1Value, field2Value)) {
            return true;
        } else {
            return false;
        }
    }

}