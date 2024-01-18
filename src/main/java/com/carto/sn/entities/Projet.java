package com.carto.sn.entities;

import java.util.Collection;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
public class Projet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProjet;
	private String nomProjet;
	private String pointFocal;
	private String description;
	private int duree;
	private String temps;
	@Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] dataImage;
	private String statut;
	
	@ManyToMany(fetch = FetchType.EAGER)
	Set <Partenaire> partenaire;
	@ManyToMany(fetch = FetchType.EAGER)
	Set <Region> region;
	@ManyToMany(fetch = FetchType.EAGER)
	Set <Type> type; 
	@OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
	private Set<ProjetPartenaireRegion> projetPartenaireRegion;

	 public String generateBase64Image() {
	        return Base64.encodeBase64String(this.dataImage);
	    }


	public Projet(String nomProjet, String pointFocal, String description, int duree, String temps, byte[] dataImage,
			String statut) {
		super();
		this.nomProjet = nomProjet;
		this.pointFocal = pointFocal;
		this.description = description;
		this.duree = duree;
		this.temps = temps;
		this.dataImage = dataImage;
		this.statut = statut;
	}


	public Projet(String nomProjet, String pointFocal, String description, int duree, String temps, byte[] dataImage,
			String statut, Set<Partenaire> partenaire, Set<Region> region, Set<Type> type) {
		super();
		this.nomProjet = nomProjet;
		this.pointFocal = pointFocal;
		this.description = description;
		this.duree = duree;
		this.temps = temps;
		this.dataImage = dataImage;
		this.statut = statut;
		this.partenaire = partenaire;
		this.region = region;
		this.type = type;
	}


	public Projet(String nomProjet, Set<Partenaire> partenaire, Set<Region> region, Set<Type> type) {
		super();
		this.nomProjet = nomProjet;
		this.partenaire = partenaire;
		this.region = region;
		this.type = type;
	}


	public Projet(String nomProjet, String pointFocal, String description, int duree, String temps, byte[] dataImage,
			String statut, Set<Type> type) {
		super();
		this.nomProjet = nomProjet;
		this.pointFocal = pointFocal;
		this.description = description;
		this.duree = duree;
		this.temps = temps;
		this.dataImage = dataImage;
		this.statut = statut;
		this.type = type;
	}
	 

	

}
