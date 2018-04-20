package packages.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.Oglas;
import packages.beans.RegistrovaniKorisnik;
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
		return oglasRepository.findByIdAndStatus(id, status);
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

	@Override
	public Page<Oglas> getOglasiByRegKorAndStatus(RegistrovaniKorisnik rk, OglasStatus status, Pageable page) {
		return oglasRepository.findByRegKorisnikAndStatus(rk, status, page);
	}

	@Override
	public Page<Oglas> getAllOthersOglasi(RegistrovaniKorisnik rk, OglasStatus status, Pageable page) {
		return oglasRepository.findOthersOglasi(rk, status, page);
	}

	@Override
	public ArrayList<Oglas> getOglasiByRegKorAndStatusArray(RegistrovaniKorisnik rk, OglasStatus status) {
		return oglasRepository.findByRegKorisnikAndStatusArray(rk, status);
	}

	
	
}
