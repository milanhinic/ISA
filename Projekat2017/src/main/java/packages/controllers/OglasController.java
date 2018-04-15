package packages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import packages.beans.Sala;
import packages.services.OglasService;

@RestController
@RequestMapping(value = "app/")
public class OglasController {

	@Autowired
	private OglasService os;
	
	
	
	@RequestMapping(value = "sacuvajOglas/{pozBioId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Sala> dodajOglas(@RequestBody Sala novaSala, @PathVariable int pozBioId){
		
		return null;
		
	}
}
