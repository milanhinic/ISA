package packages.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rezervacija {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	private Karta karta;
	
	@ManyToOne(optional = false)
	private RegistrovaniKorisnik regKorisnik;
	
	@Column(nullable = true)
	private Integer ocenaAmbijenta;
	
	@Column(nullable = true)
	private Integer ocenaProjekcije;
	
	public Rezervacija() {}

	public Rezervacija(Long id, Karta karta, RegistrovaniKorisnik regKorisnik, 
			Integer ocenaAmbijenta, Integer ocenaProjekcije) {
		super();
		this.id = id;
		this.karta = karta;
		this.regKorisnik = regKorisnik;
		this.ocenaAmbijenta = ocenaAmbijenta;
		this.ocenaProjekcije = ocenaProjekcije;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Karta getKarta() {
		return karta;
	}

	public void setKarta(Karta karta) {
		this.karta = karta;
	}

	public RegistrovaniKorisnik getRegKorisnik() {
		return regKorisnik;
	}

	public void setRegKorisnik(RegistrovaniKorisnik regKorisnik) {
		this.regKorisnik = regKorisnik;
	}

	public Integer getOcenaAmbijenta() {
		return ocenaAmbijenta;
	}

	public void setOcenaAmbijenta(Integer ocenaAmbijenta) {
		this.ocenaAmbijenta = ocenaAmbijenta;
	}

	public Integer getOcenaProjekcije() {
		return ocenaProjekcije;
	}

	public void setOcenaProjekcije(Integer ocenaProjekcije) {
		this.ocenaProjekcije = ocenaProjekcije;
	}

	@Override
	public String toString() {
		return "Rezervacija [id=" + id + ", karta=" + karta + ", regKorisnik=" + regKorisnik + ", ocenaAmbijenta="
				+ ocenaAmbijenta + ", ocenaProjekcije=" + ocenaProjekcije + "]";
	}
}
