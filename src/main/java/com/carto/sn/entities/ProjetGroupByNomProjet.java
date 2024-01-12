package com.carto.sn.entities;

import org.apache.tomcat.util.codec.binary.Base64;

public class ProjetGroupByNomProjet {
	private String nomProjet;
	private String type;
	private String responsable;
	private String statut;
	private byte[] dataImage;
	private Long idProjet;
	private String libelleLocalisation;
	
	
	
	public ProjetGroupByNomProjet(String nomProjet, String type, String responsable, String statut, byte[] dataImage,
			Long idProjet, String libelleLocalisation) {
		super();
		
		this.nomProjet = nomProjet;
		this.type = type;
		this.responsable = responsable;
		this.statut = statut;
		this.dataImage = dataImage;
		this.idProjet = idProjet;
		this.libelleLocalisation = libelleLocalisation;
	}
	
	public String getLibelleLocalisation() {
		return libelleLocalisation;
	}

	public void setLibelleLocalisation(String libelleLocalisation) {
		this.libelleLocalisation = libelleLocalisation;
	}

	public Long getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(Long idProjet) {
		this.idProjet = idProjet;
	}

	public String getNomProjet() {
		return nomProjet;
	}
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
	public String getStatut() {
		return statut;
	}
	public void setstatut(String statut) {
		this.statut = statut;
	}

	public byte[] getDataImage() {
		return dataImage;
	}

	public void setDataImage(byte[] dataImage) {
		this.dataImage = dataImage;
	}
	
	public String generateBase64Image() {
        return Base64.encodeBase64String(this.dataImage);
    }

}
