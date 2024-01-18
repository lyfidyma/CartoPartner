package com.carto.sn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjetPartenaireRegion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProjetPartenaireRegion;
	@ManyToOne
	@JoinColumn(name="idProjet")
	private Projet projet;
	@ManyToOne
	@JoinColumn(name="idPartenaire")
	private Partenaire partenaire;
	@ManyToOne
	@JoinColumn(name="idRegion")
	private Region region;
	public ProjetPartenaireRegion(Projet projet, Partenaire partenaire, Region region) {
		super();
		this.projet = projet;
		this.partenaire = partenaire;
		this.region = region;
	}

}
