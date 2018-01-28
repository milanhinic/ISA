package packages.beans;

public class PozBio {

	private String id;
	private String tip; 
	private String naziv;
	private String adresa;
	private String opis;
	
	public PozBio() {
		
	}
	
	public PozBio(String id, String tip, String naziv, String adresa, String opis) {
		super();
		this.id = id;
		this.tip = tip;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
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
	
	
	
}
