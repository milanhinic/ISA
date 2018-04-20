package packages.controllers;

import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.beans.Oglas;
import packages.beans.Ponuda;
import packages.beans.Poruka;
import packages.beans.RegistrovaniKorisnik;
import packages.enumerations.OglasStatus;
import packages.security.TokenUtils;
import packages.services.KorisnikService;
import packages.services.PorukaService;
import packages.services.RegistrovaniKorisnikService;

@Transactional
@RestController
@RequestMapping(value = "app/secured/")
public class PorukaController {
	
	@Autowired
	private PorukaService pc;

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private RegistrovaniKorisnikService regKorisnikService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "obrisiLicnuPoruku/{id}", method = RequestMethod.DELETE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Integer> obrisiLicnuPoruku(@PathVariable int id, ServletRequest request){
				
		
		int retVal =  pc.deleteById(new Long(id));
		
		if(retVal!=0) {
			return new ResponseEntity<Integer>(retVal, HttpStatus.OK);
		}
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Pokusavate pristupiti nepostojecem oglasu!");
		return new ResponseEntity<Integer>(null, httpHeader, HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiLicnePoruke/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Poruka> getLicnePoruke(@PathVariable int id, ServletRequest request){
		
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
		
		
		Page<Poruka> retVal =  pc.getPorukeByRk(rk, new PageRequest(id-1, 20));
		System.out.println("Retval   ->   " + retVal);
		
		if(retVal.getSize() <= 0) {
			return null;
		}
		
		
		return retVal;
		
	}
	
}
