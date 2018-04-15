package packages.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.Oglas;
import packages.enumerations.OglasStatus;
import packages.repositories.OglasRepository;
import packages.serviceInterfaces.OglasInterface;

public class OglasService implements OglasInterface{

	private OglasRepository oglasRepository;
	
	
	@Override
	public Oglas addOglas(Oglas oglas) {
		return oglasRepository.save(oglas);
	}

	@Override
	public Oglas getOglasById(Long id) {
		return oglasRepository.findById(id);
	}
	

	@Override
	public Oglas getOglasByIdAndStatus(Long id, OglasStatus status) {
		return null;
	}

	@Override
	public int deleteOglas(Long id) {
		return oglasRepository.deleteById(id);
	}

	@Override
	public Page<Oglas> getAllOglasiByStatus(OglasStatus status, Pageable page) {
		return oglasRepository.findByStatus(status, page);
	}

}
