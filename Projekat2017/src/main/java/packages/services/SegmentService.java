package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.Sala;
import packages.beans.Segment;
import packages.repositories.SegmentRepository;
import packages.repositories.SegmentTipRepository;
import packages.serviceInterfaces.SegmentInterface;

@Service
public class SegmentService implements SegmentInterface{

	@Autowired
	SegmentRepository sr;
	
	@Autowired
	SegmentTipRepository str;
	
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

}
