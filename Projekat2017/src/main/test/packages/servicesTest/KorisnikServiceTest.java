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

import packages.beans.Korisnik;
import packages.constraints.KorisnikContstraints;
import packages.repositories.KorisnikRepository;
import packages.services.KorisnikService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KorisnikServiceTest {

	@Autowired
	KorisnikService korisnikService;
	
	@Test
	@Transactional
    @Rollback(true)
	public void testAddKorisnik() {
		
		Korisnik korisnik = new Korisnik();
		korisnik.setEmail(KorisnikContstraints.NEW_EMAIL);
		korisnik.setIme(KorisnikContstraints.NEW_FIRST_NAME);
		korisnik.setPrezime(KorisnikContstraints.NEW_LAST_NAME);
		korisnik.setLozinka(KorisnikContstraints.DB_LOZINKA);
		korisnik.setStatus(KorisnikContstraints.DB_STATUS);
		korisnik.setTip(KorisnikContstraints.DB_TIP);
		korisnik.setGrad(KorisnikContstraints.DB_GRAD);
		korisnik.setTelefon(KorisnikContstraints.DB_TELEFON);
		
		Korisnik dbKorisnik = korisnikService.addKorisnik(korisnik);
		assertThat(dbKorisnik).isNotNull();
		assertThat(dbKorisnik.getId()).isNotNull();
		assertThat(dbKorisnik.getEmail()).isEqualTo(KorisnikContstraints.NEW_EMAIL);
		assertThat(dbKorisnik.getIme()).isEqualTo(KorisnikContstraints.NEW_FIRST_NAME);
		assertThat(dbKorisnik.getPrezime()).isEqualTo(KorisnikContstraints.NEW_LAST_NAME);
		assertThat(dbKorisnik.getLozinka()).isEqualTo(KorisnikContstraints.DB_LOZINKA);
		assertThat(dbKorisnik.getStatus()).isEqualTo(KorisnikContstraints.DB_STATUS);
		assertThat(dbKorisnik.getTip()).isEqualTo(KorisnikContstraints.DB_TIP);
		assertThat(dbKorisnik.getGrad()).isEqualTo(KorisnikContstraints.DB_GRAD);
		assertThat(dbKorisnik.getTelefon()).isEqualTo(KorisnikContstraints.DB_TELEFON);
		
	}
	
	@Test
	public void testGetKorisnikByEmail() {
		
		Korisnik dbKorisnik = korisnikService.getKorisnikByEmail(KorisnikContstraints.DB_EMAIL);
		
		assertThat(dbKorisnik).isNotNull();
		assertThat(dbKorisnik.getId()).isEqualTo(KorisnikContstraints.DB_ID);
		assertThat(dbKorisnik.getEmail()).isEqualTo(KorisnikContstraints.DB_EMAIL);
		assertThat(dbKorisnik.getIme()).isEqualTo(KorisnikContstraints.DB_FIRST_NAME);
		assertThat(dbKorisnik.getPrezime()).isEqualTo(KorisnikContstraints.DB_LAST_NAME);
		assertThat(dbKorisnik.getLozinka()).isEqualTo(KorisnikContstraints.DB_LOZINKA);
		assertThat(dbKorisnik.getStatus()).isEqualTo(KorisnikContstraints.DB_STATUS);
		assertThat(dbKorisnik.getTip()).isEqualTo(KorisnikContstraints.DB_TIP);
		assertThat(dbKorisnik.getGrad()).isEqualTo(KorisnikContstraints.DB_GRAD);
		assertThat(dbKorisnik.getTelefon()).isEqualTo(KorisnikContstraints.DB_TELEFON);
			
	}
	
	@Test
	public void testGetKorisnikById() {
		
		Korisnik dbKorisnik = korisnikService.getKorisnik(KorisnikContstraints.DB_ID);
		
		assertThat(dbKorisnik).isNotNull();
		assertThat(dbKorisnik.getId()).isEqualTo(KorisnikContstraints.DB_ID);
		assertThat(dbKorisnik.getEmail()).isEqualTo(KorisnikContstraints.DB_EMAIL);
		assertThat(dbKorisnik.getIme()).isEqualTo(KorisnikContstraints.DB_FIRST_NAME);
		assertThat(dbKorisnik.getPrezime()).isEqualTo(KorisnikContstraints.DB_LAST_NAME);
		assertThat(dbKorisnik.getLozinka()).isEqualTo(KorisnikContstraints.DB_LOZINKA);
		assertThat(dbKorisnik.getStatus()).isEqualTo(KorisnikContstraints.DB_STATUS);
		assertThat(dbKorisnik.getTip()).isEqualTo(KorisnikContstraints.DB_TIP);
		assertThat(dbKorisnik.getGrad()).isEqualTo(KorisnikContstraints.DB_GRAD);
		assertThat(dbKorisnik.getTelefon()).isEqualTo(KorisnikContstraints.DB_TELEFON);
		
	}
	
	@Test
	public void testGetKorisnikByEmailAndLozinka() {
		
		Korisnik dbKorisnik = korisnikService.getKorisnikByEmailAndLozinka(KorisnikContstraints.DB_EMAIL, KorisnikContstraints.DB_LOZINKA);
		
		assertThat(dbKorisnik).isNotNull();
		assertThat(dbKorisnik.getId()).isEqualTo(KorisnikContstraints.DB_ID);
		assertThat(dbKorisnik.getEmail()).isEqualTo(KorisnikContstraints.DB_EMAIL);
		assertThat(dbKorisnik.getIme()).isEqualTo(KorisnikContstraints.DB_FIRST_NAME);
		assertThat(dbKorisnik.getPrezime()).isEqualTo(KorisnikContstraints.DB_LAST_NAME);
		assertThat(dbKorisnik.getLozinka()).isEqualTo(KorisnikContstraints.DB_LOZINKA);
		assertThat(dbKorisnik.getStatus()).isEqualTo(KorisnikContstraints.DB_STATUS);
		assertThat(dbKorisnik.getTip()).isEqualTo(KorisnikContstraints.DB_TIP);
		assertThat(dbKorisnik.getGrad()).isEqualTo(KorisnikContstraints.DB_GRAD);
		assertThat(dbKorisnik.getTelefon()).isEqualTo(KorisnikContstraints.DB_TELEFON);
			
	}
	
	@Test
	public void testGetAllKorisnikList() {
		
		Page<Korisnik> korisnici = korisnikService.getAllKorisnikList(KorisnikContstraints.DB_STATUS, KorisnikContstraints.DB_TIP, new PageRequest(KorisnikContstraints.PAGE_N, KorisnikContstraints.PAGE_SIZE));
		assertThat(korisnici.getContent().size()).isEqualTo(KorisnikContstraints.DB_COUNT_REG);
	}
	
	@Test
	public void testGetKorisnikList() {
		
		Page<Korisnik> korisnici = korisnikService.getKorisnikList(KorisnikContstraints.DB_STATUS, KorisnikContstraints.DB_TIP, KorisnikContstraints.DB_EMAIL, new PageRequest(KorisnikContstraints.PAGE_N, KorisnikContstraints.PAGE_SIZE));
		assertThat(korisnici.getContent().size()).isEqualTo(KorisnikContstraints.DB_COUNT_REG);
	}
	
	@Test
	public void testGetRegKorisnikCount() {
		
		Long count = korisnikService.getRegKorisnikCount(KorisnikContstraints.DB_STATUS, KorisnikContstraints.DB_TIP, KorisnikContstraints.DB_EMAIL);
		assertThat(count).isEqualTo(KorisnikContstraints.DB_COUNT_REG_NOT);
	}
	
	@Test
	public void testGetKorisniciImePrezime() {
		
		Page<Korisnik> korisnici = korisnikService.getKorisniciImePrezime(KorisnikContstraints.DB_STATUS, KorisnikContstraints.DB_TIP, KorisnikContstraints.DB_EMAIL, KorisnikContstraints.DB_IME_PREZ, new PageRequest(KorisnikContstraints.PAGE_N, KorisnikContstraints.PAGE_SIZE));
		assertThat(korisnici.getContent().size()).isEqualTo(KorisnikContstraints.DB_COUNT_IME_PREZ);
		
	}
	
	@Test(expected = DataIntegrityViolationException.class)
    @Transactional
    @Rollback(true)
	public void testAddEmailExists() {
		
		Korisnik korisnik = new Korisnik();
		korisnik.setEmail(KorisnikContstraints.DB_EMAIL);
		korisnik.setIme(KorisnikContstraints.NEW_FIRST_NAME);
		korisnik.setPrezime(KorisnikContstraints.NEW_LAST_NAME);
		korisnik.setLozinka(KorisnikContstraints.DB_LOZINKA);
		korisnik.setStatus(KorisnikContstraints.DB_STATUS);
		korisnik.setTip(KorisnikContstraints.DB_TIP);
		korisnik.setGrad(KorisnikContstraints.DB_GRAD);
		
		Korisnik korisnikDb = korisnikService.addKorisnik(korisnik);
		
	}
	
	
	
	
}
