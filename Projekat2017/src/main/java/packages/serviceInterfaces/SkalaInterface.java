package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.Skala;

public interface SkalaInterface {

	public Skala addSkala(Skala skala);
	
	public Skala findById(Long id);
	
	public Skala findByNaziv(String naziv);
	
	public ArrayList<Skala> getAllSkala();
	
	public int deleteSklaById(Long id);
	
}
