package packages.components;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import packages.beans.Korisnik;
import packages.beans.KorisnikDTO;

@Component
public class KorisnikToKorisnikDTO implements Converter<Korisnik,KorisnikDTO>{

	@Override
	public KorisnikDTO convert(Korisnik korisnik) {
		
		if(korisnik == null) return null;
		
		ModelMapper modelMapper = new ModelMapper();
		KorisnikDTO korisnikDTO = modelMapper.map(korisnik,KorisnikDTO.class);
		
		return korisnikDTO;	
	}
	
}
