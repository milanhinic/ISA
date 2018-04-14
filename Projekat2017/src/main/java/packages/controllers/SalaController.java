package packages.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import packages.beans.PozBio;
import packages.beans.Sala;
import packages.services.PozBioService;
import packages.services.SalaService;

@RestController
@RequestMapping(value = "app/")
public class SalaController {
	
	@Autowired
	private SalaService ssr;
	
	@Autowired
	private PozBioService pbs;
	
	@RequestMapping(value = "dodajSalu/{pozBioId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Sala> dodajSalu(@RequestBody Sala novaSala, @PathVariable int pozBioId){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(novaSala.getNaziv().equals("") || novaSala.getNaziv() == null || novaSala == null) {
			httpHeader.add("message", "Neuspesno kreiranje nove sale, nevalidan objekat.");
			return new ResponseEntity<Sala>(null, httpHeader, HttpStatus.OK);
		}
		
		PozBio pozBio = pbs.getPozBio(new Long(pozBioId)); 
		
		if(pozBio == null) {
			httpHeader.add("message", "Neuspesno kreiranje nove sale,nepostojece pozoriste/bioskop.");
			return new ResponseEntity<Sala>(null, httpHeader, HttpStatus.OK);
		}
		
		novaSala.setPozBio(pozBio);
		
		Sala retVal = ssr.addSala(novaSala);
		if(retVal != null) {
			httpHeader.add("message", "Uspesno kreirana nova sala.");
			return new ResponseEntity<Sala>(retVal, httpHeader, HttpStatus.OK);
		}
		
		httpHeader.add("message", "Neuspesno kreiranje nove sale.");
		return new ResponseEntity<Sala>(null, httpHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value = "vratiSale/{pozBioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Sala>> vratiSale(@PathVariable int pozBioId){
		
		PozBio pozBio = pbs.getPozBio(new Long(pozBioId)); 
		
		ArrayList<Sala> sale = ssr.getSalasByPozBio(pozBio);
		
		return new ResponseEntity<ArrayList<Sala>>(sale, HttpStatus.OK);
	}
	
	@RequestMapping(value = "vratiJednuSalu/{salaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Sala> vratiJednuSalu(@PathVariable int salaId){
		
		Sala retVal = ssr.getSala(new Long(salaId));
		
		return new ResponseEntity<Sala>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "izmeniSalu/{salaId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Sala> izmeniSalu(@RequestBody @Valid Sala novaSala, @PathVariable int salaId, BindingResult result){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(result.hasErrors()) {
			httpHeader.add("message", "Neuspesna izmena sale, nevalidan objekat.");
			return new ResponseEntity<Sala>(null, httpHeader, HttpStatus.OK);
		}
		
		Sala retVal = ssr.addSala(novaSala);
		if(retVal != null) {
			httpHeader.add("message", "Uspesno izmenjena sala.");
			return new ResponseEntity<Sala>(retVal, httpHeader, HttpStatus.OK);
		}
		
		httpHeader.add("message", "Neuspesna izmena sale.");
		return new ResponseEntity<Sala>(null, httpHeader, HttpStatus.OK);
	}
}
