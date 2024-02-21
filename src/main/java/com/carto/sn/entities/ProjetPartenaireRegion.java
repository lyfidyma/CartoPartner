package com.carto.sn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProjetPartenaireRegion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProjetPartenaireRegion;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idProjet")
	private Projet projet;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idPartenaire")
	private Partenaire partenaire;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idRegion")
	private Region region;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idCommune")
	private Commune commune;
	
	
	public ProjetPartenaireRegion(Projet projet, Partenaire partenaire, Region region) {
		super();
		this.projet = projet;
		this.partenaire = partenaire;
		this.region = region;
	}


	public Long getIdProjetPartenaireRegion() {
		return idProjetPartenaireRegion;
	}


	public void setIdProjetPartenaireRegion(Long idProjetPartenaireRegion) {
		this.idProjetPartenaireRegion = idProjetPartenaireRegion;
	}


	public Projet getProjet() {
		return projet;
	}


	public void setProjet(Projet projet) {
		this.projet = projet;
	}


	public Partenaire getPartenaire() {
		return partenaire;
	}


	public void setPartenaire(Partenaire partenaire) {
		this.partenaire = partenaire;
	}


	public Region getRegion() {
		return region;
	}


	public void setRegion(Region region) {
		this.region = region;
	}


	public Commune getCommune() {
		return commune;
	}


	public void setCommune(Commune commune) {
		this.commune = commune;
	}


	public ProjetPartenaireRegion(Projet projet, Partenaire partenaire, Region region, Commune commune) {
		super();
		this.projet = projet;
		this.partenaire = partenaire;
		this.region = region;
		this.commune = commune;
	}

}
