package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.PozBio;
import packages.beans.Sala;
import packages.repositories.SalaRepository;
import packages.serviceInterfaces.SalaSegmentInterface;

@Service
public class SalaSegmentService implements SalaSegmentInterface{
	
	@Autowired
	private SalaRepository sr;

	@Override
	public Sala getSala(Long id) {
		// TODO Auto-generated method stub
		return sr.findOne(id);
	}

	@Override
	public Sala addSala(Sala sala) {
		// TODO Auto-generated method stub
		return sr.save(sala);
	}

	@Override
	public ArrayList<Sala> getAllSalas() {
		// TODO Auto-generated method stub
		return (ArrayList<Sala>) sr.findAll();
	}

	@Override
	public ArrayList<Sala> getSalasByPozBio(PozBio pozBio) {
		// TODO Auto-generated method stub
		return sr.findByPozBio(pozBio);
	}


}
