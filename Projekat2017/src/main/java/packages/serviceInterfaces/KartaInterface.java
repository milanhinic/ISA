package packages.serviceInterfaces;

import packages.beans.Karta;
import packages.beans.Projekcija;
import packages.beans.Sediste;

public interface KartaInterface {
	
	public Karta createKarta(Karta karta);
	
	public void deleteKarta(Karta karta);
	
	public Karta findByProjekcijaAndSediste(Projekcija projekcija, Sediste sediste);
	
}
