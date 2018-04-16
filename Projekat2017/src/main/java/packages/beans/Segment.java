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

@Entity
public class Segment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	@NotNull(message = "More pripadati SALI.")
	private Sala sala;
	
	@ManyToOne(optional = false)
	@NotNull(message = "Mora posedovati TIP.")
	private TipSegmenta tip;
	
	public Segment() {}

	public Segment(Sala sala, TipSegmenta tip) {
		super();
		this.sala = sala;
		this.tip = tip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Segment [id=" + id + ", sala=" + sala + ", tip=" + tip + "]";
	}
}
