package packages.serviceInterfaces;

import packages.beans.Oglas;
import packages.enumerations.OglasStatus;

public interface OglasInterface {

	public Oglas addOglas(Oglas oglas);
	
	public Oglas getOglasById(Long id);
	
	public Oglas getOglasByIdAndStatus(Long id, OglasStatus status);
	

	
}
