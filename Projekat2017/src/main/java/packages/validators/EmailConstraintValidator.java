package packages.validators;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<EmailValidation,String>{

	@Override
	public void initialize(EmailValidation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext ctx) {
		
		if(email==null || email.isEmpty()) {
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate("Morate uneti email adresu").addConstraintViolation();
			return false;
		}
		
		return email.matches("[a-zA-z0-9._]{0,64}@[a-z]{2,10}(\\.[a-z]{2,10})+");
	}


}
