package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import packages.beans.Karta;
import packages.beans.Projekcija;
import packages.beans.Sediste;
import packages.beans.Segment;

public interface SedisteRepository extends JpaRepository<Sediste, Long>{
	
	public ArrayList<Sediste> findBySegment(Segment segment);
	
	public int countBySegment(Segment segment);

}
