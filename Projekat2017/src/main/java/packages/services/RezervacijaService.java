package packages.services;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import packages.beans.Karta;
import packages.beans.PozBio;
import packages.beans.Projekcija;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Rezervacija;
import packages.exceptions.KartaExistsException;
import packages.repositories.KartaRepository;
import packages.repositories.RezervacijaRepository;
import packages.serviceInterfaces.RezervacijaInterface;

@Service
public class RezervacijaService implements RezervacijaInterface{

	@Autowired
	private RezervacijaRepository rezervacijaRepository;
	
	@Autowired 
	private KartaRepository kartaRepository;

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

	@Override
	public Double getPrihod(Projekcija p) {
		
		return rezervacijaRepository.getPrihod(p);
	}

	@Override
	public Integer countVisitsForDate(PozBio pozBio, Date dayStart, Date dayEnd) {
		
		return rezervacijaRepository.countVisitsForDate(pozBio, dayStart, dayEnd);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Rezervacija createBrzaRezervacija(Rezervacija rezervacija) throws KartaExistsException{
		
		ArrayList<Rezervacija> res = rezervacijaRepository.findByKarta(rezervacija.getKarta());
		
		if(res.size() > 0) {
			throw new KartaExistsException("Karta je vec rezervisana!");
		}
		
		return rezervacijaRepository.save(rezervacija);
	}

	@Override
	public ArrayList<Rezervacija> findByKarta(Karta karta) {
		// TODO Auto-generated method stub
		return rezervacijaRepository.findByKarta(karta);
	}

}
