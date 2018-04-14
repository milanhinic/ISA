package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.Projekcija;

public interface ProjekcijaInterface {

	public Projekcija addProjekcija(Projekcija projekcija);
	
	public ArrayList<Projekcija> getAllProjekcija();
	
	public ArrayList<Projekcija> getProjekcijaBySala(String salaName);
	
	public Projekcija getProjekcijaByFilmPre(int id);
	
	public ArrayList<Projekcija> getProjekcijaByVreme(String vreme);
	
	
	
}
