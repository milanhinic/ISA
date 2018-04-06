package packages.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Zahtev implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	private RegistrovaniKorisnik posiljalac;
	
	@ManyToOne(optional = false)
	private RegistrovaniKorisnik primalac;
	
	public Zahtev() {}
	
	public Zahtev(Long id, RegistrovaniKorisnik posiljalac, RegistrovaniKorisnik primalac) {
		super();
		this.id = id;
		this.posiljalac = posiljalac;
		this.primalac = primalac;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegistrovaniKorisnik getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(RegistrovaniKorisnik posiljalac) {
		this.posiljalac = posiljalac;
	}

	public RegistrovaniKorisnik getPrimalac() {
		return primalac;
	}

	public void setPrimalac(RegistrovaniKorisnik primalac) {
		this.primalac = primalac;
	}	
}
