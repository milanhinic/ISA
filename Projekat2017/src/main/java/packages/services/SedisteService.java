package packages.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import packages.beans.Sediste;
import packages.beans.Segment;
import packages.repositories.SedisteRepository;
import packages.serviceInterfaces.SedisteInterface;

@Service
public class SedisteService implements SedisteInterface{

	@Autowired
	private SedisteRepository sr;
	
	@Override
	public Sediste getSediste(Long id) {
		// TODO Auto-generated method stub
		return sr.findOne(id);
	}

	@Override
	public Sediste addSediste(Sediste sediste) {
		// TODO Auto-generated method stub
		return sr.save(sediste);
	}

	@Override
	public ArrayList<Sediste> getAllSedista() {
		// TODO Auto-generated method stub
		return (ArrayList<Sediste>) sr.findAll();
	}

	@Override
	public ArrayList<Sediste> getSedistaBySegment(Segment segment) {
		// TODO Auto-generated method stub
		return sr.findBySegment(segment);
	}

	@Override
	public int getBrojSedistaBySegment(Segment segment) {
		// TODO Auto-generated method stub
		return sr.countBySegment(segment);
	}

}
