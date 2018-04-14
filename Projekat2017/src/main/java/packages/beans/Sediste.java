package packages.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Sediste {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	@NotNull(message = "More pripadati SEGMENTU.")
	private Segment segment;
	
	public Sediste() {
		
	}
	
	public Sediste(Segment segment) {
		this.segment = segment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	@Override
	public String toString() {
		return "Sediste [id=" + id + ", segment=" + segment + "]";
	}
	
}
