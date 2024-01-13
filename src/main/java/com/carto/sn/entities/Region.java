package com.carto.sn.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Region {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRegion;
	private String region;
	
	@OneToMany(mappedBy = "region")
	private Collection <Departement> departement;
	
	@ManyToOne
	@JoinColumn(name="idPays", updatable = true)
	private Pays pays;
	
	public Region(String region) {
		super();
		this.region = region;
	}

}
