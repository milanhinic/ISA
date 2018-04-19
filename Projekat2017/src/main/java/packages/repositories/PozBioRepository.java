package packages.repositories;


import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;

public interface PozBioRepository extends JpaRepository<PozBio, Long>{
	
	Page<PozBio> findByTip(PozBioTip tip, Pageable pageable);
	
	Long countByTip(PozBioTip tip);
	
	Page<PozBio> findByTipAndNazivLikeIgnoreCase(PozBioTip tip, String naziv, Pageable pageable);
	
	Long countByTipAndNazivLikeIgnoreCase(PozBioTip tip, String naziv);
	
	ArrayList<PozBio> findByTip(PozBioTip tip);
	
}
