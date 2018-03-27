package packages.serviceInterfaces;

import packages.beans.Korisnik;

public interface KorisnikInterface {

	public Korisnik addKorisnik(Korisnik korisnik);
	
	public Korisnik getKorisnikByEmail(String email);
	
	public Korisnik getKorisnik(Long id);
	
	public Korisnik getKorisnikByEmailAndLozinka(String email, char[] cs);
}
