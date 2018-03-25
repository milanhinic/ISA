package packages.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.Korisnik;
import packages.repositories.KorisnikRepository;
import packages.serviceInterfaces.KorisnikInterface;

@Service
public class KorisnikService implements KorisnikInterface{
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Override
	public Korisnik addKorisnik(Korisnik korisnik) {
		
		return korisnikRepository.save(korisnik);
	}

	@Override
	public Korisnik getKorisnikByEmail(String email) {
		
		return korisnikRepository.findByEmail(email);
	}

	@Override
	public Korisnik getKorisnik(Long id) {
		
		return korisnikRepository.findOne(id);
	}

	
	
}
