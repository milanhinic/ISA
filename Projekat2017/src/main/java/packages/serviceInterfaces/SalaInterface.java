package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.PozBio;
import packages.beans.Sala;
import packages.beans.Segment;


public interface SalaInterface {
	
	public Sala getSala(Long id);
	
	public Sala addSala(Sala sala);
	
	public ArrayList<Sala> getAllSalas();

	public ArrayList<Sala> getSalasByPozBio(PozBio pozBio);
	

}
