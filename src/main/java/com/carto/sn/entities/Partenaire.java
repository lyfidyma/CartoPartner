package com.carto.sn.entities;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Partenaire {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPartenaire;
	@NotEmpty(message="Renseigner le nom du partenaire")
	private String nomPartenaire;
	private String adresse;
	@JsonIgnore
	@ManyToMany(mappedBy="partenaire")
	Set <Projet> projet;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	Set <PartenaireLocal> partenaireLocal;
	@JsonIgnore
	@OneToMany(mappedBy = "partenaire", cascade = CascadeType.ALL)
	private Set<ProjetPartenaireRegion> projetPartenaireRegion;
	
	
	public Partenaire(String nomPartenaire, String adresse) {
		super();
		this.nomPartenaire = nomPartenaire;
		this.adresse = adresse;
	}


	public Long getIdPartenaire() {
		return idPartenaire;
	}


	public void setIdPartenaire(Long idPartenaire) {
		this.idPartenaire = idPartenaire;
	}


	public String getNomPartenaire() {
		return nomPartenaire;
	}


	public void setNomPartenaire(String nomPartenaire) {
		this.nomPartenaire = nomPartenaire;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public Set<Projet> getProjet() {
		return projet;
	}


	public void setProjet(Set<Projet> projet) {
		this.projet = projet;
	}


	public Set<PartenaireLocal> getPartenaireLocal() {
		return partenaireLocal;
	}


	public void setPartenaireLocal(Set<PartenaireLocal> partenaireLocal) {
		this.partenaireLocal = partenaireLocal;
	}


	public Set<ProjetPartenaireRegion> getProjetPartenaireRegion() {
		return projetPartenaireRegion;
	}


	public void setProjetPartenaireRegion(Set<ProjetPartenaireRegion> projetPartenaireRegion) {
		this.projetPartenaireRegion = projetPartenaireRegion;
	}
	
	
		

}
