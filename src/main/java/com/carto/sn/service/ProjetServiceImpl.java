package com.carto.sn.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.carto.sn.dao.CategorieRepository;
import com.carto.sn.dao.CommuneRepository;
import com.carto.sn.dao.DepartementRepository;
import com.carto.sn.dao.PartenaireLocalRepository;
import com.carto.sn.dao.PartenaireRepository;
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
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetPartenaireRegion;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.entities.Utilisateur;
import com.carto.sn.entities.Village;
import com.carto.sn.enums.EnumProfil;

@Service
public class ProjetServiceImpl implements ProjetService{
	
	@Autowired
	private ProjetRepository projetRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private PartenaireLocalRepository partenaireLocalRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private CommuneRepository communeRepository;
	@Autowired
	private VillageRepository villageRepository;
	@Autowired
	private PartenaireRepository partenaireRepository;
	@Autowired
	private ProjetPartenaireRegionRepository pprRepository;
	
	
	@Override
	public List<Projet> tousLesProjets() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Projet> proj = projetRepository.findAll();
		List<Projet> projetUtilisateur = new ArrayList<>();
		
		for(GrantedAuthority grAuth : authentication.getAuthorities()) {
			if(grAuth.getAuthority().equals("ROLE_"+EnumProfil.ADMIN)) {
			return proj;
		}
	}
	
			for(Projet p:proj) {	
				Set<Utilisateur> ut = p.getUtilisateur();
				for(Utilisateur u:ut) {
					if(u.getLogin().equals(authentication.getName())) {
						projetUtilisateur.add(p);
					}
				}
			}
			return projetUtilisateur;
	}

	@Override
	public Projet ajoutProjet(String nomProjet, String pointFocal, String description, String nomType,
			MultipartFile file, String statut, int dateDebut, int dateFin, String nomCategorie) throws IOException {
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Type typeProjet = typeRepository.findByNomType(nomType);
		Utilisateur user = utilisateurRepository.findByLogin(authentication.getName()).orElse(null);
		Projet proj = null;
		Set<Type> sType = Stream.of(typeProjet)
                .collect(Collectors.toCollection(HashSet::new));
		
		Categorie categorie = categorieRepository.findByNomCategorie(nomCategorie);
		Set<Categorie> sCategorie = Stream.of(categorie)
                .collect(Collectors.toCollection(HashSet::new));
		
		proj = projetRepository.save(new Projet(nomProjet, pointFocal, description, dateDebut, dateFin, file.getBytes(), statut));
		proj.setType(sType);
		proj.setCategorie(sCategorie);
		for(GrantedAuthority authority :authentication.getAuthorities()) {
			if(authority.getAuthority().equals("ROLE_USER")){
//				Set<Utilisateur> sUtilisateur = Stream.of(user)
//		                .collect(Collectors.toCollection(HashSet::new));
//				proj.setUtilisateur(sUtilisateur);
				//proj.getUtilisateur().add(user);
				user.getProjet().add(proj);
				break;
			}
		}
		return proj;
	}

	@Override
	public Optional<Projet> projetParId(Long id) {
		return projetRepository.findById(id);
	}

	@Override
	public List<Projet> chercher(String motCle) {
		return projetRepository.chercher(motCle);
	}

	@Override
	public void supprimerProjet(Long idProjet) {
		projetRepository.deleteById(idProjet);		
	}

	@Override
	public Projet modifierProjet(Long idProjet, String nomProjet, String pointFocal, String description, String type,
			MultipartFile file, String statut, int dateDebut, int dateFin, String nomCategorie) throws IOException {
		Projet projet = null;
		Categorie categorie = categorieRepository.findByNomCategorie(nomCategorie);
		Set<Categorie> sCategorie = Stream.of(categorie).collect(Collectors.toCollection(HashSet::new));
		
		projet = projetRepository.findById(idProjet).orElse(projet);
		projet.setNomProjet(nomProjet);
		projet.setPointFocal(pointFocal);
		projet.setDescription(description);
		projet.setStatut(statut);
		projet.setCategorie(sCategorie);
		if(file.isEmpty()==false)
			projet.setDataImage(file.getBytes());
		projetRepository.save(projet);
		return projet;
	}

	@Override
	public List<Projet> findOneIdByProjetName(String nomProjet) {
		return projetRepository.findOneIdByProjetName(nomProjet);
	}

	@Override
	public Projet ajoutPartenaireAuProjet(String nomProjet, String nomDuPartenaire, String nomRegion,
			String nomDepartement, String nomCommune, String nomVillage, String latitude, String longitude,
			String nomPartenaireLocal) {
		Projet proj = projetRepository.findByNomProjet(nomProjet);
		Partenaire part = partenaireRepository.findByNomPartenaire(nomDuPartenaire);
		Region reg = regionRepository.findByNomRegion(nomRegion);
		Departement dep = departementRepository.findByNomDepartement(nomDepartement);
		Commune com = communeRepository.findByNomCommune(nomCommune);
		if(nomVillage!=null) {
			Village vil = null;
			if(villageRepository.findByNomVillage(nomVillage)!=null) {
				vil = villageRepository.findByNomVillage(nomVillage);
				vil.setLatitude(latitude);
				vil.setLongitude(longitude);
				villageRepository.save(vil);
			}else {
				vil = villageRepository.save(new Village(nomVillage, com));
			}
		}
		
		PartenaireLocal paLoc = null;
		if(nomPartenaireLocal!=null) {
		if(partenaireLocalRepository.findByNomPartenaireLocal(nomPartenaireLocal)!=null) {
			paLoc = partenaireLocalRepository.findByNomPartenaireLocal(nomPartenaireLocal);	
		}else {
			Set<Partenaire> sPartenaire = Stream.of(part)
	                .collect(Collectors.toCollection(HashSet::new));
			paLoc = partenaireLocalRepository.save(new PartenaireLocal(nomPartenaireLocal, sPartenaire));
		}
		part.getPartenaireLocal().add(paLoc);
		proj.getPartenaire().add(part);
		}
		
		proj.getRegion().add(reg);
		projetRepository.save(proj);
		
		if(pprRepository.findProjetPartenaireRegion(proj.getIdProjet(), part.getIdPartenaire(), reg.getIdRegion(), com.getIdCommune()).isEmpty()) {
			ProjetPartenaireRegion ppr = pprRepository.save(new ProjetPartenaireRegion(proj, part, reg, com));
		
		}
		
		
		return proj;
	}

	@Override
	public Projet findByNomProjet(String nomProjet) {
		return projetRepository.findByNomProjet(nomProjet);
	}

	@Override
	public List<Projet> findByPointFocal(String pointFocal) {
		return projetRepository.findByPointFocal(pointFocal);
	}

	@Override
	public Projet ajoutProjetAUtilisateur(Long idUtilisateur, String nomProjet) {
		Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElse(null);
		Projet projet = projetRepository.findByNomProjet(nomProjet);
	
		utilisateur.getProjet().add(projet);
		//utilisateurRepository.save(utilisateur);
			//projet.getUtilisateur().add(utilisateur);
			//projetRepository.save(projet);
			return projet;
	}

	@Override
	public List<Projet> groupByPointFocal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Projet> proj = projetRepository.groupByPointFocal();
		List<Projet> projetUtilisateur = new ArrayList<>();
		for(GrantedAuthority grAuth : authentication.getAuthorities()) {
			if(grAuth.getAuthority().equals("ROLE_"+EnumProfil.ADMIN)) {
			return proj;
			}
		}
		
			for(Projet p:proj) {
				Set<Utilisateur> ut = p.getUtilisateur();
				for(Utilisateur u:ut) {
					if(u.getLogin().equals(authentication.getName())) {
						projetUtilisateur.add(p);
					}
				}
			}
			return projetUtilisateur;
	}

	@Override
	public Projet trouverProjetParIdProjet(Long idProjet) {
		return projetRepository.findById(idProjet).orElse(null);
	}

	@Override
	public Projet cloturerProjet(Long idProjet) {
		Projet projet = projetRepository.findById(idProjet).orElse(null);
		String statut="Clôturé";
		projet.setStatut(statut);
		projetRepository.save(projet);
		return projet;
	}

}
