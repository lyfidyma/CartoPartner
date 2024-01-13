package com.carto.sn.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Localisation;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetGroupByNomProjet;
import com.carto.sn.entities.ProjetGroupByNomProjetTypeLocalisation;
import com.carto.sn.entities.Utilisateur;
import com.carto.sn.service.GeocodingService;
import com.carto.sn.service.ICarto;
import com.carto.sn.storage.CustomMultipartFile;

import jakarta.validation.Valid;


@Controller
public class CartoController {
	
	@Autowired
	private ICarto iCarto;
	@Autowired
    private GeocodingService geocodingService;

	@RequestMapping({"/", "index"})
	public String accueil(Model model) {
		List <ProjetGroupByNomProjet> listProjet = iCarto.groupByNomProjet();
		//List <Projet> listProjet = iCarto.tousLesProjets();
		model.addAttribute("listProjet", listProjet);
		return "index";
	}
	
	@RequestMapping({"projet"})
	public String projet(Model model, String motCle) {
		
		if(motCle==null) {
		//List <Projet> listProjet = iCarto.tousLesProjets();
		List <ProjetGroupByNomProjet> listProjet = iCarto.groupByNomProjet();
		model.addAttribute("listProjet", listProjet);
		}else {
			List <Projet> listProjet = iCarto.chercher(motCle);
			model.addAttribute("listProjet", listProjet);
		}
		
		
		return "projet";
	}
	
	@RequestMapping("nouveauProjet")
	public String nouveauProjet(@ModelAttribute("unProjet") Projet unProjet, @ModelAttribute("unPartenaire") Partenaire unPartenaire,
			@ModelAttribute("uneLocalisation") Localisation uneLocalisation, Model model) {
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
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, 
			@ModelAttribute("unProjet") Projet unProjet, Model model, 
			String nomProjet, Long idProjet) {
		List <ProjetGroupByNomProjetTypeLocalisation> listProjetType = iCarto.projetEtTypes(nomProjet);
		List <ProjetGroupByNomProjet> listGroupByProjet = iCarto.groupByNomProjet();
		
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
		model.addAttribute("listProjetType", listProjetType);
		model.addAttribute("listGroupByProjet", listGroupByProjet);
		
		
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
			if(nomProjet!=null)
			model.addAttribute("nomProjet", nomProjet);
			if(idProjet==null)
				idProjet=iCarto.findOneIdByProjetName(nomProjet).get(0).getIdProjet();
		
		model.addAttribute("idProjet", idProjet);
		return "choixPartenaire";
	}
	
	@RequestMapping("nouveauPartenaire")
	public String nouveauPartenaire(@ModelAttribute("unPartenaire") Partenaire unPartenaire, Model model) {
		
		return "nouveauPartenaire";
	}
	
	@RequestMapping("localisation")
	public String localisation(@ModelAttribute("uneLocalisation") Localisation uneLocalisation, Model model) {
		List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
		model.addAttribute("listLocalisation", listLocalisation);
		return "localisation";
	}
	
	@RequestMapping("cartographie")
	public String  cartographie(Model model, String query, String nomProjet, RedirectAttributes ra) {
		
		  List <ProjetGroupByNomProjet> listProjet = iCarto.groupByNomProjet(); 
		  List <Partenaire> listPartenaire = iCarto.tousLesPartenaires(); 
		  List <Localisation>  listLocalisation = iCarto.toutesLesLocalisations();
		  model.addAttribute("listProjet", listProjet);
		  model.addAttribute("listPartenaire", listPartenaire);
		  model.addAttribute("listLocalisation", listLocalisation);
		  JSONObject location;
		 List <String> listLat = new ArrayList<>();
		 List <String> listLong = new ArrayList<>();
		 List <Long> listId = new ArrayList<>();
		  if(query==null) {
			
			  for(int i=0; i<listLocalisation.size(); i++) {
				  try {
					location = geocodingService.search(listLocalisation.get(i).getLibelleLocalisation());
					listLat.add(location.getString("lat"));
					listLong.add(location.getString("lon"));
					listId.add(location.getLong("osm_id"));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			  }
			  model.addAttribute("listId",listId);
			  model.addAttribute("listLat",listLat);
			  model.addAttribute("listLong",listLong);
			  
		  }
		  
		  if(query!=null) {
		 
		
	        try {
	            location = geocodingService.search(query);
	            
	            model.addAttribute("locationLat", location.getString("lat"));
	            model.addAttribute("locationLon", location.getString("lon"));
	            
	           
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  }
		 
		  
		return "cartographie";
	}
	
	@RequestMapping("sauvegarderLocalisation")
	public String sauvegarderLocalisation(@Valid @ModelAttribute("uneLocalisation") Localisation uneLocalisation, BindingResult br,
			String libelleLocalisation, RedirectAttributes ra) {
		if(br.hasErrors()) {
			return "localisation";
		}
		List <Localisation> listLoc = iCarto.toutesLesLocalisations();
		for(Localisation loc:listLoc) {
			if(loc.getLibelleLocalisation().equals(libelleLocalisation)) {
				ra.addFlashAttribute("messageDoublon", "Cette localisation existe déjà");
				ra.addFlashAttribute("libelleLocalisation", libelleLocalisation);
				return "redirect:/localisation";
			}
		}
			
		iCarto.ajoutLocalisation(libelleLocalisation);
		return "redirect:/localisation";
	}
	
	@RequestMapping("sauvegarderPartenaire")
	public String sauvegarderPartenaire(@Valid @ModelAttribute("unPartenaire") Partenaire unPartenaire, BindingResult br, Long idPartenaire, String nomPartenaire, String adresse, RedirectAttributes ra) {
		
		if(br.hasErrors()) {
			return "nouveauPartenaire";
		}
		if(idPartenaire==null) {
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		for(Partenaire part:listPartenaire) {
			if(part.getNomPartenaire().equals(nomPartenaire)) {
				ra.addFlashAttribute("messageDoublon", "Ce partenaire existe déjà");
				ra.addAttribute("nomPartenaire", nomPartenaire);
				return "redirect:/nouveauPartenaire";
			}
		}
			
	}
		
		
		iCarto.ajoutPartenaire(idPartenaire, nomPartenaire, adresse);
		return "redirect:/partenaire";
}
	
	@RequestMapping("sauvegarderProjet")
	public String sauvegarderProjet(@ModelAttribute("unProjet") Projet unProjet,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("uneLocalisation") Localisation uneLocalisation, 
			Model model, Long idProjet, String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, String statut, MultipartFile file,
			int duree, String temps, boolean activate, RedirectAttributes ra) throws IOException{
		
		if(nomProjet.isBlank()) {
			List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
			List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
			model.addAttribute("listLocalisation", listLocalisation);
			model.addAttribute("listPartenaire", listPartenaire);
			model.addAttribute("messageErreurNomProj", "Renseigner le nom du projet");
			return "nouveauProjet";
			
		}
		if(type.isBlank()) {
			List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
			List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
			model.addAttribute("listLocalisation", listLocalisation);
			model.addAttribute("listPartenaire", listPartenaire);
			model.addAttribute("messageErreurType", "Choisir le type du projet");
			return "nouveauProjet";
			
		}
		
		
		if(activate==true) {
			statut = "En cours";
		}else {
			statut = "inactif";
		}
		if(idProjet == null) {
			iCarto.ajoutProjet(nomProjet, responsable, nomPartenaire, libelleLocalisation, description, type, file, statut, duree, temps);
			ra.addFlashAttribute("flagEnregistrement", "1");
			return "redirect:/projet";
		}
		else if (idProjet != null) {
			iCarto.modifierProjet(idProjet, nomProjet, responsable, nomPartenaire, libelleLocalisation, description, type, file, statut, duree, temps);
			ra.addFlashAttribute("flagModification", "1");
			return "redirect:/listeProjet";
		}
		
		return "redirect:/projet";
	}
	
	@RequestMapping("sauvegarderChoixPartenaire")
		public String sauvegarderChoixPartenaire(Model model, Long idProjet, String nomProjet, String nomDuPartenaire, String libelleDeLaLocalisation, 
				@ModelAttribute("unProjet") Projet unProjet, String radioButtonSelected, RedirectAttributes ra) throws IOException{
			
			String responsable=iCarto.projetParId(idProjet).get().getResponsable();
			String description= iCarto.projetParId(idProjet).get().getDescription();
			int duree = iCarto.projetParId(idProjet).get().getDuree();
			String temps = iCarto.projetParId(idProjet).get().getTemps();
			String statut=iCarto.projetParId(idProjet).get().getStatut();
			String type="";
			MultipartFile file = new CustomMultipartFile(iCarto.projetParId(idProjet).get().getDataImage());
		    
			if(radioButtonSelected==null)
				{
					model.addAttribute("idProjet", idProjet);
					model.addAttribute("nomProjet", nomProjet);
					model.addAttribute("flag", "2");
					return "choixPartenaire";
				}
			if(radioButtonSelected.equals("rouge"))
				type="Egalite de genre";
			else if(radioButtonSelected.equals("orange"))
				type = "Education / Formation professionnelle";
			else if (radioButtonSelected.equals("jaune"))
				type = "Gouvernance";
			
			iCarto.ajoutProjet(nomProjet, responsable, nomDuPartenaire, libelleDeLaLocalisation, description, type, file, 
					statut, duree, temps);
		model.addAttribute("idProjet", idProjet);
		model.addAttribute("nomProjet", nomProjet);
		model.addAttribute("flag", "1");
		//return "redirect:/choixPartenaire?nomProjet="+nomProjet+"&idProjet="+idProjet;
		return "choixPartenaire";
	}
	
	@RequestMapping("supprimerPartenaire")
	public String supprimerPartenaire(Long idPartenaire) {
		iCarto.supprimerPartenaire(idPartenaire);
		return "redirect:/partenaire";
	}
	
	
	@RequestMapping("getDonneesPartenaireAModifier")
	public String modifierPartenaire(@ModelAttribute("unPartenaire") Partenaire unPartenaire, Model model, Long idPartenaire ) {
		model.addAttribute("idPartenaire", idPartenaire);
		unPartenaire = iCarto.findPartenaireById(idPartenaire).get();
		model.addAttribute("unPartenaire", unPartenaire);
		return "nouveauPartenaire";
	}
	
	@RequestMapping("nouveauProfil")
	public String nouveauProfil(@ModelAttribute("unProfil") Profil unProfil, Model model) {
		List <Profil> listProfil = iCarto.tousLesProfils();
		model.addAttribute("listProfil", listProfil);
		return "nouveauProfil";
	}
	
	@RequestMapping("sauvegarderProfil")
	public String sauvegarderProfil(@Valid @ModelAttribute("unProfil") Profil unProfil, BindingResult br, Model model, 
			String nomProfil, RedirectAttributes ra) {
		
		if(br.hasErrors()) {
			List <Profil> listProfil = iCarto.tousLesProfils();
			model.addAttribute("listProfil", listProfil);
			return "nouveauProfil";
		}
		
		List <Profil> listProfil = iCarto.tousLesProfils();
		for(Profil prof:listProfil) {
			if(prof.getNomProfil().equals(nomProfil)) {
				
				ra.addFlashAttribute("messageDoublon", "Ce profil existe déjà");
				ra.addFlashAttribute("nomProfil", nomProfil);
				return "redirect:/nouveauProfil";
			}
		}
		iCarto.ajoutProfil(nomProfil);
		return "redirect:/nouveauProfil";
	}
	
	@RequestMapping("utilisateur")
	public String utilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, Model model) {
		List <Utilisateur> listUtilisateur = iCarto.tousLesUtilisateurs();
		model.addAttribute("listUtilisateur", listUtilisateur);
		return "utilisateur";
	}
	
	@RequestMapping("nouvelUtilisateur")
	public String nouvelUtilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, 
			@ModelAttribute("unProfil") Profil unProfil, Model model) {
		List <Profil> listProfil = iCarto.tousLesProfils();
		model.addAttribute("listProfil", listProfil);
		return "nouvelUtilisateur";
	}
	
	@RequestMapping("sauvegarderUtilisateur")
	public String sauvegarderUtilisateur(@Valid @ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, BindingResult br,
			@ModelAttribute("unProfil") Profil unProfil, Model model, Long idUtilisateur, String nomUtilisateur, 
			String prenomUtilisateur, String login, String password, String nomProfil,
			RedirectAttributes ra) {
		
		if(br.hasErrors()) {
			List <Profil> listProfil = iCarto.tousLesProfils();
			model.addAttribute("listProfil", listProfil);
			return "nouvelUtilisateur";
		}
		if(idUtilisateur== null) {
		List <Utilisateur> listUtil = iCarto.tousLesUtilisateurs();
		for(Utilisateur util:listUtil) {
			if(util.getLogin().equals(login)) {
				List <Profil> listProfil = iCarto.tousLesProfils();
				model.addAttribute("listProfil", listProfil);
				model.addAttribute("messageDoublon", "Ce login existe déjà");
				return "nouvelUtilisateur";
			}
		}
	}
		iCarto.ajoutUtilisateur(idUtilisateur, nomUtilisateur, prenomUtilisateur, login, password, nomProfil);
		return "redirect:/utilisateur";
}
	
	@RequestMapping("supprimerUtilisateur")
	public String supprimerUtilisateur(Long idUtilisateur) {
		iCarto.supprimerUtilisateur(idUtilisateur);
		return "redirect:/utilisateur";
	}
	
	@RequestMapping("getDonneesUtilisateurAModifier")
	public String getDonneesUtilisateurAModifier(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, 
			@ModelAttribute("unProfil") Profil unProfil, Model model, Long idUtilisateur) {
		
		unUtilisateur = iCarto.findUtilisateurById(idUtilisateur).get();
		model.addAttribute("unUtilisateur", unUtilisateur);
		List <Profil> listProfil = iCarto.tousLesProfils();
		model.addAttribute("listProfil",listProfil);
		model.addAttribute("flagProfil", iCarto.findUtilisateurById(idUtilisateur).get().getProfil());
		
		return "nouvelUtilisateur";
	}
	
	@RequestMapping("supprimerLocalisation")
	public String supprimerLocalsation(Long idLocalisation) {
		iCarto.supprimerLocalisation(idLocalisation);
		return "redirect:/localisation";
	}
	
	@RequestMapping("parametres")
	public String parametres() {
		return "parametres";
	}
	
	@RequestMapping("supprimerProfil")
	public String supprimerProfil(Long idProfil) {
		iCarto.supprimerProfil(idProfil);
		return "redirect:/nouveauProfil";
	}
	
	@RequestMapping("appErreur")
	public String appErreur() {
		return "appErreur";
	}
	
	@RequestMapping("listeProjet")
	public String listeProjet(Model model) {
		List <Projet> listProjet = iCarto.tousLesProjets();
		model.addAttribute("listProjet", listProjet);
		return "listeProjet";
	}
	
	@RequestMapping("supprimerProjet")
	public String supprimerProjet(Long idProjet) {
		iCarto.supprimerProjet(idProjet);
		return "redirect:/listeProjet";
	}
	
	@RequestMapping("getDonneesProjetAModifier")
	public String getDonneesProjetAModifier(@ModelAttribute("unProjet") Projet unProjet, 
			@ModelAttribute("unPartenaire") Partenaire unPartenaire,
			@ModelAttribute("uneLocalisation") Localisation uneLocalisation, Model model, Long idProjet) {
		
		unProjet = iCarto.projetParId(idProjet).get();
		unPartenaire = iCarto.findPartenaireById(unProjet.getPartenaire().getIdPartenaire()).get();
		uneLocalisation = iCarto.findLocalisationById(unProjet.getLocalisation().getIdLocalisation()).get();
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
		model.addAttribute("unProjet", unProjet);
		model.addAttribute("unPartenaire", unPartenaire);
		model.addAttribute("uneLocalisation", uneLocalisation);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
		
		return "nouveauProjet";
	}
	
	@PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        JSONObject location;
        try {
            location = geocodingService.search(query);
            model.addAttribute("location", location);
        } catch (IOException e) {
            e.printStackTrace();
        }
       

        return "cartographie";
    }
}
