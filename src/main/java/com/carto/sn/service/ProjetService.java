package com.carto.sn.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.carto.sn.entities.Projet;

public interface ProjetService {
	public List <Projet> tousLesProjets();
	public Projet ajoutProjet(String nomProjet, String pointFocal, 
			String description, String nomType, MultipartFile file, String statut, int dateDebut, int dateFin, String nomCategorie) throws IOException;
	public Optional <Projet> projetParId(Long id);
	public List<Projet> chercher(String motCle);
	public void supprimerProjet(Long idProjet);
	public Projet modifierProjet(Long idProjet, String nomProjet, String pointFocal,  
			String description, String type, MultipartFile file, String statut, int dateDebut, int dateFin, String nomCategorie) throws IOException;
	public List <Projet> findOneIdByProjetName(String nomProjet);
	public Projet ajoutPartenaireAuProjet(String nomProjet, String nomDuPartenaire, String nomRegion, String nomDepartement,
			String nomCommune, String nomVillage, String latitude, String longitude, String nomPartenaireLocal );
	public Projet findByNomProjet(String nomProjet);
	public List<Projet> findByPointFocal(String pointFocal);
	public Projet ajoutProjetAUtilisateur(Long idUtilisateur, String nomProjet);
	public List<Projet> groupByPointFocal();
	public Projet trouverProjetParIdProjet(Long idProjet);
	public Projet cloturerProjet(Long idProjet);

}
