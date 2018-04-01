package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.PozBio;
import packages.beans.Sala;


public interface SalaSegmentInterface {
	
	public Sala getSala(Long id);
	
	public Sala addSala(Sala sala);
	
	public ArrayList<Sala> getAllSalas();

	public ArrayList<Sala> getSalasByPozBio(PozBio pozBio);
	

}
