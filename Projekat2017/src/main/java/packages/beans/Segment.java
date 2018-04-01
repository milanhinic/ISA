package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Segment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Neophodno je uneti BROJ SEDISTA.")
	@Max(5000)
	@Min(1)
	private int broj_sedista;
	
	@ManyToOne(optional = false)
	private Sala sala;
	
	@ManyToOne(optional = false)
	private TipSegmenta tip;
	
	public Segment() {}

	public Segment(int broj_sedista, Sala sala, TipSegmenta tip) {
		super();
		this.broj_sedista = broj_sedista;
		this.sala = sala;
		this.tip = tip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBroj_sedista() {
		return broj_sedista;
	}

	public void setBroj_sedista(int broj_sedista) {
		this.broj_sedista = broj_sedista;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public TipSegmenta getTip() {
		return tip;
	}

	public void setTip(TipSegmenta tip) {
		this.tip = tip;
	}
	
}
