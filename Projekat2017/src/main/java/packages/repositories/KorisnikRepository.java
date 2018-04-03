package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik,Long>{

	Korisnik findByEmail(String email);
	
	Korisnik findByEmailAndLozinka(String email, char[] cs);
}
