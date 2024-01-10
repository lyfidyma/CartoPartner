package com.carto.sn.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.carto.sn.entities.Localisation;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetGroupByNomProjetTypeLocalisation;
import com.carto.sn.entities.Utilisateur;


public interface ICarto {
	
	public Localisation ajoutLocalisation(String libelle);
	public Partenaire ajoutPartenaire(Long idPartenaire, String nomPartenaire, String adresse);
	public List <Partenaire> tousLesPartenaires();
	public List <Localisation> toutesLesLocalisations();
	public List <Projet> tousLesProjets();
	public Projet ajoutProjet(String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, MultipartFile file, String statut, LocalDate dateDebut, LocalDate dateFin) throws IOException;
	public Projet ajouterPartenaireAuProjet(String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, String statut, LocalDate dateDebut, LocalDate dateFin);
	public void supprimerPartenaire(Long idPartenaire);
	public Partenaire modifierPartenaire(String nomPartenaire, String nouveauNomPartenaire, String nouvelleAdresse);
	public List <ProjetGroupByNomProjetTypeLocalisation> projetEtTypes(String nomProjet);
	public Optional <Projet> projetParId(Long id);
	public Optional <Partenaire> findPartenaireById(Long id);
	public List<Projet> chercher(String motCle);
	public Profil ajoutProfil(String profil);
	public Utilisateur ajoutUtilisateur(Long idUtilisateur, String nom, String prenom, String login, String password, String profil);
	public List <Profil> tousLesProfils();
	public List <Utilisateur> tousLesUtilisateurs();
	public void supprimerUtilisateur(Long idUtilisateur);
	public Optional<Utilisateur> findUtilisateurById(Long idUtilisateur);
	public void supprimerLocalisation(Long idLocalisation);
	public void supprimerProfil(Long idProfil);
	public void supprimerProjet(Long idProjet);
	public Optional <Localisation> findLocalisationById(Long idLocalisation);
	public Projet modifierProjet(Long idProjet, String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, MultipartFile file, String statut, LocalDate dateDebut, LocalDate dateFin) throws IOException;

}
