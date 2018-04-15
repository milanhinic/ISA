package packages.serviceInterfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.Oglas;
import packages.enumerations.OglasStatus;

public interface OglasInterface {

	public Oglas addOglas(Oglas oglas);
	
	public Oglas getOglasById(Long id);
	
	public Oglas getOglasByIdAndStatus(Long id, OglasStatus status);
	
	public Page<Oglas> getAllOglasiByStatus(OglasStatus status, Pageable page);
	
	public int deleteOglas(Long id);
	

	
}
