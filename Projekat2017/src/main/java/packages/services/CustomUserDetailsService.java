package packages.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import packages.beans.CustomUserDetailsFactory;
import packages.beans.Korisnik;
import packages.repositories.KorisnikRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikRepository.findByEmail(username);
		
		if(korisnik==null) {
			throw new UsernameNotFoundException("Korisnik ne postoji");
		}else {
			return CustomUserDetailsFactory.createCustomUserDetails(korisnik);
		}
			
		
	}

}
