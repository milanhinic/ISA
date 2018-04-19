package packages.dto;

import java.util.ArrayList;

public class RezervisiDTO {

	private ArrayList<KorisnikSedisteDTO> korisnikSedistaDTO;
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
