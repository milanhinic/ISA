package packages.beans;

import packages.enumerations.KorisnikTip;

public class KorisnikDTO {

	private KorisnikTip tip;
	private String email;
	private String ime;
	private String prezime;
	private String grad;
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
