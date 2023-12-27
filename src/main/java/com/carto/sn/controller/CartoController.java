package com.carto.sn.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Localisation;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.Utilisateur;
import com.carto.sn.service.ICarto;

@Controller
public class CartoController {
	
	@Autowired
	private ICarto iCarto;
	

	@RequestMapping({"/", "index"})
	public String accueil(Model model) {
		
		List <Projet> listProjet = iCarto.tousLesProjets();
		model.addAttribute("listProjet", listProjet);
		return "index";
	}
	
	@RequestMapping({"projet"})
	public String projet(Model model, String motCle) {
		
		if(motCle==null) {
		List <Projet> listProjet = iCarto.tousLesProjets();
		model.addAttribute("listProjet", listProjet);
		}else {
			List <Projet> listProjet = iCarto.chercher(motCle);
			model.addAttribute("listProjet", listProjet);
		}
		
		
		return "projet";
	}
	
	@RequestMapping("nouveauProjet")
	public String nouveauProjet(Model model) {
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
		
		return "nouveauProjet";
	}
	
	@RequestMapping("partenaire")
	public String partenaire(Model model) {
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
		return "partenaire";
	}
	
	@RequestMapping("choixPartenaire")
	public String choixPartenaire(@ModelAttribute("uneLocalisation") Localisation uneLocalisation, 
			@ModelAttribute("unPartenaire") Partenaire unPartenaire,Model model, String nomProjet, Long idProjet) {
		List <Projet> listProjetType = iCarto.projetEtTypes(nomProjet);
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
		model.addAttribute("listProjetType", listProjetType);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
		if(nomProjet!=null)
			model.addAttribute("nomProjet", nomProjet);
		//model.addAttribute("idProjet", idProjet);
		return "choixPartenaire";
	}
	
	@RequestMapping("nouveauPartenaire")
	public String nouveauPartenaire() {
		return "nouveauPartenaire";
	}
	
	@RequestMapping("localisation")
	public String localisation() {
		return "localisation";
	}
	
	@RequestMapping("cartographie")
	public String  cartographie() {
		return "cartographie";
	}
	
	@RequestMapping("sauvegarderLocalisation")
	public String sauvegarderLocalisation(String nomLocalisation, RedirectAttributes ra) {
		if(nomLocalisation.isBlank()==false)
			iCarto.ajoutLocalisation(nomLocalisation);
		ra.addFlashAttribute("messageSucces", nomLocalisation+ ' '+ "bien enregistré(e)" );
		return "redirect:/localisation";
	}
	
	@RequestMapping("sauvegarderPartenaire")
	public String sauvegarderPartenaire(Long idPartenaire, String nomPartenaire, String adresse, RedirectAttributes ra) {
		if(nomPartenaire.isBlank()==false || adresse.isBlank()== false)
			iCarto.ajoutPartenaire(idPartenaire, nomPartenaire, adresse);
		ra.addFlashAttribute("messageSucces", nomPartenaire+ ' '+"bien enregistré(e)");
		return "redirect:/partenaire";
	}
	
	@RequestMapping("sauvegarderProjet")
	public String sauvegarderProjet(String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, String statut, MultipartFile file,
			LocalDate dateDebut, LocalDate dateFin, boolean activate, RedirectAttributes ra) throws IOException{
		if(nomProjet.isBlank()==false) {
			if(activate==true) {
			statut = "En cours";
			}else {
				statut = "inactif";
			}
			iCarto.ajoutProjet(nomProjet, responsable, nomPartenaire, libelleLocalisation, description, type, file, statut, dateDebut, dateFin);
			ra.addFlashAttribute("messageSucces", nomProjet+' '+"bien enregistré");
		}else {
			ra.addFlashAttribute("messageErreur", nomProjet+' '+"non enregistré");
		}
		return "redirect:/projet";
	}
	
	@RequestMapping("sauvegarderChoixPartenaire")
		public String sauvegarderChoixPartenaire(String nomProjet, String nomDuPartenaire, String libelleDeLaLocalisation, 
				MultipartFile file,String radioButtonSelected, RedirectAttributes ra) throws IOException{
			String responsable="responsable";
			String description="description";
			LocalDate dateDebut=LocalDate.now();
			LocalDate dateFin=LocalDate.now();
			String statut="En cours";
			String type="";
			if(radioButtonSelected.equals("rouge"))
				type="Egalité de genre";
			else if(radioButtonSelected.equals("orange"))
				type = "Education / Formation professionnelle";
			else if (radioButtonSelected.equals("jaune"))
				type = "Gouvernance";
			
			iCarto.ajoutProjet(nomProjet, responsable, nomDuPartenaire, libelleDeLaLocalisation, description, type, file, statut, dateDebut, dateFin);
		ra.addFlashAttribute("nomProjet", nomProjet);
		return "redirect:/choixPartenaire";
	}
	
	@RequestMapping("supprimerPartenaire")
	public String supprimerPartenaire(Long idPartenaire) {
		iCarto.supprimerPartenaire(idPartenaire);
		return "redirect:/partenaire";
	}
	
	@RequestMapping("getInfosProjet")
	@ResponseBody
	public Object getInfosProjet(Long idProjet) {	
		if(iCarto.projetParId(idProjet)==null) {
			return null;
		}else {
			Map<String, Object> object = new HashMap<>();
			object.put("nomProjet", iCarto.projetParId(idProjet).get().getNomProjet());
			object.put("responsable", iCarto.projetParId(idProjet).get().getResponsable());
			object.put("bailleur", iCarto.projetParId(idProjet).get().getPartenaire().getNomPartenaire());
			object.put("localite", iCarto.projetParId(idProjet).get().getLocalisation().getLibelleLocalisation());
			object.put("description", iCarto.projetParId(idProjet).get().getDescription());
			object.put("type", iCarto.projetParId(idProjet).get().getType());
			object.put("dateDebut", iCarto.projetParId(idProjet).get().getDateDebut());
			object.put("dateFin", iCarto.projetParId(idProjet).get().getDateFin());
			object.put("statut", iCarto.projetParId(idProjet).get().getStatut());
			
			return object;
		}
	}
	
	@RequestMapping("getDonneesPartenaireAModifier")
	public String modifierPartenaire(Model model, Long idPartenaire ) {
		model.addAttribute("idPartenaire", idPartenaire);
		model.addAttribute("nomPartenaire", iCarto.findPartenaireById(idPartenaire).get().getNomPartenaire());
		model.addAttribute("adresse", iCarto.findPartenaireById(idPartenaire).get().getAdresse());
		return "nouveauPartenaire";
	}
	
	@RequestMapping("nouveauProfil")
	public String nouveauProfil() {
		return "nouveauProfil";
	}
	
	@RequestMapping("sauvegarderProfil")
	public String sauvegarderProfil(String profil) {
		iCarto.ajoutProfil(profil);
		return "redirect:/nouveauProfil";
	}
	
	@RequestMapping("utilisateur")
	public String utiisateur(Model model) {
		List <Utilisateur> listUtilisateur = iCarto.tousLesUtilisateurs();
		model.addAttribute("listUtilisateur", listUtilisateur);
		return "utilisateur";
	}
	
	@RequestMapping("nouvelUtilisateur")
	public String nouvelUtiisateur(Model model) {
		List <Profil> listProfil = iCarto.tousLesProfils();
		model.addAttribute("listProfil", listProfil);
		return "nouvelUtilisateur";
	}
	
	@RequestMapping("sauvegarderUtilisateur")
	public String sauvegarderUtilisateur(String nomUtilisateur, String prenomUtilisateur, String login, String password, String profil) {
		iCarto.ajoutUtilisateur(nomUtilisateur, prenomUtilisateur, login, password, profil);
		return "redirect:/utilisateur";
	}
	
}
