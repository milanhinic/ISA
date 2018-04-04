package packages.serviceInterfaces;

import packages.beans.RegistrovaniKorisnik;

public interface RegistrovaniKorisnikInterface {

	public RegistrovaniKorisnik addRegistrovaniKorisnik(RegistrovaniKorisnik regKorisnik);
	
	public RegistrovaniKorisnik getRegKorisnik(Long id);
	
}
