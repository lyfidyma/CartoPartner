package com.carto.sn.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carto.sn.dao.LocalisationRepository;
import com.carto.sn.dao.PartenaireRepository;
import com.carto.sn.dao.ProfilRepository;
import com.carto.sn.dao.ProjetRepository;
import com.carto.sn.dao.UtilisateurRepository;
import com.carto.sn.entities.Localisation;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.Utilisateur;

@Service
@Transactional
public class CartoImpl implements ICarto{
	
	@Autowired
	private LocalisationRepository localisationRepository;
	@Autowired
	private PartenaireRepository partenaireRepository;
	@Autowired
	private ProjetRepository projetRepository;
	@Autowired
	private StorageService storageService;
	@Autowired
	private ProfilRepository profilRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Localisation ajoutLocalisation(String libelle) {
		Localisation localisation = null;
		localisation = localisationRepository.save(new Localisation(libelle));
		return localisation;
	}

	@Override
	public Partenaire ajoutPartenaire(Long idPartenaire, String nomPartenaire, String adresse) {
		
		Partenaire partenaire = null;
		if(idPartenaire==null) {
		partenaire = partenaireRepository.save(new Partenaire(nomPartenaire, adresse));
		return partenaire;
		}else {
			partenaire = partenaireRepository.findById(idPartenaire).orElse(partenaire);
			partenaire.setNomPartenaire(nomPartenaire);
			partenaire.setAdresse(adresse);
			partenaireRepository.save(partenaire);
			return partenaire;
		}
	}

	@Override
	public List<Partenaire> tousLesPartenaires() {
		return partenaireRepository.findAll();
	}

	@Override
	public List<Localisation> toutesLesLocalisations() {
		return localisationRepository.findAll();
	}
	
	@Override
	public List<Projet> tousLesProjets() {
		return projetRepository.findAll();
	}

	@Override
	public Projet ajoutProjet(String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type,  MultipartFile file, String statut, LocalDate dateDebut, LocalDate dateFin) throws IOException{
		
		Projet proj = null;
		 String cheminImage = null;
		Localisation loc = localisationRepository.findByLibelleLocalisation(libelleLocalisation);
		Partenaire part = partenaireRepository.findByNomPartenaire(nomPartenaire);
		/*
		 * if(file.isEmpty()==false) { storageService.init();
		 * storageService.store(file); Path path =
		 * storageService.load(file.getOriginalFilename()); cheminImage =
		 * MvcUriComponentsBuilder .fromMethodName(FileUploadController.class,
		 * "serveFile", path.getFileName().toString()).build().toString(); }
		 */
		proj = projetRepository.save(new Projet(nomProjet, dateDebut, dateFin, responsable, description, type, file.getBytes(), statut, part, loc));
		return proj;
	}

	@Override
	public Projet ajouterPartenaireAuProjet(String nomProjet, String responsable, String nomPartenaire,
			String libelleLocalisation, String description, String type, String statut, LocalDate dateDebut,
			LocalDate dateFin) {
		Projet proj = null;
		Localisation loc = localisationRepository.findByLibelleLocalisation(libelleLocalisation);
		Partenaire part = partenaireRepository.findByNomPartenaire(nomPartenaire);
		proj = projetRepository.save(new Projet(nomProjet, dateDebut, dateFin, responsable, description, type, statut, part, loc));
		return proj;
	}

	@Override
	public void supprimerPartenaire(Long idPartenaire) {
		partenaireRepository.deleteById(idPartenaire);
	}

	@Override
	public List<Projet> projetEtTypes(String nomProjet) {
		return projetRepository.findByNomProjet(nomProjet);
	}

	@Override
	public Optional<Projet> projetParId(Long id) {
		return projetRepository.findById(id);
	}

	@Override
	public Partenaire modifierPartenaire(String nomPartenaire, String nouveauNomPartenaire, String nouvelleAdresse) {
		Partenaire partenaire  = null;
		partenaire = partenaireRepository.findByNomPartenaire(nomPartenaire);
		partenaire.setNomPartenaire(nouveauNomPartenaire);
		partenaire.setAdresse(nouvelleAdresse);
		partenaireRepository.save(partenaire);
		return partenaire;
	}

	@Override
	public Optional <Partenaire> findPartenaireById(Long id) {
		return partenaireRepository.findById(id);
	}

	@Override
	public List<Projet> chercher(String motCle) {
		return projetRepository.chercher(motCle);
	}

	@Override
	public Profil ajoutProfil(String profil) {
		Profil prof = null;
		prof = profilRepository.save(new Profil(profil));
		return prof;
	}

	@Override
	public Utilisateur ajoutUtilisateur(String nom, String prenom, String login, String password, String profil) {
		Utilisateur utilisateur = null;
		Profil profilRole = profilRepository.findByNomProfil(profil);
		Set<Profil> roles = Stream.of(profilRole)
                .collect(Collectors.toCollection(HashSet::new));
		
			utilisateur = utilisateurRepository.save(new Utilisateur(nom, prenom, login, passwordEncoder.encode(password)));
			utilisateur.setProfil(roles);
		return utilisateur;
	}

	@Override
	public List <Profil> tousLesProfils() {
		return profilRepository.findAll();
	}

	@Override
	public List<Utilisateur> tousLesUtilisateurs() {
		return utilisateurRepository.findAll();
	}

	

	

}
