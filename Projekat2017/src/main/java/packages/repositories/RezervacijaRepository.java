package packages.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.RegistrovaniKorisnik;
import packages.beans.Rezervacija;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long>{

	@Query("select r from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p where r.regKorisnik = ?1 and p.datum > ?2 order by p.datum asc")
	public Page<Rezervacija> findByRegKorisnikAndCanCancel(RegistrovaniKorisnik regKorisnik, Date currentDate, Pageable page);
	
	@Query("select count(r) from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p where r.regKorisnik = ?1 and p.datum > ?2")
	public Long countByRegKorisnikAndCanCancel(RegistrovaniKorisnik regKorisnik, Date currentDate);
	
	@Query("select r from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p where r.regKorisnik = ?1 and p.datum <= ?2 order by p.datum desc")
	public Page<Rezervacija> findHistory(RegistrovaniKorisnik regKorisnik, Date currentDate, Pageable page);
	
	@Query("select count(r) from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p where r.regKorisnik = ?1 and p.datum <= ?2")
	public Long countHistory(RegistrovaniKorisnik regKorisnik, Date currentDate);
	
	@Query("select r from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p where r.regKorisnik = ?1 and p.datum > ?2 and r.id = ?3")
	public Rezervacija findOneByRegKorisnikAndCanCancel(RegistrovaniKorisnik regKorisnik, Date currentDate, Long id);
	
}
