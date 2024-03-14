package com.carto.sn.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Region {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRegion;
	@NotBlank(message="Renseigner le nom de la région")
	private String nomRegion;
	
	@JsonIgnore
	@OneToMany(mappedBy = "region")
	private Collection <Departement> departement;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idPays")
	private Pays pays;
	@JsonIgnore
	@ManyToMany(mappedBy = "region")
	Set <Projet> projet;
	@JsonIgnore
	@OneToMany(mappedBy = "region")
	private Set<ProjetPartenaireRegion> projetPartenaireRegion;
	
	
	public Region(String nomRegion) {
		super();
		this.nomRegion = nomRegion;
	}

	public Region(@NotBlank(message = "Renseigner le nom de la région") String nomRegion,
			List<Departement> departement, Pays pays) {
		super();
		this.nomRegion = nomRegion;
		this.departement = departement;
		this.pays = pays;
	}

	public Region(@NotBlank(message = "Renseigner le nom de la région") String nomRegion, Pays pays) {
		super();
		this.nomRegion = nomRegion;
		this.pays = pays;
	}

	public Long getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Long idRegion) {
		this.idRegion = idRegion;
	}

	public String getNomRegion() {
		return nomRegion;
	}

	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	public Collection<Departement> getDepartement() {
		return departement;
	}

	public void setDepartement(List<Departement> departement) {
		this.departement = departement;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}

	public Set<Projet> getProjet() {
		return projet;
	}

	public void setProjet(Set<Projet> projet) {
		this.projet = projet;
	}

	public Set<ProjetPartenaireRegion> getProjetPartenaireRegion() {
		return projetPartenaireRegion;
	}

	public void setProjetPartenaireRegion(Set<ProjetPartenaireRegion> projetPartenaireRegion) {
		this.projetPartenaireRegion = projetPartenaireRegion;
	}
	
}
