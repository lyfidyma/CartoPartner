package com.carto.sn.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Audited(withModifiedFlag = true)
public class Commune {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCommune;
	private String nomCommune;
	@JsonIgnore
	@OneToMany(mappedBy="commune")
	@NotAudited
	private Collection <Village> village;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idDepartement")
	private Departement departement;
	@JsonIgnore
	@OneToMany(mappedBy = "commune")
	@NotAudited
	private Set<ProjetPartenaireRegion> projetPartenaireRegion;
	
	public Commune(String nomCommune, Departement departement) {
		super();
		this.nomCommune = nomCommune;
		this.departement = departement;
	}


	public Long getIdCommune() {
		return idCommune;
	}


	public void setIdCommune(Long idCommune) {
		this.idCommune = idCommune;
	}


	public String getNomCommune() {
		return nomCommune;
	}


	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}


	public Collection<Village> getVillage() {
		return village;
	}


	public void setVillage(List<Village> village) {
		this.village = village;
	}


	public Departement getDepartement() {
		return departement;
	}


	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	
}
