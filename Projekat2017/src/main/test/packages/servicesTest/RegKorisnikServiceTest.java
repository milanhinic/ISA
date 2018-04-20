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
import packages.beans.RegistrovaniKorisnik;
import packages.beans.Zahtev;
import packages.constraints.RegKorisnikConstraints;
import packages.services.KorisnikService;
import packages.services.RegistrovaniKorisnikService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegKorisnikServiceTest {

	@Autowired
	RegistrovaniKorisnikService regKorisnikService;
	
	@Autowired
	KorisnikService korisnikService;
	
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
    @Rollback(true)
	public void testInsertRegKorisnik() {
		RegistrovaniKorisnik reg = new RegistrovaniKorisnik();
		Korisnik korisnik = korisnikService.getKorisnik(RegKorisnikConstraints.NEW_REG_KOR_ID);
		reg.setBr_bodova(RegKorisnikConstraints.DB_BRBODOVA);
		reg.setReg_korisnik_id(korisnik);
		RegistrovaniKorisnik regDB = regKorisnikService.addRegistrovaniKorisnik(reg);
		
		assertThat(regDB).isNotNull();
		assertThat(regDB.getBr_bodova()).isEqualTo(RegKorisnikConstraints.DB_BRBODOVA);
		assertThat(regDB.getReg_korisnik_id()).isEqualTo(korisnik);
		assertThat(regDB.getId()).isNotNull();
			
	}
	
	@Test
	public void testGetById() {
		Korisnik korisnik = korisnikService.getKorisnik(RegKorisnikConstraints.DB_REG_KOR_ID);
		RegistrovaniKorisnik reg = regKorisnikService.getRegKorisnik(RegKorisnikConstraints.DB_ID);
		assertThat(reg).isNotNull();
		assertThat(reg.getBr_bodova()).isEqualTo(RegKorisnikConstraints.DB_BRBODOVA);
		assertThat(reg.getReg_korisnik_id().getId()).isEqualTo(korisnik.getId());
	}
	
	@Test
	public void testGetZahtev() {
		
		RegistrovaniKorisnik posiljalac = regKorisnikService.getRegKorisnik(RegKorisnikConstraints.DB_POS);
		RegistrovaniKorisnik primalac = regKorisnikService.getRegKorisnik(RegKorisnikConstraints.DB_PRIM);
		Zahtev z = regKorisnikService.getZahtevByPosiljalacAndPrimalac(posiljalac, primalac);
		assertThat(z).isNotNull();
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testAddZahtev() {
		
		RegistrovaniKorisnik posiljalac = regKorisnikService.getRegKorisnik(RegKorisnikConstraints.DB_POS);
		RegistrovaniKorisnik primalac = regKorisnikService.getRegKorisnik(RegKorisnikConstraints.DB_PRIM_NEW);
		
		Zahtev z = new Zahtev();
		z.setPosiljalac(posiljalac);
		z.setPrimalac(primalac);
		
		Zahtev zDB = regKorisnikService.addZahtev(z);
		
		assertThat(zDB).isNotNull();
		assertThat(zDB.getId()).isNotNull();
		assertThat(zDB.getPosiljalac().getId()).isEqualTo(posiljalac.getId());
		assertThat(zDB.getPrimalac().getId()).isEqualTo(primalac.getId());
			
	}
	
	@Test
	public void testGetRegKorisnikByKorisnik() {
		
		Korisnik korisnik = korisnikService.getKorisnik(RegKorisnikConstraints.DB_REG_KOR_ID);
		RegistrovaniKorisnik regKorisnik = regKorisnikService.getRegKorisnikByKorisnikId(korisnik);
		
		assertThat(regKorisnik).isNotNull();
	
	}
	
	@Test
	public void testFindByPrimalac() {
		
		RegistrovaniKorisnik primalac = regKorisnikService.getRegKorisnik(RegKorisnikConstraints.DB_PRIM);

		Page<Korisnik> korisnici = regKorisnikService.getPosiljaociFromZahtev(primalac, new PageRequest(RegKorisnikConstraints.PAGE_N, RegKorisnikConstraints.PAGE_SIZE));
		assertThat(korisnici.getContent().size()).isEqualTo(RegKorisnikConstraints.COUNT_BY_PRIMALAC);
			
	}
	
	@Test
	public void testCountByPrimalac() {
		
		RegistrovaniKorisnik primalac = regKorisnikService.getRegKorisnik(RegKorisnikConstraints.DB_PRIM);

		Long count = regKorisnikService.getPosiljaociCount(primalac);
		assertThat(count).isEqualTo(RegKorisnikConstraints.COUNT_BY_PRIMALAC);
	
	}
	
	@Test
	public void testGetPrijatelji() {
		
		Korisnik korisnik = korisnikService.getKorisnik(RegKorisnikConstraints.DB_REG_KOR_ID);
		
		Page<Korisnik> prijatelji = regKorisnikService.getPrijatelji(korisnik, new PageRequest(RegKorisnikConstraints.PAGE_N, RegKorisnikConstraints.PAGE_SIZE));
		assertThat(prijatelji.getContent().size()).isEqualTo(RegKorisnikConstraints.DB_REG_KOR_PCOUNT);
		
	}
	
	@Test
	public void testGetPrijateljiByNameAndSurname() {
		
		Korisnik korisnik = korisnikService.getKorisnik(RegKorisnikConstraints.DB_REG_KOR_ID);
		
		Page<Korisnik> prijatelji = regKorisnikService.getPrijateljiByNameAndSurname(korisnik, RegKorisnikConstraints.IMEPREZIME,new PageRequest(RegKorisnikConstraints.PAGE_N, RegKorisnikConstraints.PAGE_SIZE));
		assertThat(prijatelji.getContent().size()).isEqualTo(RegKorisnikConstraints.DB_REG_KOR_PCOUNTIME);		
		
	}
	
	@Test
	public void testCountPrijateljiByNameAndSurname() {
		
		Korisnik korisnik = korisnikService.getKorisnik(RegKorisnikConstraints.DB_REG_KOR_ID);
		
		Long count = regKorisnikService.countPrijateljiByNameAndSurname(korisnik, RegKorisnikConstraints.IMEPREZIME);
		assertThat(count).isEqualTo(RegKorisnikConstraints.DB_REG_KOR_PCOUNTIME);		
		
	}
	
}
