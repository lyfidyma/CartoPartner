package com.carto.sn.entities;

import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Projet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProjet;
	private String nomProjet;
	private String pointFocal;
	private String description;
	private int dateDebut;
	private int dateFin;
	@Lob
    @Column(length = Integer.MAX_VALUE)
	@NotAudited
    private byte[] dataImage;
	private String statut;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	Set <Partenaire> partenaire;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	Set <Region> region;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@NotAudited
	Set <Type> type; 
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	Set <Categorie> categorie;
	@JsonIgnore
	@OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
	@NotAudited
	private Set<ProjetPartenaireRegion> projetPartenaireRegion;
	@JsonIgnore
	@ManyToMany(mappedBy="projet")
	private Set <Utilisateur> utilisateur;

	 public String generateBase64Image() {
	        return Base64.encodeBase64String(this.dataImage);
	    }


	public Projet(String nomProjet, String pointFocal, String description, int dateDebut, int dateFin, byte[] dataImage,
			String statut) {
		super();
		this.nomProjet = nomProjet;
		this.pointFocal = pointFocal;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dataImage = dataImage;
		this.statut = statut;
	}


	public Projet(String nomProjet, String pointFocal, String description, int dateDebut, int dateFin, byte[] dataImage,
			String statut, Set<Partenaire> partenaire, Set<Region> region, Set<Type> type) {
		super();
		this.nomProjet = nomProjet;
		this.pointFocal = pointFocal;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dataImage = dataImage;
		this.statut = statut;
		this.partenaire = partenaire;
		this.region = region;
		this.type = type;
	}


	public Projet(String nomProjet, Set<Partenaire> partenaire, Set<Region> region, Set<Type> type) {
		super();
		this.nomProjet = nomProjet;
		this.partenaire = partenaire;
		this.region = region;
		this.type = type;
	}


	public Projet(String nomProjet, String pointFocal, String description, int dateDebut, int dateFin, byte[] dataImage,
			String statut, Set<Type> type) {
		super();
		this.nomProjet = nomProjet;
		this.pointFocal = pointFocal;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dataImage = dataImage;
		this.statut = statut;
		this.type = type;
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


	public String getPointFocal() {
		return pointFocal;
	}


	public void setPointFocal(String pointFocal) {
		this.pointFocal = pointFocal;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(int dateDebut) {
		this.dateDebut = dateDebut;
	}


	public int getDateFin() {
		return dateFin;
	}


	public void setDateFin(int dateFin) {
		this.dateFin = dateFin;
	}


	public byte[] getDataImage() {
		return dataImage;
	}


	public void setDataImage(byte[] dataImage) {
		this.dataImage = dataImage;
	}


	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}


	public Set<Partenaire> getPartenaire() {
		return partenaire;
	}


	public void setPartenaire(Set<Partenaire> partenaire) {
		this.partenaire = partenaire;
	}


	public Set<Region> getRegion() {
		return region;
	}


	public void setRegion(Set<Region> region) {
		this.region = region;
	}


	public Set<Type> getType() {
		return type;
	}


	public void setType(Set<Type> type) {
		this.type = type;
	}


	public Set<Categorie> getCategorie() {
		return categorie;
	}


	public void setCategorie(Set<Categorie> categorie) {
		this.categorie = categorie;
	}


	public Set<ProjetPartenaireRegion> getProjetPartenaireRegion() {
		return projetPartenaireRegion;
	}


	public void setProjetPartenaireRegion(Set<ProjetPartenaireRegion> projetPartenaireRegion) {
		this.projetPartenaireRegion = projetPartenaireRegion;
	}


	public Set<Utilisateur> getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Set<Utilisateur> utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Projet(String nomProjet, String pointFocal, String description, int dateDebut, int dateFin, byte[] dataImage,
			String statut, Set<Type> type, Set<Categorie> categorie) {
		super();
		this.nomProjet = nomProjet;
		this.pointFocal = pointFocal;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dataImage = dataImage;
		this.statut = statut;
		this.type = type;
		this.categorie = categorie;
	}




	


	

	
	 

	

}
