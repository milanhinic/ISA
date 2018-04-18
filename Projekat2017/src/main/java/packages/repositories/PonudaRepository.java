package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Oglas;
import packages.beans.Ponuda;

public interface PonudaRepository extends JpaRepository<Ponuda, Long> {

	ArrayList<Ponuda> findByOglas(Oglas oglas);
	
	public int deleteById(Long id);
	
}
