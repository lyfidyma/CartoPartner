package com.carto.sn.service;

import java.io.IOException;
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

import com.carto.sn.dao.DepartementRepository;
import com.carto.sn.dao.PartenaireRepository;
import com.carto.sn.dao.PaysRepository;
import com.carto.sn.dao.ProfilRepository;
import com.carto.sn.dao.ProjetRepository;
import com.carto.sn.dao.RegionRepository;
import com.carto.sn.dao.TypeRepository;
import com.carto.sn.dao.UtilisateurRepository;
import com.carto.sn.entities.Departement;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Pays;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetGroupByNomProjet;
import com.carto.sn.entities.ProjetGroupByNomProjetTypeLocalisation;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.entities.Utilisateur;

@Service
@Transactional
public class CartoImpl implements ICarto{
	
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private PaysRepository paysRepository;
	@Autowired
	private DepartementRepository departementRepository;
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
	private TypeRepository typeRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Region ajoutRegion(String nomDepartement, String nomRegion, String nomPays) {
		Pays unPays = paysRepository.save(new Pays(nomPays));
		Region uneRegion = regionRepository.save(new Region(nomRegion, unPays));
		Departement unDepartement = departementRepository.save(new Departement(nomDepartement, uneRegion));
		
		return uneRegion;
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
	public List<Region> toutesLesRegions() {
		return regionRepository.findAll();
	}
	
	@Override
	public List<Projet> tousLesProjets() {
		return projetRepository.findAll();
	}

	@Override
	public Projet ajoutProjet(String nomProjet, String pointFocal, 
			String description, String nomType,  MultipartFile file, String statut, int duree, String temps) throws IOException{
		Type typeProjet = typeRepository.findByNomType(nomType);
		Projet proj = null;
		Set<Type> sType = Stream.of(typeProjet)
                .collect(Collectors.toCollection(HashSet::new));
		
		
		proj = projetRepository.save(new Projet(nomProjet, pointFocal, description, duree, temps, file.getBytes(), statut));
		proj.setType(sType);
		return proj;
	}

	@Override
	public Projet ajouterPartenaireAuProjet(String nomProjet, String pointFocal, String nomPartenaire,
			String libelleLocalisation, String description, String type, String statut, int duree, String temps) {
		Projet proj = null;
		//Localisation loc = localisationRepository.findByLibelleLocalisation(libelleLocalisation);
		Partenaire part = partenaireRepository.findByNomPartenaire(nomPartenaire);
		//proj = projetRepository.save(new Projet(nomProjet, type, part, loc));
		return proj;
	}

	@Override
	public void supprimerPartenaire(Long idPartenaire) {
		partenaireRepository.deleteById(idPartenaire);
	}

	@Override
	public List<ProjetGroupByNomProjetTypeLocalisation> projetEtTypes(String nomProjet) {
		//return projetRepository.findByNomProjet(nomProjet);
		return null;
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
	public Utilisateur ajoutUtilisateur(Long idUtilisateur, String nom, String prenom, String login, String password, String profil) {
		Utilisateur utilisateur = null;
		Profil profilRole = profilRepository.findByNomProfil(profil);
		Set<Profil> roles = Stream.of(profilRole)
                .collect(Collectors.toCollection(HashSet::new));
		if(idUtilisateur == null) {
			utilisateur = utilisateurRepository.save(new Utilisateur(nom, prenom, login, passwordEncoder.encode(password)));
			
		}else if(idUtilisateur!=null){
			utilisateur = utilisateurRepository.findById(idUtilisateur).orElse(utilisateur);
			utilisateur.setNomUtilisateur(nom);
			utilisateur.setPrenomUtilisateur(prenom);
			utilisateur.setLogin(login);
			if(password.isBlank()==false)
				utilisateur.setPassword(passwordEncoder.encode(password));
				utilisateurRepository.save(utilisateur);
		}
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

	@Override
	public void supprimerUtilisateur(Long idUtilisateur) {
		 utilisateurRepository.deleteById(idUtilisateur);
		
	}

	@Override
	public Optional<Utilisateur> findUtilisateurById(Long idUtilisateur) {
		return utilisateurRepository.findById(idUtilisateur);
	}

	@Override
	public void supprimerRegion(Long idRegion) {
		regionRepository.deleteById(idRegion);
		
	}

	@Override
	public void supprimerProfil(Long idProfil) {
		profilRepository.deleteById(idProfil);
		
	}

	@Override
	public void supprimerProjet(Long idProjet) {
		projetRepository.deleteById(idProjet);
		
	}

	@Override
	public Optional<Region> findRegionById(Long idRegion) {
		return regionRepository.findById(idRegion);
	}

	@Override
	public Projet modifierProjet(Long idProjet, String nomProjet, String pointFocal, String nomPartenaire, String libelleLocalisation,
			String description, String type, MultipartFile file, String statut, int duree, String temps)
			throws IOException {
		Projet projet = null;
		
		projet = projetRepository.findById(idProjet).orElse(projet);
		projet.setNomProjet(nomProjet);
		projet.setPointFocal(pointFocal);
		projet.setDescription(description);
		//projet.setType(type);
		projet.setStatut(statut);
		Partenaire partenaire = partenaireRepository.findByNomPartenaire(nomPartenaire);
		//projet.setPartenaire(partenaire);
		//Localisation localisation = localisationRepository.findByLibelleLocalisation(libelleLocalisation);
	//	projet.setLocalisation(localisation);
		if(file.isEmpty()==false)
			projet.setDataImage(file.getBytes());
		projetRepository.save(projet);
		return projet;
	}

	@Override
	public List<ProjetGroupByNomProjet> groupByNomProjet() {
		//return projetRepository.findByProjet();
		return null;
	}

	@Override
	public  List <Projet> findOneIdByProjetName(String nomProjet) {
		return projetRepository.findOneIdByProjetName(nomProjet);
	}

	@Override
	public Type ajoutType(String type, String couleur) {
		Type unType = null;
		unType = typeRepository.save(new Type(type, couleur));
		return unType;
	}

	@Override
	public void supprimerType(Long idType) {
		typeRepository.deleteById(idType);
		
	}

	@Override
	public List<Type> tousLesTypes() {
		return typeRepository.findAll();
	}

	@Override
	public List<Departement> tousLesDepartements() {
		return departementRepository.findAll();
	}

	@Override
	public List<Pays> tousLesPays() {
		return paysRepository.findAll();
	}

	@Override
	public Projet ajoutPartenaireAuProjet(String nomProjet, String nomDuPartenaire, String nomRegion, String nomType)  {
		
		Projet proj = projetRepository.findByNomProjet(nomProjet);
		if(nomType!=null) {
		Type typeProjet = typeRepository.findByNomType(nomType);
		Set<Type> sType = Stream.of(typeProjet)
                .collect(Collectors.toCollection(HashSet::new));
		proj.getType().add(typeProjet);
		}
		Partenaire part = partenaireRepository.findByNomPartenaire(nomDuPartenaire);
		Set<Partenaire> sPartenaire = Stream.of(part)
                .collect(Collectors.toCollection(HashSet::new));
		
		Region reg = regionRepository.findByNomRegion(nomRegion);
		Set<Region> sRegion = Stream.of(reg)
                .collect(Collectors.toCollection(HashSet::new));
		
		
		
		proj.getPartenaire().add(part);
		proj.getRegion().add(reg);
		projetRepository.save(proj);
		
		return proj;
	}

	

	

}
