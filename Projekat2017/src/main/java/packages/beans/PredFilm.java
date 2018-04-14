package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import packages.enumerations.PredFilmTip;


@Entity
public class PredFilm implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Neophodno je uneti NAZIV.")
	@Size(max = 90, message = "Prekoracen maksimalan broj karaktera za polje NAZIV.")
	private String naziv;
	
	@Column(nullable = false)
	@NotNull(message = "Neophodno je uneti TIP.")
	@Enumerated(EnumType.STRING)
	private PredFilmTip tip;
	
	@Column(nullable = false)
	@NotNull(message = "Neophodno je uneti ZANR.")
	@Size(max = 30, message = "Prekoracen maksimalan broj karaktera za polje ZANR.")
	private String zanr;
	
	@Column(nullable = true)
	@Size(max = 60, message = "Prekoracen maksimalan broj karaktera za polje REDITELJ.")
	private String reditelj;
	
	@Column(nullable = false)
	@NotNull(message = "Neophodno je uneti Trajanje.")
	@Min(0)
	@Max(1500)
	private int trajanje;
	
	@Column(nullable = true)
	private String opis;
	
	@Column(nullable = true)
	private String glumci;
	
	public PredFilm() {
		
	}
	
	public PredFilm(String naziv, String zanr, String reditelj, int trajanje, String opis, String glumci) {
		super();
		this.naziv = naziv;
		this.zanr = zanr;
		this.reditelj = reditelj;
		this.trajanje = trajanje;
		this.opis = opis;
		this.glumci = glumci;
	}



	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getZanr() {
		return zanr;
	}

	public void setZanr(String zanr) {
		this.zanr = zanr;
	}

	public String getReditelj() {
		return reditelj;
	}

	public void setReditelj(String reditelj) {
		this.reditelj = reditelj;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PredFilmTip getTip() {
		return tip;
	}

	public void setTip(PredFilmTip tip) {
		this.tip = tip;
	}

	public String getGlumci() {
		return glumci;
	}

	public void setGlumci(String glumci) {
		this.glumci = glumci;
	}

	@Override
	public String toString() {
		return "PredFilm [id=" + id + ", naziv=" + naziv + ", tip=" + tip + ", zanr=" + zanr + ", reditelj=" + reditelj
				+ ", trajanje=" + trajanje + ", opis=" + opis + ", glumci=" + glumci + "]";
	}
	
	
	
}
