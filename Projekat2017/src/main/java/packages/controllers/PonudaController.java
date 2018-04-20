package packages.controllers;

import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.beans.Oglas;
import packages.beans.Ponuda;
import packages.beans.Poruka;
import packages.beans.PozBio;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Sala;
import packages.enumerations.OglasStatus;
import packages.security.TokenUtils;
import packages.services.KorisnikService;
import packages.services.OglasService;
import packages.services.PonudaService;
import packages.services.PorukaService;
import packages.services.RegistrovaniKorisnikService;


@Controller
@Transactional
@RestController
@RequestMapping(value = "app/secured/")
public class PonudaController {

	@Autowired
	private OglasService oc;
	
	@Autowired
	private PonudaService pc;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private RegistrovaniKorisnikService regKorisnikService;
	
	@Autowired
	private PorukaService porSer;
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "dodajPonudu/{oglasId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ponuda> dodajPonudu(@RequestBody Ponuda novaPonuda, @PathVariable int oglasId, ServletRequest request){
		
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

		RegistrovaniKorisnik posiljalac = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		if(novaPonuda.getIznos().equals(0)) {
			httpHeader.add("message", "Neuspesno kreiranje nove ponuda, nevalidan objekat.");
			return new ResponseEntity<Ponuda>(null, httpHeader, HttpStatus.OK);
		}
		
		
		Oglas oglas =  oc.getOglasById(new Long(oglasId));
		
		if(oglas == null) {
			httpHeader.add("message", "Neuspesno kreiranje nove ponude,nepostojeci oglas.");
			return new ResponseEntity<Ponuda>(null, httpHeader, HttpStatus.OK);
		}
		
		Ponuda novaNovaPonuda = new Ponuda(oglas, novaPonuda.getIznos(), posiljalac);
		novaNovaPonuda.setOglas(oglas);
		
		
		Ponuda retVal = pc.addPonuda(novaNovaPonuda);
		
		if(retVal != null) {
			httpHeader.add("message", "Uspesno kreirana nove ponuda.");
			return new ResponseEntity<Ponuda>(retVal, httpHeader, HttpStatus.OK);
		}
		
		httpHeader.add("message", "Neuspesno kreiranje nove ponude.");
		return new ResponseEntity<Ponuda>(null, httpHeader, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiPonude/{idOglasa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Ponuda>> vratiPonude(@PathVariable int idOglasa){
		
		Oglas oglas =  oc.getOglasById(new Long(idOglasa));
		
		ArrayList<Ponuda> ponude = pc.getPonudeByOglas(oglas);
		
		if(ponude.size() == 0) {
			System.out.println("------------------>  Nema ponuuuuuuuuuuuda");
		}
		
		return new ResponseEntity<ArrayList<Ponuda>>(ponude, HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiMojePonude/{idOglasa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Ponuda>> vratiMojePonude(@PathVariable int idOglasa , ServletRequest request){
		
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

		RegistrovaniKorisnik posiljalac = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Oglas oglas =  oc.getOglasById(new Long(idOglasa));
		
		ArrayList<Ponuda> sale = pc.getPonudeByOglasAndRegKor(oglas, posiljalac);
		
		return new ResponseEntity<ArrayList<Ponuda>>(sale, HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiTudjePonude/{idOglasa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Ponuda>> vratiTudjePonude(@PathVariable int idOglasa , ServletRequest request){
		
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

		RegistrovaniKorisnik posiljalac = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Oglas oglas =  oc.getOglasById(new Long(idOglasa));
		
		ArrayList<Ponuda> sale = pc.getTudjePonudeByOglasAndRegKor(oglas, posiljalac);
		
		return new ResponseEntity<ArrayList<Ponuda>>(sale, HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiJednuPonudu/{idPonude}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Ponuda> vratiJedniuPonudu(@PathVariable int idPonude , ServletRequest request){
		
		HttpHeaders httpHeader = new HttpHeaders();
		Ponuda retVal = pc.getPonuda(new Long(idPonude));
		
		
		if(retVal!= null) {
			httpHeader.add("message", "Uspesno sacuvana izmenjena ponuda.");
			return new ResponseEntity<Ponuda>(retVal, httpHeader,HttpStatus.OK);
		}
		
		System.out.println("Prazna ponuda");
		
		httpHeader.add("message", "Neuspesno sacuvana izmenjena ponuda");
		return new ResponseEntity<Ponuda>(null, httpHeader,HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "sacuvajIzmenjenuPonudu/{idOglasa}/{idPonude}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Ponuda> sacuvajIzmenjenuPonudu(@RequestBody Ponuda novaPonuda , @PathVariable int idOglasa, @PathVariable int idPonude ,ServletRequest request){
		
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

		RegistrovaniKorisnik posiljalac = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		Oglas oglas = oc.getOglasById(new Long(idOglasa));
		Ponuda staraPonuda = pc.getPonuda(new Long(idPonude));
		
		staraPonuda.setIznos(novaPonuda.getIznos());
		//novaPonuda.setOglas(oglas);
		//novaPonuda.setRk(posiljalac);
		
		Ponuda retVal = pc.addPonuda(staraPonuda);
		
		if(retVal!= null) {
			httpHeader.add("message", "Uspesno dobavljanje  ponude.");
			return new ResponseEntity<Ponuda>(retVal, httpHeader,HttpStatus.OK);
		}
		
		httpHeader.add("message", "Neuspesno dobavljanje  ponude.");
		return new ResponseEntity<Ponuda>(null, httpHeader,HttpStatus.OK);
		
	}
	
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "obrisiPonudu/{idPonude}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Integer> sacuvajIzmenjenuPonudu(@PathVariable int idPonude){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		int retVal = pc.deletePonuda(new Long(idPonude));
	
		System.out.println("Rezultat brsanja --->" + retVal);
		
		if(retVal <= 0) {
			httpHeader.add("message", "Uspesno dobavljanje  ponude.");
			return new ResponseEntity<Integer>(retVal, httpHeader,HttpStatus.OK);
		}
		
		httpHeader.add("message", "Neuspesno dobavljanje  ponude.");
		return new ResponseEntity<Integer>(null, httpHeader,HttpStatus.OK);
		
	}
	
	

	@RequestMapping(value = "prihvatiPonudu/{oglasId}/{ponudaId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> prihvatiPonudu(@PathVariable int oglasId, @PathVariable int ponudaId,ServletRequest request){
		
		HttpHeaders httpHeader = new HttpHeaders();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		Oglas maticniOglas = oc.getOglasByIdAndStatus(new Long(oglasId), OglasStatus.A);
		
		if(maticniOglas == null) {
			httpHeader.add("message", "Neuspesno prihvatanje izabrane ponude.");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		}
		
		String potvrdnaPoruka = "Postovani, vasa ponuda za oglas "+maticniOglas.getNaziv()+ " je prihvacena. Pazarisli ste rekvizit. Uzivajte";
		String obavestenje = "Postovani, vasa ponuda za oglas "+maticniOglas.getNaziv()+ " je odbijena. Vise srece drugi put.";
		
		ArrayList<Ponuda> prilozenePonude =  pc.getPonudeByOglas(maticniOglas);
		
		if(prilozenePonude.size() == 0) {
			httpHeader.add("message", "Nema ponuda. Greska  pri dobavljanju.");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		}
		Ponuda izabranaPonuda = pc.getPonuda(new Long(ponudaId));
		System.out.println("IP --->  " + izabranaPonuda);
		
		maticniOglas.setStatus(OglasStatus.P);
		Oglas oglasRet = oc.addOglas(maticniOglas);
		
		
		if(izabranaPonuda == null) {
			httpHeader.add("message", "Nema nema izabrane ponude.");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		}

		for(Ponuda ponuda : prilozenePonude) {
			if(ponuda.getId().equals(izabranaPonuda.getId())) {
				
				RegistrovaniKorisnik odabraniRegKor = izabranaPonuda.getRk();
		
				if(odabraniRegKor == null) {
					httpHeader.add("message", "Nema vlasnika izabrane ponude.");
					return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
					
				}
				
				Poruka retVal = new Poruka(potvrdnaPoruka, odabraniRegKor);	
				
				Poruka retPor = porSer.addPoruka(retVal);
				
				if(retPor == null) {
					httpHeader.add("message", "Greska pri slanju pot. poruke.");
					return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
				}
				
				
			}else {
				
				RegistrovaniKorisnik primalac = ponuda.getRk();
				
				if(primalac == null) {
					httpHeader.add("message", "Nema vlasnika izabrane ponude.");
					return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
					
				}
				
				Poruka retVal = new Poruka(obavestenje, primalac);
				
				Poruka retPor = porSer.addPoruka(retVal);
				
				if(retPor == null) {
					httpHeader.add("message", "Greska pri slanju obavestenja poruke.");
					return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
				}
				
				
			}
			
			
		}
		
		
		httpHeader.add("message", "Uspesno.");
		return new ResponseEntity<Boolean>(true, httpHeader, HttpStatus.OK);
		
	}
	
}
