package jp.co.edi_java.app.util.validate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistsValidator implements ConstraintValidator<EmailConfirm, String> {

//	@Autowired
//	TestService testService;

    @Override
    public void initialize(EmailConfirm constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
//    	TestEntity ret = testService.selectByEmail(value);
//    	if(ret != null) return false;
    	return true;
    }

}