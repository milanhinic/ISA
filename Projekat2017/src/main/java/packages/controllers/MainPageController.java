package packages.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.Application;
import packages.beans.Korisnik;
import packages.beans.PozBio;
import packages.enumerations.KorisnikTip;
import packages.enumerations.PozBioTip;
import packages.enumerations.RegKorisnikStatus;
import packages.services.KorisnikService;
import packages.services.PozBioService;

@RestController
@RequestMapping(value = "app/")
@Transactional
public class MainPageController {
	
	@Autowired
	PozBioService pbs;
	
	@Autowired
	KorisnikService kser;
	
	
	@RequestMapping(value = "bioskopi/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiBioskope(@PathVariable int id) {
		
		Page<PozBio> retVal = pbs.getPozBioList(PozBioTip.BIO, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		
		return retVal;
	}
	
	@RequestMapping(value = "pozorista/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiPozorista(@PathVariable int id) {
		
		Page<PozBio> retVal = pbs.getPozBioList(PozBioTip.POZ, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		
		return retVal;
	}
	
	@RequestMapping(value = "vratiJedan/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PozBio> vratiJedan(@PathVariable int id) {
		
		PozBio tempPozBio = pbs.getPozBio(new Long(id));
		
		if(tempPozBio != null) {
			return new ResponseEntity<PozBio>(tempPozBio, HttpStatus.OK);
		}
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Pokusavate pristupiti nepostojecem pozoristu/bioskopu!");
		return new ResponseEntity<PozBio>(null, httpHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value = "dodajNoviPozBio", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PozBio> dodajNoviPozBio(@RequestBody @Valid PozBio noviPozBio, BindingResult result) {
		
		System.out.println(noviPozBio.toString());
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(noviPozBio.getTip() == PozBioTip.POZ) {
			httpHeader.add("message", "Novo pozoriste je uspesno kreirano.");
		}else if(noviPozBio.getTip() == PozBioTip.BIO) {
			httpHeader.add("message", "Novi bioskop je uspesno kreiran.");
		}else {
			httpHeader.add("message", "Neuspesno dodavanje novog pozorista/bioskopa.");
			return new ResponseEntity<PozBio>(null, httpHeader, HttpStatus.OK);
		}
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<PozBio>(null,httpHeader, HttpStatus.OK);
		}else{
			PozBio retVal = pbs.addPozBio(noviPozBio);
			
			if(retVal != null) {
				return new ResponseEntity<PozBio>(retVal, httpHeader, HttpStatus.OK);
			}
		}
		
		httpHeader.add("message", "Neuspesno dodavanje novog pozorista/bioskopa.");
		return new ResponseEntity<PozBio>(null,httpHeader, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "adminFz/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Korisnik> vratiAdministratoreFz(@PathVariable int id) {
		
		Page<Korisnik> retVal = kser.getAllKorisnikList(RegKorisnikStatus.A, KorisnikTip.AF, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
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
