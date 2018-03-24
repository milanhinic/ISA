package packages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "bioskopi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiBioskope() {
		
		return pbs.getPozBioList(PozBioTip.BIO, new PageRequest(0, 10));
	}
	
	@RequestMapping(value = "pozorista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiPozorista() {
		
		return pbs.getPozBioList(PozBioTip.POZ, new PageRequest(0, 10));
	}
	
	@RequestMapping(value = "prebroj", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int prebroj() {
		
		return pbs.getRowCount();
	}
	
	@RequestMapping(value = "vratiJedan/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PozBio vratiJedan(@PathVariable int id) {
		PozBio tempPozBio = pbs.getPozBio(new Long(id));
		System.out.println(tempPozBio.toString());
		return tempPozBio;
	}

}
