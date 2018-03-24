package packages.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;

public interface PozBioRepository extends PagingAndSortingRepository<PozBio, Long>{
	
	Page<PozBio> findByTip(PozBioTip tip, Pageable pageable);
	
}