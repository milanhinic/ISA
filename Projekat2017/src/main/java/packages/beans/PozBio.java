package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import packages.enumerations.PozBioTip;

@Entity
public class PozBio implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PozBioTip tip; 
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private String adresa;
	
	@Column(nullable = true)
	private String opis;
	
	public PozBio() {
		
	}
	
	public PozBio(PozBioTip tip, String naziv, String adresa, String opis) {
		super();
		this.tip = tip;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PozBioTip getTip() {
		return tip;
	}

	public void setTip(PozBioTip tip) {
		this.tip = tip;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	@Override
	public String toString() {
		return "PozBio [id=" + id + ", tip=" + tip + ", naziv=" + naziv + ", adresa=" + adresa + ", opis=" + opis + "]";
	}
	
	
	
}
