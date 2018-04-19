package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import packages.beans.Karta;
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
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Karta findByProjekcijaAndSediste(Projekcija projekcija, Sediste sediste) {
		
		return kartaRepository.findByProjekcijaAndSediste(projekcija, sediste);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public ArrayList<Karta> createKarte(ArrayList<Karta> karte) throws KartaExistsException {
		
		ArrayList<Karta> retVal = new ArrayList<Karta>();
		
		for(Karta k : karte) {		
			Karta kartaOld = findByProjekcijaAndSediste(k.getProjekcija(), k.getSediste());
			if(kartaOld!=null)
				throw new KartaExistsException("Mesto je vec rezervisano");		
		}
		
		for(Karta k : karte) {
			Karta ret = kartaRepository.save(k);
			retVal.add(k);
		}
		
		return retVal;
	}

	@Override
	public Karta createKarta(Karta karta) {
		
		return kartaRepository.save(karta);
	}
	
}
