package packages.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.Karta;
import packages.repositories.KartaRepository;
import packages.serviceInterfaces.KartaInterface;

@Service
public class KartaService implements KartaInterface{

	@Autowired
	private KartaRepository kartaRepository;

	@Override
	public Karta createKarta(Karta karta) {
		
		return kartaRepository.save(karta);
	}

	@Override
	public void deleteKarta(Karta karta) {
		
		kartaRepository.delete(karta);
	}
	
}
