package packages.serviceInterfaces;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.Oglas;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.RezervisaniOglas;

public interface RezervisaniOglasInterface {

	
	public RezervisaniOglas addRezOgl(RezervisaniOglas ro);
	
	public ArrayList<RezervisaniOglas> findByRegKor(Long rk);
	
	public Page<RezervisaniOglas> findByOglas(Long oglas, Pageable page);
	
	public int deleteById(Long id);
	
}
