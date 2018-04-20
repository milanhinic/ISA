package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Poruka;
import packages.beans.RegistrovaniKorisnik;

public interface PorukaRepository extends JpaRepository<Poruka, Long>{

	Poruka findById(Long id);
	
	Page<Poruka> findByRk(RegistrovaniKorisnik rk, Pageable page);
	
	public int deleteById(Long id);
	
}
