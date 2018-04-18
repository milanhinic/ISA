package packages.dto;

import packages.beans.Sediste;

public class SedisteDTO {

	private Sediste sediste;
	private boolean zauzeto;
	
	public SedisteDTO(){}

	public SedisteDTO(Sediste sediste, boolean zauzeto) {
		super();
		this.sediste = sediste;
		this.zauzeto = zauzeto;
	}

	public Sediste getSediste() {
		return sediste;
	}

	public void setSediste(Sediste sediste) {
		this.sediste = sediste;
	}

	public boolean isZauzeto() {
		return zauzeto;
	}

	public void setZauzeto(boolean zauzeto) {
		this.zauzeto = zauzeto;
	}
}
