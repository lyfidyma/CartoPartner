package com.carto.sn.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.carto.sn.entities.Departement;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Pays;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetGroupByNomProjet;
import com.carto.sn.entities.ProjetGroupByNomProjetTypeLocalisation;
import com.carto.sn.entities.ProjetPartenaireRegion;
import com.carto.sn.entities.ProjetsEtRegions;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.entities.Utilisateur;


public interface ICarto {
	
	public Region ajoutRegion(String nomDepartement, String nomRegion, String nomPays);
	public Partenaire ajoutPartenaire(Long idPartenaire, String nomPartenaire, String adresse);
	public List <Partenaire> tousLesPartenaires();
	public List <Region> toutesLesRegions();
	public List <Projet> tousLesProjets();
	public Projet ajoutProjet(String nomProjet, String pointFocal, 
			String description, String nomType, MultipartFile file, String statut, int duree, String temps) throws IOException;
	public Projet ajouterPartenaireAuProjet(String nomProjet, String pointFocal, String nomPartenaire, String libelleRegion, 
			String description, String type, String statut, int duree, String temps);
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
	public void supprimerRegion(Long idRegion);
	public void supprimerProfil(Long idProfil);
	public void supprimerProjet(Long idProjet);
	public Optional <Region> findRegionById(Long idRegion);
	public Projet modifierProjet(Long idProjet, String nomProjet, String pointFocal, String nomPartenaire, String libelleRegion, 
			String description, String type, MultipartFile file, String statut, int duree, String temps) throws IOException;
	public List <ProjetGroupByNomProjet> groupByNomProjet();
	public List <Projet> findOneIdByProjetName(String nomProjet);
	public Type ajoutType(String type, String couleur);
	public void supprimerType(Long idType);
	public List <Type> tousLesTypes();
	public List<Departement> tousLesDepartements();
	public List<Pays> tousLesPays();
	public Projet ajoutPartenaireAuProjet(String nomProjet, String nomDuPartenaire, String nomRegion, String nomType);
	public Projet findByNomProjet(String nomProjet);
	public Partenaire findByNomPartenaire(String nomPartenaire);
	public List<Projet> findByPoinFocal(String pointFocal);
	public List <ProjetPartenaireRegion> findByIdProjet(Long idProjet);
	public List <ProjetPartenaireRegion> findByIdPartenaire(Long idPartenaire);
	public List <ProjetPartenaireRegion> findByIdRegion(Long idRegion);
	
	public List<Type> findByNomType(String nomType);
	public List<Partenaire> findByNomPartenaireList(String nomPartenaire);
	
}
