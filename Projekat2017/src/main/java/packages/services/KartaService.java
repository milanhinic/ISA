package packages.services;

import java.util.ArrayList;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import packages.beans.Karta;
import packages.beans.PozBio;
import packages.beans.Projekcija;
import packages.beans.Sediste;
import packages.exceptions.KartaExistsException;
import packages.repositories.KartaRepository;
import packages.serviceInterfaces.KartaInterface;

@Service
public class KartaService implements KartaInterface{

	@Autowired
	private KartaRepository kartaRepository;

	@Override
	public void deleteKarta(Karta karta) {
		
		kartaRepository.delete(karta);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Karta findByProjekcijaAndSediste(Projekcija projekcija, Sediste sediste) {
		
		return kartaRepository.findByProjekcijaAndSediste(projekcija, sediste);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public ArrayList<Karta> createKarte(ArrayList<Karta> karte) throws KartaExistsException {
		
		ArrayList<Karta> retVal = new ArrayList<Karta>();
		
		for(Karta k : karte) {		
			Karta kartaOld = findByProjekcijaAndSediste(k.getProjekcija(), k.getSediste());
			if(kartaOld!=null)
				throw new KartaExistsException("Mesto je vec rezervisano");		
		}
		
		for(Karta k : karte) {
			Karta ret = kartaRepository.save(k);
			retVal.add(ret);
		}
		
		return retVal;
	}

	@Override
	public Karta findBySediste(Sediste sediste) {
		// TODO Auto-generated method stub
		return kartaRepository.findBySediste(sediste);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void findByProjekcijaAndSedisteBrza(Projekcija projekcija, Sediste sediste) throws KartaExistsException{
		// TODO Auto-generated method stub
		
		if(kartaRepository.findByProjekcijaAndSediste(projekcija, sediste) != null) {
			throw new KartaExistsException("Mesto je vec rezervisano");	
		}
		
	}

	@Override
	public ArrayList<Karta> vratiBrzeZa(PozBio pozBio, Date datum) {
		// TODO Auto-generated method stub
		return kartaRepository.vratiBrzeZa(pozBio, datum);
	}

	@Override
	public Karta getKarta(Long id) {
		// TODO Auto-generated method stub
		return kartaRepository.getOne(id);
	}
		
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Karta createKarta(Karta karta) throws KartaExistsException{
		
		if(findByProjekcijaAndSediste(karta.getProjekcija(), karta.getSediste()) != null) {
			throw new KartaExistsException("Mesto je vec rezervisano");	
		}
		
		return kartaRepository.save(karta);

	}
	
}
