package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.PozBio;
import packages.beans.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long>{
	
	ArrayList<Sala> findByPozBio(PozBio pozBio);

}
