package packages.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Karta;
import packages.beans.Korisnik;
import packages.beans.PozBio;
import packages.beans.Projekcija;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Rezervacija;
import packages.beans.Sediste;
import packages.security.TokenUtils;
import packages.services.KartaService;
import packages.services.KorisnikService;
import packages.services.PozBioService;
import packages.services.ProjekcijaService;
import packages.services.RegistrovaniKorisnikService;
import packages.services.RezervacijaService;
import packages.services.SedisteService;

@RestController
@RequestMapping(value = "app/")
public class KartaController {
	
	@Autowired
	private SedisteService ss;
	
	@Autowired
	private KartaService ks;
	
	@Autowired
	private ProjekcijaService ps;
	
	@Autowired
	private RezervacijaService rzs;
	
	@Autowired
	private PozBioService pbs;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private RegistrovaniKorisnikService regKorisnikService;

	@RequestMapping(value = "formirajBrzu/{idProj}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Boolean> formirajBrzu(@RequestBody ArrayList<Long> zaBrzu, @PathVariable int idProj){
		
		HttpHeaders header = new HttpHeaders();
		
		if(zaBrzu == null) {
			header.add("message", "Neispravna lista sedista!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Projekcija projekcija = ps.getProjekcija(new Long(idProj));
		
		if(projekcija == null) {
			header.add("message", "Nepostojeca projekcija!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		if(!zaBrzu.isEmpty() ){
			for(Long sedId : zaBrzu) {
				Sediste sediste = ss.getSediste(sedId);
				if(ks.findByProjekcijaAndSedisteBrza(projekcija, sediste)) {
					Karta brza = new Karta(null, projekcija, sediste, true);
					ks.createKarta(brza);
					System.out.println(brza.toString());
				}
			}
		}
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@RequestMapping(value = "vratiBrzu/{idPozBio}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Karta>> formirajBrzu(@PathVariable int idPozBio, @RequestParam String datum){
	
		HttpHeaders header = new HttpHeaders();
		
		PozBio pozBio = pbs.getPozBio(new Long(idPozBio));
		
		if(pozBio == null) {
			header.add("message", "Nepostojeca projekcija!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		String tempDat = datum.substring(0, 24);
		
		DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
		Date pocetak = null;
		try {
			pocetak = (Date)formatter.parse(tempDat);        

		} catch (ParseException e) {
			header.add("message", "Neispravan format datuma!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		ArrayList<Karta> retVal = ks.vratiBrzeZa(pozBio, pocetak);
		
		return new ResponseEntity<ArrayList<Karta>>(retVal, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "rezervisiBrzo/{idKarta}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> rezervisiBrzo(@PathVariable int idKarta, ServletRequest request){
		
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
		
		RegistrovaniKorisnik formira = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Karta karta = ks.getKarta(new Long(idKarta));
		
		if(karta == null) {
			return null;
		}
		
		Rezervacija novaRez = new Rezervacija(null, karta, formira, null, null);
		rzs.createRezervacija(novaRez);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	
}
