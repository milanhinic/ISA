package packages.repositories;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.PredFilm;
import packages.beans.Projekcija;
import packages.beans.Sala;

public interface ProjekcijaRepository extends JpaRepository<Projekcija, Long>{

	public ArrayList<Projekcija> findByPredFilm(PredFilm predFilm);
	
	public ArrayList<Projekcija> findBySala(Sala sala);
	
	@Query("from Projekcija p where p.sala = ?1 and p.datum between ?2 and ?3")
	public ArrayList<Projekcija> projekcijasBetween(Sala sala, Date pocetak, Date kraj);
	
}
