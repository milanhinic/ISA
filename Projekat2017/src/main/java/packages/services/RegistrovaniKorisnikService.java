package packages.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.RegistrovaniKorisnik;
import packages.repositories.RegistrovaniKorisnikRepository;
import packages.serviceInterfaces.RegistrovaniKorisnikInterface;

@Service
public class RegistrovaniKorisnikService implements RegistrovaniKorisnikInterface{

	@Autowired
	RegistrovaniKorisnikRepository registrovaniKorisnikRepository;
	
	@Override
	public RegistrovaniKorisnik addRegistrovaniKorisnik(RegistrovaniKorisnik regKorisnik) {

		return registrovaniKorisnikRepository.save(regKorisnik);
	}

	@Override
	public RegistrovaniKorisnik getRegKorisnik(Long id) {

		return registrovaniKorisnikRepository.findOne(id);
	}
	
}
