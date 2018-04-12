package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class TipSegmenta implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Neophodno je uneti NAZIV.")
	@Size(max = 10, message = "Prekoracen maksimalan broj karaktera za polje NAZIV.")
	private String naziv;
	
	@Column(nullable = false)
	@NotNull(message = "Neophodno je uneti CENU.")
	@Min(0)
	private double cena;
	
	public TipSegmenta() { }

	public TipSegmenta(String naziv, double cena) {
		super();
		this.naziv = naziv;
		this.cena = cena;
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

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
}
