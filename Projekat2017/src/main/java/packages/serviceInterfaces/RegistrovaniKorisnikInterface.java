package packages.serviceInterfaces;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.Korisnik;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Zahtev;

public interface RegistrovaniKorisnikInterface {

	public RegistrovaniKorisnik addRegistrovaniKorisnik(RegistrovaniKorisnik regKorisnik);
	
	public RegistrovaniKorisnik getRegKorisnik(Long id);
	
	public RegistrovaniKorisnik getRegKorisnikByKorisnikId(Korisnik k);
	
	public RegistrovaniKorisnik getKorisnikId(Korisnik k);
	
	public Zahtev getZahtevByPosiljalacAndPrimalac(RegistrovaniKorisnik posiljalac, RegistrovaniKorisnik primalac);
	
	public Zahtev addZahtev(Zahtev zahtev);
	
	public void deleteZahtev(Zahtev zahtev);
	
	public Page<Korisnik> getPosiljaociFromZahtev(RegistrovaniKorisnik primalac, Pageable pageable);
	
	public Long getPosiljaociCount(RegistrovaniKorisnik primalac);
	
	public Page<Korisnik> getPrijatelji(Korisnik korisnik, Pageable pageable);
	
	public Long getPrijateljiBroj(Korisnik korisnik);
	
	public Page<Korisnik> getPrijateljiByNameAndSurname(Korisnik korisnik, String imeprezime, Pageable pageable);
	
	public Long countPrijateljiByNameAndSurname(Korisnik korisnik, String imeprezime);

}
