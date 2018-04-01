package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import packages.beans.PozBio;
import packages.beans.Sala;

public interface SalaRepository extends CrudRepository<Sala, Long>{
	
	ArrayList<Sala> findByPozBio(PozBio pozBio);

}
