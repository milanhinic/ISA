package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.Poruka;
import packages.beans.RegistrovaniKorisnik;
import packages.repositories.PorukaRepository;
import packages.serviceInterfaces.PorukaInterface;

@Service
public class PorukaService implements PorukaInterface{
	
	@Autowired
	private PorukaRepository pr;

	@Override
	public Poruka addPoruka(Poruka poruka) {
		return pr.save(poruka);
	}

	@Override
	public Page<Poruka> getPorukeByRk(RegistrovaniKorisnik rk, Pageable page) {
		return pr.findByRk(rk, page);
	}

	@Override
	public int deleteById(Long id) {
		return pr.deleteById(id);
	}
	
}
