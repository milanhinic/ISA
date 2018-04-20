package packages.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Poruka {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String poruka;

	@ManyToOne(optional = false)
	private RegistrovaniKorisnik rk;

	public Poruka() {
		
	}

	public Poruka(Long id, String poruka, RegistrovaniKorisnik rk) {
		super();
		this.id = id;
		this.poruka = poruka;
		this.rk = rk;
	}
	
	public Poruka(String poruka, RegistrovaniKorisnik rk) {
		super();
		this.poruka = poruka;
		this.rk = rk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}

	public RegistrovaniKorisnik getRk() {
		return rk;
	}

	public void setRk(RegistrovaniKorisnik rk) {
		this.rk = rk;
	}

	@Override
	public String toString() {
		return "Poruka [id=" + id + ", poruka=" + poruka + ", rk=" + rk + "]";
	}
	
	
	
	
	
}
