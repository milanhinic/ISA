package packages.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import packages.beans.RegistrovaniKorisnik;
import packages.beans.Rezervacija;
import packages.constraints.RezervacijaServiceConstraints;
import packages.services.RegistrovaniKorisnikService;
import packages.services.RezervacijaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RezervacijaServiceTest {

	@Autowired
	RezervacijaService rezervacijaService;
	
	@Autowired
	RegistrovaniKorisnikService rks;
	
	@Test(expected = DataIntegrityViolationException.class)
    @Transactional
    @Rollback(true)
	public void testAddRezervacija(){
		
		Rezervacija r = rezervacijaService.findById(RezervacijaServiceConstraints.DB_ID);
		r = rezervacijaService.createRezervacija(r);
		
	}
	
	@Test
	public void testCanCancel() {
		
		RegistrovaniKorisnik rk = rks.getRegKorisnik(RezervacijaServiceConstraints.RK_ID);
		Page<Rezervacija> rez = rezervacijaService.getByRegKorisnikAndCanCancel(rk, new PageRequest(0,10));
		assertThat(rez.getContent().size()).isEqualTo(RezervacijaServiceConstraints.CC_SIZE);
		
	}
	
	@Test
	public void testHistory() {
		RegistrovaniKorisnik rk = rks.getRegKorisnik(RezervacijaServiceConstraints.RK_ID);
		Page<Rezervacija> rez = rezervacijaService.getHistory(rk, new PageRequest(0,10));
		assertThat(rez.getContent().size()).isEqualTo(RezervacijaServiceConstraints.H_SIZE);
	}
	
	@Test
	public void testHistoryCount() {
		RegistrovaniKorisnik rk = rks.getRegKorisnik(RezervacijaServiceConstraints.RK_ID);
		Long rez = rezervacijaService.countHistory(rk);
		assertThat(rez).isEqualTo(RezervacijaServiceConstraints.H_SIZE);
	}
	
	@Test
	public void testOneByCanCancel() {
		RegistrovaniKorisnik rk = rks.getRegKorisnik(RezervacijaServiceConstraints.RK_ID);
		Rezervacija r = rezervacijaService.findOneByKorisnikAndCanCancel(rk, RezervacijaServiceConstraints.CC);
		assertThat(r).isNotNull();
		
		
	}
	
	
}
