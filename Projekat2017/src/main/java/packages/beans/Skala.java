package packages.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Skala {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private Double od;
	
	@Column(nullable = false)
	private Double doo;

	public Skala() {
	
	}
	
	
	public Skala(Long id, String naziv, Double od, Double doo) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.od = od;
		this.doo = doo;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public Double getOd() {
		return od;
	}


	public void setOd(Double od) {
		this.od = od;
	}


	public Double getDoo() {
		return doo;
	}


	public void setDoo(Double doo) {
		this.doo = doo;
	}


	@Override
	public String toString() {
		return "Skala [id=" + id + ", naziv=" + naziv + ", od=" + od + ", doo=" + doo + "]";
	}
	
	
	
	
	
	
}
