package packages.serviceInterfaces;

import packages.beans.Korisnik;

public interface KorisnikInterface {

	public Korisnik addKorisnik(Korisnik korisnik);
	
	public Korisnik getKorisnikByEmail(String email);
}
