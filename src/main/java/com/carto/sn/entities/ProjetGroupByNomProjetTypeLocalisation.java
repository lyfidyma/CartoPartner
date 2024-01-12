package com.carto.sn.entities;

public class ProjetGroupByNomProjetTypeLocalisation {
	private Long idLocalisation;
	private Long idPartenaire;
	private String nomProjet;
	private String type;
	private String localisation;
	private String nomPartenaire;
	public ProjetGroupByNomProjetTypeLocalisation(Long idLocalisation, Long idPartenaire, String nomProjet, String type,
			String localisation, String nomPartenaire) {
		super();
		this.idLocalisation = idLocalisation;
		this.idPartenaire = idPartenaire;
		this.nomProjet = nomProjet;
		this.type = type;
		this.localisation = localisation;
		this.nomPartenaire = nomPartenaire;
	}
	public Long getIdLocalisation() {
		return idLocalisation;
	}
	public void setIdLocalisation(Long idLocalisation) {
		this.idLocalisation = idLocalisation;
	}
	public Long getIdPartenaire() {
		return idPartenaire;
	}
	public void setIdPartenaire(Long idPartenaire) {
		this.idPartenaire = idPartenaire;
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
	public String getLocalisation() {
		return localisation;
	}
	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	
	public String getNomPartenaire() {
		return nomPartenaire;
	}
	public void setNomPartenaire(String nomPartenaire) {
		this.nomPartenaire = nomPartenaire;
	}
	
	
}
