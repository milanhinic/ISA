package packages.controllers;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;
import packages.services.EmailService;
import packages.services.KorisnikService;

import org.springframework.http.MediaType;

@RestController
@RequestMapping(value = "app/")
public class RegisterController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "registracija", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> registrujKorisnika(@RequestBody @Valid Korisnik korisnik, BindingResult result) {
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Korisnik uspesno registrovan");
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<Boolean>(false,httpHeader, HttpStatus.OK);
		}
	
		if(korisnik.getTelefon() != null && korisnik.getTelefon().isEmpty())
			korisnik.setTelefon(null);
		
		korisnik.setTip(KorisnikTip.RK);
		korisnik.setStatus(RegKorisnikStatus.N);
		
		try {
			korisnik = korisnikService.addKorisnik(korisnik);
		}catch(Exception e){
			httpHeader.set("message", "Greska kod unosa podataka");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);		
		}
		
		try {
			emailService.sendConfirmationMail(korisnik);
		} catch (MessagingException e) {
			System.out.println("Neuspesno poslat mail");
		}
		
		return new ResponseEntity<Boolean>(true,httpHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value = "aktivirajNalog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Void> aktivirajKorisnika(@PathVariable long id, HttpServletResponse httpServletResponse) {
		
		Korisnik korisnik = korisnikService.getKorisnik(id);
		if(korisnik!=null) {
			if(korisnik.getStatus().equals(RegKorisnikStatus.N)) {
				korisnik.setStatus(RegKorisnikStatus.A);
				korisnikService.addKorisnik(korisnik);
				try {
					httpServletResponse.sendRedirect("http://localhost:4200/aktiviranNalog");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
