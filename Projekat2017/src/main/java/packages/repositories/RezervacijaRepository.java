package packages.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.PozBio;
import packages.beans.PredFilm;
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
	
	@Query("select count(r) from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p where p.predFilm = ?1 and r.ocenaProjekcije != null")
	public Long countProjectionScores(PredFilm predFilm);
	
	@Query("select sum(r.ocenaProjekcije) from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p where p.predFilm = ?1 and r.ocenaProjekcije != null")
	public Long getProjectionScores(PredFilm predFilm);
	
	@Query("select count(r) from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p INNER JOIN p.sala s where s.pozBio = ?1 and r.ocenaAmbijenta != null")
	public Long countAmbientScores(PozBio pozBio);
	
	@Query("select sum(r.ocenaAmbijenta) from Rezervacija r INNER JOIN r.karta k INNER JOIN k.projekcija p INNER JOIN p.sala s where s.pozBio = ?1 and r.ocenaAmbijenta != null")
	public Long getAmbientScores(PozBio pozBio);
	
}
