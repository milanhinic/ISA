package packages.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;
import packages.security.TokenUtils;
import packages.services.KorisnikService;
import packages.services.PozBioService;

@RestController
@RequestMapping(value = "app/")
public class PozBioController {
	
	@Autowired
	PozBioService pbs;
	
	@Autowired 
	TokenUtils tokenUtils;
	
	@Autowired
	KorisnikService korisnikService;
	
	@RequestMapping(value = "bioskopi/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiBioskope(@PathVariable int id) {
		
		Long bioskopiCount = pbs.getRowCount(PozBioTip.BIO);
		
		if(bioskopiCount<=0) {
			return null;
		}else if(id<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(bioskopiCount/10)+1;
		
		Page<PozBio> retVal = null;
		
		if(id>poslednja) {
			retVal = pbs.getPozBioList(PozBioTip.BIO, new PageRequest(poslednja-1, 10));
		}else {
			retVal = pbs.getPozBioList(PozBioTip.BIO, new PageRequest(id-1, 10));
		}
			
		if(retVal.getSize() <= 0) {
			return null;
		}
		
		return retVal;
	}
	
	@RequestMapping(value = "bioskopiPretraga/stranica={page}&kriterijum={naz}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiBioskopeNaziv(@PathVariable("page") int page, @PathVariable("naz") String naziv) {
		
		Long bioskopiCount = pbs.getPozBioCountNaziv(PozBioTip.BIO, naziv);
		
		if(bioskopiCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(bioskopiCount/10)+1;
		
		Page<PozBio> retVal = null;
		
		if(page>poslednja) {
			retVal = pbs.getPozBioListNaziv(PozBioTip.BIO, naziv, new PageRequest(poslednja-1, 10));
		}else {
			retVal = pbs.getPozBioListNaziv(PozBioTip.BIO, naziv, new PageRequest(page-1, 10));
		}
			
		if(retVal.getSize() <= 0) {
			return null;
		}
		
		return retVal;
	}
	
	@RequestMapping(value = "pozorista/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiPozorista(@PathVariable int id) {
		
		Long pozoristaCount = pbs.getRowCount(PozBioTip.POZ);
		
		if(pozoristaCount<=0) {
			return null;
		}else if(id<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(pozoristaCount/10)+1;
		
		Page<PozBio> retVal = null;
		
		if(id>poslednja) {
			retVal = pbs.getPozBioList(PozBioTip.POZ, new PageRequest(poslednja-1, 10));
		}else {
			retVal = pbs.getPozBioList(PozBioTip.POZ, new PageRequest(id-1, 10));
		}
			
		if(retVal.getSize() <= 0) {
			return null;
		}
		
		return retVal;
	}
	
	@RequestMapping(value = "pozoristaPretraga/stranica={page}&kriterijum={naz}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<PozBio> vratiPozoristaNaziv(@PathVariable("page") int page, @PathVariable("naz") String naziv) {
		
		Long pozoristaCount = pbs.getPozBioCountNaziv(PozBioTip.POZ, naziv);
		
		if(pozoristaCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(pozoristaCount/10)+1;
		
		Page<PozBio> retVal = null;
		
		if(page>poslednja) {
			retVal = pbs.getPozBioListNaziv(PozBioTip.POZ, naziv, new PageRequest(poslednja-1, 10));
		}else {
			retVal = pbs.getPozBioListNaziv(PozBioTip.POZ, naziv, new PageRequest(page-1, 10));
		}
			
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
	
	@RequestMapping(value = "izmeniPozBio", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PozBio> izmeniPozBio(@RequestBody @Valid PozBio pozBio, BindingResult result) {
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<PozBio>(null, httpHeader, HttpStatus.OK);
		}else{
			PozBio retVal = pbs.addPozBio(pozBio);
			
			if(retVal != null) {
				return new ResponseEntity<PozBio>(retVal, httpHeader, HttpStatus.OK);
			}
		}
		
		httpHeader.add("message", "Neuspesno dodavanje novog pozorista/bioskopa.");
		return new ResponseEntity<PozBio>(null,httpHeader, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="ocenaAmbijenta/{idPozBio}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> ocenaAmbijenta(@PathVariable int idPozBio){
		HttpHeaders httpHeader = new HttpHeaders();
		
		PozBio pozBio = pbs.getPozBio(new Long(idPozBio));
		
		if(pozBio == null) {
			httpHeader.add("message", "Nepostojecie pozoriste/bioskop!");
			return new ResponseEntity<>(null, httpHeader, HttpStatus.OK);
		}
		
		Double ocenaAmbijenta;
		
		try {
			ocenaAmbijenta = pbs.getAmbientScore(pozBio);
		}catch(NullPointerException e) {
			ocenaAmbijenta = new Double(0.0);
		}
		
		return new ResponseEntity<Double>(ocenaAmbijenta, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "vratiSva", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<ArrayList<PozBio>>> vratiSvee(){
		
		ArrayList<ArrayList<PozBio>> retVal = new ArrayList<ArrayList<PozBio>>();
		
		ArrayList<PozBio> pozs = pbs.getAllPozBiosList(PozBioTip.POZ);
		ArrayList<PozBio> bios = pbs.getAllPozBiosList(PozBioTip.BIO);
		
		retVal.add(pozs);
		retVal.add(bios);
		
		return new ResponseEntity<ArrayList<ArrayList<PozBio>>>(retVal, HttpStatus.OK);
	}

}
