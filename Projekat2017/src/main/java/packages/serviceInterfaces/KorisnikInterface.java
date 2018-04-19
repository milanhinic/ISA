package packages.serviceInterfaces;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.Korisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;

public interface KorisnikInterface {

	public Korisnik addKorisnik(Korisnik korisnik);
	
	public Korisnik getKorisnikByEmail(String email);
	
	public Korisnik getKorisnik(Long id);
	
	public Korisnik getKorisnikByEmailAndLozinka(String email, char[] cs);
	
	public Page<Korisnik> getKorisnikList(RegKorisnikStatus status,KorisnikTip tip,String email,Pageable pageable);
	
	public Page<Korisnik> getAllKorisnikList(RegKorisnikStatus status,KorisnikTip tip, Pageable pageable);
	
	public ArrayList<Korisnik> getAllKorisnikListArray(RegKorisnikStatus status,KorisnikTip tip);
	
	public Long getRegKorisnikCount(RegKorisnikStatus status,KorisnikTip tip,String email);
	
	public Page<Korisnik> getKorisniciImePrezime(RegKorisnikStatus status, KorisnikTip tip, String email, String imeprezime, Pageable pageable);
	
	public Long countKorisniciImePrezime(RegKorisnikStatus status, KorisnikTip tip, String email, String imeprezime);

	public int deleteById(Long id);
	
}
