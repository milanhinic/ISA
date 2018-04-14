package packages.serviceInterfaces;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.FilmPre;
import packages.enumerations.FilmPreTip;


public interface FilmPreInterface {

	public FilmPre addFilmPre(FilmPre filmPre);
	
	public FilmPre getFilmPrebyName(String name);
	
	public ArrayList<FilmPre> getAllFilmPre(int mode);
	
	public Page<FilmPre> getPozBioList(FilmPreTip tip, Pageable pageable);
	
}
