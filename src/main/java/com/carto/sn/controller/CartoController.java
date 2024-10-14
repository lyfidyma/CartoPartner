package com.carto.sn.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.dao.PrivilegeRepository;
import com.carto.sn.dao.ProjetPartenaireRegionRepository;
import com.carto.sn.dto.ProjetPartenaireDTO;
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
import com.carto.sn.service.CategorieService;
import com.carto.sn.service.CommuneService;
import com.carto.sn.service.DepartementService;
import com.carto.sn.service.GeocodingService;
import com.carto.sn.service.ICarto;
import com.carto.sn.service.PartenaireLocalService;
import com.carto.sn.service.PartenaireService;
import com.carto.sn.service.PrivilegeService;
import com.carto.sn.service.ProfilService;
import com.carto.sn.service.ProjetPartenaireRegionService;
import com.carto.sn.service.ProjetService;
import com.carto.sn.service.RegionService;
import com.carto.sn.service.TypeService;
import com.carto.sn.service.UtilisateurService;
import com.carto.sn.service.VillageService;

import jakarta.validation.Valid;

@Controller
@PreAuthorize("isAuthenticated()")
public class CartoController {

	@Autowired
	private ICarto iCarto;
	@Autowired
	private CategorieService categorieService;
	@Autowired
	private CommuneService communeService;
	@Autowired
	private DepartementService departementService;
	@Autowired
	private PartenaireLocalService parteLocalService;
	@Autowired
	private PartenaireService partenaireService;
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private ProfilService profilService;
	@Autowired
	private ProjetPartenaireRegionService pprService;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private VillageService villageService;
	@Autowired
	private GeocodingService geocodingService;

//	@RequestMapping("/index")
//	public String index(@ModelAttribute("uneRegion") Region uneRegion, @ModelAttribute("unType") Type unType,
//			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unProjet") Projet unProjet,
//			Model model, String nomProjet, Long idProjet) {
//		List<Projet> listProjetPoinFocal = projetService.groupByPointFocal();
//		List<Projet> listProjet = projetService.tousLesProjets();
//
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listRegion = regionService.toutesLesRegions();
//		List<Type> listType = typeService.tousLesTypes();
//		model.addAttribute("listProjet", listProjet);
//		model.addAttribute("listType", listType);
//
//		model.addAttribute("listProjetPoinFocal", listProjetPoinFocal);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listRegion", listRegion);
//		if (nomProjet != null)
//			model.addAttribute("nomProjet", nomProjet);
//
//		model.addAttribute("idProjet", idProjet);
//
//		List<ProjetPartenaireRegion> listPpr = pprService.tousLesProjetsPartenairesRegions();
//
//		model.addAttribute("listProjet", listProjet);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listRegion", listRegion);
//		model.addAttribute("listType", listType);
//		JSONObject location;
//		List<String> listLat = new ArrayList<>();
//		List<String> listLong = new ArrayList<>();
//		List<String> listName = new ArrayList<>();
//		Set<String> listPart = new HashSet<>();
//		// HashMap <String, List<String>> listTypeRegion = new HashMap<String,
//		// List<String>>();
//		// List<String> uneListeType = new ArrayList<>();
//		Set<Commune> listProjetsEtRegions = new HashSet<>();
//		// Set <Type> listProjetsEtType = null;
//		HashSet<String> listNombreRegions = new HashSet<String>();
//		Set<Projet> listNombreProjets = new HashSet<>();
//
//		for (int i = 0; i < listPpr.size(); i++) {
//
//			listProjetsEtRegions.add(listPpr.get(i).getCommune());
//
//			for (Commune r : listProjetsEtRegions) {
//
//				try {
//					listNombreRegions.add(r.getNomCommune());
//					location = geocodingService.search(r.getNomCommune(),
//							r.getDepartement().getRegion().getPays().getNomPays());
//					listLat.add(location.getString("lat"));
//					listLong.add(location.getString("lon"));
//					listName.add(r.getNomCommune());
//					listNombreProjets.addAll(r.getDepartement().getRegion().getProjet());
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//		}
//
//		Set<Partenaire> partDesProjets = new HashSet<Partenaire>();
//		for (Projet pro : listNombreProjets) {
//
//			partDesProjets.addAll(pro.getPartenaire());
//		}
//
//		for (Partenaire p : partDesProjets) {
//			listPart.add(p.getNomPartenaire());
//		}
//
//		List<String> listDesProjets = new ArrayList<>();
//		for (Projet p : listNombreProjets) {
//			listDesProjets.add(p.getNomProjet());
//		}
//		model.addAttribute("listDesProjets", listDesProjets);
//		model.addAttribute("nombreProjets", listDesProjets.size());
//		model.addAttribute("nombreRegions", listNombreRegions.size());
//		model.addAttribute("listName", listName);
//		model.addAttribute("listLat", listLat);
//		model.addAttribute("listLong", listLong);
//		model.addAttribute("listPart", listPart);
//		model.addAttribute("listPartSize", partDesProjets.size());
//		model.addAttribute("nomDuType", listType.size());
//
//		return "index";
//	}

//	@RequestMapping("projet")
//	public String projet(Model model, String motCle) {
//
//		if (motCle == null) {
//			// List <Projet> listProjet = iCarto.tousLesProjets();
//			List<Projet> listProjet = projetService.tousLesProjets();
//			model.addAttribute("listProjet", listProjet);
//		} else {
//			List<Projet> listProjet = projetService.chercher(motCle);
//			model.addAttribute("listProjet", listProjet);
//		}
//
//		return "projet";
//	}
//
//	@RequestMapping("nouveauProjet")
//	public String nouveauProjet(@ModelAttribute("unProjet") Projet unProjet,
//			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("uneRegion") Region uneRegion,
//			@ModelAttribute("unType") Type unType, @ModelAttribute("uneCategorie") Categorie uneCategorie,
//			Model model) {
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listLocalisation = regionService.toutesLesRegions();
//		List<Type> listType = typeService.tousLesTypes();
//		List<Categorie> listCategorie = categorieService.toutesLesCategories();
//
//		model.addAttribute("listCategorie", listCategorie);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listLocalisation", listLocalisation);
//		model.addAttribute("listType", listType);
//
//		return "nouveauProjet";
//	}

//	@RequestMapping("partenaire")
//	public String partenaire(Model model) {
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listLocalisation = regionService.toutesLesRegions();
//		List<PartenaireLocal> listPartenaireLocal = parteLocalService.tousLesPartenairesLocaux();
//		model.addAttribute("listPartenaireLocal", listPartenaireLocal);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listLocalisation", listLocalisation);
//		return "partenaire";
//	}
//
//	@RequestMapping("displayChoixPartenaire")
//	public String choixPartenaire(@ModelAttribute("uneRegion") Region uneRegion,
//			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unType") Type unType,
//			@ModelAttribute("unProjet") Projet unProjet, Model model, @RequestParam(defaultValue = "") String nomProjet,
//			@RequestParam(defaultValue = "") Long idProjet, @RequestParam(defaultValue = "") String nomPartenaire,
//			@RequestParam(defaultValue = "") String pointFocal, @RequestParam(defaultValue = "") String nomType,
//			String pageAAfficher, String flag) {
//		// List <ProjetPartenaireRegion> listPpr =
//		// iCarto.tousLesProjetsPartenairesRegions();
//		List<Projet> listProjet = projetService.tousLesProjets();
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listRegion = regionService.toutesLesRegions();
//		List<Projet> listProjetPoinFocal = projetService.groupByPointFocal();
//
//		if (nomProjet.isEmpty() == false) {
//			List<ProjetPartenaireRegion> projetName = null;
//			Projet projetName1 = null;
//			projetName1 = projetService.findByNomProjet(nomProjet);
//			projetName = pprService.findByIdProjet(projetName1.getIdProjet());
//			model.addAttribute("projetName1", projetName1);
//			model.addAttribute("projetName", projetName);
//		}
//
//		if (pageAAfficher.equals("pageIndex")) {
//
//			if (nomPartenaire.isEmpty() == false) {
//				Partenaire partenaireName1;
//				Set<Partenaire> partenaireProjet = new HashSet<Partenaire>();
//				Set<Projet> projetsDunPartenaire = new HashSet<Projet>();
//				Set<ProjetPartenaireRegion> partenaireName = new HashSet<ProjetPartenaireRegion>();
//
//				// Set <Projet> projetPourType=new HashSet<Projet>();
//				partenaireName1 = partenaireService.findByNomPartenaire(nomPartenaire);
//				for (int i = 0; i < listProjet.size(); i++) {
//					partenaireProjet = (listProjet.get(i).getPartenaire());
//					for (Partenaire p : partenaireProjet) {
//						if (partenaireName1.getNomPartenaire() == p.getNomPartenaire()) {
//							projetsDunPartenaire.addAll(p.getProjet());
//							for (Projet pr : projetsDunPartenaire) {
//								partenaireName.addAll(pprService.findByIdProjet(pr.getIdProjet()));
//							}
//						}
//
//					}
//				}
//
//				// model.addAttribute("projetPourType", projetPourType);
//				model.addAttribute("partenaireName1", partenaireName1);
//				model.addAttribute("partenaireName", partenaireName);
//			}
//
//			if (pointFocal.isEmpty() == false) {
//				List<Projet> pointFocalName = null;
//				pointFocalName = projetService.findByPointFocal(pointFocal);
//				model.addAttribute("pointFocalName", pointFocalName);
//			}
//
//			if (nomType.isEmpty() == false) {
//				Type typeName1 = null;
//				Set<Type> projetType = null;
//				Set<ProjetPartenaireRegion> typeName = new HashSet<ProjetPartenaireRegion>();
//				typeName1 = typeService.findByNomType(nomType);
//
//				for (int i = 0; i < listProjet.size(); i++) {
//
//					projetType = listProjet.get(i).getType();
//
//					for (Type typ : projetType) {
//						if (typeName1.getNomType() == typ.getNomType()) {
//							typeName.addAll(pprService.findByIdProjet(listProjet.get(i).getIdProjet()));
//
//						}
//					}
//				}
//
//				model.addAttribute("typeName", typeName);
//				model.addAttribute("typeName1", typeName1);
//			}
//		}
//
//		List<Type> listType = typeService.tousLesTypes();
//		model.addAttribute("listProjet", listProjet);
//		model.addAttribute("listType", listType);
//		model.addAttribute("listProjetPoinFocal", listProjetPoinFocal);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listRegion", listRegion);
//		if (nomProjet != null) {
//			model.addAttribute("nomProjet", nomProjet);
//			model.addAttribute("flag", flag);
//		}
//		// if(idProjet==null)
//		// idProjet=iCarto.findOneIdByProjetName(nomProjet).get(0).getIdProjet();
//		if (nomPartenaire != null)
//			model.addAttribute("nomPartenaire", nomPartenaire);
//
//		model.addAttribute("idProjet", idProjet);
//		if (pageAAfficher.equals("pageIndex"))
//			return "index";
//		return "choixPartenaire";
//	}
//
//	@RequestMapping("nouveauPartenaire")
//	public String nouveauPartenaire(@ModelAttribute("unPartenaire") Partenaire unPartenaire, Model model) {
//
//		return "nouveauPartenaire";
//	}

//	@RequestMapping("localisation")
//	public String localisation(@ModelAttribute("uneRegion") Region uneRegion,
//			@ModelAttribute("unDepartement") Departement unDepartement,
//			@ModelAttribute("uneCommune") Commune uneCommune, @ModelAttribute("unVillage") Village unVillage,
//			@ModelAttribute("unPays") Pays unPays, Model model) {
//		List<Commune> listCommune = communeService.toutesLesCommunes();
//		List<Departement> listDepartement = departementService.tousLesDepartements();
//		List<Village> listVillage = villageService.tousLesVillages();
//
//		model.addAttribute("listVillage", listVillage);
//		model.addAttribute("listCommune", listCommune);
//		model.addAttribute("listDepartement", listDepartement);
//		return "localisation";
//	}

//	@RequestMapping("cartographie")
//	public String cartographie(@ModelAttribute("unProjet") Projet unProjet, @ModelAttribute("unType") Type unType,
//			@ModelAttribute("unPartenaire") Partenaire unPartenaire, Model model, Long idProjet, String type,
//			String departement, String region, RedirectAttributes ra) {
//
//		List<ProjetPartenaireRegion> listPpr = pprService.tousLesProjetsPartenairesRegions();
//		List<Projet> listProjet = projetService.tousLesProjets();
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listRegion = regionService.toutesLesRegions();
//		List<Type> listType = typeService.tousLesTypes();
//
//		model.addAttribute("listProjet", listProjet);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listRegion", listRegion);
//		model.addAttribute("listType", listType);
//		JSONObject location;
//		List<String> listLat = new ArrayList<>();
//		List<String> listLong = new ArrayList<>();
//		List<String> listName = new ArrayList<>();
//		Set<String> listPart = new HashSet<>();
//		Set<String> listPartSize = new HashSet<>();
//		// HashMap <String, List<String>> listTypeRegion = new HashMap<String,
//		// List<String>>();
//		// List<String> uneListeType = new ArrayList<>();
//		Set<Commune> listProjetsEtRegions = new HashSet<>();
//		// Set <Type> listProjetsEtType = null;
//		HashSet<String> listNombreRegions = new HashSet<String>();
//		Set<Projet> listNombreProjets = new HashSet<>();
//
//		if (idProjet == null) {
//			HashMap<String, Set<String>> hmap = new HashMap<String, Set<String>>();
//			HashMap<String, Set<String>> hmapProjet = new HashMap<String, Set<String>>();
//			HashMap<String, Set<String>> hmapType = new HashMap<String, Set<String>>();
//			Set<String> listDesProjets = new HashSet<>();
//			Set<String> listDesProjetsSize = new HashSet<>();
//			Set<String> listTypeParRegion = new HashSet<String>();
//			Set<Type> listCouleur = new HashSet<Type>();
//			for (int i = 0; i < listPpr.size(); i++) {
//
//				listProjetsEtRegions.add(listPpr.get(i).getCommune());
//				listDesProjets.add(listPpr.get(i).getProjet().getNomProjet());
//
//				listDesProjetsSize.add(listPpr.get(i).getProjet().getNomProjet());
//				listPart.add(listPpr.get(i).getPartenaire().getNomPartenaire());
//
//				listPartSize.add(listPpr.get(i).getPartenaire().getNomPartenaire());
//
//				listCouleur.addAll(listPpr.get(i).getProjet().getType());
//				for (Type t : listCouleur) {
//					listTypeParRegion.add(t.getCouleur());
//				}
//				for (Map.Entry<String, Set<String>> entry : hmap.entrySet()) {
//					if (entry.getKey() == listPpr.get(i).getCommune().getNomCommune()) {
//						listPart.addAll(entry.getValue());
//					}
//				}
//
//				for (Map.Entry<String, Set<String>> entry : hmapProjet.entrySet()) {
//					if (entry.getKey() == listPpr.get(i).getCommune().getNomCommune()) {
//						listDesProjets.addAll(entry.getValue());
//					}
//				}
//				for (Map.Entry<String, Set<String>> entry : hmapType.entrySet()) {
//					if (entry.getKey() == listPpr.get(i).getCommune().getNomCommune()) {
//						listTypeParRegion.addAll(entry.getValue());
//					}
//				}
//				hmap.put(listPpr.get(i).getCommune().getNomCommune(), listPart);
//				hmapProjet.put(listPpr.get(i).getCommune().getNomCommune(), listDesProjets);
//				hmapType.put(listPpr.get(i).getCommune().getNomCommune(), listTypeParRegion);
//
//				listDesProjets = new HashSet<>();
//				listTypeParRegion = new HashSet<>();
//				listPart = new HashSet<>();
//			}
//			for (Commune r : listProjetsEtRegions) {
//
//				try {
//					listNombreRegions.add(r.getNomCommune());
//					location = geocodingService.search(r.getNomCommune(),
//							r.getDepartement().getRegion().getPays().getNomPays());
//					listLat.add(location.getString("lat"));
//					listLong.add(location.getString("lon"));
//					listName.add(r.getNomCommune());
//					listNombreProjets.addAll(r.getDepartement().getRegion().getProjet());
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			model.addAttribute("listTypeParRegion", hmapType);
//			model.addAttribute("listDesProjets", hmapProjet);
//			model.addAttribute("nombreProjets", "tous les projets, " + listDesProjetsSize.size());
//			model.addAttribute("nombreRegions", listNombreRegions.size());
//			model.addAttribute("listName", listName);
//			model.addAttribute("listLat", listLat);
//			model.addAttribute("listLong", listLong);
//			model.addAttribute("listPart", hmap);
//			model.addAttribute("listPartSize", listPartSize.size());
//			model.addAttribute("nomDuType", listType.size());
//
//		}
//
//		if (idProjet != null) {
//			HashMap<String, Set<String>> hmap = new HashMap<String, Set<String>>();
//			HashMap<String, Set<String>> hmapProjet = new HashMap<String, Set<String>>();
//			HashMap<String, Set<String>> hmapType = new HashMap<String, Set<String>>();
//			Set<String> listTypeParRegion = new HashSet<String>();
//			String nomDuType = null;
//			String codeCouleur = null;
//			Set<Commune> com = new HashSet<>();
//			Set<String> listDesProjets = new HashSet<>();
//			Set<String> listDesProjetsSize = new HashSet<>();
//
//			List<ProjetPartenaireRegion> proj = pprService.findByIdProjet(idProjet);
//			Set<Type> typ = projetService.projetParId(idProjet).get().getType();
//			for (Type t : typ) {
//				listTypeParRegion.add(t.getCouleur());
//				nomDuType = t.getNomType();
//				codeCouleur = t.getCouleur();
//			}
//
//			for (ProjetPartenaireRegion p : proj) {
//				com.add(p.getCommune());
//				listPart.add(p.getPartenaire().getNomPartenaire());
//				listPartSize.add(p.getPartenaire().getNomPartenaire());
//				listDesProjets.add(p.getProjet().getNomProjet());
//				listDesProjetsSize.add(p.getProjet().getNomProjet());
//				for (Map.Entry<String, Set<String>> entry : hmap.entrySet()) {
//					if (entry.getKey().equals(p.getCommune().getNomCommune())) {
//						listPart.addAll(entry.getValue());
//					}
//				}
//				for (Map.Entry<String, Set<String>> entry : hmapProjet.entrySet()) {
//					if (entry.getKey().equals(p.getCommune().getNomCommune())) {
//						listDesProjets.addAll(entry.getValue());
//					}
//				}
//
//				hmapProjet.put(p.getCommune().getNomCommune(), listDesProjets);
//				hmapType.put(p.getCommune().getNomCommune(), listTypeParRegion);
//				hmap.put(p.getCommune().getNomCommune(), listPart);
//				listPart = new HashSet<>();
//				listDesProjets = new HashSet<>();
//			}
//
//			for (Commune r : com) {
//				try {
//					listNombreRegions.add(r.getNomCommune());
//					location = geocodingService.search(r.getNomCommune(),
//							r.getDepartement().getRegion().getPays().getNomPays());
//					listLat.add(location.getString("lat"));
//					listLong.add(location.getString("lon"));
//					listName.add(r.getNomCommune());
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//			}
//
//			model.addAttribute("listTypeParRegion", hmapType);
//			model.addAttribute("nomDuType", nomDuType);
//			model.addAttribute("listPart", hmap);
//			model.addAttribute("listPartSize", listPartSize.size());
//			model.addAttribute("nombreRegions", listNombreRegions.size());
//			model.addAttribute("listDesProjets", hmapProjet);
//			model.addAttribute("listLong", listLong);
//			model.addAttribute("listName", listName);
//			model.addAttribute("listLat", listLat);
//			model.addAttribute("listLong", listLong);
//			model.addAttribute("codeCouleur", codeCouleur);
//			model.addAttribute("nombreProjets", projetService.projetParId(idProjet).get().getNomProjet());
//
//		}
//
//		return "cartographie";
//	}
//
//	@RequestMapping("cartographieType")
//	public String cartographieType(@ModelAttribute("unProjet") Projet unProjet,
//			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unType") Type unType, Model model,
//			String nomType, RedirectAttributes ra) {
//
//		HashMap<String, Set<String>> hmap = new HashMap<String, Set<String>>();
//		HashMap<String, Set<String>> hmapProjet = new HashMap<String, Set<String>>();
//		HashMap<String, Set<String>> hmapType = new HashMap<String, Set<String>>();
//		// List <ProjetGroupByNomProjet> listProjet = iCarto.groupByNomProjet();
//		Set<String> listDesProjets = new HashSet<>();
//		Set<String> listDesProjetsSize = new HashSet<>();
//		Set<String> listTypeParRegion = new HashSet<String>();
//		List<Projet> listProjet = projetService.tousLesProjets();
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listRegion = regionService.toutesLesRegions();
//		List<Type> listType = typeService.tousLesTypes();
//
//		model.addAttribute("listProjet", listProjet);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listRegion", listRegion);
//		model.addAttribute("listType", listType);
//		JSONObject location;
//		List<String> listLat = new ArrayList<>();
//		List<String> listLong = new ArrayList<>();
//		List<Long> listId = new ArrayList<>();
//		List<String> listName = new ArrayList<>();
//		String codeCouleur = null;
//		if (nomType != null) {
//			Set<Type> projetType = null;
//			Set<ProjetPartenaireRegion> typeName = new HashSet<ProjetPartenaireRegion>();
//
//			Type tp = typeService.findByNomType(nomType);
//			listTypeParRegion.add(tp.getCouleur());
//			codeCouleur = tp.getCouleur();
//
//			for (int i = 0; i < listProjet.size(); i++) {
//
//				projetType = listProjet.get(i).getType();
//
//				for (Type typ : projetType) {
//					if (tp.getNomType() == typ.getNomType()) {
//						typeName.addAll(pprService.findByIdProjet(listProjet.get(i).getIdProjet()));
//					}
//				}
//			}
//			Set<String> listPart = new HashSet<>();
//			Set<String> listPartSize = new HashSet<>();
//			// Set <Projet> lesProjets = new HashSet<>();
//
//			for (ProjetPartenaireRegion ppr : typeName) {
//
//				try {
//					location = geocodingService.search(ppr.getCommune().getNomCommune(),
//							ppr.getRegion().getPays().getNomPays());
//					listLat.add(location.getString("lat"));
//					listLong.add(location.getString("lon"));
//					listId.add(location.getLong("osm_id"));
//					listName.add(ppr.getCommune().getNomCommune());
//
//					listDesProjets.add(ppr.getProjet().getNomProjet());
//					listDesProjetsSize.add(ppr.getProjet().getNomProjet());
//					listPart.add(ppr.getPartenaire().getNomPartenaire());
//					listPartSize.add(ppr.getPartenaire().getNomPartenaire());
//					for (Map.Entry<String, Set<String>> entry : hmap.entrySet()) {
//						if (entry.getKey().equals(ppr.getCommune().getNomCommune())) {
//							listPart.addAll(entry.getValue());
//						}
//					}
//
//					for (Map.Entry<String, Set<String>> entry : hmapProjet.entrySet()) {
//						if (entry.getKey().equals(ppr.getCommune().getNomCommune())) {
//							listDesProjets.addAll(entry.getValue());
//						}
//					}
//					hmapProjet.put(ppr.getCommune().getNomCommune(), listDesProjets);
//					hmapType.put(ppr.getCommune().getNomCommune(), listTypeParRegion);
//					hmap.put(ppr.getCommune().getNomCommune(), listPart);
//					listPart = new HashSet<>();
//					listDesProjets = new HashSet<>();
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//			}
//
//			// List <String> prot = new ArrayList<>();
//			// for(Projet p:lesProjets)
//			// prot.add(p.getNomProjet());
//
//			model.addAttribute("listId", listId);
//			model.addAttribute("listLat", listLat);
//			model.addAttribute("listLong", listLong);
//			model.addAttribute("listName", listName);
//			model.addAttribute("listTypeParRegion", hmapType);
//			model.addAttribute("nomDuType", nomType);
//			model.addAttribute("codeCouleur", codeCouleur);
//			model.addAttribute("listPart", hmap);
//			model.addAttribute("listPartSize", listPartSize);
//			model.addAttribute("nombreProjets", listDesProjetsSize.size());
//			model.addAttribute("listDesProjets", hmapProjet);
//		}
//
//		return "cartographie";
//	}
//
//	@RequestMapping("cartographiePartenaire")
//	public String cartographiePartenaire(@ModelAttribute("unProjet") Projet unProjet,
//			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unType") Type unType, Model model,
//			String nomPartenaire, RedirectAttributes ra) {
//
//		HashMap<String, Set<String>> hmap = new HashMap<String, Set<String>>();
//		HashMap<String, Set<String>> hmapProjet = new HashMap<String, Set<String>>();
//		HashMap<String, Set<String>> hmapType = new HashMap<String, Set<String>>();
//		List<Projet> listProjet = projetService.tousLesProjets();
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listRegion = regionService.toutesLesRegions();
//		List<Type> listType = typeService.tousLesTypes();
//		model.addAttribute("listProjet", listProjet);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listRegion", listRegion);
//		model.addAttribute("listType", listType);
//		JSONObject location;
//		List<String> listLat = new ArrayList<>();
//		List<String> listLong = new ArrayList<>();
//		List<Long> listId = new ArrayList<>();
//		List<String> listName = new ArrayList<>();
//		Set<String> listPart = new HashSet<>();
//		Set<String> listPartSize = new HashSet<>();
//		Set<String> listDesProjets = new HashSet<>();
//		Set<String> listDesProjetsSize = new HashSet<>();
//		Set<Type> listCouleur = new HashSet<Type>();
//		Set<Type> nomType = new HashSet<>();
//		Set<String> listTypeParRegion = new HashSet<>();
//		Set<Projet> lesProjets = new HashSet<>();
//
//		if (nomPartenaire != null) {
//			Set<Partenaire> projetType = null;
//			Set<ProjetPartenaireRegion> typeName = new HashSet<ProjetPartenaireRegion>();
//			Partenaire parte = partenaireService.findByNomPartenaire(nomPartenaire);
//			for (int i = 0; i < listProjet.size(); i++) {
//
//				projetType = listProjet.get(i).getPartenaire();
//
//				for (Partenaire typ : projetType) {
//					if (parte.getNomPartenaire() == typ.getNomPartenaire()) {
//						typeName.addAll(pprService.findByIdProjet(listProjet.get(i).getIdProjet()));
//					}
//				}
//			}
//
//			for (ProjetPartenaireRegion ppr : typeName) {
//				try {
//					location = geocodingService.search(ppr.getCommune().getNomCommune(),
//							ppr.getRegion().getPays().getNomPays());
//					listLat.add(location.getString("lat"));
//					listLong.add(location.getString("lon"));
//					listId.add(location.getLong("osm_id"));
//
//					listName.add(ppr.getCommune().getNomCommune());
//
//					listDesProjets.add(ppr.getProjet().getNomProjet());
//
//					listDesProjetsSize.add(ppr.getProjet().getNomProjet());
//					listPart.add(ppr.getPartenaire().getNomPartenaire());
//
//					listPartSize.add(ppr.getPartenaire().getNomPartenaire());
//
//					listCouleur.addAll(ppr.getProjet().getType());
//					for (Type t : listCouleur) {
//						listTypeParRegion.add(t.getCouleur());
//					}
//					for (Map.Entry<String, Set<String>> entry : hmap.entrySet()) {
//						if (entry.getKey() == ppr.getCommune().getNomCommune()) {
//							listPart.addAll(entry.getValue());
//						}
//					}
//
//					for (Map.Entry<String, Set<String>> entry : hmapProjet.entrySet()) {
//						if (entry.getKey() == ppr.getCommune().getNomCommune()) {
//							listDesProjets.addAll(entry.getValue());
//						}
//					}
//					for (Map.Entry<String, Set<String>> entry : hmapType.entrySet()) {
//						if (entry.getKey() == ppr.getCommune().getNomCommune()) {
//							listTypeParRegion.addAll(entry.getValue());
//						}
//					}
//					hmap.put(ppr.getCommune().getNomCommune(), listPart);
//					hmapProjet.put(ppr.getCommune().getNomCommune(), listDesProjets);
//					hmapType.put(ppr.getCommune().getNomCommune(), listTypeParRegion);
//
//					listDesProjets = new HashSet<>();
//					listTypeParRegion = new HashSet<>();
//					listPart = new HashSet<>();
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			model.addAttribute("listId", listId);
//			model.addAttribute("listLat", listLat);
//			model.addAttribute("listLong", listLong);
//			model.addAttribute("listName", listName);
//			model.addAttribute("listPart", hmap);
//			model.addAttribute("listPartSize", nomPartenaire);
//			model.addAttribute("nomDuType", listCouleur.size());
//			model.addAttribute("nombreProjets", listDesProjetsSize.size());
//			model.addAttribute("listDesProjets", hmapProjet);
//			model.addAttribute("listTypeParRegion", hmapType);
//
//		}
//
//		return "cartographie";
//	}

//	@RequestMapping("sauvegarderLocalisation")
//	public String sauvegarderLocalisation(@Valid @ModelAttribute("uneRegion") Region uneRegion, BindingResult br,
//			String nomDepartement, String nomRegion, String nomCommune, String nomPays, RedirectAttributes ra) {
//		if (br.hasErrors()) {
//			return "localisation";
//		}
//		/*
//		 * List <Region> listRegion = iCarto.toutesLesRegions(); for(Region loc:listLoc)
//		 * { if(loc.getNomRegion().equals(libelleRegion)) {
//		 * ra.addFlashAttribute("messageDoublon", "Cette localisation existe déjà");
//		 * ra.addFlashAttribute("libelleLocalisation", libelleRegion); return
//		 * "redirect:/localisation"; } }
//		 */
//		List<Departement> listDepartement = departementService.tousLesDepartements();
//		ra.addFlashAttribute("listDepartement", listDepartement);
//
//		regionService.ajoutRegion(nomDepartement, nomRegion, nomPays, nomCommune);
//		return "redirect:/localisation";
//	}

//	@RequestMapping("sauvegarderPartenaire")
//	public String sauvegarderPartenaire(@Valid @ModelAttribute("unPartenaire") Partenaire unPartenaire,
//			BindingResult br, Long idPartenaire, String nomPartenaire, String adresse, RedirectAttributes ra) {
//
//		if (br.hasErrors()) {
//			return "nouveauPartenaire";
//		}
//		if (idPartenaire == null) {
//			List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//			for (Partenaire part : listPartenaire) {
//				if (part.getNomPartenaire().equals(nomPartenaire)) {
//					ra.addFlashAttribute("messageDoublon", "Ce partenaire existe déjà");
//					ra.addAttribute("nomPartenaire", nomPartenaire);
//					return "redirect:/nouveauPartenaire";
//				}
//			}
//
//		}
//
//		partenaireService.ajoutPartenaire(idPartenaire, nomPartenaire, adresse);
//		return "redirect:/partenaire";
//	}

//	@RequestMapping("sauvegarderProjet")
//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'AJOUTER_PROJET')")
//	public String sauvegarderProjet(@ModelAttribute("unProjet") Projet unProjet, @ModelAttribute("unType") Type unType,
//			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("uneRegion") Region uneRegion,
//			Model model, Long idProjet, String nomProjet, String pointFocal, String description, String nomType,
//			String statut, MultipartFile file, int dateDebut, int dateFin, String nomCategorie, RedirectAttributes ra)
//			throws IOException {
//
//		if (nomProjet.isBlank()) {
//
//			List<Type> listType = typeService.tousLesTypes();
//
//			model.addAttribute("listType", listType);
//			model.addAttribute("messageErreurNomProj", "Renseigner le nom du projet");
//			return "nouveauProjet";
//
//		}
//		if (nomType.isBlank()) {
//			List<Type> listType = typeService.tousLesTypes();
//			model.addAttribute("listType", listType);
//			model.addAttribute("messageErreurType", "Choisir le type du projet");
//			return "nouveauProjet";
//
//		}
//
//		if (idProjet == null) {
//			List<Projet> listProjet = projetService.tousLesProjets();
//			for (int i = 0; i < listProjet.size(); i++) {
//				if (nomProjet.equals(listProjet.get(i).getNomProjet())) {
//					List<Type> listType = typeService.tousLesTypes();
//					model.addAttribute("listType", listType);
//					model.addAttribute("messageDoublon", "Ce projet existe");
//					return "nouveauProjet";
//				}
//			}
//			projetService.ajoutProjet(nomProjet, pointFocal, description, nomType, file, statut, dateDebut, dateFin,
//					nomCategorie);
//			// ra.addFlashAttribute("flagEnregistrement", "1");
//			return "redirect:/nouveauProjet";
//		} else if (idProjet != null) {
//			projetService.modifierProjet(idProjet, nomProjet, pointFocal, description, nomType, file, statut, dateDebut,
//					dateFin, nomCategorie);
//			// ra.addFlashAttribute("flagModification", "1");
//			return "redirect:/listeProjet";
//		}
//
//		return "redirect:/projet";
//	}

	/*
	 * @RequestMapping("sauvegarderChoixPartenaire") public String
	 * sauvegarderChoixPartenaire(Model model, Long idProjet, String nomProjet,
	 * String nomDuPartenaire, String nomRegion,
	 * 
	 * @ModelAttribute("unProjet") Projet unProjet, String radioButtonSelected,
	 * RedirectAttributes ra) throws IOException{
	 * 
	 * 
	 * //String pointFocal=iCarto.projetParId(idProjet).get().getPointFocal();
	 * //String description= iCarto.projetParId(idProjet).get().getDescription();
	 * //int duree = iCarto.projetParId(idProjet).get().getDuree(); //String temps =
	 * iCarto.projetParId(idProjet).get().getTemps(); //String
	 * statut=iCarto.projetParId(idProjet).get().getStatut(); String type="";
	 * //MultipartFile file = new
	 * CustomMultipartFile(iCarto.projetParId(idProjet).get().getDataImage());
	 * if(idProjet!=null) { if(radioButtonSelected==null) {
	 * model.addAttribute("idProjet", idProjet); model.addAttribute("nomProjet",
	 * nomProjet); model.addAttribute("flag", "2"); return "choixPartenaire"; }
	 * if(radioButtonSelected.equals("rouge")) type="Egalité de genre"; else
	 * if(radioButtonSelected.equals("orange")) type =
	 * "Education / Formation professionnelle"; else if
	 * (radioButtonSelected.equals("jaune")) type = "Gouvernance"; }
	 * iCarto.ajoutPartenaireAuProjet(nomProjet, nomDuPartenaire, nomRegion, type);
	 * 
	 * model.addAttribute("idProjet", idProjet); model.addAttribute("nomProjet",
	 * nomProjet); model.addAttribute("flag", "1"); //return
	 * "redirect:/choixPartenaire?nomProjet="+nomProjet+"&idProjet="+idProjet;
	 * return "choixPartenaire"; }
	 */

//	@RequestMapping("supprimerPartenaire")
//	public String supprimerPartenaire(Long idPartenaire) {
//		partenaireService.supprimerPartenaire(idPartenaire);
//		return "redirect:/partenaire";
//	}
//
//	@RequestMapping("getDonneesPartenaireAModifier")
//	public String modifierPartenaire(@ModelAttribute("unPartenaire") Partenaire unPartenaire, Model model,
//			Long idPartenaire) {
//		model.addAttribute("idPartenaire", idPartenaire);
//		unPartenaire = partenaireService.findPartenaireById(idPartenaire).get();
//		model.addAttribute("unPartenaire", unPartenaire);
//		return "nouveauPartenaire";
//	}

//	@RequestMapping("nouveauProfil")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	public String nouveauProfil(@ModelAttribute("unProfil") Profil unProfil, Model model) {
//		List<EnumProfil> enumProfils = Arrays.asList(EnumProfil.values());
//		List<EnumPrivilege> enumPrivileges = Arrays.asList(EnumPrivilege.values());
//		Map<String, List<Privilege>> profilPrivilegeMap = new HashMap<>();
//		List<Profil> listProfils = profilService.tousLesProfils();
//		
//		for(Profil prof : listProfils) {		
//			profilPrivilegeMap.computeIfAbsent(prof.getNomProfil(), k-> new ArrayList<>())
//			  .addAll(prof.getPrivilege());			
//		}
//		
//		model.addAttribute("listProfil", profilService.tousLesProfils());
//		model.addAttribute("enumProfils", enumProfils);
//		model.addAttribute("enumPrivileges", enumPrivileges);
//		model.addAttribute("profilPrivilegeMap", profilPrivilegeMap);
//		return "nouveauProfil";
//	}
	
	@RequestMapping("getPrivilegeByProfil")
	@ResponseBody
	public Object getPrivilegeByProfil(Long idProfil) {
		List<Privilege> listPrivileges = privilegeService.findPrivilegeByProfil(idProfil);
		List<String> listNomPrivileges = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		for(Privilege p : listPrivileges) {
			listNomPrivileges.add(p.getNomPrivilege());
		}
		object.put("listNomPrivileges", listNomPrivileges);
		return object;
	}

//	@RequestMapping("sauvegarderProfil")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	public String sauvegarderProfil(@Valid @ModelAttribute("unProfil") Profil unProfil, BindingResult br, Model model,
//			String nomProfil, RedirectAttributes ra) {
//		List<EnumPrivilege> enumPrivileges = Arrays.asList(EnumPrivilege.values());
//		if (br.hasErrors()) {
//			List<Profil> listProfil = profilService.tousLesProfils();
//			model.addAttribute("listProfil", listProfil);
//			model.addAttribute("enumPrivileges", enumPrivileges);
//			return "nouveauProfil";
//		}
//
//		List<Profil> listProfil = profilService.tousLesProfils();
//		for (Profil prof : listProfil) {
//			if (prof.getNomProfil().equals(nomProfil)) {
//
//				ra.addFlashAttribute("messageDoublon", "Ce profil existe déjà");
//				ra.addFlashAttribute("nomProfil", nomProfil);
//				return "redirect:/nouveauProfil";
//			}
//		}
//		profilService.ajoutProfil(nomProfil);
//		return "redirect:/nouveauProfil";
//	}

	@RequestMapping("utilisateur")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String utilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur, Model model) {
		List<Utilisateur> listUtilisateur = utilisateurService.tousLesUtilisateurs();
		model.addAttribute("listUtilisateur", listUtilisateur);
		return "utilisateur";
	}

	@RequestMapping("nouvelUtilisateur")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String nouvelUtilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur,
			@ModelAttribute("unProfil") Profil unProfil, Model model) {
		List<Profil> listProfil = profilService.tousLesProfils();
		model.addAttribute("listProfil", listProfil);
		return "nouvelUtilisateur";
	}

	@RequestMapping("sauvegarderUtilisateur")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String sauvegarderUtilisateur(@Valid @ModelAttribute("unUtilisateur") Utilisateur unUtilisateur,
			BindingResult br, @ModelAttribute("unProfil") Profil unProfil, Model model, Long idUtilisateur,
			String nomUtilisateur, String prenomUtilisateur, String login, String password, String nomProfil,
			RedirectAttributes ra) {

		if (br.hasErrors()) {
			List<Profil> listProfil = profilService.tousLesProfils();
			model.addAttribute("listProfil", listProfil);
			return "nouvelUtilisateur";
		}
		if (idUtilisateur == null) {
			List<Utilisateur> listUtil = utilisateurService.tousLesUtilisateurs();
			for (Utilisateur util : listUtil) {
				if (util.getLogin().equals(login)) {
					List<Profil> listProfil = profilService.tousLesProfils();
					model.addAttribute("listProfil", listProfil);
					model.addAttribute("messageDoublon", "Ce login existe déjà");
					return "nouvelUtilisateur";
				}
			}
		}
		utilisateurService.ajoutUtilisateur(idUtilisateur, nomUtilisateur, prenomUtilisateur, login, password, nomProfil);
		return "redirect:/utilisateur";
	}

	@RequestMapping("supprimerUtilisateur")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String supprimerUtilisateur(Long idUtilisateur) {
		utilisateurService.supprimerUtilisateur(idUtilisateur);
		return "redirect:/utilisateur";
	}

	@RequestMapping("getDonneesUtilisateurAModifier")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getDonneesUtilisateurAModifier(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur,
			@ModelAttribute("unProfil") Profil unProfil, Model model, Long idUtilisateur) {

		unUtilisateur = utilisateurService.findUtilisateurById(idUtilisateur).get();
		model.addAttribute("unUtilisateur", unUtilisateur);
		List<Profil> listProfil = profilService.tousLesProfils();
		model.addAttribute("listProfil", listProfil);
		model.addAttribute("flagProfil", utilisateurService.findUtilisateurById(idUtilisateur).get().getProfil());

		return "nouvelUtilisateur";
	}

	@RequestMapping("supprimerRegion")
	public String supprimerRegion(Long idRegion) {
		regionService.supprimerRegion(idRegion);
		return "redirect:/localisation";
	}

	@RequestMapping("supprimerCommune")
	public String supprimerCommune(Long idCommune) {
		communeService.supprimerCommune(idCommune);
		return "redirect:/localisation";
	}

//	@RequestMapping("parametres")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	public String parametres() {
//		return "parametres";
//	}

//	@RequestMapping("supprimerProfil")
//	public String supprimerProfil(Long idProfil) {
//		profilService.supprimerProfil(idProfil);
//		return "redirect:/nouveauProfil";
//	}

//	@RequestMapping("appErreur")
//	public String appErreur() {
//		return "appErreur";
//	}

//	@RequestMapping("listeProjet")
//	public String listeProjet(Model model) {
//		List<Projet> listProjet = projetService.tousLesProjets();
//		model.addAttribute("listProjet", listProjet);
//		return "listeProjet";
//	}
//
//	@RequestMapping("supprimerProjet")
//	public String supprimerProjet(Long idProjet) {
//		projetService.supprimerProjet(idProjet);
//		return "redirect:/listeProjet";
//	}

//	@RequestMapping("getDonneesProjetAModifier")
//	public String getDonneesProjetAModifier(@ModelAttribute("unProjet") Projet unProjet,
//			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unType") Type unType,
//			@ModelAttribute("uneRegion") Region uneRegion, @ModelAttribute("uneCategorie") Categorie uneCategorie,
//			Model model, Long idProjet) {
//
//		unProjet = projetService.projetParId(idProjet).get();
//		Set<Type> type = unProjet.getType();
//		for (Type t : type) {
//			unType = typeService.findByIdType(t.getIdType());
//		}
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Type> listType = typeService.tousLesTypes();
//		Set<Categorie> categorie = unProjet.getCategorie();
//
//		for (Categorie cat : categorie) {
//			uneCategorie = categorieService.findByIdCategorie(cat.getIdCategorie());
//
//		}
//
//		List<Categorie> listCategorie = categorieService.toutesLesCategories();
//		model.addAttribute("unProjet", unProjet);
//		model.addAttribute("unType", unType);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listType", listType);
//		model.addAttribute("listCategorie", listCategorie);
//		model.addAttribute("uneCategorie", uneCategorie);
//
//		return "nouveauProjet";
//	}

//	@RequestMapping("type")
//	public String type(@ModelAttribute("unType") Type unType, Model model) {
//		List<Type> listType = typeService.tousLesTypes();
//		model.addAttribute("listType", listType);
//
//		return "type";
//	}
//
//	@RequestMapping("sauvegarderType")
//	public String sauvegarderType(@ModelAttribute("unType") Type unType, Model model, String nomType, String couleur) {
//		typeService.ajoutType(nomType, couleur);
//		return "redirect:/type";
//	}
//
//	@RequestMapping("supprimerType")
//	public String supprimerType(@ModelAttribute("unType") Type unType, Long idType) {
//		typeService.supprimerType(idType);
//		return "redirect:/type";
//	}

//	@RequestMapping("liaisonPartenaireProjet")
//	public String projetPartenaireRegionLiaison(
//			@ModelAttribute("projetPartenaireDTO") ProjetPartenaireDTO projetPartenaireDTO, Model model) {
//		List<Projet> listProjet = projetService.tousLesProjets();
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listRegion = regionService.toutesLesRegions();
//		List<Departement> listDepartement = departementService.tousLesDepartements();
//		List<PartenaireLocal> listPartenaireLocal = parteLocalService.tousLesPartenairesLocaux();
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listProjet", listProjet);
//		model.addAttribute("listRegion", listRegion);
//		model.addAttribute("listDepartement", listDepartement);
//		model.addAttribute("listPartenaireLocal", listPartenaireLocal);
//
//		return "liaisonPartenaireProjet";
//	}

//	@RequestMapping("lierPartenaire")
//	public String projetPartenaireRegion(@ModelAttribute ProjetPartenaireDTO projetPartenaireDTO, Model model) {
//
//		List<Projet> listProjet = projetService.tousLesProjets();
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listRegion = regionService.toutesLesRegions();
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listProjet", listProjet);
//		model.addAttribute("listRegion", listRegion);
//
//		if (projetPartenaireDTO.getNomCommune() == null) {
//			model.addAttribute("messageErreur", "Renseigner la commune");
//			return "liaisonPartenaireProjet";
//		}
//		String nomProjet = projetPartenaireDTO.getNomProjet();
//		String[] nomCommune = projetPartenaireDTO.getNomCommune();
//		String[] nomPartenaire = projetPartenaireDTO.getNomPartenaire();
//		String[] nomRegion = projetPartenaireDTO.getNomRegion();
//		String[] nomDepartement = projetPartenaireDTO.getNomDepartement();
//		String[] nomVillage = projetPartenaireDTO.getNomVillage();
//		String[] latitude = projetPartenaireDTO.getLatitude();
//		String[] longitude = projetPartenaireDTO.getLongitude();
//		String[] nomPartenaireLocal = projetPartenaireDTO.getNomPartenaireLocal();
//
//		if (nomCommune.length >= 1) {
//			if (nomPartenaireLocal.length == 0) {
//				// for(int p=0; p < nomPartenaire.length; p++) {
//				for (int i = 0; i < nomCommune.length; i++) {
//					if (nomVillage.length == 0) {
//						if (communeService.findByNomCommune(nomCommune[i]) != null) {
//							Commune comm = communeService.findByNomCommune(nomCommune[i]);
//							for (int b = 0; b < nomDepartement.length; b++) {
//								if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
//									for (int c = 0; c < nomRegion.length; c++) {
//										if (comm.getDepartement().getRegion().getNomRegion().equals(nomRegion[c])) {
//											projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c], nomRegion[c],
//													nomDepartement[b], nomCommune[i], null, null, null, null);
//
//										}
//									}
//
//								}
//
//							}
//
//						}
//
//					} else {
//						if (latitude.length == 0 || longitude.length == 0) {
//							if (communeService.findByNomCommune(nomCommune[i]) != null) {
//								Commune comm = communeService.findByNomCommune(nomCommune[i]);
//								for (int b = 0; b < nomDepartement.length; b++) {
//									if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
//										for (int c = 0; c < nomRegion.length; c++) {
//											if (comm.getDepartement().getRegion().getNomRegion().equals(nomRegion[c])) {
//												projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
//														nomRegion[c], nomDepartement[b], nomCommune[i], nomVillage[i],
//														null, null, null);
//
//											}
//										}
//
//									}
//
//								}
//
//							}
//
//						} else {
//
//							if (communeService.findByNomCommune(nomCommune[i]) != null) {
//								Commune comm = communeService.findByNomCommune(nomCommune[i]);
//								for (int b = 0; b < nomDepartement.length; b++) {
//									if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
//										for (int c = 0; c < nomRegion.length; c++) {
//											if (comm.getDepartement().getRegion().getNomRegion().equals(nomRegion[c])) {
//												projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
//														nomRegion[c], nomDepartement[b], nomCommune[i], nomVillage[i],
//														latitude[i], longitude[i], null);
//
//											}
//										}
//
//									}
//
//								}
//
//							}
//
//						}
//					}
//				}
//
//			} else {
//
//				for (int i = 0; i < nomCommune.length; i++) {
//					for (int j = 0; j < nomPartenaireLocal.length; j++) {
//						if (nomVillage.length == 0) {
//							if (communeService.findByNomCommune(nomCommune[i]) != null) {
//								Commune comm = communeService.findByNomCommune(nomCommune[i]);
//								for (int b = 0; b < nomDepartement.length; b++) {
//									if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
//										for (int c = 0; c < nomRegion.length; c++) {
//											if (comm.getDepartement().getRegion().getNomRegion().equals(nomRegion[c])) {
//												projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
//														nomRegion[c], nomDepartement[b], nomCommune[i], null, null,
//														null, nomPartenaireLocal[j]);
//
//											}
//										}
//
//									}
//
//								}
//
//							}
//
//						} else {
//							if (latitude.length == 0 || longitude.length == 0) {
//								if (communeService.findByNomCommune(nomCommune[i]) != null) {
//									Commune comm = communeService.findByNomCommune(nomCommune[i]);
//									for (int b = 0; b < nomDepartement.length; b++) {
//										if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
//											for (int c = 0; c < nomRegion.length; c++) {
//												if (comm.getDepartement().getRegion().getNomRegion()
//														.equals(nomRegion[c])) {
//													projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
//															nomRegion[c], nomDepartement[b], nomCommune[i],
//															nomVillage[i], null, null, nomPartenaireLocal[j]);
//
//												}
//											}
//
//										}
//
//									}
//
//								}
//
//							} else {
//
//								if (communeService.findByNomCommune(nomCommune[i]) != null) {
//									Commune comm = communeService.findByNomCommune(nomCommune[i]);
//									for (int b = 0; b < nomDepartement.length; b++) {
//										if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
//											for (int c = 0; c < nomRegion.length; c++) {
//												if (comm.getDepartement().getRegion().getNomRegion()
//														.equals(nomRegion[c])) {
//													projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
//															nomRegion[c], nomDepartement[b], nomCommune[i],
//															nomVillage[i], latitude[i], longitude[i],
//															nomPartenaireLocal[j]);
//												}
//											}
//
//										}
//
//									}
//
//								}
//
//							}
//						}
//					}
//
//				}
//			}
//		}
//
//		Projet unProjet = projetService.findByNomProjet(projetPartenaireDTO.getNomProjet());
//
//		model.addAttribute("idProjet", projetPartenaireDTO.getNomProjet());
//		model.addAttribute("nomProjet", projetPartenaireDTO.getNomProjet());
//		model.addAttribute("flag", "Succes");
//		model.addAttribute("pageAAfficher", "choixPartenaire");
//		model.addAttribute("unProjet", unProjet);
//		return "choixPartenaire";
//
//	}

//	@RequestMapping("categorie")
//	public String categorie(@ModelAttribute("uneCategorie") Categorie uneCategorie, Model model) {
//		List<Categorie> listCategorie = categorieService.toutesLesCategories();
//		model.addAttribute("listCategorie", listCategorie);
//		return "categorie";
//	}

//	@RequestMapping("sauvegarderCategorie")
//	public String sauvegarderCategorie(@Valid @ModelAttribute("uneCategorie") Categorie uneCategorie, BindingResult br,
//			Model model, String nomCategorie) {
//		List<Categorie> listCategorie = categorieService.toutesLesCategories();
//
//		if (br.hasErrors()) {
//			model.addAttribute("listCategorie", listCategorie);
//			return "categorie";
//		}
//		if (categorieService.findByNomCategorie(nomCategorie) != null) {
//			model.addAttribute("messageDoublon", "Cette catégorie existe");
//			return "categorie";
//		}
//		categorieService.ajoutCategorie(nomCategorie);
//		return "redirect:/categorie";
//	}

//	@RequestMapping("/getDepartement")
//	@ResponseBody
//	public Set<Departement> getDepartement(String nomRegion) {
//
//		if (departementService.findDepartementByNomRegion(nomRegion) == null) {
//			return null;
//		}
//		// Map<String, Object> object = new HashMap<>();
//		List<Departement> listDep = departementService.findDepartementByNomRegion(nomRegion);
//		// for(Departement d:listDep)
//		// object.put(d.getNomDepartement(), d.getRegion().getNomRegion());
//		// System.out.println(listDep);
//		Set<Departement> listDepSet = new HashSet<>(listDep);
//
//		return listDepSet;
//
//	}

//	@RequestMapping("/getCommune")
//	@ResponseBody
//	public Object getCommune(String nomDepartement) {
//
//		if (communeService.findCommuneByNomDepartement(nomDepartement) == null) {
//			return null;
//		}
//		List<Commune> listCom = communeService.findCommuneByNomDepartement(nomDepartement);
//
//		return listCom;
//
//	}

//	@RequestMapping("/getVillage")
//	@ResponseBody
//	public Object getVillage(String nomCommune) {
//
//		if (villageService.findVillageByNomCommune(nomCommune) == null) {
//			return null;
//		}
//		List<Village> listVil = villageService.findVillageByNomCommune(nomCommune);
//
//		return listVil;
//
//	}

//	@RequestMapping("lierPartenaireLocalAPartenaire")
//	public String lierPartenaireLocalAPartenaire(String nomPartenaireLocal, Long idPartenaire, Model model) {
//
//		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
//		List<Region> listLocalisation = regionService.toutesLesRegions();
//		List<PartenaireLocal> listPartenaireLocal = parteLocalService.tousLesPartenairesLocaux();
//		model.addAttribute("listPartenaireLocal", listPartenaireLocal);
//		model.addAttribute("listPartenaire", listPartenaire);
//		model.addAttribute("listLocalisation", listLocalisation);
//
//		if (nomPartenaireLocal.isEmpty()) {
//			model.addAttribute("messageErreur", "Le partenaire communautaire n'est pas renseigné");
//			return "partenaire";
//		}
//
//		if (partenaireService.findPartenaireById(idPartenaire) != null) {
//			Partenaire part = partenaireService.findPartenaireByIdPartenaire(idPartenaire);
//			if (part.getPartenaireLocal() != null) {
//				Set<PartenaireLocal> paLo = part.getPartenaireLocal();
//				for (PartenaireLocal pc : paLo) {
//					if (pc.getNomPartenaireLocal().equals(nomPartenaireLocal)) {
//						model.addAttribute("messageErreur",
//								part.getNomPartenaire() + " est déjà lié à " + nomPartenaireLocal);
//						return "partenaire";
//					}
//				}
//			}
//		}
//
//		parteLocalService.ajoutPartenaireLocalAPartenaire(nomPartenaireLocal, idPartenaire);
//
//		return "redirect:/partenaire";
//
//	}

//	@RequestMapping("getDonneesProjetAAjouterAUtilisateur")
//	public String getDonneesProjetAAjouterAUtilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur,
//			@ModelAttribute("unProjet") Projet unProjet, Model model, Long idUtilisateur) {
//
//		unUtilisateur = utilisateurService.findUtilisateurById(idUtilisateur).get();
//		model.addAttribute("unUtilisateur", unUtilisateur);
//		List<Projet> listProjet = projetService.tousLesProjets();
//		model.addAttribute("listProjet", listProjet);
//		// model.addAttribute("flagProfil",
//		// iCarto.findUtilisateurById(idUtilisateur).get().getProfil());
//
//		return "projetUtilisateur";
//	}

//	@RequestMapping("/sauvegarderProjetUtilisateur")
//	public String sauvegarderProjetUtilisateur(@ModelAttribute("unUtilisateur") Utilisateur unUtilisateur,
//			@ModelAttribute("unProjet") Projet unProjet, Model model, Long idUtilisateur, String nomProjet) {
//		unUtilisateur = utilisateurService.findUtilisateurById(idUtilisateur).get();
//		List<Projet> listProjet = projetService.tousLesProjets();
//
//		projetService.ajoutProjetAUtilisateur(idUtilisateur, nomProjet);
//		model.addAttribute("unUtilisateur", unUtilisateur);
//		model.addAttribute("listProjet", listProjet);
//		return "projetUtilisateur";
//	}
//
//	@RequestMapping("/detailsProjet")
//	public String detailsProjet(@ModelAttribute("unProjet") Projet unProjet, Model model, Long idProjet,
//			String nomProjet) {
//
//		Projet projet = projetService.trouverProjetParIdProjet(idProjet);
//		model.addAttribute("listProjet", projetService.tousLesProjets());
//		model.addAttribute("unProjet", projet);
//		return "detailsProjet";
//	}

	@RequestMapping("cloturerProjet")
	public String cloturerProjet(Long idProjet) {
		projetService.cloturerProjet(idProjet);
		return "redirect:/detailsProjet?idProjet=" + idProjet;
	}

	@RequestMapping("operations")
	public String UserOperations(Model model, String login) {

		List<Utilisateur> userOperations = utilisateurService.getAllOperationsOfUser(login);
		model.addAttribute("userOperations", userOperations);
		return "userOperation";
	}

	@RequestMapping("sauvegarderPrivilege")
	public String sauvegarderPrivilege(Model model, Long idProfil, String checkboxPrivilege[]) {

		privilegeService.ajoutPrivilege(idProfil, checkboxPrivilege);
		return "redirect:nouveauProfil";

	}
	@RequestMapping("enableOrDisableUserAccount")
	@ResponseBody
	public String enableOrDisableUserAccount(Long idUtilisateur, boolean isEnabled) {
		utilisateurService.enableOrDisableUserAccount(idUtilisateur, isEnabled);
		return "redirect:utilisateur";
	}
	
	@RequestMapping("supprimerCategorie")
	public String supprimerCategorie(Long idCategorie) {
		categorieService.supprimerCategorie(idCategorie);
		return "redirect:/categorie";
	}
}
