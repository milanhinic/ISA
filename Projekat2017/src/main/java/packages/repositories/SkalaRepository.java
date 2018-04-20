package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Skala;;

public interface SkalaRepository extends JpaRepository<Skala, Long>{

	Skala findById(Long id);
	
	Skala findByNaziv(String naziv);
	
	public int deleteById(Long id);
	
}
