package packages.beans;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class CustomUserDetailsFactory {

	private CustomUserDetailsFactory() {}
	
	public static CustomUserDetails createCustomUserDetails(Korisnik korisnik) {
		return new CustomUserDetails(
				korisnik.getEmail(),
				korisnik.getId(),
				mapToGrantedAuthorities(korisnik.getTip().toString())
				);
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(String role) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }
	
}
