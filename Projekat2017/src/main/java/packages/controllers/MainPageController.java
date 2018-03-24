package packages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
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
		
		
		/*for(int i = 1; i <= 20; i++) {
			PozBio noviBioskop = new PozBio(PozBioTip.BIO, "Bioskop Broj "+i, "Adresa "+i+" bb", "Ukratko o Bioskopu Broj "+i+"...");
			pbs.addPozBio(noviBioskop);
		}
		*/
		return pbs.getPozBioList(PozBioTip.BIO, new PageRequest(0, 10));
	}
	
	@RequestMapping(value = "pozorista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiPozorista() {
		
		return pbs.getPozBioList(PozBioTip.POZ, new PageRequest(0, 10));
	}

}
