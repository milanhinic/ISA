package packages.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import packages.beans.PozBio;

@RestController
@RequestMapping(value = "app/")
public class MainPageController {
	
	@RequestMapping(value = "bioskopi", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<PozBio> vratiBioskope() {
		
		ArrayList<PozBio> bioskopi = new ArrayList<PozBio>();
		
		for(int i = 1; i <= 10; i++) {
			PozBio noviBioskop = new PozBio("Bio"+i, "B", "Bioskop Broj "+i, "Adresa "+i+" bb", "Ukratko o Bioskopu Broj "+i+"...");
			bioskopi.add(noviBioskop);
		}
		
		return bioskopi;
	}
	
	@RequestMapping(value = "pozorista", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<PozBio> vratiPozorista() {
		ArrayList<PozBio> pozorista = new ArrayList<PozBio>();
		
		for(int i = 1; i <= 5; i++) {
			PozBio novoPozoriste = new PozBio("Poz"+i, "P", "Pozoriste Broj "+i, "Adresa "+i+" bb", "Ukratko o Pozoristu Broj "+i+"...");
			pozorista.add(novoPozoriste);
		}
		
		return pozorista;
	}

}
