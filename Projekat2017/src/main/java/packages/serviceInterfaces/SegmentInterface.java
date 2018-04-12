package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.Sala;
import packages.beans.Segment;

public interface SegmentInterface {

	public Segment getSegment(Long id);
	
	public Segment addSegment(Segment segment);
	
	public ArrayList<Segment> getAllSegments();

	public ArrayList<Segment> getSegmentsBySala(Sala sala);
	
}
