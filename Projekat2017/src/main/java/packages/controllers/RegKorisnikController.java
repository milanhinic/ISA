package packages.controllers;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.beans.KorisnikDTO;
import packages.components.KorisnikToKorisnikDTO;
import packages.security.TokenUtils;
import packages.services.KorisnikService;

@RestController
@RequestMapping(value = "app/secured/")
public class RegKorisnikController {

	@Autowired
	private KorisnikService korisnikService; 
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private KorisnikToKorisnikDTO toKorisnikDTO;
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiRegKorisnika", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> vratiRegKorisnika(ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.BAD_REQUEST);
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik korisnik = korisnikService.getKorisnikByEmail(email);
		
		if(korisnik==null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.BAD_REQUEST);
		}
		
		KorisnikDTO korisnikDTO =  toKorisnikDTO.convert(korisnik);
		
		return new ResponseEntity<KorisnikDTO>(korisnikDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "izmena", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> izmeniKorisnika(@RequestBody @Valid KorisnikDTO korisnik, BindingResult result){
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Uspesno izmenjeni licni podaci");
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<Boolean>(false,httpHeader, HttpStatus.OK);
		}
		
		Korisnik zaIzmenu = korisnikService.getKorisnikByEmail(korisnik.getEmail());
		
		if(zaIzmenu==null) {
			httpHeader.set("message", "Ne postojeci korisnik");
			return new ResponseEntity<Boolean>(false,httpHeader, HttpStatus.OK);		
		}
		
		if(korisnik.getTelefon() != null && korisnik.getTelefon().isEmpty())
			korisnik.setTelefon(null);
		
		zaIzmenu.setIme(korisnik.getIme());
		zaIzmenu.setPrezime(korisnik.getPrezime());
		zaIzmenu.setGrad(korisnik.getGrad());
		zaIzmenu.setTelefon(korisnik.getTelefon());
		
		try {
			zaIzmenu = korisnikService.addKorisnik(zaIzmenu);
		}catch(Exception e){
			httpHeader.set("message", "Greska kod izmene podataka");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);		
		}
		
		return new ResponseEntity<Boolean>(true,httpHeader, HttpStatus.OK);
	}
	
}
