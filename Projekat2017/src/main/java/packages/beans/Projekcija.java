package packages.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Projekcija implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(nullable = false)
	private Long redniBrojProjekcije;
	
	@Column(nullable = false)
	private Long idFilmPre;
	
	@Column(nullable = false)
	private Long idSale;
	
	@Column(nullable = false)	
	private String vreme;

	public Projekcija() {
		
	}

	public Projekcija(Long redniBrojProjekcije, Long idFilmPre, Long idSale, String vreme) {
		super();
		this.redniBrojProjekcije = redniBrojProjekcije;
		this.idFilmPre = idFilmPre;
		this.idSale = idSale;
		this.vreme = vreme;
	}

	@Override
	public String toString() {
		return "Projekcija [redniBrojProjekcije=" + redniBrojProjekcije + ", idFilmPre=" + idFilmPre + ", idSale="
				+ idSale + ", vreme=" + vreme + "]";
	}
	
	
	
	
	
	
	
}
