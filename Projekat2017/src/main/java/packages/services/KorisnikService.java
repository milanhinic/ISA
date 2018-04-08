package packages.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.Korisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;
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

	@Override
	public Korisnik getKorisnikByEmailAndLozinka(String email, char[] cs) {

		return korisnikRepository.findByEmailAndLozinka(email, cs);
	}

	@Override
	public Page<Korisnik> getKorisnikList(RegKorisnikStatus status, KorisnikTip tip, String email, Pageable pageable) {
		
		return korisnikRepository.findByStatusAndTipAndEmailNot(status, tip, email, pageable);
	}

	@Override
	public Long getRegKorisnikCount(RegKorisnikStatus status,KorisnikTip tip,String email) {
		
		return korisnikRepository.countByStatusAndTipAndEmailNot(status, tip, email);
	}	
}
