package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Oglas;
import packages.enumerations.OglasStatus;

public interface OglasRepository extends JpaRepository<Oglas,Long>{

	Oglas findById(Long id);
	
	Oglas findByNazivAndStatus(String naziv, OglasStatus status);
	
	Page<Oglas> findByIdAndStatus(Long id, OglasStatus status,Pageable pageable);
	
	Page<Oglas> findByStatus(OglasStatus status, Pageable pageable);
	
	ArrayList<Oglas> findByStatus(OglasStatus status);
	
	int deleteById(Long id);
	
}
