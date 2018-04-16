package packages.controllers;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.PozBio;
import packages.beans.PredFilm;
import packages.beans.Projekcija;
import packages.beans.Sala;
import packages.services.PozBioService;
import packages.services.PredFilmService;
import packages.services.ProjekcijaService;
import packages.services.SalaService;

@RestController
@RequestMapping(value="/app")
public class ProjekcijaController {

	@Autowired
	private ProjekcijaService ps;
	
	@Autowired
	private SalaService ss;
	
	@Autowired
	private PredFilmService pfs;
	
	@Autowired
	private PozBioService pbs;
	
	
	@RequestMapping(value="/sacuvajProjekciju", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Projekcija> dodajProjekciju(@RequestParam int idSale, @RequestParam int idPredFilm, @RequestParam String datum) {
		
		HttpHeaders header = new HttpHeaders();
		
		if(datum == null || datum.isEmpty()) {
			header.add("message", "Niste izabrali datum!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		String tempDat = datum.substring(0, 24);
		
		DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
		Date pocetak = null;
		try {
			pocetak = (Date)formatter.parse(tempDat);        

		} catch (ParseException e) {
			header.add("message", "Neispravan format datuma!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		Sala sala = ss.getSala(new Long(idSale));
		
		if(sala == null) {
			header.add("message", "Formirate projekciju za nepostojecu salu!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		PredFilm predFilm = pfs.getPredFilm(new Long(idPredFilm));
		
		if(predFilm == null) {
			header.add("message", "Formirate projekciju za nepostojeci entitet!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		
		long ONE_MINUTE_IN_MILLIS = 60000;

		Calendar cal = Calendar.getInstance();
		cal.setTime(pocetak);
		long vremePocetka = cal.getTimeInMillis();
		Date kraj = new Date(vremePocetka + (predFilm.getTrajanje() * ONE_MINUTE_IN_MILLIS));

		System.out.println("***Pocetak: "+pocetak+", trajanje : "+predFilm.getTrajanje()+", kraj: "+kraj);
		
		ArrayList<Projekcija> flag = ps.getProjekcijasBySala(sala);
		
		if(!flag.isEmpty()) {
			
			for(Projekcija projekcija : flag) {
				
				PredFilm tempPF = projekcija.getPredFilm();
				
				cal.setTime(projekcija.getDatum());
				long pocetakProjekcije = cal.getTimeInMillis();
				Date krajProjekcije = new Date(pocetakProjekcije + (tempPF.getTrajanje() * ONE_MINUTE_IN_MILLIS));
				
				if(projekcija.getDatum().after(pocetak) && krajProjekcije.before(kraj)) {
					header.add("message", "Projekcija se preklapa sa postojecom, izmenite vreme!");
					return new ResponseEntity<>(null, header, HttpStatus.OK);
				}
				
				if(projekcija.getDatum().before(pocetak) && krajProjekcije.after(kraj)) {
					header.add("message", "Projekcija se preklapa sa postojecom, izmenite vreme!");
					return new ResponseEntity<>(null, header, HttpStatus.OK);
				}
				
				if(projekcija.getDatum().before(pocetak) && (krajProjekcije.after(pocetak) && krajProjekcije.before(kraj))) {
					header.add("message", "Projekcija se preklapa sa postojecom, izmenite vreme!");
					return new ResponseEntity<>(null, header, HttpStatus.OK);
				}
				
				if((projekcija.getDatum().after(pocetak) && projekcija.getDatum().before(kraj)) && krajProjekcije.after(kraj)) {
					header.add("message", "Projekcija se preklapa sa postojecom, izmenite vreme!");
					return new ResponseEntity<>(null, header, HttpStatus.OK);
				}
				
			}
			
		}
		
		Projekcija novaProjekcija = new Projekcija(predFilm, sala, pocetak);
		
		novaProjekcija = ps.addProjekcija(novaProjekcija);

		return new ResponseEntity<Projekcija>(novaProjekcija, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "vratiProjekcijePoDanu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ArrayList<Projekcija>> vratiProjekcijePoDanu(@RequestParam int idPozBio, @RequestParam String datum){
		
		System.out.println(idPozBio+" "+datum);
		
		HttpHeaders header = new HttpHeaders();
		
		if(datum == null || datum.isEmpty()) {
			header.add("message", "Niste uneli vreme!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		String tempDat = datum.substring(0, 16);
		
		System.out.println("REZ: "+tempDat);
		
		DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
		Date pocetak = null;
		try {
			pocetak = (Date)formatter.parse(tempDat);        

		} catch (ParseException e) {
			header.add("message", "Neispravan format datuma!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		long ONE_DAY_IN_MILLIS = 86340000;

		Calendar cal = Calendar.getInstance();
		cal.setTime(pocetak);
		long vremePocetka = cal.getTimeInMillis();
		Date kraj = new Date(vremePocetka + ONE_DAY_IN_MILLIS);
		
		System.out.println("***Pocetak: "+pocetak+", kraj: "+kraj);
		
		PozBio pozBio = pbs.getPozBio(new Long(idPozBio));
		
		if(pozBio == null) {
			header.add("message", "Birate projekciju za nepostojeci bioskop!");
			return new ResponseEntity<>(null, header, HttpStatus.OK);
		}
		
		ArrayList<Projekcija> retVal = ps.getProjekcijasBetween(pozBio, pocetak, kraj);
		
		return new ResponseEntity<ArrayList<Projekcija>>(retVal, HttpStatus.OK);
	}
	
}
