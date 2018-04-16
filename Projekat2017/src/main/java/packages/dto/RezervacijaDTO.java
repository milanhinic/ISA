package packages.dto;

import java.util.Date;

public class RezervacijaDTO {

	private Long id;
	
	private String projekcijaPredFilmNaziv;
	
	private String projekcijaSalaPozBioNaziv;
	
	private String projekcijaSalaNaziv;
	
	private String kartaSedisteSegmentTipNaziv;
	
	private double kartaSedisteSegmentTipCena;
	
	private Long kartaSedisteId;
	
	private String projekcijaDatum;
	
	private Integer ocenaAmbijenta;
	
	private Integer ocenaProjekcije;
	
	private Long projekcijaId;
	
	public RezervacijaDTO() {}

	public RezervacijaDTO(Long id, String projekcijaPredFilmNaziv, String projekcijaSalaPozBioNaziv,
			String projekcijaSalaNaziv, String kartaSedisteSegmentTipNaziv, double kartaSedisteSegmentTipCena, Long kartaSedisteId, String projekcijaDatum,
			Integer ocenaAmbijenta, Integer ocenaProjekcije, Long projekcijaId) {
		super();
		this.id = id;
		this.projekcijaPredFilmNaziv = projekcijaPredFilmNaziv;
		this.projekcijaSalaPozBioNaziv = projekcijaSalaPozBioNaziv;
		this.projekcijaSalaNaziv = projekcijaSalaNaziv;
		this.kartaSedisteSegmentTipNaziv = kartaSedisteSegmentTipNaziv;
		this.kartaSedisteSegmentTipCena = kartaSedisteSegmentTipCena;
		this.kartaSedisteId = kartaSedisteId;
		this.projekcijaDatum = projekcijaDatum;
		this.ocenaAmbijenta = ocenaAmbijenta;
		this.ocenaProjekcije = ocenaProjekcije;
		this.projekcijaId = projekcijaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjekcijaPredFilmNaziv() {
		return projekcijaPredFilmNaziv;
	}

	public void setProjekcijaPredFilmNaziv(String projekcijaPredFilmNaziv) {
		this.projekcijaPredFilmNaziv = projekcijaPredFilmNaziv;
	}

	public String getProjekcijaSalaPozBioNaziv() {
		return projekcijaSalaPozBioNaziv;
	}

	public void setProjekcijaSalaPozBioNaziv(String projekcijaSalaPozBioNaziv) {
		this.projekcijaSalaPozBioNaziv = projekcijaSalaPozBioNaziv;
	}

	public String getProjekcijaSalaNaziv() {
		return projekcijaSalaNaziv;
	}

	public void setProjekcijaSalaNaziv(String projekcijaSalaNaziv) {
		this.projekcijaSalaNaziv = projekcijaSalaNaziv;
	}

	public String getKartaSedisteSegmentTipNaziv() {
		return kartaSedisteSegmentTipNaziv;
	}

	public void setKartaSedisteSegmentTipNaziv(String kartaSedisteSegmentTipNaziv) {
		this.kartaSedisteSegmentTipNaziv = kartaSedisteSegmentTipNaziv;
	}

	public double getKartaSedisteSegmentTipCena() {
		return kartaSedisteSegmentTipCena;
	}

	public void setKartaSedisteSegmentTipCena(double kartaSedisteSegmentTipCena) {
		this.kartaSedisteSegmentTipCena = kartaSedisteSegmentTipCena;
	}

	public Long getKartaSedisteId() {
		return kartaSedisteId;
	}

	public void setKartaSedisteId(Long kartaSedisteId) {
		this.kartaSedisteId = kartaSedisteId;
	}

	public String getProjekcijaDatum() {
		return projekcijaDatum;
	}

	public void setProjekcijaDatum(String projekcijaDatum) {
		this.projekcijaDatum = projekcijaDatum;
	}

	public Integer getOcenaAmbijenta() {
		return ocenaAmbijenta;
	}

	public void setOcenaAmbijenta(Integer ocenaAmbijenta) {
		this.ocenaAmbijenta = ocenaAmbijenta;
	}

	public Integer getOcenaProjekcije() {
		return ocenaProjekcije;
	}

	public void setOcenaProjekcije(Integer ocenaProjekcije) {
		this.ocenaProjekcije = ocenaProjekcije;
	}

	public Long getProjekcijaId() {
		return projekcijaId;
	}

	public void setProjekcijaId(Long projekcijaId) {
		this.projekcijaId = projekcijaId;
	};
}
