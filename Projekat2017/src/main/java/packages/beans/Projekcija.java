package packages.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Projekcija implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	private PredFilm predFilm;
	
	@ManyToOne(optional = false)
	private Sala sala;
	
	@Column(nullable = false)
	@NotNull(message = "Obavezno je izabrati DATUM")
	private Date datum;
	
	public Projekcija() {
		
	}

	public Projekcija(PredFilm predFilm, Sala sala, Date datum) {
		super();
		this.predFilm = predFilm;
		this.sala = sala;
		this.datum = datum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PredFilm getPredFilm() {
		return this.predFilm;
	}

	public void setPredFilm(PredFilm predFilm) {
		this.predFilm = predFilm;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Projekcija [id=" + id + ", filmPred=" + predFilm + ", sala=" + sala + ", datum=" + datum + "]";
	}
	
	
}
