package packages.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class KorisnikSedisteDTO {

	@NotNull(message = "Morate uneti id korisnika")
	@Min(value = 1, message = "Morate uneti id korisnika")
	private Long korisnikId;
	@NotNull(message = "Morate uneti id sedista")
	@Min(value = 1, message = "Morate uneti id sedista")
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
