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
public class PartenaireLocal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPartenaireLocal;
	private String nomPartenaireLocal;
	@JsonIgnore
	@ManyToMany(mappedBy="partenaireLocal")
	Set <Partenaire> partenaire;
	
	public Long getIdPartenaireLocal() {
		return idPartenaireLocal;
	}


	public void setIdPartenaireLocal(Long idPartenaireLocal) {
		this.idPartenaireLocal = idPartenaireLocal;
	}


	public String getNomPartenaireLocal() {
		return nomPartenaireLocal;
	}


	public void setNomPartenaireLocal(String nomPartenaireLocal) {
		this.nomPartenaireLocal = nomPartenaireLocal;
	}


	public Set<Partenaire> getPartenaire() {
		return partenaire;
	}


	public void setPartenaire(Set<Partenaire> partenaire) {
		this.partenaire = partenaire;
	}


	

	
	public PartenaireLocal(String nomPartenaireLocal, Set<Partenaire> partenaire) {
		super();
		this.nomPartenaireLocal = nomPartenaireLocal;
		this.partenaire = partenaire;
	}

}
