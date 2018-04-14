package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.FilmPre;
import packages.beans.PozBio;
import packages.enumerations.FilmPreTip;
import packages.repositories.FilmPreRepository;
import packages.serviceInterfaces.FilmPreInterface;

@Service
public class FilmPreService implements FilmPreInterface{

	@Autowired
	private FilmPreRepository filmPreRepository;
	
	
	@Override
	public FilmPre addFilmPre(FilmPre filmPre) {
		return filmPreRepository.save(filmPre);
	}

	@Override
	public FilmPre getFilmPrebyName(String name) {
		return null;
	}

	@Override
	public ArrayList<FilmPre> getAllFilmPre(int mode) {
		if(mode == 1) {
				
		}
		return (ArrayList<FilmPre>) filmPreRepository.findAll();
		
	}

	@Override
	public Page<FilmPre> getPozBioList(FilmPreTip tip, Pageable pageable) {
		return filmPreRepository.findByTip(tip, pageable);
	}

}
