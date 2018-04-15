package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.PredFilm;
import packages.beans.Projekcija;
import packages.beans.Sala;


public interface ProjekcijaInterface {

	public Projekcija getProjekcija(Long id);
	
	public Projekcija addProjekcija(Projekcija projekcija);
	
	public ArrayList<Projekcija> getAllProjekcijas();

	public ArrayList<Projekcija> getProjekcijasByPredFilm(PredFilm predFilm);
	
	public ArrayList<Projekcija> getProjekcijasBySala(Sala sala);
	
}
