package packages.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import packages.beans.Segment;


public class SegmentDTO {
	
	@NotNull(message = "Neophodno je uneti BROJ SEDISTA.")
	@Max(5000)
	@Min(1)
	private int brojSedista;
	
	@NotNull(message = "Neophodno je uneti SEGMENT.")
	private Segment segment;
	
	public SegmentDTO() {}

	public SegmentDTO(int broj_sedista, Segment segment) {
		super();
		this.brojSedista = broj_sedista;
		this.segment = segment;
	}

	public int getBrojSedista() {
		return brojSedista;
	}

	public void setBrojSedista(int brojSedista) {
		this.brojSedista = brojSedista;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	
}