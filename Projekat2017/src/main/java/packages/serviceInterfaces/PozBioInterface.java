package packages.serviceInterfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;

public interface PozBioInterface {

	public PozBio getPozBio(Long id, PozBioTip tip);
	
	public PozBio addPozBio(PozBio pozBio);
	
	public Page<PozBio> getPozBioList(PozBioTip tip, Pageable pageable);
	
	//public Page<PozBio> getAllPozBios(String tip);

}
