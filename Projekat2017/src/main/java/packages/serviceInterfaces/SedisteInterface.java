package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.Sediste;
import packages.beans.Segment;


public interface SedisteInterface {
	
	public Sediste getSediste(Long id);
	
	public Sediste addSediste(Sediste sediste);
	
	public ArrayList<Sediste> getAllSedista();

	public ArrayList<Sediste> getSedistaBySegment(Segment segment);
	
	public int getBrojSedistaBySegment(Segment segment);

}
