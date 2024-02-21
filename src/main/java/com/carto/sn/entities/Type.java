package com.carto.sn.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Type {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idType;
	private String nomType;
	private String couleur;
	@JsonIgnore
	@ManyToMany(mappedBy="type")
	Set <Projet> projet; 
	
	
	public Type(String nomType, String couleur) {
		super();
		this.nomType = nomType;
		this.couleur = couleur;
	}


	public Long getIdType() {
		return idType;
	}


	public void setIdType(Long idType) {
		this.idType = idType;
	}


	public String getNomType() {
		return nomType;
	}


	public void setNomType(String nomType) {
		this.nomType = nomType;
	}


	public String getCouleur() {
		return couleur;
	}


	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}


	public Set<Projet> getProjet() {
		return projet;
	}


	public void setProjet(Set<Projet> projet) {
		this.projet = projet;
	}
	
	

}
