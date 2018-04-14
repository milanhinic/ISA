package packages.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Oglas;
import packages.enumerations.OglasStatus;

public interface OglasRepository extends JpaRepository<Oglas,Long>{

	Oglas findById(Long id);
	
	Page<Oglas> findByIdAndStatus(Long id, OglasStatus status,Pageable pageable);
	
	Page<Oglas> findByStatus(OglasStatus status, Pageable pageable);
	
	int deleteById(Long id);
	
}
