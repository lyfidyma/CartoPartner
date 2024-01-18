package com.carto.sn.entities;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Region {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRegion;
	@NotBlank(message="Renseigner le nom de la région")
	private String nomRegion;
	
	@OneToMany(mappedBy = "region")
	private Collection <Departement> departement;
	
	@ManyToOne
	@JoinColumn(name="idPays", updatable = true)
	private Pays pays;
	
	@ManyToMany(mappedBy = "region")
	Set <Projet> projet;
	@OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
	private Set<ProjetPartenaireRegion> projetPartenaireRegion;
	public Region(String nomRegion) {
		super();
		this.nomRegion = nomRegion;
	}

	public Region(@NotBlank(message = "Renseigner le nom de la région") String nomRegion,
			Collection<Departement> departement, Pays pays) {
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
	
}
