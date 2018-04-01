package packages.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import packages.enumerations.KorisnikTip;
import packages.validators.TelephoneValidation;

public class KorisnikDTO {

	private KorisnikTip tip;	
	@NotNull(message = "Morate uneti email adresu")
	@Size(max = 90, message = "Uneli ste prevelik broj karaktera za email adresu")
	private String email;
	@NotNull(message = "Morate uneti ime")
	@Size(min = 1,max = 30,message = "Uneli ste nedozvoljen broj karaktera za ime")
	private String ime;
	@NotNull(message = "Morate uneti prezime")
	@Size(min = 1,max = 30,message = "Uneli ste nedozvoljen broj karaktera za prezime")
	private String prezime;
	@NotNull(message = "Morate uneti grad")
	@Size(min = 1,max = 60,message = "Uneli ste nedozvoljen broj karaktera za grad")
	private String grad;
	@TelephoneValidation
	private String telefon;
	
	public KorisnikDTO() {}

	public KorisnikDTO(KorisnikTip tip, String email, String ime, String prezime, String grad, String telefon) {
		super();
		this.tip = tip;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.grad = grad;
		this.telefon = telefon;
	}
	
	public KorisnikTip getTip() {
		return tip;
	}

	public void setTip(KorisnikTip tip) {
		this.tip = tip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
}
