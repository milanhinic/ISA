package packages.dto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LozinkaDTO {

	@NotNull(message = "Morate uneti prethodnu lozinku")
	@Size(min = 8, max = 30, message = "Uneli ste nedozvoljen broj karaktera za prethodnu lozinku")
	private char[] staraLozinka;

	@NotNull(message = "Morate uneti novu lozinku")
	@Size(min = 8, max = 30, message = "Uneli ste nedozvoljen broj karaktera za novu lozinku")
	private char[] novaLozinka;
	
	@NotNull(message = "Morate potvrditi novu lozinku lozinku")
	@Size(min = 8, max = 30, message = "Uneli ste nedozvoljen broj karaktera za potvrdu nove lozinke")
	private char[] potvrdaLozinke;
	
	public LozinkaDTO() {}
	
	public LozinkaDTO(char[] staraLozinka, char[] novaLozinka, char[] potvrdaLozinke) {
		super();
		this.staraLozinka = staraLozinka;
		this.novaLozinka = novaLozinka;
		this.potvrdaLozinke = potvrdaLozinke;
	}

	public char[] getStaraLozinka() {
		return staraLozinka;
	}

	public void setStaraLozinka(char[] staraLozinka) {
		this.staraLozinka = staraLozinka;
	}

	public char[] getNovaLozinka() {
		return novaLozinka;
	}

	public void setNovaLozinka(char[] novaLozinka) {
		this.novaLozinka = novaLozinka;
	}

	public char[] getPotvrdaLozinke() {
		return potvrdaLozinke;
	}

	public void setPotvrdaLozinke(char[] potvrdaLozinke) {
		this.potvrdaLozinke = potvrdaLozinke;
	}
	
	
}