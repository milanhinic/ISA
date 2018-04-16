package packages.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import packages.beans.RegistrovaniKorisnik;
import packages.beans.Rezervacija;
import packages.repositories.RezervacijaRepository;
import packages.serviceInterfaces.RezervacijaInterface;

@Service
public class RezervacijaService implements RezervacijaInterface{

	@Autowired
	private RezervacijaRepository rezervacijaRepository;

	@Override
	public Rezervacija createRezervacija(Rezervacija rezervacija) {
		
		return rezervacijaRepository.save(rezervacija);
	}

	@Override
	public void deleteRezervacija(Long id) {
		
		rezervacijaRepository.delete(id);
	}

	@Override
	public Page<Rezervacija> getByRegKorisnikAndCanCancel(RegistrovaniKorisnik registrovaniKorisnik, Pageable pageable){
		
		long currentTimeMilisec = System.currentTimeMillis()+30*60*1000;
		Date date = new Date(currentTimeMilisec);
		
		return rezervacijaRepository.findByRegKorisnikAndCanCancel(registrovaniKorisnik, date, pageable);
	}

	@Override
	public Page<Rezervacija> getHistory(RegistrovaniKorisnik registrovaniKorisnik, Pageable pageable) {
		
		long currentTimeMilisec = System.currentTimeMillis()+30*60*1000;
		Date date = new Date(currentTimeMilisec);
		
		return rezervacijaRepository.findHistory(registrovaniKorisnik, date, pageable);
	}

	@Override
	public Rezervacija findById(Long id) {
		
		return rezervacijaRepository.findOne(id);
	}


	@Override
	public Long countHistory(RegistrovaniKorisnik registrovaniKorisnik) {
		
		long currentTimeMilisec = System.currentTimeMillis()+30*60*1000;
		Date date = new Date(currentTimeMilisec);
		
		return rezervacijaRepository.countHistory(registrovaniKorisnik, date);
	}

	@Override
	public Long countByRegKorisnikAndCanCancel(RegistrovaniKorisnik registrovaniKorisnik) {
		
		long currentTimeMilisec = System.currentTimeMillis()+30*60*1000;
		Date date = new Date(currentTimeMilisec);
		
		return rezervacijaRepository.countByRegKorisnikAndCanCancel(registrovaniKorisnik, date);
	}

	@Override
	public Rezervacija findOneByKorisnikAndCanCancel(RegistrovaniKorisnik registrovaniKorisnik, Long id) {

		long currentTimeMilisec = System.currentTimeMillis()+30*60*1000;
		Date date = new Date(currentTimeMilisec);
		
		return rezervacijaRepository.findOneByRegKorisnikAndCanCancel(registrovaniKorisnik, date, id);
	}
	
}
