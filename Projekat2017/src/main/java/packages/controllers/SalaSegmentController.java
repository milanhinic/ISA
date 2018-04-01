package packages.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.PozBio;
import packages.beans.Sala;
import packages.services.PozBioService;
import packages.services.SalaSegmentService;

@RestController
@RequestMapping(value = "app/")
public class SalaSegmentController {
	
	@Autowired
	private SalaSegmentService ssr;
	
	@Autowired
	private PozBioService pbs;
	
	@RequestMapping(value = "dodajSalu/{pozBioId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Sala dodajSalu(@RequestBody Sala novaSala, @PathVariable int pozBioId){
		
		PozBio pozBio = pbs.getPozBio(new Long(pozBioId)); 
		novaSala.setPozBio(pozBio);
		
		return ssr.addSala(novaSala);
	}
	
	@RequestMapping(value = "vratiSale/{pozBioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArrayList<Sala> vratiSale(@PathVariable int pozBioId){
		
		System.out.println(pozBioId);
		
		PozBio pozBio = pbs.getPozBio(new Long(pozBioId)); 
		
		return ssr.getSalasByPozBio(pozBio);
	}
}
