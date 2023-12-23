package com.carto.sn.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.carto.sn.entities.Localisation;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Projet;
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
	public String projet(Model model) {
		List <Projet> listProjet = iCarto.tousLesProjets();
		model.addAttribute("listProjet", listProjet);
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
			@ModelAttribute("unPartenaire") Partenaire unPartenaire,Model model, String nomProjet) {
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
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
	public String sauvegarderPartenaire(String nomPartenaire, String adresse, RedirectAttributes ra) {
		if(nomPartenaire.isBlank()==false || adresse.isBlank()== false)
			iCarto.ajoutPartenaire(nomPartenaire, adresse);
		ra.addFlashAttribute("messageSucces", nomPartenaire+ ' '+"bien enregistré(e)");
		return "redirect:/partenaire";
	}
	
	@RequestMapping("sauvegarderProjet")
	public String sauvegarderProjet(String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, String statut, 
			LocalDate dateDebut, LocalDate dateFin, boolean activate, RedirectAttributes ra) {
		if(nomProjet.isBlank()==false) {
			if(activate==true) {
			statut = "En cours";
			}else {
				statut = "inactif";
			}
			iCarto.ajoutProjet(nomProjet, responsable, nomPartenaire, libelleLocalisation, description, type, statut, dateDebut, dateFin);
			ra.addFlashAttribute("messageSucces", nomProjet+' '+"bien enregistré");
		}else {
			ra.addFlashAttribute("messageErreur", nomProjet+' '+"non enregistré");
		}
		return "redirect:/projet";
	}
	
	@RequestMapping("sauvegarderChoixPartenaire")
		public String sauvegarderChoixPartenaire(String nomProjet, String nomDuPartenaire, String libelleDeLaLocalisation, 
				String radioButtonSelected, RedirectAttributes ra) {
		System.out.println(libelleDeLaLocalisation+"--"+nomDuPartenaire+"----");
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
			
			iCarto.ajoutProjet(nomProjet, responsable, nomDuPartenaire, libelleDeLaLocalisation, description, type, statut, dateDebut, dateFin);
		ra.addFlashAttribute("nomProjet", nomProjet);
		return "redirect:/choixPartenaire";
	}
	
	@RequestMapping("supprimerPartenaire")
	public String supprimerPartenaire(Long idPartenaire) {
		iCarto.supprimerPartenaire(idPartenaire);
		return "redirect:/partenaire";
	}

}
