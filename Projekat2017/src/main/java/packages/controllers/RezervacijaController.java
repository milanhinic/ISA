package packages.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Karta;
import packages.beans.Korisnik;
import packages.beans.Projekcija;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Rezervacija;
import packages.beans.Sala;
import packages.beans.Sediste;
import packages.beans.Segment;
import packages.dto.RezervacijaDTO;
import packages.dto.SedisteDTO;
import packages.dto.SegmentDTO;
import packages.security.TokenUtils;
import packages.services.KartaService;
import packages.services.KorisnikService;
import packages.services.ProjekcijaService;
import packages.services.RegistrovaniKorisnikService;
import packages.services.RezervacijaService;
import packages.services.SedisteService;
import packages.services.SegmentService;

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
	
	@Autowired
	private ProjekcijaService projekcijaService;
	
	@Autowired
	private SegmentService segmentiService;
	
	@Autowired
	private SedisteService sedisteService;
	
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
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="vratiSegmenteProj/{idProj}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Segment>> vratiSegmenteZaProjekciju(@PathVariable int idProj) {
		
		HttpHeaders header = new HttpHeaders();
		
		if(idProj < 1) {
			header.add("message", "Nepostojeca projekcija!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Projekcija projekcija = projekcijaService.getProjekcija(new Long(idProj));
		
		if(projekcija == null) {
			header.add("message", "Greska prilikom ucitavanja projekcije");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		ArrayList<Segment> segmenti = segmentiService.getSegmentsBySala(projekcija.getSala());
		
		if(segmenti == null || segmenti.size()==0) {
			header.add("message", "Greska prilikom ucitavanja segmenata");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		return new ResponseEntity<ArrayList<Segment>>(segmenti, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="vratiSedistaProj/proj={idProj}&seg={idSeg}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<SedisteDTO>> vratiSedistaZaProj(@PathVariable("idProj") int idProj, @PathVariable("idSeg") int idSeg){
		
		HttpHeaders header = new HttpHeaders();
		
		Segment segment = segmentiService.getSegment(new Long(idSeg));
		
		if(segment == null) {
			header.add("message", "Segmenta nije validan!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Projekcija projekcija = projekcijaService.getProjekcija(new Long(idProj));
		if(projekcija==null) {
			header.add("message", "Projekcija nije validna!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
			
		ArrayList<Sediste> sedista = sedisteService.getSedistaBySegment(segment);
		
		if(sedista==null || sedista.size()==0) {
			header.add("message", "Nema sedista!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);	
		}
		
		ArrayList<SedisteDTO> retVal = new ArrayList<SedisteDTO>();
		
		for(Sediste s : sedista) {
			
			Karta karta = kartaService.findByProjekcijaAndSediste(projekcija, s);
			boolean zauzeto = false;
			if(karta!=null)
				zauzeto = true;
			
			SedisteDTO sDTO = new SedisteDTO(s,zauzeto);
			retVal.add(sDTO);		
		}
		
		return new ResponseEntity<ArrayList<SedisteDTO>>(retVal, HttpStatus.OK);
	}
	
	
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "oceni/{mode}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Boolean> oceniAmbijentProjekciju(@PathVariable int mode,  @RequestParam int idProjekcije, @RequestParam int ocena, ServletRequest request){
		
		HttpHeaders header = new HttpHeaders();
		
		if(ocena < 1 || ocena > 5) {
			header.add("message", "Ocena mora biti na skali od 1 do 5.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik korisnik = korisnikService.getKorisnikByEmail(email);
		
		if(korisnik==null) {
			header.add("message", "Nepostojeci korisnik, greska.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		RegistrovaniKorisnik logregKorisnik = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Rezervacija rezervacija = rezervacijaService.findById(new Long(idProjekcije));
		
		if(rezervacija == null) {
			header.add("message", "Nepostojeca rezervacija, pokusajte ponovo.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		if(!rezervacija.getRegKorisnik().getId().equals(logregKorisnik.getId())) {
			header.add("message", "Pokusavate da ocenite tudju rezervaciju!.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		//ambijent
		if(mode == 0) {
			if(rezervacija.getOcenaAmbijenta() != null) {
				header.add("message", "Vec ste ocenili ambijent za ovu rezervaciju.");
				return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
			}
			rezervacija.setOcenaAmbijenta(new Integer(ocena));
			rezervacijaService.createRezervacija(rezervacija);
		//projekcija	
		}else if(mode == 1) {
			if(rezervacija.getOcenaProjekcije() != null) {
				header.add("message", "Vec ste ocenili projekciju vezanu za ovu rezervaciju.");
				return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
			}
			rezervacija.setOcenaProjekcije(new Integer(ocena));
			rezervacijaService.createRezervacija(rezervacija);
		}else {
			header.add("message", "Nepostojeci tip ocene.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "izbrisiOcenu/{mode}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Boolean> izbrisiOcenu(@PathVariable int mode,  @RequestParam int idProjekcije, ServletRequest request){
		
		HttpHeaders header = new HttpHeaders();
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik korisnik = korisnikService.getKorisnikByEmail(email);
		
		if(korisnik==null) {
			header.add("message", "Nepostojeci korisnik, greska.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		RegistrovaniKorisnik logregKorisnik = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Rezervacija rezervacija = rezervacijaService.findById(new Long(idProjekcije));
		
		if(rezervacija == null) {
			header.add("message", "Nepostojeca rezervacija, pokusajte ponovo.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		if(!rezervacija.getRegKorisnik().getId().equals(logregKorisnik.getId())) {
			header.add("message", "Pokusavate da izbrisete ocenu za tudju rezervaciju!.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		//ambijent
		if(mode == 0) {
			rezervacija.setOcenaAmbijenta(null);
			rezervacijaService.createRezervacija(rezervacija);
		//projekcija	
		}else if(mode == 1) {
			rezervacija.setOcenaProjekcije(null);
			rezervacijaService.createRezervacija(rezervacija);
		}else {
			header.add("message", "Nepostojeci tip ocene.");
			return new ResponseEntity<Boolean>(false, header, HttpStatus.OK);
		}
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
}
