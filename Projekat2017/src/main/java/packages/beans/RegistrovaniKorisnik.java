package packages.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class RegistrovaniKorisnik implements Serializable{

	private static final long serialVersionUID = 1L;

	public RegistrovaniKorisnik() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(optional=false)
	private Korisnik reg_korisnik_id;
	
	@Column(nullable = false)
	private int br_bodova;
	
	@ManyToMany
	private Set<RegistrovaniKorisnik> prijatelji;
	
	
	
	public RegistrovaniKorisnik(Long id, Korisnik reg_korisnik_id, int br_bodova) {
		super();
		this.id = id;
		this.reg_korisnik_id = reg_korisnik_id;
		this.br_bodova = br_bodova;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Korisnik getReg_korisnik_id() {
		return reg_korisnik_id;
	}

	public void setReg_korisnik_id(Korisnik reg_korisnik_id) {
		this.reg_korisnik_id = reg_korisnik_id;
	}

	public int getBr_bodova() {
		return br_bodova;
	}

	public void setBr_bodova(int br_bodova) {
		this.br_bodova = br_bodova;
	}

	public Set<RegistrovaniKorisnik> getPrijatelji() {
		return prijatelji;
	}

	public void setPrijatelji(Set<RegistrovaniKorisnik> prijatelji) {
		this.prijatelji = prijatelji;
	}


	@Override
	public String toString() {
		return "RegistrovaniKorisnik [id=" + id + ", reg_korisnik_id=" + reg_korisnik_id + ", br_bodova=" + br_bodova
				+ ", prijatelji=" + prijatelji + ", licniOglasi="  + "]";
	}

}
