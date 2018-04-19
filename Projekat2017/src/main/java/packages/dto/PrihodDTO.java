package packages.dto;

public class PrihodDTO {
	
	private int idPozBio;
	private String datumOd;
	private String datumDo;
	private int mode;
	
	private PrihodDTO() {
		
	}
	
	private PrihodDTO(int idPozBio, String datumOd, String datumDo, int mode) {
		this.idPozBio = idPozBio;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.mode = mode;
	}

	public int getIdPozBio() {
		return idPozBio;
	}

	public void setIdPozBio(int idPozBio) {
		this.idPozBio = idPozBio;
	}

	public String getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(String datumOd) {
		this.datumOd = datumOd;
	}

	public String getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(String datumDo) {
		this.datumDo = datumDo;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return "PrihodDTO [idPozBio=" + idPozBio + ", datumOd=" + datumOd + ", datumDo=" + datumDo + ", mode=" + mode
				+ "]";
	}
	
}
