package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.Karta;
import packages.beans.Projekcija;
import packages.beans.Sediste;
import packages.exceptions.KartaExistsException;

public interface KartaInterface {
	
	public Karta createKarta(Karta karta);
	
	public ArrayList<Karta> createKarte(ArrayList<Karta> karte) throws KartaExistsException;
	
	public void deleteKarta(Karta karta);
	
	public Karta findByProjekcijaAndSediste(Projekcija projekcija, Sediste sediste);
	
}
