package packages.serviceInterfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.Korisnik;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Zahtev;

public interface RegistrovaniKorisnikInterface {

	public RegistrovaniKorisnik addRegistrovaniKorisnik(RegistrovaniKorisnik regKorisnik);
	
	public RegistrovaniKorisnik getRegKorisnik(Long id);
	
	public RegistrovaniKorisnik getRegKorisnikByKorisnikId(Korisnik k);
	
	public Zahtev getZahtevByPosiljalacAndPrimalac(RegistrovaniKorisnik posiljalac, RegistrovaniKorisnik primalac);
	
	public Zahtev addZahtev(Zahtev zahtev);
	
	public void deleteZahtev(Zahtev zahtev);
	
	public Page<Korisnik> getPosiljaociFromZahtev(RegistrovaniKorisnik primalac, Pageable pageable);
	
	public Long getPosiljaociCount(RegistrovaniKorisnik primalac);
	
	public Page<RegistrovaniKorisnik> getPrijatelji(Korisnik korisnik, Pageable pageable);
	
	public Long getPrijateljiBroj(Korisnik korisnik);
	
	
}
