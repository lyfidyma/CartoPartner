package com.carto.sn.entities;

public class ProjetsEtRegions {
	private Long idProjet;
	private Long idRegion;
	public ProjetsEtRegions(Long idProjet, Long idRegion) {
		super();
		this.idProjet = idProjet;
		this.idRegion = idRegion;
	}
	public Long getIdProjet() {
		return idProjet;
	}
	public void setIdProjet(Long idProjet) {
		this.idProjet = idProjet;
	}
	public Long getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(Long idRegion) {
		this.idRegion = idRegion;
	}

}
