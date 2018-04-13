package packages.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class SegmentDTO {
	
	@NotNull(message = "Neophodno je uneti BROJ SEDISTA.")
	@Max(5000)
	@Min(1)
	private int brojSedista;
	
	public SegmentDTO() {}

	public SegmentDTO(int broj_sedista) {
		super();
		this.brojSedista = broj_sedista;
	}

	public int getBrojSedista() {
		return brojSedista;
	}

	public void setBrojSedista(int brojSedista) {
		this.brojSedista = brojSedista;
	}
	
}
