package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ponuda implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Long idRegKor;
	
	@Column(nullable = false)
	private Long idOglasa;
	
	@Column(nullable = false)
	private Double iznos;

	public Ponuda() {
		
	}

	public Ponuda(Long id, Long idRegKor, Long idOglasa, Double iznos) {
		super();
		this.id = id;
		this.idRegKor = idRegKor;
		this.idOglasa = idOglasa;
		this.iznos = iznos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdRegKor() {
		return idRegKor;
	}

	public void setIdRegKor(Long idRegKor) {
		this.idRegKor = idRegKor;
	}

	public Long getIdOglasa() {
		return idOglasa;
	}

	public void setIdOglasa(Long idOglasa) {
		this.idOglasa = idOglasa;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	@Override
	public String toString() {
		return "Ponuda [id=" + id + ", idRegKor=" + idRegKor + ", idOglasa=" + idOglasa + ", iznos=" + iznos + "]";
	}
	
	
	
	
	
}
