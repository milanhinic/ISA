package packages.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneConstraintValidator implements ConstraintValidator<TelephoneValidation,String>{

	@Override
	public void initialize(TelephoneValidation arg0) {
		
	}

	@Override
	public boolean isValid(String telephone, ConstraintValidatorContext ctx) {
		
		if(telephone == null || telephone.isEmpty()) return true;
		
		return telephone.matches("\\+?[0-9]{6,12}");
	}

	

}
