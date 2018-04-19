package packages.validators;

import java.util.ArrayList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import packages.dto.KorisnikSedisteDTO;

public class KorisnikSedisteConstraintValidator implements ConstraintValidator<KorisnikSedisteValidation,ArrayList<KorisnikSedisteDTO>>{

	@Override
	public void initialize(KorisnikSedisteValidation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(ArrayList<KorisnikSedisteDTO> lista, ConstraintValidatorContext arg1) {
		
		if(lista==null) {
			return false;
		}
		
		if(lista.size()==0) {
			return false;
		}
		
		
		return true;
	}

	

}
