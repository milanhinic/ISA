package packages.controllers;

import java.util.ArrayList;

import javax.transaction.Transactional;
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

import packages.beans.Oglas;
import packages.beans.Ponuda;
import packages.beans.PozBio;
import packages.beans.Sala;
import packages.services.OglasService;
import packages.services.PonudaService;


@Controller
@Transactional
@RestController
@RequestMapping(value = "app/")
public class PonudaController {

	@Autowired
	private OglasService oc;
	
	@Autowired
	private PonudaService pc;
	
	
	@RequestMapping(value = "dodajPonudu/{oglasId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Ponuda> dodajPonudu(@RequestBody Ponuda novaPonuda, @PathVariable int oglasId){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(novaPonuda.getIznos().equals(0)) {
			httpHeader.add("message", "Neuspesno kreiranje nove ponuda, nevalidan objekat.");
			return new ResponseEntity<Ponuda>(null, httpHeader, HttpStatus.OK);
		}
		
		
		Oglas oglas =  oc.getOglasById(new Long(oglasId));
		Ponuda novaNovaPonuda = new Ponuda(oglas, novaPonuda.getIznos());
		
		if(oglas == null) {
			httpHeader.add("message", "Neuspesno kreiranje nove ponude,nepostojeci oglas.");
			return new ResponseEntity<Ponuda>(null, httpHeader, HttpStatus.OK);
		}
		
		//novaPonuda.setOglasa(oglas);
		novaNovaPonuda.setOglasa(oglas);
		
		//Ponuda retVal = pc.addPonuda(novaPonuda);
		Ponuda retVal = pc.addPonuda(novaNovaPonuda);
		if(retVal != null) {
			httpHeader.add("message", "Uspesno kreirana nove ponuda.");
			return new ResponseEntity<Ponuda>(retVal, httpHeader, HttpStatus.OK);
		}
		
		httpHeader.add("message", "Neuspesno kreiranje nove ponude.");
		return new ResponseEntity<Ponuda>(null, httpHeader, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "vratiPonude/{idOglasa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Ponuda>> vratiPonude(@PathVariable int idOglasa){
		
		Oglas oglas =  oc.getOglasById(new Long(idOglasa));
		
		ArrayList<Ponuda> sale = pc.getPonudeByOglas(oglas);
		
		return new ResponseEntity<ArrayList<Ponuda>>(sale, HttpStatus.OK);
	}
	
	@RequestMapping(value = "izmeniPonudu/{idOglas}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Ponuda> izmeniSalu(@RequestBody @Valid Ponuda novaPonuda, @PathVariable int idOglas, BindingResult result){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(result.hasErrors()) {
			httpHeader.add("message", "Neuspesna izmena ponude, nevalidan objekat.");
			return new ResponseEntity<Ponuda>(null, httpHeader, HttpStatus.OK);
		}
		
		Ponuda retVal = pc.addPonuda(novaPonuda);
		if(retVal != null) {
			httpHeader.add("message", "Uspesno izmenjena ponuda.");
			return new ResponseEntity<Ponuda>(retVal, httpHeader, HttpStatus.OK);
		}
		
		httpHeader.add("message", "Neuspesna izmena ponude.");
		return new ResponseEntity<Ponuda>(null, httpHeader, HttpStatus.OK);
	}
	
	
}
