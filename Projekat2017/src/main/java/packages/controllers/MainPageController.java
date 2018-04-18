package packages.controllers;

import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;
import packages.services.KorisnikService;

@RestController
@RequestMapping(value = "app/")
@Transactional
public class MainPageController {

	
	@Autowired
	KorisnikService kser;


	
	@RequestMapping(value = "adminFz/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Korisnik> vratiAdministratoreFz(@PathVariable int id) {
		
		Page<Korisnik> retVal = kser.getAllKorisnikList(RegKorisnikStatus.A, KorisnikTip.AF, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		
		return retVal;

	}

	
	@RequestMapping(value = "adminFzA", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArrayList<Korisnik> vratiAdministratoreFzA() {
		
		ArrayList<Korisnik> retVal = kser.getAllKorisnikListArray(RegKorisnikStatus.A, KorisnikTip.AF);
		if(retVal.size() <= 0) {
			return null;
		}
		
		return retVal;
	}

	
	@RequestMapping(value = "adminPB", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArrayList<Korisnik> vratiAdministratorePB() {
		
		ArrayList<Korisnik> retVal = kser.getAllKorisnikListArray(RegKorisnikStatus.A, KorisnikTip.AU);
		if(retVal.size() <= 0) {
			return null;
		}
		
		return retVal;


	}

	@RequestMapping(value = "adminSis/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Korisnik> vratiAdministratoreSis(@PathVariable int id) {
		
		Page<Korisnik> retVal = kser.getAllKorisnikList(RegKorisnikStatus.A, KorisnikTip.AS, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		return retVal;
	}
	
	@RequestMapping(value = "adminFz/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Integer> deleteAdminFz(@PathVariable int id) {
		
		int retVal =  kser.deleteById(new Long(id));
		
		if(retVal >= 1) {
			return new ResponseEntity<Integer>(retVal, HttpStatus.OK);
		}
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Niste obrisali administratora Fz!");
		return new ResponseEntity<Integer>(null, httpHeader, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "adminSi/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Integer> deleteAdminSis(@PathVariable int id) {
		
		int retVal =  kser.deleteById(new Long(id));
		
		if(retVal >= 1) {
			return new ResponseEntity<Integer>(retVal, HttpStatus.OK);
		}
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Niste obrisali administratora Fz!");
		return new ResponseEntity<Integer>(null, httpHeader, HttpStatus.OK);
	}
	
}
