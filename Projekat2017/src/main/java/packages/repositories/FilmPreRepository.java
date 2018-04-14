package packages.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import packages.beans.FilmPre;
import packages.enumerations.FilmPreTip;


public interface FilmPreRepository extends PagingAndSortingRepository<FilmPre,Long>{

	Page<FilmPre> findByTip(FilmPreTip tip, Pageable pageable);
	
	
}
