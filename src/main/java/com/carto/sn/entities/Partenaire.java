package com.carto.sn.entities;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partenaire {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPartenaire;
	@NotEmpty(message="Renseigner le nom du partenaire")
	private String nomPartenaire;
	private String adresse;
	
	@ManyToMany(mappedBy="partenaire")
	Set <Projet> projet;
	
	@OneToMany(mappedBy = "partenaire", cascade = CascadeType.ALL)
	private Set<ProjetPartenaireRegion> projetPartenaireRegion;
	public Partenaire(String nomPartenaire, String adresse) {
		super();
		this.nomPartenaire = nomPartenaire;
		this.adresse = adresse;
	}
	
	
		

}
