package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import packages.beans.Oglas;
import packages.beans.Ponuda;
import packages.beans.RegistrovaniKorisnik;

public interface PonudaRepository extends JpaRepository<Ponuda, Long> {

	ArrayList<Ponuda> findByOglas(Oglas oglas);
	
	Ponuda findById(Long id);
	
	ArrayList<Ponuda> findByRk(RegistrovaniKorisnik rk);
	
	ArrayList<Ponuda> findByOglasAndRk(Oglas oglas, RegistrovaniKorisnik rk);
	
	@Query("select p from Ponuda p where p.oglas = :ogl and p.rk != :rk")
	public ArrayList<Ponuda> getTudjePonude(@Param("ogl") Oglas oglas, @Param("rk") RegistrovaniKorisnik rk);
	
	public int deleteById(Long id);
	
}
