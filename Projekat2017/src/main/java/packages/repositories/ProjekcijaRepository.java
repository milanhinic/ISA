package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.PredFilm;
import packages.beans.Projekcija;
import packages.beans.Sala;

public interface ProjekcijaRepository extends JpaRepository<Projekcija, Long>{

	public ArrayList<Projekcija> findByPredFilm(PredFilm predFilm);
	
	public ArrayList<Projekcija> findBySala(Sala sala);
	
	
}
