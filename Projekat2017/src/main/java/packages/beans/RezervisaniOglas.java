package packages.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class RezervisaniOglas {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@Column(nullable = false)
	private Long rk;
	
	
	@Column(nullable = false)
	private Long oglas;


	public RezervisaniOglas() {
	
	}


	public RezervisaniOglas(Long id, Long rk, Long oglas) {
		super();
		this.id = id;
		this.rk = rk;
		this.oglas = oglas;
	}
	
	public RezervisaniOglas(Long rk, Long oglas) {
		super();
		this.rk = rk;
		this.oglas = oglas;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getRk() {
		return rk;
	}


	public void setRk(Long rk) {
		this.rk = rk;
	}


	public Long getOglas() {
		return oglas;
	}


	public void setOglas(Long oglas) {
		this.oglas = oglas;
	}


	@Override
	public String toString() {
		return "RezervisaniOglas [id=" + id + ", rk=" + rk + ", oglas=" + oglas + "]";
	}
	
	
	
	
}
