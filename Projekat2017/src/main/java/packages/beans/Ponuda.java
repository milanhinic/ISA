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

	public Ponuda() {
		
	}

	public Ponuda(Long id, Oglas oglas, Double iznos) {
		super();
		this.id = id;
		this.oglas = oglas;
		this.iznos = iznos;
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


	public Oglas getOglasa() {
		return oglas;
	}

	public void setOglasa(Oglas idOglasa) {
		this.oglas = oglas;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	@Override
	public String toString() {
		return "Ponuda [id=" + id + ", oglas=" + oglas + ", iznos=" + iznos + "]";
	}
	
	
	
	
	
}
