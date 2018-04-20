package packages.repositories;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.Karta;
import packages.beans.PozBio;
import packages.beans.Projekcija;
import packages.beans.Sediste;

public interface KartaRepository extends JpaRepository<Karta, Long>{

	public Karta findByProjekcijaAndSediste(Projekcija projekcija, Sediste sediste);
	
	public Karta findBySediste(Sediste sediste);
	
	@Query("select k from Karta k INNER JOIN k.projekcija p INNER JOIN p.sala s where s.pozBio = ?1 and p.datum >= ?2 and k.brzaRezervacija = true")
	public ArrayList<Karta> vratiBrzeZa(PozBio pozBio, Date datum);
	
	
}
