package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import packages.beans.Oglas;
import packages.beans.RegistrovaniKorisnik;
import packages.enumerations.OglasStatus;

public interface OglasRepository extends JpaRepository<Oglas,Long>{

	
	Oglas findById(Long id);
	
	Oglas findByNazivAndStatus(String naziv, OglasStatus status);
	
	@Query("select o from Oglas o INNER JOIN o.rk where o.rk = ?1 and o.status = ?2 order by o.id asc")
	Page<Oglas> findByRegKorisnikAndStatus(RegistrovaniKorisnik rk, OglasStatus status, Pageable page);
	
	@Query("select o from Oglas o INNER JOIN o.rk where o.rk = ?1 and o.status = ?2 order by o.id asc")
	ArrayList<Oglas> findByRegKorisnikAndStatusArray(RegistrovaniKorisnik rk, OglasStatus status);
	
	@Query("select o from Oglas o where o.rk != :rk and o.status != :status")
	Page<Oglas> findOthersOglasi(@Param("rk") RegistrovaniKorisnik rk,@Param("status") OglasStatus status, Pageable page);
	
	Oglas findByIdAndStatus(Long id, OglasStatus status);
	
	Page<Oglas> findByStatus(OglasStatus status, Pageable pageable);
	
	ArrayList<Oglas> findByStatus(OglasStatus status);
	
	int deleteById(Long id);
	
}
