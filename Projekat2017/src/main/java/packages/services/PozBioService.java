package packages.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import packages.beans.PozBio;
import packages.enumerations.PozBioTip;
import packages.repositories.PozBioRepository;
import packages.serviceInterfaces.PozBioInterface;

@Service
public class PozBioService implements PozBioInterface {

	@Autowired
	PozBioRepository pbr;
	
	
	@Override
	public PozBio getPozBio(Long id, PozBioTip tip) {
		//Assert.isTrue(id >= 0, "Primarni identifikator pozorista/bioskopa ne sme biti < 0!");
		//Assert.isTrue(!(tip.equals(PozBioTip.BIO) || tip.equals(PozBioTip.POZ) || (tip == null)), "Tip se mora odnositi ili na bioskop ili na pozoriste!");
		return pbr.findOne(id);
	}

	@Override
	public PozBio addPozBio(PozBio pozBio) {
		//Assert.isNull(pozBio, "Nije moguce upisati null kao pozoriste/bioskop!");
		return pbr.save(pozBio);
	}

	@Override
	public Page<PozBio> getPozBioList(PozBioTip tip, Pageable pageable) {

		//Assert.isFalse(!(tip.equals(PozBioTip.BIO) || tip.equals(PozBioTip.POZ) || (tip == null)), "Tip se mora odnositi ili na bioskop ili na pozoriste!");
		Assert.notNull(pageable, "Naispravno navedena duzina liste za vracanje pozorista/bioskopa!");
		
		return pbr.findByTip(tip, pageable);
	}

}
