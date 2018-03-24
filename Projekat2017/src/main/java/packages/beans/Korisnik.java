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

import packages.enumerations.KorisnikTip;
import packages.enumerations.RegKorisnikStatus;


@Entity
public class Korisnik implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private KorisnikTip tip; 
	
	@Column(nullable = false, unique = true, length = 90)
	private String email;
	
	@Column(nullable = false, length = 30)
	private char[] lozinka;
	
	@Column(nullable = false, length = 30)
	private String ime;
	
	@Column(nullable = false, length = 30)
	private String prezime;
	
	@Column(nullable = false, length = 60)
	private String grad;
	
	@Column(nullable = true, length = 20)
	private String telefon;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RegKorisnikStatus status; 
	
	public Korisnik() {
		
	}

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
