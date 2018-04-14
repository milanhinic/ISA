package packages.repositories;


import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.Korisnik;
import packages.beans.Oglas;
import packages.beans.RegistrovaniKorisnik;

public interface RegistrovaniKorisnikRepository extends JpaRepository<RegistrovaniKorisnik,Long>{

	@Query("from RegistrovaniKorisnik where reg_korisnik_id = ?1")
	public RegistrovaniKorisnik findByRegKorisnikId(Korisnik id);
	
	@Query("select k from RegistrovaniKorisnik reg INNER JOIN reg.prijatelji prijatelji INNER JOIN prijatelji.reg_korisnik_id k where reg.reg_korisnik_id = ?1 order by k.ime, k.prezime asc")
	public Page<Korisnik> getPrijatelji(Korisnik korisnik,Pageable pageable);
	
	@Query("select count(elements(reg.prijatelji)) from RegistrovaniKorisnik as reg where reg.reg_korisnik_id = ?1")
	public Long getPrijateljiBroj(Korisnik korisnik);
	
	@Query("select k from RegistrovaniKorisnik reg INNER JOIN reg.prijatelji prijatelji INNER JOIN prijatelji.reg_korisnik_id k where reg.reg_korisnik_id = ?1 and (UPPER(CONCAT(k.ime, ' ', k.prezime)) LIKE UPPER(?2) or UPPER(CONCAT(k.prezime, ' ', k.ime)) LIKE UPPER(?2)) order by k.ime, k.prezime asc")
	public Page<Korisnik> getPrijateljiByNameAndSurname(Korisnik korisnik, String imeprezime, Pageable pageable);
	
	@Query("select count(k) from RegistrovaniKorisnik reg INNER JOIN reg.prijatelji prijatelji INNER JOIN prijatelji.reg_korisnik_id k where reg.reg_korisnik_id = ?1 and (UPPER(CONCAT(k.ime, ' ', k.prezime)) LIKE UPPER(?2) or UPPER(CONCAT(k.prezime, ' ', k.ime)) LIKE UPPER(?2))")
	public Long countPrijateljiByNameAndSurname(Korisnik korisnik, String imeprezime);
	
	@Query("select reg.licniOglasi from RegistrovaniKorisnik as reg INNER JOIN ")
	public Page<Oglas> getOglasi(Korisnik korisnik, Pageable pageable);
}
