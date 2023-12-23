package com.carto.sn.service;

import java.time.LocalDate;
import java.util.List;

import com.carto.sn.entities.Localisation;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Projet;


public interface ICarto {
	
	public Localisation ajoutLocalisation(String libelle);
	public Partenaire ajoutPartenaire(String nomPartenaire, String adresse);
	public List <Partenaire> tousLesPartenaires();
	public List <Localisation> toutesLesLocalisations();
	public List <Projet> tousLesProjets();
	public Projet ajoutProjet(String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, String statut, LocalDate dateDebut, LocalDate dateFin);
	public Projet ajouterPartenaireAuProjet(String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, String statut, LocalDate dateDebut, LocalDate dateFin);
	public void supprimerPartenaire(Long idPartenaire);

}
