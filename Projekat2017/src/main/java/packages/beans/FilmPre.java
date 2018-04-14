package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import packages.enumerations.FilmPreTip;

@Entity
public class FilmPre implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FilmPreTip tip;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private String spisakGlumaca;
	
	@Column(nullable = false)
	private String imeReditelja;
	
	@Column(nullable = false)
	private Integer trajanje;
	
	
	@Column(nullable = false)
	private Double prosecnaOcena;


	public FilmPre() {
	
	}

	public FilmPre(Long id, FilmPreTip tip, String naziv, String spisakGlumaca, String imeReditelja, Integer trajanje,
			Double prosecnaOcena) {
		super();
		this.id = id;
		this.tip = tip;
		this.naziv = naziv;
		this.spisakGlumaca = spisakGlumaca;
		this.imeReditelja = imeReditelja;
		this.trajanje = trajanje;
		this.prosecnaOcena = prosecnaOcena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FilmPreTip getTip() {
		return tip;
	}

	public void setTip(FilmPreTip tip) {
		this.tip = tip;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSpisakGlumaca() {
		return spisakGlumaca;
	}

	public void setSpisakGlumaca(String spisakGlumaca) {
		this.spisakGlumaca = spisakGlumaca;
	}

	public String getImeReditelja() {
		return imeReditelja;
	}

	public void setImeReditelja(String imeReditelja) {
		this.imeReditelja = imeReditelja;
	}

	public Integer getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(Integer trajanje) {
		this.trajanje = trajanje;
	}

	public Double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	@Override
	public String toString() {
		return "FilmPre [id=" + id + ", tip=" + tip + ", naziv=" + naziv + ", spisakGlumaca=" + spisakGlumaca
				+ ", imeReditelja=" + imeReditelja + ", trajanje=" + trajanje + ", prosecnaOcena=" + prosecnaOcena
				+ "]";
	}
	
	
	
	
	
	
	
}
