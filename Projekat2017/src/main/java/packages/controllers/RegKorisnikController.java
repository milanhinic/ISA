package packages.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RestController;

import packages.beans.Korisnik;
import packages.beans.KorisnikDTO;
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Zahtev;
import packages.components.KorisnikToKorisnikDTO;
import packages.dto.LozinkaDTO;
import packages.enumerations.KorisnikTip;
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
	
	@PreAuthorize("hasAuthority('RK') or hasAuthority('AU')")
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
	
	@PreAuthorize("hasAuthority('RK') or hasAuthority('AU')")
	@RequestMapping(value = "izmenaLozinke", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> izmeniLozinku(@RequestBody @Valid LozinkaDTO lozinkaDTO, BindingResult result, ServletRequest request){
		
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
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)){
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add("message", "Uspesno izmenjena lozinka");
			
		if(result.hasErrors()) {
			httpHeader.set("message", result.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<Boolean>(false,httpHeader, HttpStatus.OK);
		}
		
		if(!Arrays.equals(logovanKorisnik.getLozinka(), lozinkaDTO.getStaraLozinka())) {
			httpHeader.set("message", "Niste uneli ispravnu lozinku koju zelite da menjate");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		}
		
		if(!Arrays.equals(lozinkaDTO.getNovaLozinka(),lozinkaDTO.getPotvrdaLozinke())) {
			httpHeader.set("message", "Niste ispravno potvrdili vasu novu lozinku");
			return new ResponseEntity<Boolean>(false, httpHeader, HttpStatus.OK);
		}
		
		logovanKorisnik.setLozinka(lozinkaDTO.getNovaLozinka());
		
		try {
			logovanKorisnik = korisnikService.addKorisnik(logovanKorisnik);
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
	@RequestMapping(value="prihvatiZahtev", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	@RequestMapping(value="obrisiZahtev", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	@RequestMapping(value="obrisiPrijatelja", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	@RequestMapping(value="proveriPrijateljstvo", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<String>> proveriPrijateljstvo(@RequestBody ArrayList<KorisnikDTO> korisnici,ServletRequest request) {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return new ResponseEntity<ArrayList<String>>(HttpStatus.BAD_REQUEST);
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return new ResponseEntity<ArrayList<String>>(HttpStatus.BAD_REQUEST);
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return new ResponseEntity<ArrayList<String>>(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<String> retVal = new ArrayList<String>();
		for(KorisnikDTO drugiDTO : korisnici) {
		Korisnik drugiKorisnik = korisnikService.getKorisnikByEmail(drugiDTO.getEmail());
		
			if(drugiKorisnik==null) {
				return new ResponseEntity<ArrayList<String>>(HttpStatus.BAD_REQUEST);
			}
			
			if(drugiKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
				return new ResponseEntity<ArrayList<String>>(HttpStatus.BAD_REQUEST);
			}
			
			RegistrovaniKorisnik logovanReg = regKorisnikService.getRegKorisnikByKorisnikId(logovanKorisnik);
			
			if(logovanReg==null) {
				return new ResponseEntity<ArrayList<String>>(HttpStatus.BAD_REQUEST);
			}
			
			RegistrovaniKorisnik drugiReg = regKorisnikService.getRegKorisnikByKorisnikId(drugiKorisnik);
			
			if(drugiReg==null) {
				return new ResponseEntity<ArrayList<String>>(HttpStatus.BAD_REQUEST);
			}
			
			boolean usao = false;
			
			if(logovanReg.getPrijatelji().contains(drugiReg)) {
				retVal.add("P");
				usao = true;
			}
			
			Zahtev z1 = regKorisnikService.getZahtevByPosiljalacAndPrimalac(logovanReg, drugiReg);
			
			if(z1!=null) {
				retVal.add("J");
				usao = true;
			}
			
			Zahtev z2 = regKorisnikService.getZahtevByPosiljalacAndPrimalac(drugiReg, logovanReg);
			
			if(z2!=null) {
				retVal.add("M");
				usao = true;
			}
			
			if(!usao)
				retVal.add("N");
			
		}
		
		return new ResponseEntity<ArrayList<String>>(retVal,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="vratiKorisnike/{page}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> vratiKorisnike(@PathVariable int page, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return null;
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return null;
		}
		
		Long regKorisnikCount = korisnikService.getRegKorisnikCount(RegKorisnikStatus.A, KorisnikTip.RK, email);
		
		if(regKorisnikCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(regKorisnikCount/10)+1;
		Page<Korisnik> korisnici = null;
			
		if(page>poslednja) {
			korisnici = korisnikService.getKorisnikList(RegKorisnikStatus.A, KorisnikTip.RK, logovanKorisnik.getEmail(), new PageRequest(poslednja-1, 10));
		}else {
			korisnici = korisnikService.getKorisnikList(RegKorisnikStatus.A, KorisnikTip.RK, logovanKorisnik.getEmail(), new PageRequest(page-1, 10));
		}
		
		ArrayList<KorisnikDTO> retVal = new ArrayList<KorisnikDTO>();
		
		for(Korisnik k : korisnici.getContent()) {
			KorisnikDTO kDTO = toKorisnikDTO.convert(k);
			retVal.add(kDTO);
		}
			
		return retVal;	
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="vratiPosiljaoce/{page}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> vratiPosiljaoce(@PathVariable int page, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return null;
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return null;
		}
		
		RegistrovaniKorisnik logovanReg = regKorisnikService.getRegKorisnikByKorisnikId(logovanKorisnik);
		
		if(logovanReg==null) {
			return null;
		}
		
		Long posiljaociCount = regKorisnikService.getPosiljaociCount(logovanReg);
		
		if(posiljaociCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(posiljaociCount/10)+1;
		Page<Korisnik> korisnici = null;
			
		if(page>poslednja) {
			korisnici = regKorisnikService.getPosiljaociFromZahtev(logovanReg, new PageRequest(poslednja-1,10));
		}else {
			korisnici = regKorisnikService.getPosiljaociFromZahtev(logovanReg, new PageRequest(page-1,10));
		}
		
		ArrayList<KorisnikDTO> retVal = new ArrayList<KorisnikDTO>();
		
		for(Korisnik k : korisnici.getContent()) {
			KorisnikDTO kDTO = toKorisnikDTO.convert(k);
			retVal.add(kDTO);
		}
			
		return retVal;	
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="vratiPrijatelje/{page}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> vratiPrijatelje(@PathVariable int page, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return null;
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return null;
		}
		
		Long prijateljiCount = regKorisnikService.getPrijateljiBroj(logovanKorisnik);
		
		if(prijateljiCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(prijateljiCount/10)+1;
		
		Page<Korisnik> prijatelji = null;
		
		if(page>poslednja) {
			prijatelji = regKorisnikService.getPrijatelji(logovanKorisnik, new PageRequest(poslednja-1,10));
		}else {
			prijatelji = regKorisnikService.getPrijatelji(logovanKorisnik, new PageRequest(page-1,10));
		}
		
		ArrayList<KorisnikDTO> retVal = new ArrayList<KorisnikDTO>();
		
		for(Korisnik k : prijatelji.getContent()) {
			KorisnikDTO kDTO = toKorisnikDTO.convert(k);
			retVal.add(kDTO);
		}
			
		return retVal;
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="vratiPrijateljeImePrezime/stranica={page}&kriterijum={imeprezime}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> vratiPrijateljeImePrezime(@PathVariable("page") int page, @PathVariable("imeprezime") String imeprezime, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return null;
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return null;
		}
		
		Long prijateljiCount = regKorisnikService.countPrijateljiByNameAndSurname(logovanKorisnik, imeprezime);
		
		if(prijateljiCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(prijateljiCount/10)+1;
		
		Page<Korisnik> prijatelji = null;
		
		if(page>poslednja) {
			prijatelji = regKorisnikService.getPrijateljiByNameAndSurname(logovanKorisnik, imeprezime, new PageRequest(poslednja-1,10));
		}else {
			prijatelji = regKorisnikService.getPrijateljiByNameAndSurname(logovanKorisnik, imeprezime, new PageRequest(page-1,10));
		}
		
		ArrayList<KorisnikDTO> retVal = new ArrayList<KorisnikDTO>();
		
		for(Korisnik k : prijatelji.getContent()) {
			KorisnikDTO kDTO = toKorisnikDTO.convert(k);
			retVal.add(kDTO);
		}
			
		return retVal;
	}
	
	@PreAuthorize("hasAuthority('RK')")
	@RequestMapping(value="vratiKorisnikeImePrezime/stranica={page}&kriterijum={imeprezime}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> vratiKorisnikeImePrezime(@PathVariable("page") int page, @PathVariable("imeprezime") String imeprezime, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return null;
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return null;
		}
		
		Long regKorisnikCount = korisnikService.countKorisniciImePrezime(RegKorisnikStatus.A, KorisnikTip.RK, email,imeprezime);
		
		if(regKorisnikCount<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		
		int poslednja = (int)Math.ceil(regKorisnikCount/10)+1;
		Page<Korisnik> korisnici = null;
			
		if(page>poslednja) {
			korisnici = korisnikService.getKorisniciImePrezime(RegKorisnikStatus.A, KorisnikTip.RK, logovanKorisnik.getEmail(),imeprezime, new PageRequest(poslednja-1, 10));
		}else {
			korisnici = korisnikService.getKorisniciImePrezime(RegKorisnikStatus.A, KorisnikTip.RK, logovanKorisnik.getEmail(),imeprezime, new PageRequest(page-1, 10));
		}
		
		ArrayList<KorisnikDTO> retVal = new ArrayList<KorisnikDTO>();
		
		for(Korisnik k : korisnici.getContent()) {
			KorisnikDTO kDTO = toKorisnikDTO.convert(k);
			retVal.add(kDTO);
		}
			
		return retVal;	
	}
	
	@PreAuthorize("hasAuthority('RK, AS')")
	@RequestMapping(value="adminFz/stranica/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Korisnik> vratiSveAdmFz(@PathVariable int page, ServletRequest request){
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		Korisnik logovanKorisnik = korisnikService.getKorisnikByEmail(email);
		
		if(logovanKorisnik==null) {
			return null;
		}
		
		if(logovanKorisnik.getStatus().equals(RegKorisnikStatus.N)) {
			return null;
		}
		
		if(!logovanKorisnik.getTip().equals(KorisnikTip.AS)) {
			return null;
		}
		
		
		Page<Korisnik> retVal = korisnikService.getAllKorisnikList(RegKorisnikStatus.A, KorisnikTip.AF, new PageRequest(page-1, 10));
		
		if(retVal.getSize()<=0) {
			return null;
		}else if(page<=0) {
			return null;
		}
		/*
		int poslednja = (int)Math.ceil(prijateljiCount/10)+1;
		
		Page<RegistrovaniKorisnik> prijatelji = null;
		
		if(page>poslednja) {
			prijatelji = regKorisnikService.getPrijatelji(logovanKorisnik, new PageRequest(poslednja-1,10));
		}else {
			prijatelji = regKorisnikService.getPrijatelji(logovanKorisnik, new PageRequest(page-1,10));
		}
		
		ArrayList<KorisnikDTO> retVal = new ArrayList<KorisnikDTO>();
		
		for(RegistrovaniKorisnik regKor : prijatelji.getContent()) {
			Korisnik k = regKor.getReg_korisnik_id();
			KorisnikDTO kDTO = toKorisnikDTO.convert(k);
			retVal.add(kDTO);
		}
			
		*/
	
		return retVal;
	}
		
}
