package packages.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;
import packages.enumerations.PredFilmTip;

public interface PozBioRepository extends JpaRepository<PozBio, Long>{
	
	Page<PozBio> findByTip(PozBioTip tip, Pageable pageable);
	
	Long countByTip(PozBioTip tip);
	
	Page<PozBio> findByTipAndNazivLikeIgnoreCase(PozBioTip tip, String naziv, Pageable pageable);
	
	Long countByTipAndNazivLikeIgnoreCase(PozBioTip tip, String naziv);
	
}
