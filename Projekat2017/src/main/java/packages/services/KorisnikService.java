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
		
		return korisnikRepository.findByStatusAndTipAndEmailNotOrderByImeAscPrezimeAsc(status, tip, email, pageable);
	}
	
	
	@Override
	public Long getRegKorisnikCount(RegKorisnikStatus status,KorisnikTip tip,String email) {
		
		return korisnikRepository.countByStatusAndTipAndEmailNot(status, tip, email);
	}

	@Override

	public Page<Korisnik> getKorisniciImePrezime(RegKorisnikStatus status, KorisnikTip tip, String email,
			String imeprezime, Pageable pageable) {
		
		return korisnikRepository.getKorisniciByStatusAndTipAndEmailNotAndNameSurname(status, tip, email, "%"+imeprezime.trim()+"%", pageable);
	}

	@Override
	public Long countKorisniciImePrezime(RegKorisnikStatus status, KorisnikTip tip, String email,
			String imeprezime) {
		
		return korisnikRepository.getKorisniciCountByStatusAndTipAndEmailNotAndNameSurname(status, tip, email, "%"+imeprezime.trim()+"%");
	}
	public Page<Korisnik> getAllKorisnikList(RegKorisnikStatus status, KorisnikTip tip, Pageable pageable) {
		
		return korisnikRepository.findByStatusAndTip(status, tip, pageable);
	}

	@Override
	public int deleteById(Long id) {
		return korisnikRepository.deleteById(id);
		
	}	
}
