package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.RegistrovaniKorisnik;

public interface RegistrovaniKorisnikRepository extends JpaRepository<RegistrovaniKorisnik,Long>{

}
