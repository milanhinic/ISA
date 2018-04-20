package packages.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import packages.beans.Korisnik;
import packages.beans.Oglas;
import packages.beans.PozBio;
import packages.beans.PredFilm;
import packages.beans.RegistrovaniKorisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.OglasStatus;
import packages.enumerations.RegKorisnikStatus;
import packages.security.TokenUtils;
import packages.services.KorisnikService;
import packages.services.OglasService;
import packages.services.RegistrovaniKorisnikService;

@Transactional
@Controller
@RestController
@RequestMapping(value = "app/secured/")
public class OglasController {

	
	@Autowired
	private OglasService os;	
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private RegistrovaniKorisnikService regKorisnikService;
	
	
	@PreAuthorize("hasAuthority('RK') or hasAuthority('AF')")
	@RequestMapping(value = "sacuvajOglas", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Oglas> dodajOglas(@RequestParam("putanja") MultipartFile putanja, @RequestParam("naziv") String naziv,
			@RequestParam("opis") String opis, @RequestParam("aktivnoDo") String aktivnoDo, ServletRequest request) throws IOException{
		
		HttpHeaders httpHeader = new HttpHeaders();
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

		Oglas noviOglas = new Oglas(naziv, opis, aktivnoDo, putanja.getBytes(), OglasStatus.N, logregKorisnik);
		
		if(noviOglas !=null) {
			
			Oglas retVal = os.addOglas(noviOglas);
			
			if(retVal!= null) {
				return new ResponseEntity<Oglas>(retVal, httpHeader, HttpStatus.OK);
			}else {
				httpHeader.add("message", "Neuspesno dodavanje novog oglasa.");
				return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
			}
			
		}
		
		
		httpHeader.add("message", "Neuspesno dodavanje novog oglasa.");
		return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
		
		

	}
	
	@PreAuthorize("hasAuthority('RK') or hasAuthority('AF')")
	@RequestMapping(value = "odobriOglase/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Oglas> getAllOglasi(@PathVariable int id){
		
		Page<Oglas> retVal =  os.getAllOglasiByStatus(OglasStatus.N, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		return retVal;
		
	}
	
	@PreAuthorize("hasAuthority('RK') or hasAuthority('AF')")
	@RequestMapping(value = "dobaviTudjeOglase/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Oglas> getAllTudjiOglasi(@PathVariable int id, ServletRequest request){
		
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
		
		Page<Oglas> retVal =  os.getAllOthersOglasi(logregKorisnik, OglasStatus.N, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		return retVal;
		
	}

	
	@PreAuthorize("hasAuthority('RK') or hasAuthority('AF')")
	@RequestMapping(value = "oglas/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> getOglas(@PathVariable int id){
		
		Oglas retVal =  os.getOglasById(new Long(id));
		if(retVal != null) {
			return new ResponseEntity<Oglas>(retVal, HttpStatus.OK);
		}
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Pokusavate pristupiti nepostojecem oglasu!");
		return new ResponseEntity<Oglas>(null, httpHeader, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAuthority('AF')")
	@RequestMapping(value = "obrisiOglas/{id}", method = RequestMethod.DELETE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Integer> obrisiOglas(@PathVariable int id){
		
		int retVal =  os.deleteOglas(new Long(id));
		if(retVal != 0) {
			return new ResponseEntity<Integer>(retVal, HttpStatus.OK);
		}
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Pokusavate pristupiti nepostojecem oglasu!");
		return new ResponseEntity<Integer>(null, httpHeader, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAuthority('AF')")
	@RequestMapping(value = "odobriOglas", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> odobriOglas(@RequestBody @Valid Oglas oglas, BindingResult result){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
		}else{
			oglas.setStatus(OglasStatus.A);
			Oglas retVal = os.addOglas(oglas);
			
			if(retVal != null) {
				return new ResponseEntity<Oglas>(retVal, httpHeader, HttpStatus.OK);
			}
		}
		
		httpHeader.add("message", "Neuspesno odobravanje oglasa.");
		return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAuthority('RK') or hasAuthority('AF')")
	@RequestMapping(value = "izmeniOglas/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> dodajIzmenjeniOglas(@PathVariable int id, @RequestParam("naziv") String naziv, @RequestParam("opis") String opis, @RequestParam("aktivnoDo") String aktivnoDo) throws IOException{
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(naziv == null || opis == null || aktivnoDo == null) {
			httpHeader.add("message", "Neuspesna izmena oglasa, nevalidan objekat.");
			return new ResponseEntity<Oglas>(null, httpHeader, HttpStatus.OK);
		}
		
		
		
		Oglas noviOglas = os.getOglasById(new Long(id));
		noviOglas.setNaziv(naziv);
		noviOglas.setOpis(opis);
		noviOglas.setAktivnoDo(aktivnoDo);
		noviOglas.setStatus(OglasStatus.A);

		
		if(noviOglas !=null) {
			
			Oglas retVal = os.addOglas(noviOglas);
			if(retVal!= null) {
				return new ResponseEntity<Oglas>(retVal, httpHeader, HttpStatus.OK);
			}else {
				httpHeader.add("message", "Neuspesno izmena oglasa.");
				return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
			}
			
		}
		
		
		httpHeader.add("message", "Neuspesno dodavanje novog oglasa.");
		return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
	}
	 
	@PreAuthorize("hasAuthority('RK') or hasAuthority('AF')")
	@RequestMapping(value = "izmeniOglasSaSlikom/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> dodajIzmenjeniOglasSaSlikom(@PathVariable int id,@RequestParam("putanja") MultipartFile putanja, @RequestParam("naziv") String naziv, @RequestParam("opis") String opis, @RequestParam("aktivnoDo") String aktivnoDo,  ServletRequest request) throws IOException{
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(naziv == null || opis == null || aktivnoDo == null) {
			httpHeader.add("message", "Neuspesna izmena oglasa, nevalidan objekat.");
			return new ResponseEntity<Oglas>(null, httpHeader, HttpStatus.OK);
		}
		
		Oglas noviOglas = os.getOglasById(new Long(id));
		noviOglas.setNaziv(naziv);
		noviOglas.setOpis(opis);
		noviOglas.setAktivnoDo(aktivnoDo);
		noviOglas.setStatus(OglasStatus.A);
		noviOglas.setPath(putanja.getBytes());
		
		if(noviOglas !=null) {
			
			Oglas retVal = os.addOglas(noviOglas);
			if(retVal!= null) {
				return new ResponseEntity<Oglas>(retVal, httpHeader, HttpStatus.OK);
			}else {
				httpHeader.add("message", "Neuspesno izmena oglasa.");
				return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
			}
			
		}	
	 
		
		httpHeader.add("message", "Neuspesno dodavanje novog oglasa.");
		return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
		
		
	}

	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "dobaviLicneOglase/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Oglas> getAllOdobreniOglasi(@PathVariable int id, ServletRequest request){
		
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

		RegistrovaniKorisnik rk = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		
		Page<Oglas> retVal =  os.getOglasiByRegKorAndStatus(rk, OglasStatus.A, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		return retVal;
		
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "dalijemoj/{oglasId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Boolean> daLiJeLicniOglas(@PathVariable int oglasId, ServletRequest request){
		
		HttpHeaders httpHeader = new HttpHeaders();
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

		RegistrovaniKorisnik rk = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Oglas oglas = os.getOglasById(new Long(oglasId));
		
		if(oglas != null) {
			if(oglas.getRk().getId().equals(rk.getId())) {
				httpHeader.add("message", "Vlasnik je.");
				return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
			}else {
				httpHeader.add("message", "Nije vlasnik je.");
				return new ResponseEntity<Boolean>(true, httpHeader, HttpStatus.OK);
			}
		}
		httpHeader.add("message", "Greska");
		return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		
		
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "dobaviOpsteOglase", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Oglas>> getOpsteOglase(ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		ArrayList<Korisnik> adminiFz =  korisnikService.getAllKorisnikListArray(RegKorisnikStatus.A, KorisnikTip.AF);
		ArrayList<Oglas> opstiOglasi = new ArrayList<Oglas>();
		ArrayList<Oglas> pomocniOglasi;
		
		if(adminiFz.size() <= 0) {
			return new ResponseEntity<ArrayList<Oglas>>(opstiOglasi, HttpStatus.OK);
		}

		ArrayList<RegistrovaniKorisnik> regAdminFz = new ArrayList<RegistrovaniKorisnik>();
		
		System.out.println("Korsnici admini +  " + adminiFz);
		
		for(Korisnik pom : adminiFz) {
			
			RegistrovaniKorisnik rk =  regKorisnikService.getKorisnikId(pom);
			
			if(rk != null) {
				regAdminFz.add(rk);;
			}
			
		}
		
		for(RegistrovaniKorisnik pom : regAdminFz) {
			
			pomocniOglasi  =  os.getOglasiByRegKorAndStatusArray(pom, OglasStatus.A);
			
			for(Oglas o : pomocniOglasi) {
				opstiOglasi.add(o);
				
			}
			
		}

		return new ResponseEntity<ArrayList<Oglas>>(opstiOglasi, HttpStatus.OK);
		
	}
	
	/*
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "rezervisiOglas/{oglasId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> rezervisiOlgas(@PathVariable int oglasId, ServletRequest request){
		
		HttpHeaders httpHeader = new HttpHeaders();
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

		RegistrovaniKorisnik rk = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Oglas oglas = os.getOglasById(new Long(oglasId));
		
		if(oglas != null) {
			
			
			
			
		}
		
		
		httpHeader.add("message", "Greska");
		return null;
		
		
	}
	*/
	
}
