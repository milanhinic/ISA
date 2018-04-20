package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ponuda implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@ManyToOne(optional = false)
	private Oglas oglas;
	
	@Column(nullable = false)
	private Double iznos;

	@ManyToOne(optional = false)
	private RegistrovaniKorisnik rk;
	
	
	public Ponuda() {
		
	}

	public Ponuda(Long id, Oglas oglas, Double iznos, RegistrovaniKorisnik rk) {
		super();
		this.id = id;
		this.oglas = oglas;
		this.iznos = iznos;
		this.rk = rk;
	}
	
	public Ponuda(Oglas oglas, Double iznos, RegistrovaniKorisnik rk) {
		super();
		this.oglas = oglas;
		this.iznos = iznos;
		this.rk = rk;
	}
	
	public Ponuda(Oglas oglas, Double iznos) {
		super();
		this.oglas = oglas;
		this.iznos = iznos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	public Oglas getOglas() {
		return oglas;
	}

	public void setOglas(Oglas oglas) {
		this.oglas = oglas;
	}

	public RegistrovaniKorisnik getRk() {
		return rk;
	}

	public void setRk(RegistrovaniKorisnik rk) {
		this.rk = rk;
	}

	@Override
	public String toString() {
		return "Ponuda [id=" + id + ", oglas=" + oglas + ", iznos=" + iznos + ", rk=" + rk + "]";
	}

	
	
	
	
	
	
}
