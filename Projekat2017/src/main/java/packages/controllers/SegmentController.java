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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Sala;
import packages.beans.Segment;
import packages.beans.TipSegmenta;
import packages.dto.SegmentDTO;
import packages.services.SalaService;
import packages.services.SegmentService;

@RestController
@RequestMapping(value = "app/")
public class SegmentController {
	
	@Autowired
	private SegmentService ss;
	
	@Autowired
	private SalaService sas;
	
	@RequestMapping(value="vratiSegmenteSala/{idSala}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Segment>> vratiSegmenteZaSalu(@PathVariable int idSala) {
		
		HttpHeaders header = new HttpHeaders();
		
		if(idSala < 1) {
			header.add("message", "Nepostojeca sala!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Sala sala = sas.getSala(new Long(idSala));
		
		if(sala == null) {
			header.add("message", "Greska prilikom ucitavanja sale sala!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		ArrayList<Segment> retVal = ss.getSegmentsBySala(sala);
		
		return new ResponseEntity<ArrayList<Segment>>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value="vratiTipoveSegmenata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<TipSegmenta>> vratiTipoveSegmenata(){
		
		ArrayList<TipSegmenta> retVal = ss.getAllTipSegments(); 
		
		return new ResponseEntity<ArrayList<TipSegmenta>>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value="sacuvajSegment/{idSala}/{idTip}", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Segment> sacuvajSegment(@RequestBody @Valid SegmentDTO noviSegment, @PathVariable int idSala, @PathVariable int idTip, BindingResult result){
						
		HttpHeaders header = new HttpHeaders();
		
		Sala sala = sas.getSala(new Long(idSala));
		
		if(result.hasErrors()) {
			header.add("message", "Segment nije validan!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		if(noviSegment.getBrojSedista() < 1 || noviSegment.getBrojSedista() > 5000) {
			header.add("message", "Nedozvoljen broj segmenata!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		if(sala == null) {
			header.add("message", "Nepostojeca sala!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		TipSegmenta tip = ss.getTipSegmenta(new Long(idTip));
		
		if(tip == null) {
			header.add("message", "Nepostojeci tip segmenta!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Segment retVal = new Segment();
		retVal.setBroj_sedista(noviSegment.getBrojSedista());
		retVal.setSala(sala);
		retVal.setTip(tip);
		
		retVal = ss.addSegment(retVal);
		
		return new ResponseEntity<Segment>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value="izmeniSegment", method= RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Segment> izmeniSegment(@RequestBody @Valid Segment segment, BindingResult result){
						
		HttpHeaders header = new HttpHeaders();
		
		if(result.hasErrors()) {
			header.add("message", "Segment nije validan!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Segment retVal = ss.addSegment(segment);
		
		return new ResponseEntity<Segment>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value="sacuvajTipSegmenta", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<TipSegmenta> sacuvajTipSegment(@RequestBody @Valid TipSegmenta noviTip, BindingResult result){
						
		HttpHeaders header = new HttpHeaders();
		
		if(result.hasErrors()) {
			header.add("message", "Tip segmenta nije validan!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		TipSegmenta retVal = ss.addTipSegmenta(noviTip);
		
		return new ResponseEntity<TipSegmenta>(retVal, HttpStatus.OK);
	}
	
	

}
