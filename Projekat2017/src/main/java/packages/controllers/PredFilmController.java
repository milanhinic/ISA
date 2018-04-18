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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.PredFilm;
import packages.enumerations.PredFilmTip;
import packages.services.PredFilmService;

@RestController
@RequestMapping(value = "app/")
public class PredFilmController {
	
	@Autowired
	private PredFilmService pfs;
	
	@RequestMapping(value = "sacuvajPredFilm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PredFilm> sacuvajPredFilm(@RequestBody @Valid PredFilm noviPredFilm, BindingResult result){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<PredFilm>(null,httpHeader, HttpStatus.OK);
		}else{
			PredFilm retVal = pfs.addPredFilm(noviPredFilm);
			
			if(retVal != null) {
				return new ResponseEntity<PredFilm>(retVal, httpHeader, HttpStatus.OK);
			}
		}
		
		httpHeader.add("message", "Neuspesno dodavanje novoe predstave/filma.");
		return new ResponseEntity<PredFilm>(null,httpHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value = "izmeniPredFilm", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PredFilm> izmeniPredFilm(@RequestBody @Valid PredFilm predFilm, BindingResult result){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<PredFilm>(null,httpHeader, HttpStatus.OK);
		}else{
			PredFilm retVal = pfs.addPredFilm(predFilm);
			
			if(retVal != null) {
				return new ResponseEntity<PredFilm>(retVal, httpHeader, HttpStatus.OK);
			}
		}
		
		httpHeader.add("message", "Neuspesna izmena predstave/filma.");
		return new ResponseEntity<PredFilm>(null,httpHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value = "vratiPredFilmove/{tip}/{stranica}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<PredFilm>> vratiPredFilmove(@PathVariable PredFilmTip tip, @PathVariable int stranica){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		if(tip == PredFilmTip.FILM) {
			Page<PredFilm> retVal = pfs.getPredFilmList(PredFilmTip.FILM, new PageRequest(stranica-1, 10));
			return new ResponseEntity<Page<PredFilm>>(retVal, HttpStatus.OK);
		}else if(tip == PredFilmTip.PRED) {
			Page<PredFilm> retVal = pfs.getPredFilmList(PredFilmTip.PRED, new PageRequest(stranica-1, 10));
			return new ResponseEntity<Page<PredFilm>>(retVal, HttpStatus.OK);
		}else {
			httpHeader.set("message", "Nepostojeci tip!");
			return new ResponseEntity<>(null,httpHeader, HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "vratiPredFilm/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PredFilm> vratiPredFilm(@PathVariable int id){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		PredFilm retVal = pfs.getPredFilm(new Long(id));
		
		if(retVal != null) {
			return new ResponseEntity<PredFilm>(retVal, HttpStatus.OK);
		}
		
		httpHeader.add("message", "Nepostojeca predstava ili film!");
		return new ResponseEntity<>(null,httpHeader, HttpStatus.OK);

	}
	
	@RequestMapping(value = "vratiSvePredFilmove/{tip}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<PredFilm>> vratiSvePredFilmove(@PathVariable int tip){
		
		HttpHeaders httpHeader = new HttpHeaders();
		ArrayList<PredFilm> retVal = new ArrayList<PredFilm>();
		
		if(tip == 0) {
			retVal = pfs.getAllPredFilmsByTip(PredFilmTip.FILM);
		}else if(tip == 1) {
			retVal = pfs.getAllPredFilmsByTip(PredFilmTip.PRED);
		}else {
			httpHeader.add("message", "Nedozvoljen tip predstave/filma!");
			return new ResponseEntity<>(null, httpHeader, HttpStatus.OK);
		}
		
		return new ResponseEntity<ArrayList<PredFilm>>(retVal, HttpStatus.OK);

	}
	
	@RequestMapping(value = "ocenaProjekcije/{idPredFilm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> izracunajProsecnuOcenu(@PathVariable int idPredFilm){
		
		HttpHeaders httpHeader = new HttpHeaders();
		
		PredFilm predFilm = pfs.getPredFilm(new Long(idPredFilm));
		
		if(predFilm == null) {
			httpHeader.add("message", "Nepostojecii film/predstava!");
			return new ResponseEntity<>(null, httpHeader, HttpStatus.OK);
		}
		
		Double ocenaProjekcije;
		
		try {
			ocenaProjekcije = pfs.getProjectionScore(predFilm);
		}catch(NullPointerException e) {
			ocenaProjekcije = new Double(0.0);
		}
		
		return new ResponseEntity<Double>(ocenaProjekcije, HttpStatus.OK);

	}
	

}
