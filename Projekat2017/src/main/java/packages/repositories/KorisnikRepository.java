package packages.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Korisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;

public interface KorisnikRepository extends JpaRepository<Korisnik,Long>{

	Korisnik findByEmail(String email);
	
	Korisnik findByEmailAndLozinka(String email, char[] cs);
	
	Page<Korisnik> findByStatusAndTipAndEmailNot(RegKorisnikStatus status,KorisnikTip tip, String email, Pageable pageable);
	
	Long countByStatusAndTipAndEmailNot(RegKorisnikStatus status,KorisnikTip tip, String email);
	
}
