package packages.serviceInterfaces;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import packages.beans.Poruka;
import packages.beans.RegistrovaniKorisnik;

public interface PorukaInterface {

	public Poruka addPoruka(Poruka poruka);
	
	public Page<Poruka> getPorukeByRk(RegistrovaniKorisnik rk, Pageable page);
	
	public int deleteById(Long id);
	
}
