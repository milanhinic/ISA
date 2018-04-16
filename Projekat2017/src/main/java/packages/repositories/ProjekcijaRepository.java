package packages.repositories;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.PozBio;
import packages.beans.PredFilm;
import packages.beans.Projekcija;
import packages.beans.Sala;

public interface ProjekcijaRepository extends JpaRepository<Projekcija, Long>{

	public ArrayList<Projekcija> findByPredFilm(PredFilm predFilm);
	
	public ArrayList<Projekcija> findBySala(Sala sala);
	
	@Query("select p from Projekcija p INNER JOIN p.sala s INNER JOIN s.pozBio pb where pb = ?1 and p.datum between ?2 and ?3")
	public ArrayList<Projekcija> projekcijasBetween(PozBio pozBio, Date pocetak, Date kraj);
	
}
