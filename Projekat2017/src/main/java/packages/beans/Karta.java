package packages.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Karta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	private Projekcija projekcija;
	
	@ManyToOne(optional = false)
	private Sediste sediste;
	
	@Column
	private Boolean brzaRezervacija;
	
	public Karta() {}

	public Karta(Long id, Projekcija projekcija, Sediste sediste, Boolean brzaRezervacija) {
		super();
		this.id = id;
		this.projekcija = projekcija;
		this.sediste = sediste;
		this.brzaRezervacija = brzaRezervacija;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Projekcija getProjekcija() {
		return projekcija;
	}

	public void setProjekcija(Projekcija projekcija) {
		this.projekcija = projekcija;
	}

	public Sediste getSediste() {
		return sediste;
	}

	public void setSediste(Sediste sediste) {
		this.sediste = sediste;
	}

	public Boolean getBrzaRezervacija() {
		return brzaRezervacija;
	}

	public void setBrzaRezervacija(Boolean brzaRezervacija) {
		this.brzaRezervacija = brzaRezervacija;
	}

	@Override
	public String toString() {
		return "Karta [id=" + id + ", projekcija=" + projekcija + ", sediste=" + sediste + ", brzaRezervacija="
				+ brzaRezervacija + "]";
	}
}
