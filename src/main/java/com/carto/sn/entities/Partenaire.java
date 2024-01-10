package com.carto.sn.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@OneToMany(mappedBy = "partenaire")
	private Collection <Projet> projet;
	public Partenaire(String nomPartenaire, String adresse) {
		super();
		this.nomPartenaire = nomPartenaire;
		this.adresse = adresse;
	}
	public Partenaire(String nomPartenaire, String adresse, Collection<Projet> projet) {
		super();
		this.nomPartenaire = nomPartenaire;
		this.adresse = adresse;
		this.projet = projet;
	}
	
		

}
