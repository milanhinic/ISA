package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.Skala;
import packages.repositories.SkalaRepository;
import packages.serviceInterfaces.SkalaInterface;

@Service
public class SkalaService implements SkalaInterface{

	@Autowired
	private SkalaRepository sr;
	
	@Override
	public Skala addSkala(Skala skala) {
		return sr.save(skala);
	}

	@Override
	public Skala findById(Long id) {
		return sr.findById(id);
	}

	@Override
	public Skala findByNaziv(String naziv) {
		return sr.findByNaziv(naziv);
	}

	@Override
	public ArrayList<Skala> getAllSkala() {
		return (ArrayList<Skala>) sr.findAll();
	}

	@Override
	public int deleteSklaById(Long id) {
		return (int) sr.deleteById(id);
	}

}
