package packages.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.Oglas;
import packages.enumerations.OglasStatus;
import packages.repositories.OglasRepository;
import packages.serviceInterfaces.OglasInterface;

@Service
public class OglasService implements OglasInterface{

	@Autowired
	private OglasRepository oglasRepository;
	
	
	@Override
	public Oglas addOglas(Oglas oglas) {
		return oglasRepository.save(oglas);
	}

	@Override
	public Oglas getOglasById(Long id) {
		return oglasRepository.findOne(id);
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

	@Override
	public Oglas getOglasByNazivAndStatus(String name, OglasStatus status) {
		return oglasRepository.findByNazivAndStatus(name, status);
	}

	@Override
	public ArrayList<Oglas> getAllOglasiByStatus(OglasStatus status) {
		return oglasRepository.findByStatus(status);
	}

	
}
