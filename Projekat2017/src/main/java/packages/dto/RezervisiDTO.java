package packages.dto;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import packages.validators.KorisnikSedisteValidation;

public class RezervisiDTO {
	
	@KorisnikSedisteValidation
	private @Valid ArrayList<KorisnikSedisteDTO> korisnikSedistaDTO;
	
	@NotNull(message = "Morate uneti id projekcije")
	@Min(value = 1, message = "Morate uneti id projekcije")
	private Long projekcijaId;
	
	public RezervisiDTO() {}

	public RezervisiDTO(ArrayList<KorisnikSedisteDTO> korisnikSedistaDTO, Long projekcijaId) {
		super();
		this.korisnikSedistaDTO = korisnikSedistaDTO;
		this.projekcijaId = projekcijaId;
	}

	public ArrayList<KorisnikSedisteDTO> getKorisnikSedistaDTO() {
		return korisnikSedistaDTO;
	}

	public void setKorisnikSedistaDTO(ArrayList<KorisnikSedisteDTO> korisnikSedistaDTO) {
		this.korisnikSedistaDTO = korisnikSedistaDTO;
	}

	public Long getProjekcijaId() {
		return projekcijaId;
	}

	public void setProjekcijaId(Long projekcijaId) {
		this.projekcijaId = projekcijaId;
	}

	@Override
	public String toString() {
		return "RezervisiDTO [korisnikSedistaDTO=" + korisnikSedistaDTO + ", projekcijaId=" + projekcijaId + "]";
	}	
	
}
