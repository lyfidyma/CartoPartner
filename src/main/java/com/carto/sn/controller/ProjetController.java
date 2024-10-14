package com.carto.sn.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Categorie;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.entities.Utilisateur;
import com.carto.sn.service.CategorieService;
import com.carto.sn.service.PartenaireService;
import com.carto.sn.service.ProjetService;
import com.carto.sn.service.RegionService;
import com.carto.sn.service.TypeService;
import com.carto.sn.service.UtilisateurService;

@Controller
@PreAuthorize("isAuthenticated()")
public class ProjetController {
	
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private PartenaireService partenaireService;
	@Autowired
	private CategorieService categorieService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private TypeService typeService;
	
	@RequestMapping("projet")
	public String projet(Model model, String motCle) {

		if (motCle == null) {
			// List <Projet> listProjet = iCarto.tousLesProjets();
			List<Projet> listProjet = projetService.tousLesProjets();
			model.addAttribute("listProjet", listProjet);
		} else {
			List<Projet> listProjet = projetService.chercher(motCle);
			model.addAttribute("listProjet", listProjet);
		}

		return "projet";
	}
	
	@GetMapping("listeProjet")
	public String listeProjet(Model model) {
		List<Projet> listProjet = projetService.tousLesProjets();
		model.addAttribute("listProjet", listProjet);
		return "listeProjet";
	}

	@PostMapping("supprimerProjet")
	public String supprimerProjet(Long idProjet) {
		projetService.supprimerProjet(idProjet);
		return "redirect:/listeProjet";
	}
	
	@RequestMapping("sauvegarderProjet")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'AJOUTER_PROJET')")
	public String sauvegarderProjet(@ModelAttribute("unProjet") Projet unProjet, @ModelAttribute("unType") Type unType,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("uneRegion") Region uneRegion,
			Model model, Long idProjet, String nomProjet, String pointFocal, String description, String nomType,
			String statut, MultipartFile file, int dateDebut, int dateFin, String nomCategorie, RedirectAttributes ra)
			throws IOException {

		if (nomProjet.isBlank()) {

			List<Type> listType = typeService.tousLesTypes();

			model.addAttribute("listType", listType);
			model.addAttribute("messageErreurNomProj", "Renseigner le nom du projet");
			return "nouveauProjet";

		}
		if (nomType.isBlank()) {
			List<Type> listType = typeService.tousLesTypes();
			model.addAttribute("listType", listType);
			model.addAttribute("messageErreurType", "Choisir le type du projet");
			return "nouveauProjet";

		}

		if (idProjet == null) {
			List<Projet> listProjet = projetService.tousLesProjets();
			for (int i = 0; i < listProjet.size(); i++) {
				if (nomProjet.equals(listProjet.get(i).getNomProjet())) {
					List<Type> listType = typeService.tousLesTypes();
					model.addAttribute("listType", listType);
					model.addAttribute("messageDoublon", "Ce projet existe");
					return "nouveauProjet";
				}
			}
			projetService.ajoutProjet(nomProjet, pointFocal, description, nomType, file, statut, dateDebut, dateFin,
					nomCategorie);
			// ra.addFlashAttribute("flagEnregistrement", "1");
			return "redirect:/nouveauProjet";
		} else if (idProjet != null) {
			projetService.modifierProjet(idProjet, nomProjet, pointFocal, description, nomType, file, statut, dateDebut,
					dateFin, nomCategorie);
			// ra.addFlashAttribute("flagModification", "1");
			return "redirect:/listeProjet";
		}

		return "redirect:/projet";
	}

	@RequestMapping("nouveauProjet")
	public String nouveauProjet(@ModelAttribute("unProjet") Projet unProjet,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("uneRegion") Region uneRegion,
			@ModelAttribute("unType") Type unType, @ModelAttribute("uneCategorie") Categorie uneCategorie,
			Model model) {
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listLocalisation = regionService.toutesLesRegions();
		List<Type> listType = typeService.tousLesTypes();
		List<Categorie> listCategorie = categorieService.toutesLesCategories();

		model.addAttribute("listCategorie", listCategorie);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
		model.addAttribute("listType", listType);

		return "nouveauProjet";
	}
	
	@RequestMapping("/sauvegarderProjetUtilisateur")
	public String sauvegarderProjetUtilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur,
			@ModelAttribute("unProjet") Projet unProjet, Model model, Long idUtilisateur, String nomProjet) {
		unUtilisateur = utilisateurService.findUtilisateurById(idUtilisateur).get();
		List<Projet> listProjet = projetService.tousLesProjets();

		projetService.ajoutProjetAUtilisateur(idUtilisateur, nomProjet);
		model.addAttribute("unUtilisateur", unUtilisateur);
		model.addAttribute("listProjet", listProjet);
		return "projetUtilisateur";
	}

	@RequestMapping("/detailsProjet")
	public String detailsProjet(@ModelAttribute("unProjet") Projet unProjet, Model model, Long idProjet,
			String nomProjet) {

		Projet projet = projetService.trouverProjetParIdProjet(idProjet);
		model.addAttribute("listProjet", projetService.tousLesProjets());
		model.addAttribute("unProjet", projet);
		return "detailsProjet";
	}
	
	@RequestMapping("getDonneesProjetAAjouterAUtilisateur")
	public String getDonneesProjetAAjouterAUtilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur,
			@ModelAttribute("unProjet") Projet unProjet, Model model, Long idUtilisateur) {

		unUtilisateur = utilisateurService.findUtilisateurById(idUtilisateur).get();
		model.addAttribute("unUtilisateur", unUtilisateur);
		List<Projet> listProjet = projetService.tousLesProjets();
		model.addAttribute("listProjet", listProjet);
		// model.addAttribute("flagProfil",
		// iCarto.findUtilisateurById(idUtilisateur).get().getProfil());

		return "projetUtilisateur";
	}
	
	@RequestMapping("getDonneesProjetAModifier")
	public String getDonneesProjetAModifier(@ModelAttribute("unProjet") Projet unProjet,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unType") Type unType,
			@ModelAttribute("uneRegion") Region uneRegion, @ModelAttribute("uneCategorie") Categorie uneCategorie,
			Model model, Long idProjet) {

		unProjet = projetService.projetParId(idProjet).get();
		Set<Type> type = unProjet.getType();
		for (Type t : type) {
			unType = typeService.findByIdType(t.getIdType());
		}
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Type> listType = typeService.tousLesTypes();
		Set<Categorie> categorie = unProjet.getCategorie();

		for (Categorie cat : categorie) {
			uneCategorie = categorieService.findByIdCategorie(cat.getIdCategorie());

		}

		List<Categorie> listCategorie = categorieService.toutesLesCategories();
		model.addAttribute("unProjet", unProjet);
		model.addAttribute("unType", unType);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listType", listType);
		model.addAttribute("listCategorie", listCategorie);
		model.addAttribute("uneCategorie", uneCategorie);

		return "nouveauProjet";
	}



}
