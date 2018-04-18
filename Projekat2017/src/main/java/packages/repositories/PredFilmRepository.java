package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.PredFilm;
import packages.enumerations.PredFilmTip;

public interface PredFilmRepository extends JpaRepository<PredFilm, Long>{

	Page<PredFilm> findByTip(PredFilmTip tip, Pageable pageable);
	
	Long countByTip(PredFilmTip tip);
	
	ArrayList<PredFilm> findByTip(PredFilmTip tip);
	
}
