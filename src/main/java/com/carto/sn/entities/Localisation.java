package com.carto.sn.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Localisation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLocalisation;
	private String libelleLocalisation;
	@OneToMany(mappedBy = "localisation")
	private Collection <Projet> projet;
	public Localisation(String libelleLocalisation) {
		super();
		this.libelleLocalisation = libelleLocalisation;
	}
	public Localisation(String libelleLocalisation, Collection<Projet> projet) {
		super();
		this.libelleLocalisation = libelleLocalisation;
		this.projet = projet;
	}

}
