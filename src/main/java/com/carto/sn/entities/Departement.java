package com.carto.sn.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Departement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDepartement;
	@NotBlank(message="Renseigner le département")
	private String nomDepartement;
	
	@ManyToOne
	@JoinColumn(name="idRegion", updatable = true)
	private Region region;
	//@ManyToMany(mappedBy = "departement")
	//Set <Projet> projet;
	
	public Departement(String nomDepartement) {
		super();
		this.nomDepartement = nomDepartement;
	}

	public Departement(String nomDepartement, Region region) {
		super();
		this.nomDepartement = nomDepartement;
		this.region = region;
	}
	
	
}
