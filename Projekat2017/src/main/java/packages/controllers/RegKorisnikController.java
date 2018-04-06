package packages.controllers;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.beans.KorisnikDTO;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Zahtev;
import packages.components.KorisnikToKorisnikDTO;
import packages.enumerations.RegKorisnikStatus;
import packages.security.TokenUtils;
import packages.services.KorisnikService;
import packages.services.RegistrovaniKorisnikService;

@RestController
@RequestMapping(value = "app/secured/")
public class RegKorisnikController {

	@Autowired
	private KorisnikService korisnikService; 
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private KorisnikToKorisnikDTO toKorisnikDTO;
	
	@Autowired
	private RegistrovaniKorisnikService regKorisnikService;
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "vratiRegKorisnika", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> vratiRegKorisnika(ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.BAD_REQUEST);
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik korisnik = korisnikService.getKorisnikByEmail(email);
		
		if(korisnik==null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.BAD_REQUEST);
		}
		
		KorisnikDTO korisnikDTO =  toKorisnikDTO.convert(korisnik);
		
		return new ResponseEntity<KorisnikDTO>(korisnikDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value = "izmena", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> izmeniKorisnika(@RequestBody @Valid KorisnikDTO korisnik, BindingResult result){
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Uspesno izmenjeni licni podaci");
		
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<Boolean>(false,httpHeader, HttpStatus.OK);
		}
		
		Korisnik zaIzmenu = korisnikService.getKorisnikByEmail(korisnik.getEmail());
		
		if(zaIzmenu==null) {
			httpHeader.set("message", "Ne postojeci korisnik");
			return new ResponseEntity<Boolean>(false,httpHeader, HttpStatus.OK);		
		}
		
		if(korisnik.getTelefon() != null && korisnik.getTelefon().isEmpty())
			korisnik.setTelefon(null);
		
		zaIzmenu.setIme(korisnik.getIme());
		zaIzmenu.setPrezime(korisnik.getPrezime());
		zaIzmenu.setGrad(korisnik.getGrad());
		zaIzmenu.setTelefon(korisnik.getTelefon());
		
		try {
			zaIzmenu = korisnikService.addKorisnik(zaIzmenu);
		}catch(Exception e){
			httpHeader.set("message", "Greska kod izmene podataka");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);		
		}
		
		return new ResponseEntity<Boolean>(true,httpHeader, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="posaljiZahtev", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> posaljiZahtev(@Valid @RequestBody KorisnikDTO primalacDTO,ServletRequest request) {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik korisnikPosiljalac = korisnikService.getKorisnikByEmail(email);
		
		if(korisnikPosiljalac==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(korisnikPosiljalac.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Korisnik korisnikPrimalac = korisnikService.getKorisnikByEmail(primalacDTO.getEmail());
		
		if(korisnikPrimalac==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(korisnikPrimalac.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik posiljalac = regKorisnikService.getRegKorisnikByKorisnikId(korisnikPosiljalac);
		
		if(posiljalac==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik primalac = regKorisnikService.getRegKorisnikByKorisnikId(korisnikPrimalac);
		
		if(primalac==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Zahtev z1 = regKorisnikService.getZahtevByPosiljalacAndPrimalac(posiljalac, primalac);
		
		if(z1!=null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Zahtev z2 = regKorisnikService.getZahtevByPosiljalacAndPrimalac(primalac, posiljalac);
		
		if(z2!=null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Zahtev zahtev = new Zahtev(null, posiljalac, primalac);
		
		regKorisnikService.addZahtev(zahtev);
				
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
				
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="prihvatiZahtev", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> prihvatiZahtev(@Valid @RequestBody KorisnikDTO posiljalacDTO,ServletRequest request) {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik korisnikPrimalac = korisnikService.getKorisnikByEmail(email);
		
		if(korisnikPrimalac==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(korisnikPrimalac.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Korisnik korisnikPosiljalac = korisnikService.getKorisnikByEmail(posiljalacDTO.getEmail());
		
		if(korisnikPosiljalac==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(korisnikPosiljalac.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik posiljalac = regKorisnikService.getRegKorisnikByKorisnikId(korisnikPosiljalac);
		
		if(posiljalac==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik primalac = regKorisnikService.getRegKorisnikByKorisnikId(korisnikPrimalac);
		
		if(primalac==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
			
		Zahtev zahtev = regKorisnikService.getZahtevByPosiljalacAndPrimalac(posiljalac, primalac);
		
		if(zahtev==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		posiljalac.getPrijatelji().add(primalac);
		primalac.getPrijatelji().add(posiljalac);
		
		try {
			regKorisnikService.addRegistrovaniKorisnik(posiljalac);
		}catch(Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		try {
			regKorisnikService.addRegistrovaniKorisnik(primalac);
		}catch(Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		try {
			regKorisnikService.deleteZahtev(zahtev);
		}catch(Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="obrisiZahtev", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> obrisiZahtev(@Valid @RequestBody KorisnikDTO drugiDTO,ServletRequest request) {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Korisnik drugiKorisnik = korisnikService.getKorisnikByEmail(drugiDTO.getEmail());
		
		if(drugiKorisnik==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(drugiKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik logovanReg = regKorisnikService.getRegKorisnikByKorisnikId(logovanKorisnik);
		
		if(logovanReg==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik drugiReg = regKorisnikService.getRegKorisnikByKorisnikId(drugiKorisnik);
		
		if(drugiReg==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Zahtev z1 = regKorisnikService.getZahtevByPosiljalacAndPrimalac(logovanReg, drugiReg);
		Zahtev z2 = regKorisnikService.getZahtevByPosiljalacAndPrimalac(drugiReg, logovanReg);
		
		if(z1 == null && z2 == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(z1 != null) {
			try {
				regKorisnikService.deleteZahtev(z1);
			}catch(Exception e) {
				return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
			}
		}
		
		if(z2 != null) {
			try {
				regKorisnikService.deleteZahtev(z2);
			}catch(Exception e) {
				return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);		
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="obrisiPrijatelja", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> obrisiPrijatelja(@Valid @RequestBody KorisnikDTO zaBrisanjeDTO,ServletRequest request) {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		Korisnik drugiKorisnik = korisnikService.getKorisnikByEmail(zaBrisanjeDTO.getEmail());
		
		if(drugiKorisnik==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		if(drugiKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik logovanReg = regKorisnikService.getRegKorisnikByKorisnikId(logovanKorisnik);
		
		if(logovanReg==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik drugiReg = regKorisnikService.getRegKorisnikByKorisnikId(drugiKorisnik);
		
		if(drugiReg==null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		logovanReg.getPrijatelji().remove(drugiReg);
		drugiReg.getPrijatelji().remove(logovanReg);
		
		try {
			regKorisnikService.addRegistrovaniKorisnik(logovanReg);
		}catch(Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		try {
			regKorisnikService.addRegistrovaniKorisnik(drugiReg);
		}catch(Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
			
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="proveriPrijateljstvo", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> proveriPrijateljstvo(@Valid @RequestBody KorisnikDTO drugiDTO,ServletRequest request) {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik drugiKorisnik = korisnikService.getKorisnikByEmail(drugiDTO.getEmail());
		
		if(drugiKorisnik==null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		if(drugiKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik logovanReg = regKorisnikService.getRegKorisnikByKorisnikId(logovanKorisnik);
		
		if(logovanReg==null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		RegistrovaniKorisnik drugiReg = regKorisnikService.getRegKorisnikByKorisnikId(drugiKorisnik);
		
		if(drugiReg==null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		if(logovanReg.getPrijatelji().contains(drugiReg)) {
			return new ResponseEntity<String>("P", HttpStatus.OK);
		}
		
		Zahtev z1 = regKorisnikService.getZahtevByPosiljalacAndPrimalac(logovanReg, drugiReg);
		
		if(z1!=null) {
			return new ResponseEntity<String>("J", HttpStatus.OK);
		}
		
		Zahtev z2 = regKorisnikService.getZahtevByPosiljalacAndPrimalac(drugiReg, logovanReg);
		
		if(z2!=null) {
			return new ResponseEntity<String>("M", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("N",HttpStatus.OK);
	}
	
}
