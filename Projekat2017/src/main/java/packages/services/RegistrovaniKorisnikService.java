package packages.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.Korisnik;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Zahtev;
import packages.repositories.RegistrovaniKorisnikRepository;
import packages.repositories.ZahtevRepository;
import org.springframework.data.domain.Page;
import packages.serviceInterfaces.RegistrovaniKorisnikInterface;

@Service
public class RegistrovaniKorisnikService implements RegistrovaniKorisnikInterface{

	@Autowired
	RegistrovaniKorisnikRepository registrovaniKorisnikRepository;
	
	@Autowired
	ZahtevRepository zahtevRepository;
	
	@Override
	public RegistrovaniKorisnik addRegistrovaniKorisnik(RegistrovaniKorisnik regKorisnik) {

		return registrovaniKorisnikRepository.save(regKorisnik);
	}

	@Override
	public RegistrovaniKorisnik getRegKorisnik(Long id) {

		return registrovaniKorisnikRepository.findOne(id);
	}

	@Override
	public Zahtev getZahtevByPosiljalacAndPrimalac(RegistrovaniKorisnik posiljalac, RegistrovaniKorisnik primalac) {

		return zahtevRepository.findByPosiljalacAndPrimalac(posiljalac, primalac);
	}

	@Override
	public Zahtev addZahtev(Zahtev zahtev) {

		return zahtevRepository.save(zahtev);
	}

	@Override
	public RegistrovaniKorisnik getRegKorisnikByKorisnikId(Korisnik k) {
		
		return registrovaniKorisnikRepository.findByRegKorisnikId(k);
	}

	@Override
	public void deleteZahtev(Zahtev zahtev) {
		
		zahtevRepository.delete(zahtev);
	}

	@Override
	public Page<Korisnik> getPosiljaociFromZahtev(RegistrovaniKorisnik primalac, Pageable pageable) {
		
		return zahtevRepository.findByPrimalac(primalac, pageable);
	}

	@Override
	public Long getPosiljaociCount(RegistrovaniKorisnik primalac) {
		
		return zahtevRepository.countByPrimalac(primalac);
	}

	@Override
	public Page<Korisnik> getPrijatelji(Korisnik korisnik, Pageable pageable) {
		
		return registrovaniKorisnikRepository.getPrijatelji(korisnik, pageable);
	}

	@Override
	public Long getPrijateljiBroj(Korisnik korisnik) {
		
		return registrovaniKorisnikRepository.getPrijateljiBroj(korisnik);
	}

	@Override
	public Page<Korisnik> getPrijateljiByNameAndSurname(Korisnik korisnik, String imeprezime, Pageable pageable) {
		
		return registrovaniKorisnikRepository.getPrijateljiByNameAndSurname(korisnik,"%"+imeprezime.trim()+"%", pageable);
	}

	@Override
	public Long countPrijateljiByNameAndSurname(Korisnik korisnik, String imeprezime) {
		
		return registrovaniKorisnikRepository.countPrijateljiByNameAndSurname(korisnik, "%"+imeprezime.trim()+"%");
	}

	
	
}
