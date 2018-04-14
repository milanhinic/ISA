package packages.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.Korisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;

public interface KorisnikRepository extends JpaRepository<Korisnik,Long>{

	Korisnik findByEmail(String email);
	
	Korisnik findByEmailAndLozinka(String email, char[] cs);
	
	Page<Korisnik> findByStatusAndTipAndEmailNotOrderByImeAscPrezimeAsc(RegKorisnikStatus status,KorisnikTip tip, String email, Pageable pageable);
	
	Page<Korisnik> findByStatusAndTip(RegKorisnikStatus status,KorisnikTip tip, Pageable pageable);
	
	Long countByStatusAndTipAndEmailNot(RegKorisnikStatus status,KorisnikTip tip, String email);
	
	@Query("from Korisnik k where k.status = ?1 and k.tip = ?2 and k.email != ?3 and (UPPER(CONCAT(k.ime, ' ', k.prezime)) LIKE UPPER(?4) or UPPER(CONCAT(k.prezime, ' ', k.ime)) LIKE UPPER(?4)) order by k.ime, k.prezime asc")
	Page<Korisnik> getKorisniciByStatusAndTipAndEmailNotAndNameSurname(RegKorisnikStatus status, KorisnikTip tip, String email, String imeprezime, Pageable pageable);
	
	@Query("select count(k) from Korisnik k where k.status = ?1 and k.tip = ?2 and k.email != ?3 and (UPPER(CONCAT(k.ime, ' ', k.prezime)) LIKE UPPER(?4) or UPPER(CONCAT(k.prezime, ' ', k.ime)) LIKE UPPER(?4))")
	Long getKorisniciCountByStatusAndTipAndEmailNotAndNameSurname(RegKorisnikStatus status, KorisnikTip tip, String email, String imeprezime);
	
	int deleteById(Long id);
	
}
