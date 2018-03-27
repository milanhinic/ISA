package packages.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.beans.KorisnikDTO;
import packages.components.KorisnikToKorisnikDTO;
import packages.enumerations.RegKorisnikStatus;
import packages.services.KorisnikService;

@RestController
@RequestMapping(value = "app/")
public class LoginController {

	@Autowired
	private KorisnikService korisnikService; 
	
	@Autowired
	private KorisnikToKorisnikDTO toKorisnikDTO;
	
	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KorisnikDTO> prijaviKorisnika(@RequestBody Korisnik korisnik) {
		
		korisnik = korisnikService.getKorisnikByEmailAndLozinka(korisnik.getEmail(), korisnik.getLozinka());
		KorisnikDTO korisnikDTO = null;
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Uspesno logovanje!");
		
		if(korisnik==null) {
			httpHeader.set("message", "Neispravno uneseni email ili lozinka.");
			return new ResponseEntity<KorisnikDTO>(null,httpHeader,HttpStatus.OK);
		}else {
			if(korisnik.getStatus().equals(RegKorisnikStatus.N)) {
				httpHeader.set("message", "Vas nalog nije aktiviran.");
				return new ResponseEntity<KorisnikDTO>(null,httpHeader,HttpStatus.OK);			
			}
			
			//Cookie cookie = new Cookie("sessionId", korisnik.getId().toString());
			//response.addCookie(cookie);
			korisnikDTO = toKorisnikDTO.convert(korisnik);
			
		}
		
		return new ResponseEntity<KorisnikDTO>(korisnikDTO,httpHeader,HttpStatus.OK);
	}
	
	
}
