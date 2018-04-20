package packages.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import packages.beans.Karta;
import packages.beans.Projekcija;
import packages.beans.Sediste;
import packages.constraints.KartaServiceConstraints;
import packages.exceptions.KartaExistsException;
import packages.serviceInterfaces.KartaInterface;
import packages.services.KartaService;
import packages.services.ProjekcijaService;
import packages.services.SedisteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KartaServiceTest {

	@Autowired
	KartaInterface kartaService;
	
	@Autowired
	ProjekcijaService projService;
	
	@Autowired
	SedisteService sedService;
	
	@Test(expected = KartaExistsException.class)
	@Transactional
    @Rollback(true)
	public void testAddKarte() throws KartaExistsException {
		
		Projekcija proj = projService.getProjekcija(KartaServiceConstraints.PROJEKCIJA_ID);
		Sediste sed1 = sedService.getSediste(KartaServiceConstraints.SEDISTE1_ID);
		Sediste sed2 = sedService.getSediste(KartaServiceConstraints.SEDISTE2_ID);

		Karta karta1 = new Karta();
		karta1.setProjekcija(proj);
		karta1.setSediste(sed1);
		karta1.setBrzaRezervacija(false);
		
		Karta karta2 = new Karta();
		karta2.setProjekcija(proj);
		karta2.setSediste(sed2);
		karta2.setBrzaRezervacija(false);
		
		ArrayList<Karta> karte = new ArrayList<Karta>();
		karte.add(karta1);
		karte.add(karta2);
		
		karte = kartaService.createKarte(karte);
		
	}
	
	
}
