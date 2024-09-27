package com.carto.sn.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.carto.sn.entities.Categorie;
import com.carto.sn.entities.Commune;
import com.carto.sn.entities.Departement;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.PartenaireLocal;
import com.carto.sn.entities.Pays;
import com.carto.sn.entities.Privilege;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetPartenaireRegion;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.entities.Utilisateur;
import com.carto.sn.entities.Village;
import com.carto.sn.enums.EnumPrivilege;


public interface ICarto {
	
	public Region ajoutRegion(String nomDepartement, String nomRegion, String nomPays, String nomCommune);
//	public Partenaire ajoutPartenaire(Long idPartenaire, String nomPartenaire, String adresse);
//	public List <Partenaire> tousLesPartenaires();
	public List <Region> toutesLesRegions();
	public List <Projet> tousLesProjets();
	public Projet ajoutProjet(String nomProjet, String pointFocal, 
			String description, String nomType, MultipartFile file, String statut, int dateDebut, int dateFin, String nomCategorie) throws IOException;
//	public Projet ajouterPartenaireAuProjet(String nomProjet, String pointFocal, String nomPartenaire, String libelleRegion, 
//			String description, String type, String statut, int dateDebut, int dateFin);
//	public void supprimerPartenaire(Long idPartenaire);
//	public Partenaire modifierPartenaire(String nomPartenaire, String nouveauNomPartenaire, String nouvelleAdresse);
	public Optional <Projet> projetParId(Long id);
//	public Optional <Partenaire> findPartenaireById(Long id);
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
	public Projet modifierProjet(Long idProjet, String nomProjet, String pointFocal,  
			String description, String type, MultipartFile file, String statut, int dateDebut, int dateFin, String nomCategorie) throws IOException;
	public List <Projet> findOneIdByProjetName(String nomProjet);
	public Type ajoutType(String type, String couleur);
	public void supprimerType(Long idType);
	public List <Type> tousLesTypes();
//	public List<Departement> tousLesDepartements();
//	public List<Pays> tousLesPays();
	public Projet ajoutPartenaireAuProjet(String nomProjet, String nomDuPartenaire, String nomRegion, String nomDepartement,
			String nomCommune, String nomVillage, String latitude, String longitude, String nomPartenaireLocal );
	public Projet findByNomProjet(String nomProjet);
//	public Partenaire findByNomPartenaire(String nomPartenaire);
	public List<Projet> findByPointFocal(String pointFocal);
	public List <ProjetPartenaireRegion> findByIdProjet(Long idProjet);
	public List <ProjetPartenaireRegion> findByIdPartenaire(Long idPartenaire);
	public List <ProjetPartenaireRegion> findByIdRegion(Long idRegion);
	public List <ProjetPartenaireRegion> tousLesProjetsPartenairesRegions();
	public Type findByNomType(String nomType);
//	public List<Commune> toutesLesCommunes();
	public List <Village> tousLesVillages();

	/*
	 * public List<Categorie> toutesLesCategories(); public Categorie
	 * ajoutCategorie(String nomCategorie); public Categorie
	 * findByNomCategorie(String nomCategorie);
	 */
//	public List<Departement> findDepartementByNomRegion(String nomRegion);
//	public List<Commune> findCommuneByNomDepartement(String nomDepartement);
	public List <Village> findVillageByNomCommune(String nomCommune);
//	public List<PartenaireLocal> tousLesPartenairesLocaux();
//	public PartenaireLocal ajoutPartenaireLocalAPartenaire(String nomPartenaireLocal, Long idPartenaire);
//	public Partenaire findPartenaireByIdPartenaire(Long idPartenaire);
	public Projet ajoutProjetAUtilisateur(Long idUtilisateur, String nomProjet);
	public List<Projet> groupByPointFocal();
//	public void supprimerCommune(Long idCommune);
//	public Commune findByNomCommune(String nomCommune);
	public Projet trouverProjetParIdProjet(Long idProjet);
	public Type findByIdType(Long idType);
//	public Categorie findByIdCategorie(Long idCategorie);
	public Projet cloturerProjet(Long idProjet);
	public List<Utilisateur> getAllOperationsOfUser(String login);
	public Profil findByIdProfil(Long idProfil);
//	public Privilege ajoutPrivilege(Long idProfil, String[] enumPrivilege);
//	public List<Privilege> findPrivilegeByProfil(Long nomProfil);
	public void enableOrDisableUserAccount(Long idUtilisateur, boolean isEnabled);
//	public void supprimerCategorie(Long idCategorie);
	
}
