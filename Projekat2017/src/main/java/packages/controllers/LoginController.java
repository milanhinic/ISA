package packages.controllers;

import java.util.Arrays;

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

import packages.beans.CustomUserDetailsFactory;
import packages.beans.Korisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;
import packages.security.TokenUtils;
import packages.services.KorisnikService;

@RestController
@RequestMapping(value = "app/")
public class LoginController {

	@Autowired
	private KorisnikService korisnikService; 
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> prijaviKorisnika(@RequestBody Korisnik korisnik) {
		
		korisnik = korisnikService.getKorisnikByEmailAndLozinka(korisnik.getEmail(), korisnik.getLozinka());
		String token = null;
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Uspesno logovanje!");
		char[] lozinka = {'l', 'o' ,'z', 'i', 'n', 'k', 'a', 'a' };		
		if(korisnik==null) {
			httpHeader.set("message", "Neispravno uneseni email ili lozinka.");
			return new ResponseEntity<String>(token,httpHeader,HttpStatus.OK);
		}else {
			if(korisnik.getStatus().equals(RegKorisnikStatus.N)) {
				httpHeader.set("message", "Vas nalog nije aktiviran.");
				return new ResponseEntity<String>(token,httpHeader,HttpStatus.OK);			
			}else if(korisnik.getTip().equals(KorisnikTip.AF) && Arrays.equals(korisnik.getLozinka(), lozinka)) {
				token = tokenUtils.generateToken(CustomUserDetailsFactory.createCustomUserDetails(korisnik));
				httpHeader.set("message", "Promeni");
				return new ResponseEntity<String>(token, httpHeader,HttpStatus.OK);
			}
			
			token = tokenUtils.generateToken(CustomUserDetailsFactory.createCustomUserDetails(korisnik));
		}
				
		return new ResponseEntity<String>(token, httpHeader,HttpStatus.OK);
	}
	
	
}
