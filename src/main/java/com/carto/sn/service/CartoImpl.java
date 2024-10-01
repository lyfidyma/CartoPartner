package com.carto.sn.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carto.sn.dao.CategorieRepository;
import com.carto.sn.dao.CommuneRepository;
import com.carto.sn.dao.DepartementRepository;
import com.carto.sn.dao.PartenaireLocalRepository;
import com.carto.sn.dao.PartenaireRepository;
import com.carto.sn.dao.PaysRepository;
import com.carto.sn.dao.PrivilegeRepository;
import com.carto.sn.dao.ProfilRepository;
import com.carto.sn.dao.ProjetPartenaireRegionRepository;
import com.carto.sn.dao.ProjetRepository;
import com.carto.sn.dao.RegionRepository;
import com.carto.sn.dao.TypeRepository;
import com.carto.sn.dao.UtilisateurRepository;
import com.carto.sn.dao.VillageRepository;
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
import com.carto.sn.enums.EnumProfil;

import jakarta.persistence.EntityManagerFactory;

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
	private ProfilRepository profilRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private ProjetPartenaireRegionRepository pprRepository;
	@Autowired
	private CommuneRepository communeRepository;
	@Autowired
	private VillageRepository villageRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private PartenaireLocalRepository partenaireLocalRepository;
	@Autowired
	private PrivilegeRepository privilegeRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private EntityManagerFactory factory;

	@Override
	public Region ajoutRegion(String nomDepartement, String nomRegion, String nomPays, String nomCommune) {
		Pays unPays = null;
		if(paysRepository.findByNomPays(nomPays)!=null) {
			unPays = paysRepository.findByNomPays(nomPays);
		}else {
			unPays = paysRepository.save(new Pays(nomPays));
		}
		Region uneRegion = null;
		if(regionRepository.findByNomRegion(nomRegion)!=null) {
			uneRegion = regionRepository.findByNomRegion(nomRegion);
		}else {
			uneRegion = regionRepository.save(new Region(nomRegion, unPays));
		}
		 Departement unDepartement = null;
		 if(departementRepository.findByNomDepartement(nomDepartement)!=null) {
			 unDepartement = departementRepository.findByNomDepartement(nomDepartement);
		 }else {
			 unDepartement = departementRepository.save(new Departement(nomDepartement, uneRegion));
		 }
		 Commune uneCommune = null;
		 if(communeRepository.findByNomCommune(nomCommune)!=null) {
			 uneCommune = communeRepository.findByNomCommune(nomCommune);
		 }else {
			 uneCommune =  communeRepository.save(new Commune(nomCommune, unDepartement));
		 }	
		
		return uneRegion;
	}

//	@Override
//	public Partenaire ajoutPartenaire(Long idPartenaire, String nomPartenaire, String adresse) {
//		
//		Partenaire partenaire = null;
//		if(idPartenaire==null) {
//		partenaire = partenaireRepository.save(new Partenaire(nomPartenaire, adresse));
//		return partenaire;
//		}else {
//			partenaire = partenaireRepository.findById(idPartenaire).orElse(partenaire);
//			partenaire.setNomPartenaire(nomPartenaire);
//			partenaire.setAdresse(adresse);
//			partenaireRepository.save(partenaire);
//			return partenaire;
//		}
//	}

//	@Override
//	public List<Partenaire> tousLesPartenaires() {
//		return partenaireRepository.findAll();
//	}

	@Override
	public List<Region> toutesLesRegions() {
		return regionRepository.findAll();
	}
	
//	@Override
//	public List<Projet> tousLesProjets() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		List<Projet> proj = projetRepository.findAll();
//		List<Projet> projetUtilisateur = new ArrayList<>();
//		
//		for(GrantedAuthority grAuth : authentication.getAuthorities()) {
//			if(grAuth.getAuthority().equals("ROLE_"+EnumProfil.ADMIN)) {
//			return proj;
//		}
//	}
//	
//			for(Projet p:proj) {	
//				Set<Utilisateur> ut = p.getUtilisateur();
//				for(Utilisateur u:ut) {
//					if(u.getLogin().equals(authentication.getName())) {
//						projetUtilisateur.add(p);
//					}
//				}
//			}
//			return projetUtilisateur;
//		
//		
//	}

//	@Override
//	public Projet ajoutProjet(String nomProjet, String pointFocal, 
//			String description, String nomType,  MultipartFile file, String statut, int dateDebut, int dateFin, String nomCategorie) throws IOException{
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		
//		Type typeProjet = typeRepository.findByNomType(nomType);
//		Utilisateur user = utilisateurRepository.findByLogin(authentication.getName()).orElse(null);
//		Projet proj = null;
//		Set<Type> sType = Stream.of(typeProjet)
//                .collect(Collectors.toCollection(HashSet::new));
//		
//		Categorie categorie = categorieRepository.findByNomCategorie(nomCategorie);
//		Set<Categorie> sCategorie = Stream.of(categorie)
//                .collect(Collectors.toCollection(HashSet::new));
//		
//		proj = projetRepository.save(new Projet(nomProjet, pointFocal, description, dateDebut, dateFin, file.getBytes(), statut));
//		proj.setType(sType);
//		proj.setCategorie(sCategorie);
//		for(GrantedAuthority authority :authentication.getAuthorities()) {
//			if(authority.getAuthority().equals("ROLE_USER")){
////				Set<Utilisateur> sUtilisateur = Stream.of(user)
////		                .collect(Collectors.toCollection(HashSet::new));
////				proj.setUtilisateur(sUtilisateur);
//				//proj.getUtilisateur().add(user);
//				user.getProjet().add(proj);
//				break;
//			}
//		}
//		return proj;
//	}

//	@Override
//	public Projet ajouterPartenaireAuProjet(String nomProjet, String pointFocal, String nomPartenaire,
//			String libelleLocalisation, String description, String type, String statut, int dateDebut, int dateFin) {
//		Projet proj = null;
//		
//		Partenaire part = partenaireRepository.findByNomPartenaire(nomPartenaire);
//		return proj;
//	}

//	@Override
//	public void supprimerPartenaire(Long idPartenaire) {
//		partenaireRepository.deleteById(idPartenaire);
//	}

//	@Override
//	public Optional<Projet> projetParId(Long id) {
//		return projetRepository.findById(id);
//	}

//	@Override
//	public Partenaire modifierPartenaire(String nomPartenaire, String nouveauNomPartenaire, String nouvelleAdresse) {
//		Partenaire partenaire  = null;
//		partenaire = partenaireRepository.findByNomPartenaire(nomPartenaire);
//		partenaire.setNomPartenaire(nouveauNomPartenaire);
//		partenaire.setAdresse(nouvelleAdresse);
//		partenaireRepository.save(partenaire);
//		return partenaire;
//	}

//	@Override
//	public Optional <Partenaire> findPartenaireById(Long id) {
//		return partenaireRepository.findById(id);
//	}

//	@Override
//	public List<Projet> chercher(String motCle) {
//		return projetRepository.chercher(motCle);
//	}

//	@Override
//	public Profil ajoutProfil(String profil) {
//		//Profil prof = null;
//		return profilRepository.save(new Profil(profil));
//		//return prof;
//	}

	@Override
	public Utilisateur ajoutUtilisateur(Long idUtilisateur, String nom, String prenom, String login, String password, String profil) {
		Utilisateur utilisateur = null;
		Profil profilRole = profilRepository.findByNomProfil(profil);
		Set<Profil> roles = Stream.of(profilRole)
                .collect(Collectors.toCollection(HashSet::new));
		if(idUtilisateur == null) {
			utilisateur = utilisateurRepository.save(new Utilisateur(nom, prenom, login, passwordEncoder.encode(password), true));
			
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

//	@Override
//	public List <Profil> tousLesProfils() {
//		return profilRepository.findAll();
//	}

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

//	@Override
//	public void supprimerProfil(Long idProfil) {
//		profilRepository.deleteById(idProfil);
//		
//	}

//	@Override
//	public void supprimerProjet(Long idProjet) {
//		projetRepository.deleteById(idProjet);
//		
//	}

	@Override
	public Optional<Region> findRegionById(Long idRegion) {
		return regionRepository.findById(idRegion);
	}

//	@Override
//	public Projet modifierProjet(Long idProjet, String nomProjet, String pointFocal,
//			String description, String type, MultipartFile file, String statut, int dateDebut, int dateFin, String nomCategorie)
//			throws IOException {
//		Projet projet = null;
//		Categorie categorie = categorieRepository.findByNomCategorie(nomCategorie);
//		Set<Categorie> sCategorie = Stream.of(categorie).collect(Collectors.toCollection(HashSet::new));
//		
//		projet = projetRepository.findById(idProjet).orElse(projet);
//		projet.setNomProjet(nomProjet);
//		projet.setPointFocal(pointFocal);
//		projet.setDescription(description);
//		projet.setStatut(statut);
//		projet.setCategorie(sCategorie);
//		if(file.isEmpty()==false)
//			projet.setDataImage(file.getBytes());
//		projetRepository.save(projet);
//		return projet;
//	}

//	@Override
//	public  List <Projet> findOneIdByProjetName(String nomProjet) {
//		return projetRepository.findOneIdByProjetName(nomProjet);
//	}

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

//	@Override
//	public List<Departement> tousLesDepartements() {
//		return departementRepository.findAll();
//	}

//	@Override
//	public List<Pays> tousLesPays() {
//		return paysRepository.findAll();
//	}

//	@Override
//	public Projet ajoutPartenaireAuProjet(String nomProjet, String nomDuPartenaire, String nomRegion, String nomDepartement,
//			String nomCommune, String nomVillage, String latitude, String longitude, String nomPartenaireLocal)  {
//		
//		Projet proj = projetRepository.findByNomProjet(nomProjet);
//		Partenaire part = partenaireRepository.findByNomPartenaire(nomDuPartenaire);
//		Region reg = regionRepository.findByNomRegion(nomRegion);
//		Departement dep = departementRepository.findByNomDepartement(nomDepartement);
//		Commune com = communeRepository.findByNomCommune(nomCommune);
//		if(nomVillage!=null) {
//			Village vil = null;
//			if(villageRepository.findByNomVillage(nomVillage)!=null) {
//				vil = villageRepository.findByNomVillage(nomVillage);
//				vil.setLatitude(latitude);
//				vil.setLongitude(longitude);
//				villageRepository.save(vil);
//			}else {
//				vil = villageRepository.save(new Village(nomVillage, com));
//			}
//		}
//		
//		PartenaireLocal paLoc = null;
//		if(nomPartenaireLocal!=null) {
//		if(partenaireLocalRepository.findByNomPartenaireLocal(nomPartenaireLocal)!=null) {
//			paLoc = partenaireLocalRepository.findByNomPartenaireLocal(nomPartenaireLocal);	
//		}else {
//			Set<Partenaire> sPartenaire = Stream.of(part)
//	                .collect(Collectors.toCollection(HashSet::new));
//			paLoc = partenaireLocalRepository.save(new PartenaireLocal(nomPartenaireLocal, sPartenaire));
//		}
//		part.getPartenaireLocal().add(paLoc);
//		proj.getPartenaire().add(part);
//		}
//		
//		proj.getRegion().add(reg);
//		projetRepository.save(proj);
//		
//		if(pprRepository.findProjetPartenaireRegion(proj.getIdProjet(), part.getIdPartenaire(), reg.getIdRegion(), com.getIdCommune()).isEmpty()) {
//			ProjetPartenaireRegion ppr = pprRepository.save(new ProjetPartenaireRegion(proj, part, reg, com));
//		
//		}
//		
//		
//		return proj;
//	}

//	@Override
//	public Projet findByNomProjet(String nomProjet) {
//		return projetRepository.findByNomProjet(nomProjet);
//	}

//	@Override
//	public Partenaire findByNomPartenaire(String nomPartenaire) {
//		return partenaireRepository.findByNomPartenaire(nomPartenaire);
//	}

//	@Override
//	public List<Projet> findByPointFocal(String pointFocal) {
//		return projetRepository.findByPointFocal(pointFocal);
//	}

//	@Override
//	public List<ProjetPartenaireRegion> findByIdProjet(Long idProjet) {
//		return pprRepository.findByIdProjet(idProjet);
//	}

//	@Override
//	public List<ProjetPartenaireRegion> findByIdPartenaire(Long idPartenaire) {
//		return pprRepository.findByIdProjet(idPartenaire);
//	}

//	@Override
//	public List<ProjetPartenaireRegion> findByIdRegion(Long idRegion) {
//		return pprRepository.findByIdProjet(idRegion);
//	}

	@Override
	public Type findByNomType(String nomType) {
		return typeRepository.findByNomType(nomType);
	}

//	@Override
//	public List<Commune> toutesLesCommunes() {
//		return communeRepository.findAll();
//	}

	@Override
	public List<Village> tousLesVillages() {
		return villageRepository.findAll();
	}

	/*
	 * @Override public List<Categorie> toutesLesCategories() { return
	 * categorieRepository.findAll(); }
	 * 
	 * @Override public Categorie ajoutCategorie(String nomCategorie) { Categorie
	 * categorie = null; categorie = categorieRepository.save(new
	 * Categorie(nomCategorie)); return categorie; }
	 * 
	 * @Override public Categorie findByNomCategorie(String nomCategorie) { return
	 * categorieRepository.findByNomCategorie(nomCategorie); }
	 */

//	@Override
//	public List<Departement> findDepartementByNomRegion(String nomRegion) {
//		return departementRepository.findDepartementByNomRegion(nomRegion);
//	}

//	@Override
//	public List<Commune> findCommuneByNomDepartement(String nomDepartement) {
//		return communeRepository.findCommuneByNomDepartement(nomDepartement);
//	}

	@Override
	public List<Village> findVillageByNomCommune(String nomCommune) {
		return villageRepository.findVillageByNomCommune(nomCommune);
	}

//	@Override
//	public List<PartenaireLocal> tousLesPartenairesLocaux() {
//		return partenaireLocalRepository.findAll();
//	}

//	@Override
//	public PartenaireLocal ajoutPartenaireLocalAPartenaire(String nomPartenaireLocal, Long idPartenaire) {
//		Partenaire part= partenaireRepository.findById(idPartenaire).orElse(null);
//		PartenaireLocal partLoc = null;
//		
//		if(partenaireLocalRepository.findByNomPartenaireLocal(nomPartenaireLocal)!=null) {
//			partLoc = partenaireLocalRepository.findByNomPartenaireLocal(nomPartenaireLocal);
//		}else {
//			Set<Partenaire> sPartenaire = Stream.of(part)
//                .collect(Collectors.toCollection(HashSet::new));
//				partLoc = partenaireLocalRepository.save(new PartenaireLocal(nomPartenaireLocal, sPartenaire));
//		}
//
//		
//		part.getPartenaireLocal().add(partLoc);
//		
//		return partLoc;
//	}

//	@Override
//	public Partenaire findPartenaireByIdPartenaire(Long idPartenaire) {
//		return partenaireRepository.findById(idPartenaire).orElse(null);
//	}

//	@Override
//	public Projet ajoutProjetAUtilisateur(Long idUtilisateur, String nomProjet) {
//		Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElse(null);
//		Projet projet = projetRepository.findByNomProjet(nomProjet);
//	
//		utilisateur.getProjet().add(projet);
//		//utilisateurRepository.save(utilisateur);
//			//projet.getUtilisateur().add(utilisateur);
//			//projetRepository.save(projet);
//			return projet;
//		
//		
//	}

//	@Override
//	public List<Projet> groupByPointFocal() {
//		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		List<Projet> proj = projetRepository.groupByPointFocal();
//		List<Projet> projetUtilisateur = new ArrayList<>();
//		for(GrantedAuthority grAuth : authentication.getAuthorities()) {
//			if(grAuth.getAuthority().equals("ROLE_"+EnumProfil.ADMIN)) {
//			return proj;
//			}
//		}
//		
//			for(Projet p:proj) {
//				Set<Utilisateur> ut = p.getUtilisateur();
//				for(Utilisateur u:ut) {
//					if(u.getLogin().equals(authentication.getName())) {
//						projetUtilisateur.add(p);
//					}
//				}
//			}
//			return projetUtilisateur;
//		
//	}

//	@Override
//	public List<ProjetPartenaireRegion> tousLesProjetsPartenairesRegions() {
//		return pprRepository.findAll();
//	}

//	@Override
//	public void supprimerCommune(Long idCommune) {
//		communeRepository.deleteById(idCommune);
//		
//	}

//	@Override
//	public Commune findByNomCommune(String nomCommune) {
//		return communeRepository.findByNomCommune(nomCommune);
//	}

//	@Override
//	public Projet trouverProjetParIdProjet(Long idProjet) {
//		return projetRepository.findById(idProjet).orElse(null);
//	}

	@Override
	public Type findByIdType(Long idType) {
		return typeRepository.findById(idType).orElse(null);
	}

//	@Override
//	public Categorie findByIdCategorie(Long idCategorie) {
//		return categorieRepository.findById(idCategorie).orElse(null);
//	}

//	@Override
//	public Projet cloturerProjet(Long idProjet) {
//		Projet projet = projetRepository.findById(idProjet).orElse(null);
//		String statut="Clôturé";
//		projet.setStatut(statut);
//		projetRepository.save(projet);
//		return projet;
//	}

	@Override
	public List<Utilisateur> getAllOperationsOfUser(String login) {
		AuditReader reader = AuditReaderFactory.get(factory.createEntityManager());
		AuditQuery queryOperationsOfUser = reader.createQuery()
				.forRevisionsOfEntity(Utilisateur.class, true, false)
				//.forRevisionsOfEntityWithChanges(Utilisateur.class, true)
				.add(AuditEntity.revisionProperty("userModificateur").eq(login));
		List <Utilisateur> listOperationsOfUser = queryOperationsOfUser.getResultList();
		
		return listOperationsOfUser;
		
	}

//	@Override
//	public Profil findByIdProfil(Long idProfil) {
//		return profilRepository.findById(idProfil).orElseThrow(RuntimeException::new);
//	}

//	@Override
//	public Privilege ajoutPrivilege(Long idProfil, String checkboxPrivilege[]) {
//		
//		Privilege nouveauPrivilege = null ;
//		Profil profil = profilRepository.findById(idProfil).get();
//		List<EnumPrivilege> enumPrivileges = Arrays.asList(EnumPrivilege.values());
//		List <Privilege> privilege = privilegeRepository.findAll();
//		/*Cette partie permet de remplir la table des privilèges. Elle n'est exécutée qu'une seule fois lorsque 
//		 * la table privilèges est vide*/
//		if(privilege.isEmpty()) {
//			for(EnumPrivilege en : enumPrivileges) {
//				privilegeRepository.save(new Privilege(en.toString()));
//				privilege = privilegeRepository.findAll();
//			}
//			
//		}
//		/*Ajout des privilèges au profil*/
//		for(String chPrivilege : checkboxPrivilege) {			
//					for(Privilege pr : privilege) {
//						if(chPrivilege.equals(pr.getNomPrivilege())) {	
//							nouveauPrivilege = pr;
//							profil.getPrivilege().add(pr);
//									
//					}
//				}
//			}
//		
//			return nouveauPrivilege;
//		
//	}

//	@Override
//	public List<Privilege> findPrivilegeByProfil(Long idProfil) {
//		Profil profil = profilRepository.findById(idProfil).orElseThrow(()-> new RuntimeException("Profil not found"));
//		List<Privilege> listPrivileges = profil.getPrivilege().stream().collect(Collectors.toList());
//		return listPrivileges;
//	}

	@Override
	public void enableOrDisableUserAccount(Long idUtilisateur, boolean isEnabled) {
		Optional <Utilisateur> utilisateur = utilisateurRepository.findById(idUtilisateur);
		utilisateur.get().setEnabled(isEnabled);
	}

//	@Override
//	public void supprimerCategorie(Long idCategorie) {
//		categorieRepository.deleteById(idCategorie);
//		
//	}

}
