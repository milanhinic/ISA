package packages.serviceInterfaces;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.PozBio;
import packages.beans.PredFilm;
import packages.beans.Projekcija;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Rezervacija;

public interface RezervacijaInterface {

	public Rezervacija createRezervacija(Rezervacija rezervacija);
	
	public void deleteRezervacija(Long id);
	
	public Page<Rezervacija> getByRegKorisnikAndCanCancel(RegistrovaniKorisnik registrovaniKorisnik, Pageable pageable);
	
	public Page<Rezervacija> getHistory(RegistrovaniKorisnik registrovaniKorisnik, Pageable pageable);
	
	public Long countHistory(RegistrovaniKorisnik registrovaniKorisnik);
	
	public Rezervacija findById(Long id);
	
	public Long countByRegKorisnikAndCanCancel(RegistrovaniKorisnik registrovaniKorisnik);
	
	public Rezervacija findOneByKorisnikAndCanCancel(RegistrovaniKorisnik registrovaniKorisnik, Long id);
	
	public Double getPrihod(Projekcija p);
	
	public Integer countVisitsForDate(PozBio pozBio, Date dayStart, Date dayEnd);
}
