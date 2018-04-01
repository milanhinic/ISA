package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Sala implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Neophodno je uneti NAZIV.")
	@Size(max = 90, message = "Prekoracen maksimalan broj karaktera za polje NAZIV.")
	private String naziv;
	
	@ManyToOne(optional = false)
	private PozBio pozBio;
	
	public Sala() {};

	public Sala(String naziv, Long brojSedista, PozBio pozBio) {
		super();
		this.naziv = naziv;
		this.pozBio = pozBio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public PozBio getPozBio() {
		return pozBio;
	}

	public void setPozBio(PozBio pozBio) {
		this.pozBio = pozBio;
	}

	@Override
	public String toString() {
		return "Sala [id=" + id + ", naziv=" + naziv + ", poz_bio=" + pozBio + "]";
	}
	
	
}
