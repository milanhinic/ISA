package packages.controllers;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import packages.beans.Oglas;
import packages.beans.PozBio;
import packages.beans.PredFilm;
import packages.enumerations.OglasStatus;
import packages.services.OglasService;

@Controller
@Transactional
@RestController
@RequestMapping(value = "app/")
public class OglasController {

	
	@Autowired
	private OglasService os;
	
	
	@RequestMapping(value = "sacuvajOglas", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> dodajOglas(@RequestParam("putanja") MultipartFile putanja, @RequestParam("naziv") String naziv, @RequestParam("opis") String opis, @RequestParam("aktivnoDo") String aktivnoDo) throws IOException{
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(naziv == null || opis == null || aktivnoDo == null) {
			httpHeader.add("message", "Neuspesno kreiranje novog oglasa, nevalidan objekat.");
			return new ResponseEntity<Oglas>(null, httpHeader, HttpStatus.OK);
		}
		
		System.out.println("SLIKA -----> " + putanja.getBytes());
		
		Oglas noviOglas = new Oglas(naziv, opis, aktivnoDo, putanja.getBytes(), OglasStatus.N);
		
		
		if(noviOglas !=null) {
			
			Oglas retVal = os.addOglas(noviOglas);
			
			if(retVal!= null) {
				return new ResponseEntity<Oglas>(retVal, httpHeader, HttpStatus.OK);
			}else {
				httpHeader.add("message", "Neuspesno dodavanje novog oglasa.");
				return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
			}
			
		}
		
		
		httpHeader.add("message", "Neuspesno dodavanje novog oglasa.");
		return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
		
		
	}
	
	
	@RequestMapping(value = "odobriOglase/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Oglas> getAllOglasi(@PathVariable int id){
		
		Page<Oglas> retVal =  os.getAllOglasiByStatus(OglasStatus.N, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		return retVal;
		
	}
	
	@RequestMapping(value = "dobaviOdobreneOglase/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Oglas> getAllOdobreniOglasi(@PathVariable int id){
		
		Page<Oglas> retVal =  os.getAllOglasiByStatus(OglasStatus.A, new PageRequest(id-1, 10));
		if(retVal.getSize() <= 0) {
			return null;
		}
		return retVal;
		
	}
	
	@RequestMapping(value = "oglas/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> getOglas(@PathVariable int id){
		
		Oglas retVal =  os.getOglasById(new Long(id));
		if(retVal != null) {
			return new ResponseEntity<Oglas>(retVal, HttpStatus.OK);
		}
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Pokusavate pristupiti nepostojecem oglasu!");
		return new ResponseEntity<Oglas>(null, httpHeader, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "obrisiOglas/{id}", method = RequestMethod.DELETE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Integer> obrisiOglas(@PathVariable int id){
		
		int retVal =  os.deleteOglas(new Long(id));
		if(retVal != 0) {
			return new ResponseEntity<Integer>(retVal, HttpStatus.OK);
		}
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Pokusavate pristupiti nepostojecem oglasu!");
		return new ResponseEntity<Integer>(null, httpHeader, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "odobriOglas", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> odobriOglas(@RequestBody @Valid Oglas oglas, BindingResult result){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
		}else{
			oglas.setStatus(OglasStatus.A);
			Oglas retVal = os.addOglas(oglas);
			
			if(retVal != null) {
				return new ResponseEntity<Oglas>(retVal, httpHeader, HttpStatus.OK);
			}
		}
		
		httpHeader.add("message", "Neuspesno odobravanje oglasa.");
		return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "izmeniOglas/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> dodajIzmenjeniOglas(@PathVariable int id, @RequestParam("naziv") String naziv, @RequestParam("opis") String opis, @RequestParam("aktivnoDo") String aktivnoDo) throws IOException{
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(naziv == null || opis == null || aktivnoDo == null) {
			httpHeader.add("message", "Neuspesna izmena oglasa, nevalidan objekat.");
			return new ResponseEntity<Oglas>(null, httpHeader, HttpStatus.OK);
		}
		
		
		
		Oglas noviOglas = os.getOglasById(new Long(id));
		noviOglas.setNaziv(naziv);
		noviOglas.setOpis(opis);
		noviOglas.setAktivnoDo(aktivnoDo);
		noviOglas.setStatus(OglasStatus.A);

		
		if(noviOglas !=null) {
			
			Oglas retVal = os.addOglas(noviOglas);
			if(retVal!= null) {
				return new ResponseEntity<Oglas>(retVal, httpHeader, HttpStatus.OK);
			}else {
				httpHeader.add("message", "Neuspesno izmena oglasa.");
				return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
			}
			
		}
		
		
		httpHeader.add("message", "Neuspesno dodavanje novog oglasa.");
		return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
	}
	 
	@RequestMapping(value = "izmeniOglasSaSlikom/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Oglas> dodajIzmenjeniOglasSaSlikom(@PathVariable int id,@RequestParam("putanja") MultipartFile putanja, @RequestParam("naziv") String naziv, @RequestParam("opis") String opis, @RequestParam("aktivnoDo") String aktivnoDo) throws IOException{
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(naziv == null || opis == null || aktivnoDo == null) {
			httpHeader.add("message", "Neuspesna izmena oglasa, nevalidan objekat.");
			return new ResponseEntity<Oglas>(null, httpHeader, HttpStatus.OK);
		}
		
		Oglas noviOglas = os.getOglasById(new Long(id));
		noviOglas.setNaziv(naziv);
		noviOglas.setOpis(opis);
		noviOglas.setAktivnoDo(aktivnoDo);
		noviOglas.setStatus(OglasStatus.A);
		noviOglas.setPath(putanja.getBytes());
		
		if(noviOglas !=null) {
			
			Oglas retVal = os.addOglas(noviOglas);
			if(retVal!= null) {
				return new ResponseEntity<Oglas>(retVal, httpHeader, HttpStatus.OK);
			}else {
				httpHeader.add("message", "Neuspesno izmena oglasa.");
				return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
			}
			
		}	
	 
		
		
		httpHeader.add("message", "Neuspesno dodavanje novog oglasa.");
		return new ResponseEntity<Oglas>(null,httpHeader, HttpStatus.OK);
		
		
	}
	
}
