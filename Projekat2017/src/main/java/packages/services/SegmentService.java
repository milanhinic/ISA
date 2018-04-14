package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.Sala;
import packages.beans.Segment;
import packages.beans.TipSegmenta;
import packages.repositories.SedisteRepository;
import packages.repositories.SegmentRepository;
import packages.repositories.SegmentTipRepository;
import packages.serviceInterfaces.SegmentInterface;

@Service
public class SegmentService implements SegmentInterface{

	@Autowired
	private SegmentRepository sr;
	
	@Autowired
	private SegmentTipRepository str;
	
	
	@Override
	public Segment getSegment(Long id) {
		// TODO Auto-generated method stub
		return sr.findOne(id);
	}

	@Override
	public Segment addSegment(Segment segment) {
		// TODO Auto-generated method stub
		return sr.save(segment);
	}

	@Override
	public ArrayList<Segment> getAllSegments() {
		// TODO Auto-generated method stub
		return (ArrayList<Segment>) sr.findAll();
	}

	@Override
	public ArrayList<Segment> getSegmentsBySala(Sala sala) {
		// TODO Auto-generated method stub
		return sr.findBySala(sala);
	}

	@Override
	public ArrayList<Segment> getSegmentsByTip(TipSegmenta tip) {
		// TODO Auto-generated method stub
		return sr.findByTip(tip);
	}

	@Override
	public TipSegmenta getTipSegmenta(Long id) {
		// TODO Auto-generated method stub
		return str.findOne(id);
	}

	@Override
	public TipSegmenta addTipSegmenta(TipSegmenta tipSegmenta) {
		// TODO Auto-generated method stub
		return str.save(tipSegmenta);
	}

	@Override
	public ArrayList<TipSegmenta> getAllTipSegments() {
		// TODO Auto-generated method stub
		return (ArrayList<TipSegmenta>) str.findAll();
	}

}
