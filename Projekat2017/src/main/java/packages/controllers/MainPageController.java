package packages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;
import packages.services.PozBioService;

@RestController
@RequestMapping(value = "app/")
public class MainPageController {
	
	@Autowired
	PozBioService pbs;
	
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

}
