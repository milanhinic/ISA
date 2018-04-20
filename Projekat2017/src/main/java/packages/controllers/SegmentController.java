package packages.controllers;


import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import packages.beans.Karta;
import packages.beans.Sala;
import packages.beans.Sediste;
import packages.beans.Segment;
import packages.beans.TipSegmenta;
import packages.dto.SedisteDTO;
import packages.dto.SegmentDTO;
import packages.serviceInterfaces.KartaInterface;
import packages.services.SalaService;
import packages.services.SedisteService;
import packages.services.SegmentService;

@RestController
@RequestMapping(value = "app/")
public class SegmentController {
	
	@Autowired
	private SegmentService ss;
	
	@Autowired
	private SalaService sas;
	
	@Autowired
	private SedisteService sds;
	
	@Autowired 
	private KartaInterface ks;
	
	@RequestMapping(value="vratiSegmenteSala/{idSala}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<SegmentDTO>> vratiSegmenteZaSalu(@PathVariable int idSala) {
		
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
		
		ArrayList<SegmentDTO> retVal = new ArrayList<SegmentDTO>();
		ArrayList<Segment> segmenti = ss.getSegmentsBySala(sala);
		
		if(!segmenti.isEmpty()) {
			for(Segment tempSeg : segmenti) {
				int brSed = sds.getBrojSedistaBySegment(tempSeg);
				SegmentDTO sedSeg = new SegmentDTO(brSed, tempSeg);
				retVal.add(sedSeg);
			}
		}
		
		return new ResponseEntity<ArrayList<SegmentDTO>>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value="vratiTipoveSegmenata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<TipSegmenta>> vratiTipoveSegmenata(){
		
		ArrayList<TipSegmenta> retVal = ss.getAllTipSegments(); 
		
		return new ResponseEntity<ArrayList<TipSegmenta>>(retVal, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('AU')")
	@RequestMapping(value="secured/sacuvajSegment/{idSala}/{idTip}", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Segment> sacuvajSegment(@PathVariable int idSala, @PathVariable int idTip, @RequestParam int brojSedista){
		HttpHeaders header = new HttpHeaders();
		
		int br;
		
		try {
			br =Integer.parseInt(""+brojSedista);
		}catch (NumberFormatException e) {
			header.add("message", "Nevalidan broj sedista!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		
		if(brojSedista < 1 || brojSedista > 5000) {
			header.add("message", "Nedozvoljen broj sedista!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		System.out.println(br);
		
		Sala sala = sas.getSala(new Long(idSala));
		
		if(sala == null) {
			header.add("message", "Nepostojeca sala!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		TipSegmenta tip = ss.getTipSegmenta(new Long(idTip));
		
		if(tip == null) {
			header.add("message", "Nepostojeci tip segmenta!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Segment retVal = new Segment(sala, tip);
		
		retVal = ss.addSegment(retVal);
		
		for(int i = 0; i < br; i++) {
			Sediste sediste = new Sediste(retVal); 
			sds.addSediste(sediste);
		}
		
		return new ResponseEntity<Segment>(retVal, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('AU')")
	@RequestMapping(value="secured/izmeniSegment", method= RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@PreAuthorize("hasAuthority('AU')")
	@RequestMapping(value="secured/sacuvajTipSegmenta", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(value="vratiSedista/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Sediste>> vratiSedistaZaSegment(@PathVariable int id){
		
		HttpHeaders header = new HttpHeaders();
		
		Segment segment = ss.getSegment(new Long(id));
		
		if(segment == null) {
			header.add("message", "Segmenta nije validan!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		ArrayList<Sediste> retVal = sds.getSedistaBySegment(segment);
		
		return new ResponseEntity<ArrayList<Sediste>>(retVal, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('AU')")
	@RequestMapping(value="secured/dodajSedista/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Segment> dodajSedista(@PathVariable int id, @RequestParam int brojSedista){
		
		HttpHeaders header = new HttpHeaders();
		
		int br;
		
		try {
			br =Integer.parseInt(""+brojSedista);
		}catch (NumberFormatException e) {
			header.add("message", "Nevalidan broj sedista!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Segment segment = ss.getSegment(new Long(id));
		
		if(segment == null) {
			header.add("message", "Segmenta nije validan!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		for(int i = 0; i < br; i++) { 
			sds.addSediste(new Sediste(segment));
		}
		
		return new ResponseEntity<Segment>(segment, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('AU')")
	@RequestMapping(value="secured/obrisiSedista", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> obrisiSedista(@RequestBody ArrayList<Long> zaBrisanje){
		
		HttpHeaders header = new HttpHeaders();
		
		if(!zaBrisanje.isEmpty()){
			for(Long brisiId : zaBrisanje) {
				Sediste brisiSed = sds.getSediste(brisiId);
			
				if(ks.findBySediste(brisiSed) != null) {
					header.add("message", "Barem jedno sediste je rezervisano, brisanje je onemoguceno!");
					return new ResponseEntity<>(null, header, HttpStatus.OK);
				}
			
				sds.deleteSediste(brisiId);		
			}
		}
		
		return new ResponseEntity<String>("Brisanje uspesno izvrseno.", HttpStatus.OK);
	}
		
	@PreAuthorize("hasAuthority('AU')")
	@RequestMapping(value="secured/pripremiZaBrzu/{idSeg}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<SedisteDTO>> pripremiZaBrzu(@PathVariable int idSeg){
		
		HttpHeaders header = new HttpHeaders();
		
		Segment segment = ss.getSegment(new Long(idSeg));
		
		if(segment == null) {
			header.add("message", "Nepostojeci segment!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		ArrayList<Sediste> sedista = sds.getSedistaBySegment(segment);
		ArrayList<SedisteDTO> retVal = new ArrayList<SedisteDTO>();
		
		if(!sedista.isEmpty()) {
			for(Sediste tempSed : sedista) {
				SedisteDTO forRet = null;
				Karta karta = ks.findBySediste(tempSed);
				if(karta == null) {
					forRet = new SedisteDTO(tempSed, false);
				}else {
					forRet = new SedisteDTO(tempSed, true);
				}
				
				retVal.add(forRet);
			}
		}
		
		return new ResponseEntity<ArrayList<SedisteDTO>>(retVal, HttpStatus.OK);
	}
	
	


}
