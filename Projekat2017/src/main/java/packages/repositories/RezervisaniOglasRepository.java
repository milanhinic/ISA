package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Oglas;
import packages.beans.Poruka;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.RezervisaniOglas;

public interface RezervisaniOglasRepository extends JpaRepository<RezervisaniOglas, Long>{
	
	RezervisaniOglas findById(Long id);
	
	Page<RezervisaniOglas> findByOglas(Long oglas, Pageable page);
	
	ArrayList<RezervisaniOglas> findByRk(Long rk);
	
	public int deleteById(Long id);

}
