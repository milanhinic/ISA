package packages.serviceInterfaces;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;

public interface PozBioInterface {

	public PozBio getPozBio(Long id);
	
	public PozBio addPozBio(PozBio pozBio);
	
	public Page<PozBio> getPozBioList(PozBioTip tip, Pageable pageable);
	
	public ArrayList<PozBio> getAllPozBios();
	
	public Long getRowCount(PozBioTip tip);

}
