package packages.repositories;

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
	
	@Query("select reg.prijatelji from RegistrovaniKorisnik as reg INNER JOIN reg.reg_korisnik_id as k where k = ?1")
	public Page<RegistrovaniKorisnik> getPrijatelji(Korisnik korisnik,Pageable pageable);
	
	@Query("select count(elements(reg.prijatelji)) from RegistrovaniKorisnik as reg where reg.reg_korisnik_id = ?1")
	public Long getPrijateljiBroj(Korisnik korisnik);
	
	@Query("select reg.licniOglasi from RegistrovaniKorisnik as reg INNER JOIN ")
	public Page<Oglas> getOglasi(Korisnik korisnik, Pageable pageable);
}
