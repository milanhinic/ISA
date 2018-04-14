package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.Projekcija;
import packages.repositories.ProjekcijaRepository;
import packages.serviceInterfaces.ProjekcijaInterface;

@Service
public class ProjekcijaService implements ProjekcijaInterface{

	@Autowired
	private ProjekcijaRepository projekcijaRepository;
	
	
	
	@Override
	public Projekcija addProjekcija(Projekcija projekcija) {
		return projekcijaRepository.save(projekcija);
	}

	@Override
	public ArrayList<Projekcija> getAllProjekcija() {
		return (ArrayList<Projekcija>)projekcijaRepository.findAll();
	}

	@Override
	public ArrayList<Projekcija> getProjekcijaBySala(String salaName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projekcija getProjekcijaByFilmPre(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Projekcija> getProjekcijaByVreme(String vreme) {
		// TODO Auto-generated method stub
		return null;
	}

}
