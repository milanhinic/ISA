package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.Oglas;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.RezervisaniOglas;
import packages.repositories.RezervisaniOglasRepository;
import packages.serviceInterfaces.RezervisaniOglasInterface;

@Service
public class RezervisaniOglasService implements RezervisaniOglasInterface{

	@Autowired
	private RezervisaniOglasRepository ror;
	
	
	@Override
	public RezervisaniOglas addRezOgl(RezervisaniOglas ro) {
		return ror.save(ro);
	}

	@Override
	public ArrayList<RezervisaniOglas> findByRegKor(Long rk) {
		return ror.findByRk(rk);
	}

	@Override
	public Page<RezervisaniOglas> findByOglas(Long oglas, Pageable page) {
		return ror.findByOglas(oglas, page);
	}

	public int deleteById(Long id) {
		return (int) ror.deleteById(id);
	}
	
}
