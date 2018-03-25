package packages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;
import packages.services.KorisnikService;

import org.springframework.http.MediaType;

@RestController
@RequestMapping(value = "app/")
public class RegisterController {
	
	@Autowired
	KorisnikService korisnikService;

	@RequestMapping(value = "registracija", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> registrujKorisnika(@RequestBody Korisnik korisnik) {
		
		if(korisnikService.getKorisnikByEmail(korisnik.getEmail())!=null) {
			HttpHeaders httpHeader = new HttpHeaders();
			httpHeader.add("message", "Ova email adresa je vec iskoriscena");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		}
		
		korisnik.setTip(KorisnikTip.RK);
		korisnik.setStatus(RegKorisnikStatus.N);
			
		korisnik = korisnikService.addKorisnik(korisnik);
	
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}
	
}
