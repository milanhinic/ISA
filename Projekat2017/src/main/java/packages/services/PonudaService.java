package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.Oglas;
import packages.beans.Ponuda;
import packages.beans.RegistrovaniKorisnik;
import packages.repositories.PonudaRepository;
import packages.serviceInterfaces.PonudaInterface;

@Service
public class PonudaService implements PonudaInterface{

	@Autowired
	private PonudaRepository pr;
	
	
	@Override
	public Ponuda addPonuda(Ponuda ponuda) {
		return pr.save(ponuda);
	}

	@Override
	public Ponuda getPonuda(Long id) {
		return pr.findById(id);
	}

	@Override
	public ArrayList<Ponuda> getAllPonuda() {
		return (ArrayList<Ponuda>) pr.findAll();
	}

	@Override
	public ArrayList<Ponuda> getPonudeByOglas(Oglas oglas) {
		return pr.findByOglas(oglas);
	}

	@Override
	public int deletePonuda(Long id) {
		return (int) pr.deleteById(id);
	}

	@Override
	public ArrayList<Ponuda> getPonudeByRegKor(RegistrovaniKorisnik rk) {
		return pr.findByRk(rk);
	}

	@Override
	public ArrayList<Ponuda> getPonudeByOglasAndRegKor(Oglas oglas, RegistrovaniKorisnik rk) {
		return pr.findByOglasAndRk(oglas, rk);
	}

	@Override
	public ArrayList<Ponuda> getTudjePonudeByOglasAndRegKor(Oglas oglas, RegistrovaniKorisnik rk) {
		return pr.getTudjePonude(oglas, rk);
	}

}
