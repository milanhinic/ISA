package packages.serviceInterfaces;

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
	
}
