package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;
import packages.repositories.PozBioRepository;
import packages.serviceInterfaces.PozBioInterface;

@Service
public class PozBioService implements PozBioInterface {

	@Autowired
	PozBioRepository pbr;
	
	
	@Override
	public PozBio getPozBio(Long id) {
		
		return pbr.findOne(id);
	}

	@Override
	public PozBio addPozBio(PozBio pozBio) {
		
		return pbr.save(pozBio);
	}

	@Override
	public Page<PozBio> getPozBioList(PozBioTip tip, Pageable pageable) {

		return pbr.findByTip(tip, pageable);
	}
	
	@Override
	public ArrayList<PozBio> getAllPozBios() {
		
		return (ArrayList<PozBio>) pbr.findAll();
	}

	@Override
	public Long getRowCount(PozBioTip tip) {
		
		return pbr.countByTip(tip);
	}

	

}
