package packages.serviceInterfaces;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.Oglas;
import packages.enumerations.OglasStatus;

public interface OglasInterface {

	public Oglas addOglas(Oglas oglas);
	
	public Oglas getOglasById(Long id);
	
	public Oglas getOglasByIdAndStatus(Long id, OglasStatus status);
	
	public Oglas getOglasByNazivAndStatus(String naziv, OglasStatus status);
	
	public Page<Oglas> getAllOglasiByStatus(OglasStatus status, Pageable page);
	
	public int deleteOglas(Long id);
	
	public ArrayList<Oglas> getAllOglasiByStatus(OglasStatus status);

	
}
