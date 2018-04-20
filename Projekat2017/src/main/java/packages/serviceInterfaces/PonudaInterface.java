package packages.serviceInterfaces;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import packages.beans.Oglas;
import packages.beans.Ponuda;
import packages.beans.RegistrovaniKorisnik;
import packages.enumerations.OglasStatus;

public interface PonudaInterface {

	public Ponuda addPonuda(Ponuda ponuda);
	
	public Ponuda getPonuda(Long id);
	
	public ArrayList<Ponuda> getAllPonuda();
	
	public ArrayList<Ponuda> getPonudeByOglas(Oglas oglas);
	
	public ArrayList<Ponuda> getPonudeByRegKor(RegistrovaniKorisnik rk);
	
	public ArrayList<Ponuda> getPonudeByOglasAndRegKor(Oglas oglas, RegistrovaniKorisnik rk);
	
	public ArrayList<Ponuda> getTudjePonudeByOglasAndRegKor(Oglas oglas, RegistrovaniKorisnik rk);
	
	public int deletePonuda(Long id);
	
}
