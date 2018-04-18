package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Karta;
import packages.beans.Projekcija;
import packages.beans.Sediste;

public interface KartaRepository extends JpaRepository<Karta, Long>{

	public Karta findByProjekcijaAndSediste(Projekcija projekcija, Sediste sediste);
	
}
