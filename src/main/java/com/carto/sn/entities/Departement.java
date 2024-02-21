package com.carto.sn.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Departement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDepartement;
	@NotBlank(message="Renseigner le département")
	private String nomDepartement;
	@JsonIgnore
	@OneToMany(mappedBy="departement")
	private Collection<Commune> commune;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idRegion")
	private Region region;
	
	
	public Departement(String nomDepartement) {
		super();
		this.nomDepartement = nomDepartement;
	}

	public Departement(String nomDepartement, Region region) {
		super();
		this.nomDepartement = nomDepartement;
		this.region = region;
	}

	public Long getIdDepartement() {
		return idDepartement;
	}

	public void setIdDepartement(Long idDepartement) {
		this.idDepartement = idDepartement;
	}

	public String getNomDepartement() {
		return nomDepartement;
	}

	public void setNomDepartement(String nomDepartement) {
		this.nomDepartement = nomDepartement;
	}

	public Collection<Commune> getCommune() {
		return commune;
	}

	public void setCommune(List<Commune> commune) {
		this.commune = commune;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
	
}
