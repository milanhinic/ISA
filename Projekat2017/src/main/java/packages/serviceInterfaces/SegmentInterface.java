package packages.serviceInterfaces;

import java.util.ArrayList;

import packages.beans.Sala;
import packages.beans.Segment;
import packages.beans.TipSegmenta;

public interface SegmentInterface {

	public Segment getSegment(Long id);
	
	public Segment addSegment(Segment segment);
	
	public ArrayList<Segment> getAllSegments();

	public ArrayList<Segment> getSegmentsBySala(Sala sala);
	
	public ArrayList<Segment> getSegmentsByTip(TipSegmenta tip);
	
	public TipSegmenta getTipSegmenta(Long id);
	
	public TipSegmenta addTipSegmenta(TipSegmenta tipSegmenta);
	
	public ArrayList<TipSegmenta> getAllTipSegments();
	
}
