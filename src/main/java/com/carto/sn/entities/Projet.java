package com.carto.sn.entities;

import java.time.LocalDate;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
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
public class Projet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProjet;
	private String nomProjet;
	private String responsable;
	private String description;
	private String type;
	private int duree;
	private String temps;
	@Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] dataImage;
	private String statut;
	
	@ManyToOne
	@JoinColumn(name="idPartenaire", updatable = true)
	private Partenaire partenaire;
	@ManyToOne
	@JoinColumn(name="idLocalisation", updatable = true)
	private Localisation localisation;


	 public String generateBase64Image() {
	        return Base64.encodeBase64String(this.dataImage);
	    }


	public Projet(String nomProjet, String responsable, String description, String type, int duree, String temps,
			byte[] dataImage, String statut) {
		super();
		this.nomProjet = nomProjet;
		this.responsable = responsable;
		this.description = description;
		this.type = type;
		this.duree = duree;
		this.temps = temps;
		this.dataImage = dataImage;
		this.statut = statut;
	}


	public Projet(String nomProjet, String responsable, String description, String type, int duree, String temps,
			byte[] dataImage, String statut, Partenaire partenaire, Localisation localisation) {
		super();
		this.nomProjet = nomProjet;
		this.responsable = responsable;
		this.description = description;
		this.type = type;
		this.duree = duree;
		this.temps = temps;
		this.dataImage = dataImage;
		this.statut = statut;
		this.partenaire = partenaire;
		this.localisation = localisation;
	}


	public Projet(String nomProjet, String type, Partenaire partenaire, Localisation localisation) {
		super();
		this.nomProjet = nomProjet;
		this.type = type;
		this.partenaire = partenaire;
		this.localisation = localisation;
	}


	

}
