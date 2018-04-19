package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;
import packages.repositories.PozBioRepository;
import packages.repositories.RezervacijaRepository;
import packages.serviceInterfaces.PozBioInterface;

@Service
public class PozBioService implements PozBioInterface {

	@Autowired
	PozBioRepository pbr;
	
	@Autowired
	RezervacijaRepository rr;
	
	
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

	@Override
	public Page<PozBio> getPozBioListNaziv(PozBioTip tip, String naziv, Pageable pageable) {
		
		return pbr.findByTipAndNazivLikeIgnoreCase(tip, "%"+naziv.trim()+"%", pageable);
	}

	@Override
	public Long getPozBioCountNaziv(PozBioTip tip, String naziv) {
		
		return pbr.countByTipAndNazivLikeIgnoreCase(tip, "%"+naziv.trim()+"%");
	}

	@Override
	public Double getAmbientScore(PozBio pozBio) {
		// TODO Auto-generated method stub
		Long ukupno = rr.getAmbientScores(pozBio);
		Long count = rr.countAmbientScores(pozBio);
		
		return (double)ukupno/count;
	}


	@Override
	public Double getAverageAmbientScore(PozBioTip tip) {
		// TODO Auto-generated method stub
		Long ukupno = rr.getAmbientScoresI(tip);
		Long count = rr.countAmbientScoresI(tip);
		
		return (double)ukupno/count;
	}
	

}
