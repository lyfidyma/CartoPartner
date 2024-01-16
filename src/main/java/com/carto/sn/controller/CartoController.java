package com.carto.sn.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Departement;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Pays;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.entities.Utilisateur;
import com.carto.sn.service.GeocodingService;
import com.carto.sn.service.ICarto;

import jakarta.validation.Valid;


@Controller
public class CartoController {
	
	@Autowired
	private ICarto iCarto;
	@Autowired
    private GeocodingService geocodingService;

	@RequestMapping("/index")
	public String index(Model model) {
		//List <ProjetGroupByNomProjet> listProjet = iCarto.groupByNomProjet();
		//List <Projet> listProjet = iCarto.tousLesProjets();
		//model.addAttribute("listProjet", listProjet);
		return "test";
	}
	
	@RequestMapping("projet")
	public String projet(Model model, String motCle) {
		
		if(motCle==null) {
		//List <Projet> listProjet = iCarto.tousLesProjets();
		List <Projet> listProjet = iCarto.tousLesProjets();
		model.addAttribute("listProjet", listProjet);
		}else {
			List <Projet> listProjet = iCarto.chercher(motCle);
			model.addAttribute("listProjet", listProjet);
		}	
		
		return "projet";
	}
	
	@RequestMapping("nouveauProjet")
	public String nouveauProjet(@ModelAttribute("unProjet") Projet unProjet, @ModelAttribute("unPartenaire") Partenaire unPartenaire,
			@ModelAttribute("uneRegion") Region uneRegion, @ModelAttribute("unType") Type unType, Model model) {
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Region> listLocalisation = iCarto.toutesLesRegions();
		List <Type> listType = iCarto.tousLesTypes();
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
		model.addAttribute("listType", listType);
		
		return "nouveauProjet";
	}
	
	@RequestMapping("partenaire")
	public String partenaire(Model model) {
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Region> listLocalisation = iCarto.toutesLesRegions();
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
		return "partenaire";
	}
	
	@RequestMapping("choixPartenaire")
	
	public String choixPartenaire(@ModelAttribute("uneRegion") Region uneRegion, 
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, 
			@ModelAttribute("unProjet") Projet unProjet, Model model, 
			String nomProjet, Long idProjet) {
		//List <ProjetGroupByNomProjetTypeLocalisation> listProjetType = iCarto.projetEtTypes(nomProjet);
		//List <ProjetGroupByNomProjet> listGroupByProjet = iCarto.groupByNomProjet();
		List <Projet> listProjet= iCarto.tousLesProjets();
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List <Region> listRegion = iCarto.toutesLesRegions();
		List <Type> listType = iCarto.tousLesTypes();
		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listType", listType);
		//model.addAttribute("listGroupByProjet", listGroupByProjet);
		
		
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listRegion", listRegion);
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
	public String localisation(@ModelAttribute("uneRegion") Region uneRegion, @ModelAttribute("unDepartement") Departement unDepartement, 
			@ModelAttribute("unPays") Pays unPays, Model model) {
		List <Departement> listDepartement = iCarto.tousLesDepartements();
		model.addAttribute("listDepartement",listDepartement);
		return "localisation";
	}
	
	@RequestMapping("cartographie")
	public String  cartographie(Model model, Long idProjet, String type, String departement, String region, RedirectAttributes ra) {
		
		  
		 List <Projet> listProjet = iCarto.tousLesProjets();
		  List <Partenaire> listPartenaire = iCarto.tousLesPartenaires(); 
		  List <Region>  listRegion = iCarto.toutesLesRegions();
		 
		  model.addAttribute("listProjet", listProjet);
		  model.addAttribute("listPartenaire", listPartenaire);
		  model.addAttribute("listRegion", listRegion);
		 
		  JSONObject location;
		 List <String> listLat = new ArrayList<>();
		 List <String> listLong = new ArrayList<>();
		 List <String> listName = new ArrayList<>();
		 List <String> listPart = new ArrayList<>();
		 HashMap <String, List<String>> listTypeRegion = new HashMap<String, List<String>>();
		 List<String> uneListeType =  new ArrayList<>();
		 Set <Region> listProjetsEtRegions = null;
		 Set <Type> listProjetsEtType = null;
		 HashSet<String> listNombreRegions=new HashSet<String>();  
		 
		  if(idProjet==null) {
			  
			  for(int i=0; i<listProjet.size(); i++) {
					
					  Long id = listProjet.get(i).getIdProjet(); 
					  Set <Region> reg1 =  iCarto.projetParId(id).get().getRegion(); 
					  Set <Type> typ1 =  iCarto.projetParId(id).get().getType();
					 
					  
						  
						
				  listProjetsEtRegions = listProjet.get(i).getRegion();
				  listProjetsEtType = listProjet.get(i).getType();
				  for(Region r :listProjetsEtRegions) {
				  try {
					listNombreRegions.add(r.getNomRegion());
					location = geocodingService.search(r.getNomRegion(), r.getPays().getNomPays());
					listLat.add(location.getString("lat"));
					listLong.add(location.getString("lon"));
					listName.add(r.getNomRegion());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				  }
				  
			  }
			
			  model.addAttribute("listTypeRegion", listTypeRegion);
			  model.addAttribute("nombreProjets", listProjet.size());
			  model.addAttribute("nombreRegions", listNombreRegions.size());
			  model.addAttribute("listName",listName);
			  model.addAttribute("listLat",listLat);
			  model.addAttribute("listLong",listLong);
			  
			  
		  }
		  
		  if(idProjet!=null) {
			 
			 Set <Region> dep = iCarto.projetParId(idProjet).get().getRegion();
			 Set <Type> typ = iCarto.projetParId(idProjet).get().getType();
			 Set <Partenaire> part = iCarto.projetParId(idProjet).get().getPartenaire();
			 String listTypeParRegion=null;
			 String nomDuType=null;
			 for(Type t:typ) {
				 listTypeParRegion = t.getCouleur();
				 nomDuType = t.getNomType();
			 }
			 model.addAttribute("listTypeParRegion", listTypeParRegion);
			 model.addAttribute("nomDuType", nomDuType);
			 for(Partenaire p:part) {
				 listPart.add(p.getNomPartenaire());
			 }
			 model.addAttribute("listPart", listPart);
			 for(Region r:dep) {
				 try {
					 	listNombreRegions.add(r.getNomRegion());
						location = geocodingService.search(r.getNomRegion(), r.getPays().getNomPays());
						listLat.add(location.getString("lat"));
						listLong.add(location.getString("lon"));
						listName.add(r.getNomRegion());
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				  }
			 	  model.addAttribute("nombreRegions", listNombreRegions.size());
			 	  model.addAttribute("nombreProjets","1");
				  model.addAttribute("listName",listName);
				  model.addAttribute("listLat",listLat);
				  model.addAttribute("listLong",listLong);
			 
			
			 
			 
			  
		  }
				/*
				 * try { location = geocodingService.search(regionGeocod, paysGeocod);
				 * 
				 * model.addAttribute("locationLat", location.getString("lat"));
				 * model.addAttribute("locationLon", location.getString("lon"));
				 * 
				 * 
				 * } catch (IOException e) { e.printStackTrace(); }
				 */
		  
		 
		  
		return "cartographie";
	}
	
	@RequestMapping("cartographieDetails")
	public String  cartographieDetails(Model model, String type, String departement, String region, RedirectAttributes ra) {
		
		 // List <ProjetGroupByNomProjet> listProjet = iCarto.groupByNomProjet(); 
		 List <Projet> listProjet = iCarto.tousLesProjets();
		  List <Partenaire> listPartenaire = iCarto.tousLesPartenaires(); 
		  List <Region>  listRegion = iCarto.toutesLesRegions();
		  model.addAttribute("listProjet", listProjet);
		  model.addAttribute("listPartenaire", listPartenaire);
		  model.addAttribute("listRegion", listRegion);
		  JSONObject location;
		 List <String> listLat = new ArrayList<>();
		 List <String> listLong = new ArrayList<>();
		 List <Long> listId = new ArrayList<>();
		 
		  if(departement!=null) {
			List <Departement> listDep = iCarto.tousLesDepartements();
			  for(Departement d :listDep) {
				  try {
					location = geocodingService.search(d.getNomDepartement(), d.getRegion().getPays().getNomPays());
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
		  
		  if(type!=null) {
			 
			 
			  for(int i=0; i<listRegion.size(); i++) {
				  try {
					location = geocodingService.search(listRegion.get(i).getNomRegion(), listRegion.get(i).getPays().getNomPays());
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
		 
		  
		return "cartographie";
	}
	
	@RequestMapping("sauvegarderLocalisation")
	public String sauvegarderLocalisation(@Valid @ModelAttribute("uneRegion") Region uneRegion, BindingResult br,
			String nomDepartement, String nomRegion, String nomPays, RedirectAttributes ra) {
		if(br.hasErrors()) {
			return "localisation";
		}
		/*
		 * List <Region> listRegion = iCarto.toutesLesRegions(); for(Region loc:listLoc)
		 * { if(loc.getNomRegion().equals(libelleRegion)) {
		 * ra.addFlashAttribute("messageDoublon", "Cette localisation existe déjà");
		 * ra.addFlashAttribute("libelleLocalisation", libelleRegion); return
		 * "redirect:/localisation"; } }
		 */
		List<Departement> listDepartement = iCarto.tousLesDepartements();
		ra.addFlashAttribute("listDepartement", listDepartement);
			
		iCarto.ajoutRegion(nomDepartement, nomRegion, nomPays);
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
	public String sauvegarderProjet(@ModelAttribute("unProjet") Projet unProjet, @ModelAttribute("unType") Type unType,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("uneRegion") Region uneRegion, 
			Model model, Long idProjet, String nomProjet, String pointFocal, 
			String description, String nomType, String statut, MultipartFile file,
			int duree, String temps, RedirectAttributes ra) throws IOException{
		
		if(nomProjet.isBlank()) {
			
			List <Type> listType = iCarto.tousLesTypes();
			
			model.addAttribute("listType", listType);
			model.addAttribute("messageErreurNomProj", "Renseigner le nom du projet");
			return "nouveauProjet";
			
		}
		if(nomType.isBlank()) {
			List <Type> listType = iCarto.tousLesTypes();
			model.addAttribute("listType", listType);
			model.addAttribute("messageErreurType", "Choisir le type du projet");
			return "nouveauProjet";
			
		}
		
		if(idProjet == null) {
			List <Projet> listProjet=iCarto.tousLesProjets();
			for(int i = 0; i<listProjet.size(); i++) {
				if(nomProjet.equals(listProjet.get(i).getNomProjet())) {
					List <Type> listType = iCarto.tousLesTypes();
					model.addAttribute("listType", listType);
					model.addAttribute("messageDoublon", "Ce projet existe");
					return "nouveauProjet";
				}
			}
			iCarto.ajoutProjet(nomProjet, pointFocal, description, nomType, file, statut, duree, temps);
			ra.addFlashAttribute("flagEnregistrement", "1");
			return "redirect:/nouveauProjet";
		}
		else if (idProjet != null) {
			//iCarto.modifierProjet(idProjet, nomProjet, pointFocal, description, type, file, statut, duree, temps);
			ra.addFlashAttribute("flagModification", "1");
			return "redirect:/listeProjet";
		}
		
		return "redirect:/projet";
	}
	
	@RequestMapping("sauvegarderChoixPartenaire")
		public String sauvegarderChoixPartenaire(Model model, Long idProjet, String nomProjet, String nomDuPartenaire, 
				String nomRegion, 
				@ModelAttribute("unProjet") Projet unProjet, String radioButtonSelected, RedirectAttributes ra) throws IOException{
		
			
			//String pointFocal=iCarto.projetParId(idProjet).get().getPointFocal();
			//String description= iCarto.projetParId(idProjet).get().getDescription();
			//int duree = iCarto.projetParId(idProjet).get().getDuree();
			//String temps = iCarto.projetParId(idProjet).get().getTemps();
			//String statut=iCarto.projetParId(idProjet).get().getStatut();
			String type="";
			//MultipartFile file = new CustomMultipartFile(iCarto.projetParId(idProjet).get().getDataImage());
		    if(idProjet!=null) {
			if(radioButtonSelected==null)
				{
					model.addAttribute("idProjet", idProjet);
					model.addAttribute("nomProjet", nomProjet);
					model.addAttribute("flag", "2");
					return "choixPartenaire";
				}
			if(radioButtonSelected.equals("rouge"))
				type="Egalité de genre";
			else if(radioButtonSelected.equals("orange"))
				type = "Education / Formation professionnelle";
			else if (radioButtonSelected.equals("jaune"))
				type = "Gouvernance";
		    }
			iCarto.ajoutPartenaireAuProjet(nomProjet, nomDuPartenaire, nomRegion, type);
		
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
	
	@RequestMapping("supprimerRegion")
	public String supprimerRegion(Long idRegion) {
		iCarto.supprimerRegion(idRegion);
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
			@ModelAttribute("uneRegion") Region uneRegion, Model model, Long idProjet) {
		
		unProjet = iCarto.projetParId(idProjet).get();
	//	unPartenaire = iCarto.findPartenaireById(unProjet.getPartenaire());
		//uneLocalisation = iCarto.findLocalisationById(unProjet.getLocalisation().getIdLocalisation()).get();
		List <Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		//List <Localisation> listLocalisation = iCarto.toutesLesLocalisations();
		model.addAttribute("unProjet", unProjet);
		model.addAttribute("unPartenaire", unPartenaire);
		model.addAttribute("uneRegion", uneRegion);
		model.addAttribute("listPartenaire", listPartenaire);
	//	model.addAttribute("listRegion", listRegion);
		
		return "nouveauProjet";
	}
	
	
	@RequestMapping("type")
	public String type(@ModelAttribute("unType") Type unType, Model model) {
		List <Type> listType = iCarto.tousLesTypes();
		model.addAttribute("listType", listType);
		
		return "type";
	}
	
	@RequestMapping("sauvegarderType")
	public String sauvegarderType(@ModelAttribute ("unType") Type unType, Model model, String nomType, String couleur) {
		iCarto.ajoutType(nomType, couleur);
		return "redirect:/type";
	}
	
	@RequestMapping("supprimerType")
	public String supprimerType(@ModelAttribute ("unType") Type unType, Long idType) {
		iCarto.supprimerType(idType);
		return "redirect:/type";
	}
	
	@RequestMapping("projetPartenaireRegion")
	public String projetPartenaireRegion( @ModelAttribute ("unProjet") Projet unProjet, 
			@ModelAttribute ("unPartenaire") Partenaire unPartenaire, @ModelAttribute ("uneRegion") Region uneRegion,
			 Model model) {
		List<Projet> listProjet = iCarto.tousLesProjets();
		List<Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List<Region> listRegion = iCarto.toutesLesRegions();
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listRegion", listRegion);
		
		
		
		return "projetPartenaireRegion";
	}
	
	@RequestMapping("lierPartenaire")
	public String projetPartenaireRegion( @ModelAttribute ("unProjet") Projet unProjet, 
			@ModelAttribute ("unPartenaire") Partenaire unPartenaire, @ModelAttribute ("uneRegion") Region uneRegion,
			String nomProjet, Long idProjet, String[] nomRegion,  String[] nomDuPartenaire, String rowCount, String type, Model model) {
		List<Projet> listProjet = iCarto.tousLesProjets();
		List<Partenaire> listPartenaire = iCarto.tousLesPartenaires();
		List<Region> listRegion = iCarto.toutesLesRegions();
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listRegion", listRegion);
		
		if(rowCount.isBlank()==true) {
			iCarto.ajoutPartenaireAuProjet(nomProjet, nomDuPartenaire[0], nomRegion[0], type);
		}else if(rowCount.isBlank()== false) {
			int rowCountInt = Integer.parseInt(rowCount);
			
			for(int i=0; i < rowCountInt; i++)
				iCarto.ajoutPartenaireAuProjet(nomProjet, nomDuPartenaire[i], nomRegion[i], type);
		}
		
		
		
		model.addAttribute("idProjet", idProjet);
		model.addAttribute("nomProjet", nomProjet);
		model.addAttribute("flag", "1");
		//return "redirect:/choixPartenaire?nomProjet="+nomProjet+"&idProjet="+idProjet;
		
		
		return "projetPartenaireRegion";
	}
	
	
	
}
