package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.Oglas;
import packages.beans.Ponuda;

public interface PonudaInterface {

	public Ponuda addPonuda(Ponuda ponuda);
	
	public Ponuda getPonuda(Long id);
	
	public ArrayList<Ponuda> getAllPonuda();
	
	public ArrayList<Ponuda> getPonudeByOglas(Oglas oglas);
	
	public int deletePonuda(Long id);
	
}
