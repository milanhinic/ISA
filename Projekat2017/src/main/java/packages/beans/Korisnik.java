package packages.beans;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;
import packages.validators.EmailValidation;
import packages.validators.TelephoneValidation;


@Entity
public class Korisnik implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private KorisnikTip tip; 
	
	@NotNull(message = "Morate uneti email adresu")
	@Size(max = 90, message = "Uneli ste prevelik broj karaktera za email adresu")
	@EmailValidation
	@Column(nullable = false, unique = true, length = 90)
	private String email;
	
	@NotNull(message = "Morate uneti lozinku")
	@Size(min = 8, max = 30, message = "Uneli ste nedozvoljen broj karaktera za lozinku")
	@Column(nullable = false, length = 30)
	private char[] lozinka;
	
	@NotNull(message = "Morate uneti ime")
	@Size(min = 1,max = 30,message = "Uneli ste nedozvoljen broj karaktera za ime")
	@Column(nullable = false, length = 30)
	private String ime;
	
	@NotNull(message = "Morate uneti prezime")
	@Size(min = 1,max = 30,message = "Uneli ste nedozvoljen broj karaktera za prezime")
	@Column(nullable = false, length = 30)
	private String prezime;
	
	@NotNull(message = "Morate uneti grad")
	@Size(min = 1,max = 60,message = "Uneli ste nedozvoljen broj karaktera za grad")
	@Column(nullable = false, length = 60)
	private String grad;
	
	@TelephoneValidation
	@Column(nullable = true, length = 20)
	private String telefon;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RegKorisnikStatus status; 
	
	public Korisnik() {}

	public Korisnik(Long id, KorisnikTip tip, String email, char[] lozinka, String ime, String prezime, String grad,
			String telefon, RegKorisnikStatus status) {
		super();
		this.id = id;
		this.tip = tip;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.grad = grad;
		this.telefon = telefon;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public char[] getLozinka() {
		return lozinka;
	}

	public void setLozinka(char[] lozinka) {
		this.lozinka = lozinka;
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

	public RegKorisnikStatus getStatus() {
		return status;
	}

	public void setStatus(RegKorisnikStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", tip=" + tip + ", email=" + email + ", lozinka=" + Arrays.toString(lozinka)
				+ ", ime=" + ime + ", prezime=" + prezime + ", grad=" + grad + ", telefon=" + telefon + ", status="
				+ status + "]";
	}
	
}
