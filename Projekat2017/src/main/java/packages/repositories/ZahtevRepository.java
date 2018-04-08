package packages.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.Korisnik;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Zahtev;

public interface ZahtevRepository extends JpaRepository<Zahtev,Long>{
	
	public Zahtev findByPosiljalacAndPrimalac(RegistrovaniKorisnik posiljalac, RegistrovaniKorisnik primalac);
	
	@Query("select k from Zahtev as z INNER JOIN z.posiljalac as reg INNER JOIN reg.reg_korisnik_id as k where z.primalac = ?1")
	public Page<Korisnik> findByPrimalac(RegistrovaniKorisnik primalac, Pageable pageable);

	public Long countByPrimalac(RegistrovaniKorisnik primalac);
}
