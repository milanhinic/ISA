package packages.controllers;

import java.io.IOException;
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
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Skala;
import packages.enumerations.OglasStatus;
import packages.services.SkalaService;

@Transactional
@Controller
@RestController
@RequestMapping(value = "app/secured/")
public class SkalaController {

	@Autowired
	private SkalaService ss;
	
	@PreAuthorize("hasAuthority('AS')")
	@RequestMapping(value = "sacuvajSkalu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Skala> dodajSkalu(@RequestBody @Valid Skala novaSkala, ServletRequest request) throws IOException{
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		Skala retVal = ss.addSkala(novaSkala);
		
		
		if(retVal !=null) {
	
			httpHeader.add("message", "Uspesno dodavanje nove skale.");
			return new ResponseEntity<Skala>(retVal,httpHeader, HttpStatus.OK);
			
		}
			
		httpHeader.add("message", "Neuspesno dodavanje novoe skale.");
		return new ResponseEntity<Skala>(null,httpHeader, HttpStatus.OK);
		
		
	}
	
	
	@PreAuthorize("hasAuthority('AS')")
	@RequestMapping(value = "skala/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Skala> getSkalu(@PathVariable int id){
		
		HttpHeaders httpHeader = new HttpHeaders();
		Skala retVal = ss.findById(new Long(id));
		
		if(retVal !=null) {
			
			httpHeader.add("message", "Uspesno dobavljanje skale.");
			return new ResponseEntity<Skala>(retVal,httpHeader, HttpStatus.OK);
			
		}
			
		httpHeader.add("message", "Neuspesno dobavljanje skale.");
		return new ResponseEntity<Skala>(null,httpHeader, HttpStatus.OK);
		
		
	}
	
	
	@PreAuthorize("hasAuthority('AS')")
	@RequestMapping(value = "vratiskale", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Skala>> getAllSkale(){
		
		HttpHeaders httpHeader = new HttpHeaders();
		ArrayList<Skala> retVal = ss.getAllSkala();
		
		if(retVal !=null) {
			
			httpHeader.add("message", "Uspesno dobavljanje skale.");
			return new ResponseEntity<ArrayList<Skala>>(retVal,httpHeader, HttpStatus.OK);
			
		}
			
		httpHeader.add("message", "Neuspesno dobavljanje skale.");
		return new ResponseEntity<ArrayList<Skala>>(null,httpHeader, HttpStatus.OK);
		
		
	}
	
	
	@PreAuthorize("hasAuthority('AS')")
	@RequestMapping(value = "obrisiSkalu/{id}", method = RequestMethod.DELETE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Integer> obrisiSkalu(@PathVariable int id){
		HttpHeaders httpHeader = new HttpHeaders();
		
		int retVal =  ss.deleteSklaById(new Long(id));
		if(retVal != 0) {
			httpHeader.add("message", "Uspesno brisanje skale!");
			return new ResponseEntity<Integer>(retVal, HttpStatus.OK);
		}
		httpHeader.add("message", "Neuspesno brisanje skale!");
		return new ResponseEntity<Integer>(null, httpHeader, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "sacuvajIzmenjeuSkalu", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Skala> sacuvaIzmenjenuSkalu(@RequestBody @Valid Skala novaSkala, ServletRequest request) throws IOException{
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		Skala retVal = ss.addSkala(novaSkala);
		
		if(retVal !=null) {
	
			httpHeader.add("message", "Uspesno izmenjena nova skale.");
			return new ResponseEntity<Skala>(retVal,httpHeader, HttpStatus.OK);
			
		}
			
		httpHeader.add("message", "Neuspesno izmenjena nova skale.");
		return new ResponseEntity<Skala>(null,httpHeader, HttpStatus.OK);
		
		
	}

	
}
