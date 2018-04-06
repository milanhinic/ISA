package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.RegistrovaniKorisnik;
import packages.beans.Zahtev;

public interface ZahtevRepository extends JpaRepository<Zahtev,Long>{
	
	public Zahtev findByPosiljalacAndPrimalac(RegistrovaniKorisnik posiljalac, RegistrovaniKorisnik primalac);
	
}
