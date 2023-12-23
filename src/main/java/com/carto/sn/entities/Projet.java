package com.carto.sn.entities;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
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
public class Projet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProjet;
	private String nomProjet;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private LocalDate dateDebut;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private LocalDate dateFin;
	private String responsable;
	private String description;
	private String type;
	private String statut;
	
	@ManyToOne
	@JoinColumn(name="idPartenaire", updatable = true)
	private Partenaire partenaire;
	@ManyToOne
	@JoinColumn(name="idLocalisation", updatable = true)
	private Localisation localisation;
	public Projet(String nomProjet, LocalDate dateDebut, LocalDate dateFin, String responsable,
			String description, String type, String statut) {
		super();
		this.nomProjet = nomProjet;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.responsable = responsable;
		this.description = description;
		this.type = type;
		this.statut = statut;
	}
	public Projet(String nomProjet, LocalDate dateDebut, LocalDate dateFin, String responsable,
			String description, String type, String statut, Partenaire partenaire, Localisation localisation) {
		super();
		this.nomProjet = nomProjet;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.responsable = responsable;
		this.description = description;
		this.type = type;
		this.statut = statut;
		this.partenaire = partenaire;
		this.localisation = localisation;
	}
	

}
