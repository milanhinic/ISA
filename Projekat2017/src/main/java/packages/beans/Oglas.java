package packages.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import packages.enumerations.OglasStatus;

@Entity
public class Oglas implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private String opis;
	
	@Column(nullable = false)
	private String aktivnoDo;
	
	@Lob
	@Column(nullable = false)
	private byte[] path;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OglasStatus status;
	
	@OneToMany
	private Set<Ponuda> datePonude;

	public Oglas() {
		
	}

	public Oglas(Long id, String naziv, String opis, String aktivnoDo, String path, OglasStatus status) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.aktivnoDo = aktivnoDo;
		this.status = status;
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
	
	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getAktivnoDo() {
		return aktivnoDo;
	}

	public void setAktivnoDo(String aktivnoDo) {
		this.aktivnoDo = aktivnoDo;
	}

	public OglasStatus getStatus() {
		return status;
	}

	public void setStatus(OglasStatus status) {
		this.status = status;
	}

	public Set<Ponuda> getDatePonude() {
		return datePonude;
	}

	public void setDatePonude(Set<Ponuda> datePonude) {
		this.datePonude = datePonude;
	}

	@Override
	public String toString() {
		return "Oglas [id=" + id + ", naziv=" + naziv + ", opis=" + opis + ", aktivnoDo=" + aktivnoDo + ", path=" + path
				+ ", status=" + status + ", datePonude=" + datePonude + "]";
	}
	
	
	
	
	
}
