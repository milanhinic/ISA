package packages.dto;

public class KorisnikSedisteDTO {

	private Long korisnikId;
	private Long sedisteId;
	
	public KorisnikSedisteDTO() {}

	public KorisnikSedisteDTO(Long korisnikId, Long sedisteId) {
		super();
		this.korisnikId = korisnikId;
		this.sedisteId = sedisteId;
	}

	public Long getKorisnikId() {
		return korisnikId;
	}

	public void setKorisnikId(Long korisnikId) {
		this.korisnikId = korisnikId;
	}

	public Long getSedisteId() {
		return sedisteId;
	}

	public void setSedisteId(Long sedisteId) {
		this.sedisteId = sedisteId;
	}

	@Override
	public String toString() {
		return "KorisnikSedisteDTO [korisnikId=" + korisnikId + ", sedisteId=" + sedisteId + "]";
	}
	
}
