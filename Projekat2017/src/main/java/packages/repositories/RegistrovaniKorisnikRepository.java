package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.Korisnik;
import packages.beans.RegistrovaniKorisnik;

public interface RegistrovaniKorisnikRepository extends JpaRepository<RegistrovaniKorisnik,Long>{

	@Query("from RegistrovaniKorisnik where reg_korisnik_id = ?1")
	public RegistrovaniKorisnik findByRegKorisnikId(Korisnik id);
	
}
