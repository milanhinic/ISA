package packages.serviceInterfaces;

import java.util.ArrayList;
import java.util.Date;

import packages.beans.Karta;
import packages.beans.PozBio;
import packages.beans.Projekcija;
import packages.beans.Sediste;
import packages.exceptions.KartaExistsException;

public interface KartaInterface {
	
	public Karta createKarta(Karta karta);
	
	public ArrayList<Karta> createKarte(ArrayList<Karta> karte) throws KartaExistsException;
	
	public void deleteKarta(Karta karta);
	
	public Karta findByProjekcijaAndSediste(Projekcija projekcija, Sediste sediste);
	
	public boolean findByProjekcijaAndSedisteBrza(Projekcija projekcija, Sediste sediste);
	
	public Karta findBySediste(Sediste sediste);
	
	public ArrayList<Karta> vratiBrzeZa(PozBio pozBio, Date datum);
	
	public Karta getKarta(Long id);
	
}
