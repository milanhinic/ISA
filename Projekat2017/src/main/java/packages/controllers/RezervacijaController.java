package packages.controllers;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Karta;
import packages.beans.Korisnik;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Rezervacija;
import packages.dto.RezervacijaDTO;
import packages.security.TokenUtils;
import packages.services.KartaService;
import packages.services.KorisnikService;
import packages.services.RegistrovaniKorisnikService;
import packages.services.RezervacijaService;

@RestController
@RequestMapping(value = "app/secured/")
public class RezervacijaController {

	@Autowired
	private KartaService kartaService;
	
	@Autowired
	private RezervacijaService rezervacijaService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private RegistrovaniKorisnikService regKorisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiRezervacije/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<RezervacijaDTO> vratiRezervacije(@PathVariable int page, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik korisnik = korisnikService.getKorisnikByEmail(email);
		
		if(korisnik==null) {
			return null;
		}
		
		RegistrovaniKorisnik logregKorisnik = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Long rezervacijeCount = rezervacijaService.countByRegKorisnikAndCanCancel(logregKorisnik);
		
		if(rezervacijeCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(rezervacijeCount/10)+1;
		
		Page<Rezervacija> rezervacije = null;
		
		if(page>poslednja) {
			rezervacije = rezervacijaService.getByRegKorisnikAndCanCancel(logregKorisnik, new PageRequest(poslednja-1,10));
		}else {
			rezervacije = rezervacijaService.getByRegKorisnikAndCanCancel(logregKorisnik, new PageRequest(page-1,10));
		}
		
		if(rezervacije==null || rezervacije.getContent().size()<=0) {
			return null;
		}
		
		ArrayList<RezervacijaDTO> retVal = new ArrayList<RezervacijaDTO>();
		
		for(Rezervacija r : rezervacije.getContent()) {
				
			String nazivProj = r.getKarta().getProjekcija().getPredFilm().getNaziv();
			String nazivPozBio = r.getKarta().getProjekcija().getSala().getPozBio().getNaziv();
			String nazivSala = r.getKarta().getProjekcija().getSala().getNaziv();
			String tipSegmenta = r.getKarta().getSediste().getSegment().getTip().getNaziv();
			double cena = r.getKarta().getSediste().getSegment().getTip().getCena();
			Long sedisteId = r.getKarta().getSediste().getId();
			Long projekcijaId = r.getKarta().getProjekcija().getId();
			
			String datum = new SimpleDateFormat("dd-MM-YYYY HH-mm").format(new Timestamp(r.getKarta().getProjekcija().getDatum().getTime()));
			
			RezervacijaDTO rezervacijaDTO = new RezervacijaDTO(r.getId(), nazivProj, nazivPozBio, nazivSala, tipSegmenta, cena, sedisteId, datum, r.getOcenaAmbijenta(), r.getOcenaProjekcije(),projekcijaId);
			retVal.add(rezervacijaDTO);
		}
		
		return retVal;
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "otkaziRezervaciju/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> otkaziRezervaciju(@PathVariable int id, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);
		
		Korisnik korisnik = korisnikService.getKorisnikByEmail(email);
		
		if(korisnik==null) {
			return null;
		}
		
		RegistrovaniKorisnik logregKorisnik = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		if(logregKorisnik==null){
			return null;
		}

		HttpHeaders httpHeader = new HttpHeaders();
		
		Rezervacija rezervacija = rezervacijaService.findById(new Long(id));
		
		if(rezervacija==null) {			
			httpHeader.add("message", "Rezervacija koju zelite da otkazete ne postoji");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		}
		
		if(rezervacija.getRegKorisnik().getId()!=logregKorisnik.getId()) {	
			httpHeader.add("message", "Nemate pravo da otkazete ovu rezervaciju");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		}
		
		Rezervacija zaBrisanje = rezervacijaService.findOneByKorisnikAndCanCancel(logregKorisnik, new Long(id));
		
		if(zaBrisanje==null) {
			
			httpHeader.add("message", "Isteklo je vreme za otkazivanje ove rezervacije");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
			
		}
		
		Karta karta = zaBrisanje.getKarta();
		rezervacijaService.deleteRezervacija(new Long(id));
		kartaService.deleteKarta(karta);
		
		return new ResponseEntity<Boolean>(true, httpHeader, HttpStatus.OK);

	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiIstoriju/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<RezervacijaDTO> vratiIstoriju(@PathVariable int page, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik korisnik = korisnikService.getKorisnikByEmail(email);
		
		if(korisnik==null) {
			return null;
		}
		
		RegistrovaniKorisnik logregKorisnik = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Long istorijaCount = rezervacijaService.countHistory(logregKorisnik);
		
		if(istorijaCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(istorijaCount/10)+1;
		
		Page<Rezervacija> istorija = null;
		
		if(page>poslednja) {
			istorija = rezervacijaService.getHistory(logregKorisnik, new PageRequest(poslednja-1,10));
		}else {
			istorija = rezervacijaService.getHistory(logregKorisnik, new PageRequest(page-1,10));
		}
		
		if(istorija==null || istorija.getContent().size()<=0) {
			return null;
		}
		
		ArrayList<RezervacijaDTO> retVal = new ArrayList<RezervacijaDTO>();
		
		for(Rezervacija r : istorija.getContent()) {
				
			String nazivProj = r.getKarta().getProjekcija().getPredFilm().getNaziv();
			String nazivPozBio = r.getKarta().getProjekcija().getSala().getPozBio().getNaziv();
			String nazivSala = r.getKarta().getProjekcija().getSala().getNaziv();
			String tipSegmenta = r.getKarta().getSediste().getSegment().getTip().getNaziv();
			double cena = r.getKarta().getSediste().getSegment().getTip().getCena();
			Long sedisteId = r.getKarta().getSediste().getId();
			Long projekcijaId = r.getKarta().getProjekcija().getId();
			
			String datum = new SimpleDateFormat("dd-MM-YYYY HH-mm").format(new Timestamp(r.getKarta().getProjekcija().getDatum().getTime()));
			
			RezervacijaDTO rezervacijaDTO = new RezervacijaDTO(r.getId(), nazivProj, nazivPozBio, nazivSala, tipSegmenta, cena, sedisteId, datum, r.getOcenaAmbijenta(), r.getOcenaProjekcije(), projekcijaId);
			retVal.add(rezervacijaDTO);
		}
		
		return retVal;
	}
	
	
}
