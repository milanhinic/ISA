package packages.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Sala;
import packages.beans.Segment;
import packages.beans.TipSegmenta;

public interface SegmentRepository extends JpaRepository<Segment,Long>{

	ArrayList<Segment> findBySala(Sala sala);

	ArrayList<Segment> findByTip(TipSegmenta tip);
	
}
