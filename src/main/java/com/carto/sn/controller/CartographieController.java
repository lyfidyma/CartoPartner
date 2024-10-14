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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Commune;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetPartenaireRegion;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.service.GeocodingService;
import com.carto.sn.service.PartenaireService;
import com.carto.sn.service.ProjetPartenaireRegionService;
import com.carto.sn.service.ProjetService;
import com.carto.sn.service.RegionService;
import com.carto.sn.service.TypeService;

@Controller
@PreAuthorize("isAuthenticated()")
public class CartographieController {
	
	@Autowired
	private ProjetPartenaireRegionService pprService;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private PartenaireService partenaireService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private GeocodingService geocodingService;

	@RequestMapping("cartographie")
	public String cartographie(@ModelAttribute("unProjet") Projet unProjet, @ModelAttribute("unType") Type unType,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, Model model, Long idProjet, String type,
			String departement, String region, RedirectAttributes ra) {

		List<ProjetPartenaireRegion> listPpr = pprService.tousLesProjetsPartenairesRegions();
		List<Projet> listProjet = projetService.tousLesProjets();
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listRegion = regionService.toutesLesRegions();
		List<Type> listType = typeService.tousLesTypes();

		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listRegion", listRegion);
		model.addAttribute("listType", listType);
		JSONObject location;
		List<String> listLat = new ArrayList<>();
		List<String> listLong = new ArrayList<>();
		List<String> listName = new ArrayList<>();
		Set<String> listPart = new HashSet<>();
		Set<String> listPartSize = new HashSet<>();
		// HashMap <String, List<String>> listTypeRegion = new HashMap<String,
		// List<String>>();
		// List<String> uneListeType = new ArrayList<>();
		Set<Commune> listProjetsEtRegions = new HashSet<>();
		// Set <Type> listProjetsEtType = null;
		HashSet<String> listNombreRegions = new HashSet<String>();
		Set<Projet> listNombreProjets = new HashSet<>();

		if (idProjet == null) {
			HashMap<String, Set<String>> hmap = new HashMap<String, Set<String>>();
			HashMap<String, Set<String>> hmapProjet = new HashMap<String, Set<String>>();
			HashMap<String, Set<String>> hmapType = new HashMap<String, Set<String>>();
			Set<String> listDesProjets = new HashSet<>();
			Set<String> listDesProjetsSize = new HashSet<>();
			Set<String> listTypeParRegion = new HashSet<String>();
			Set<Type> listCouleur = new HashSet<Type>();
			for (int i = 0; i < listPpr.size(); i++) {

				listProjetsEtRegions.add(listPpr.get(i).getCommune());
				listDesProjets.add(listPpr.get(i).getProjet().getNomProjet());

				listDesProjetsSize.add(listPpr.get(i).getProjet().getNomProjet());
				listPart.add(listPpr.get(i).getPartenaire().getNomPartenaire());

				listPartSize.add(listPpr.get(i).getPartenaire().getNomPartenaire());

				listCouleur.addAll(listPpr.get(i).getProjet().getType());
				for (Type t : listCouleur) {
					listTypeParRegion.add(t.getCouleur());
				}
				for (Map.Entry<String, Set<String>> entry : hmap.entrySet()) {
					if (entry.getKey() == listPpr.get(i).getCommune().getNomCommune()) {
						listPart.addAll(entry.getValue());
					}
				}

				for (Map.Entry<String, Set<String>> entry : hmapProjet.entrySet()) {
					if (entry.getKey() == listPpr.get(i).getCommune().getNomCommune()) {
						listDesProjets.addAll(entry.getValue());
					}
				}
				for (Map.Entry<String, Set<String>> entry : hmapType.entrySet()) {
					if (entry.getKey() == listPpr.get(i).getCommune().getNomCommune()) {
						listTypeParRegion.addAll(entry.getValue());
					}
				}
				hmap.put(listPpr.get(i).getCommune().getNomCommune(), listPart);
				hmapProjet.put(listPpr.get(i).getCommune().getNomCommune(), listDesProjets);
				hmapType.put(listPpr.get(i).getCommune().getNomCommune(), listTypeParRegion);

				listDesProjets = new HashSet<>();
				listTypeParRegion = new HashSet<>();
				listPart = new HashSet<>();
			}
			for (Commune r : listProjetsEtRegions) {

				try {
					listNombreRegions.add(r.getNomCommune());
					location = geocodingService.search(r.getNomCommune(),
							r.getDepartement().getRegion().getPays().getNomPays());
					listLat.add(location.getString("lat"));
					listLong.add(location.getString("lon"));
					listName.add(r.getNomCommune());
					listNombreProjets.addAll(r.getDepartement().getRegion().getProjet());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			model.addAttribute("listTypeParRegion", hmapType);
			model.addAttribute("listDesProjets", hmapProjet);
			model.addAttribute("nombreProjets", "tous les projets, " + listDesProjetsSize.size());
			model.addAttribute("nombreRegions", listNombreRegions.size());
			model.addAttribute("listName", listName);
			model.addAttribute("listLat", listLat);
			model.addAttribute("listLong", listLong);
			model.addAttribute("listPart", hmap);
			model.addAttribute("listPartSize", listPartSize.size());
			model.addAttribute("nomDuType", listType.size());

		}

		if (idProjet != null) {
			HashMap<String, Set<String>> hmap = new HashMap<String, Set<String>>();
			HashMap<String, Set<String>> hmapProjet = new HashMap<String, Set<String>>();
			HashMap<String, Set<String>> hmapType = new HashMap<String, Set<String>>();
			Set<String> listTypeParRegion = new HashSet<String>();
			String nomDuType = null;
			String codeCouleur = null;
			Set<Commune> com = new HashSet<>();
			Set<String> listDesProjets = new HashSet<>();
			Set<String> listDesProjetsSize = new HashSet<>();

			List<ProjetPartenaireRegion> proj = pprService.findByIdProjet(idProjet);
			Set<Type> typ = projetService.projetParId(idProjet).get().getType();
			for (Type t : typ) {
				listTypeParRegion.add(t.getCouleur());
				nomDuType = t.getNomType();
				codeCouleur = t.getCouleur();
			}

			for (ProjetPartenaireRegion p : proj) {
				com.add(p.getCommune());
				listPart.add(p.getPartenaire().getNomPartenaire());
				listPartSize.add(p.getPartenaire().getNomPartenaire());
				listDesProjets.add(p.getProjet().getNomProjet());
				listDesProjetsSize.add(p.getProjet().getNomProjet());
				for (Map.Entry<String, Set<String>> entry : hmap.entrySet()) {
					if (entry.getKey().equals(p.getCommune().getNomCommune())) {
						listPart.addAll(entry.getValue());
					}
				}
				for (Map.Entry<String, Set<String>> entry : hmapProjet.entrySet()) {
					if (entry.getKey().equals(p.getCommune().getNomCommune())) {
						listDesProjets.addAll(entry.getValue());
					}
				}

				hmapProjet.put(p.getCommune().getNomCommune(), listDesProjets);
				hmapType.put(p.getCommune().getNomCommune(), listTypeParRegion);
				hmap.put(p.getCommune().getNomCommune(), listPart);
				listPart = new HashSet<>();
				listDesProjets = new HashSet<>();
			}

			for (Commune r : com) {
				try {
					listNombreRegions.add(r.getNomCommune());
					location = geocodingService.search(r.getNomCommune(),
							r.getDepartement().getRegion().getPays().getNomPays());
					listLat.add(location.getString("lat"));
					listLong.add(location.getString("lon"));
					listName.add(r.getNomCommune());

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			model.addAttribute("listTypeParRegion", hmapType);
			model.addAttribute("nomDuType", nomDuType);
			model.addAttribute("listPart", hmap);
			model.addAttribute("listPartSize", listPartSize.size());
			model.addAttribute("nombreRegions", listNombreRegions.size());
			model.addAttribute("listDesProjets", hmapProjet);
			model.addAttribute("listLong", listLong);
			model.addAttribute("listName", listName);
			model.addAttribute("listLat", listLat);
			model.addAttribute("listLong", listLong);
			model.addAttribute("codeCouleur", codeCouleur);
			model.addAttribute("nombreProjets", projetService.projetParId(idProjet).get().getNomProjet());

		}

		return "cartographie";
	}

	@RequestMapping("cartographieType")
	public String cartographieType(@ModelAttribute("unProjet") Projet unProjet,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unType") Type unType, Model model,
			String nomType, RedirectAttributes ra) {

		HashMap<String, Set<String>> hmap = new HashMap<String, Set<String>>();
		HashMap<String, Set<String>> hmapProjet = new HashMap<String, Set<String>>();
		HashMap<String, Set<String>> hmapType = new HashMap<String, Set<String>>();
		// List <ProjetGroupByNomProjet> listProjet = iCarto.groupByNomProjet();
		Set<String> listDesProjets = new HashSet<>();
		Set<String> listDesProjetsSize = new HashSet<>();
		Set<String> listTypeParRegion = new HashSet<String>();
		List<Projet> listProjet = projetService.tousLesProjets();
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listRegion = regionService.toutesLesRegions();
		List<Type> listType = typeService.tousLesTypes();

		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listRegion", listRegion);
		model.addAttribute("listType", listType);
		JSONObject location;
		List<String> listLat = new ArrayList<>();
		List<String> listLong = new ArrayList<>();
		List<Long> listId = new ArrayList<>();
		List<String> listName = new ArrayList<>();
		String codeCouleur = null;
		if (nomType != null) {
			Set<Type> projetType = null;
			Set<ProjetPartenaireRegion> typeName = new HashSet<ProjetPartenaireRegion>();

			Type tp = typeService.findByNomType(nomType);
			listTypeParRegion.add(tp.getCouleur());
			codeCouleur = tp.getCouleur();

			for (int i = 0; i < listProjet.size(); i++) {

				projetType = listProjet.get(i).getType();

				for (Type typ : projetType) {
					if (tp.getNomType() == typ.getNomType()) {
						typeName.addAll(pprService.findByIdProjet(listProjet.get(i).getIdProjet()));
					}
				}
			}
			Set<String> listPart = new HashSet<>();
			Set<String> listPartSize = new HashSet<>();
			// Set <Projet> lesProjets = new HashSet<>();

			for (ProjetPartenaireRegion ppr : typeName) {

				try {
					location = geocodingService.search(ppr.getCommune().getNomCommune(),
							ppr.getRegion().getPays().getNomPays());
					listLat.add(location.getString("lat"));
					listLong.add(location.getString("lon"));
					listId.add(location.getLong("osm_id"));
					listName.add(ppr.getCommune().getNomCommune());

					listDesProjets.add(ppr.getProjet().getNomProjet());
					listDesProjetsSize.add(ppr.getProjet().getNomProjet());
					listPart.add(ppr.getPartenaire().getNomPartenaire());
					listPartSize.add(ppr.getPartenaire().getNomPartenaire());
					for (Map.Entry<String, Set<String>> entry : hmap.entrySet()) {
						if (entry.getKey().equals(ppr.getCommune().getNomCommune())) {
							listPart.addAll(entry.getValue());
						}
					}

					for (Map.Entry<String, Set<String>> entry : hmapProjet.entrySet()) {
						if (entry.getKey().equals(ppr.getCommune().getNomCommune())) {
							listDesProjets.addAll(entry.getValue());
						}
					}
					hmapProjet.put(ppr.getCommune().getNomCommune(), listDesProjets);
					hmapType.put(ppr.getCommune().getNomCommune(), listTypeParRegion);
					hmap.put(ppr.getCommune().getNomCommune(), listPart);
					listPart = new HashSet<>();
					listDesProjets = new HashSet<>();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			// List <String> prot = new ArrayList<>();
			// for(Projet p:lesProjets)
			// prot.add(p.getNomProjet());

			model.addAttribute("listId", listId);
			model.addAttribute("listLat", listLat);
			model.addAttribute("listLong", listLong);
			model.addAttribute("listName", listName);
			model.addAttribute("listTypeParRegion", hmapType);
			model.addAttribute("nomDuType", nomType);
			model.addAttribute("codeCouleur", codeCouleur);
			model.addAttribute("listPart", hmap);
			model.addAttribute("listPartSize", listPartSize);
			model.addAttribute("nombreProjets", listDesProjetsSize.size());
			model.addAttribute("listDesProjets", hmapProjet);
		}

		return "cartographie";
	}

	@RequestMapping("cartographiePartenaire")
	public String cartographiePartenaire(@ModelAttribute("unProjet") Projet unProjet,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unType") Type unType, Model model,
			String nomPartenaire, RedirectAttributes ra) {

		HashMap<String, Set<String>> hmap = new HashMap<String, Set<String>>();
		HashMap<String, Set<String>> hmapProjet = new HashMap<String, Set<String>>();
		HashMap<String, Set<String>> hmapType = new HashMap<String, Set<String>>();
		List<Projet> listProjet = projetService.tousLesProjets();
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listRegion = regionService.toutesLesRegions();
		List<Type> listType = typeService.tousLesTypes();
		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listRegion", listRegion);
		model.addAttribute("listType", listType);
		JSONObject location;
		List<String> listLat = new ArrayList<>();
		List<String> listLong = new ArrayList<>();
		List<Long> listId = new ArrayList<>();
		List<String> listName = new ArrayList<>();
		Set<String> listPart = new HashSet<>();
		Set<String> listPartSize = new HashSet<>();
		Set<String> listDesProjets = new HashSet<>();
		Set<String> listDesProjetsSize = new HashSet<>();
		Set<Type> listCouleur = new HashSet<Type>();
		Set<Type> nomType = new HashSet<>();
		Set<String> listTypeParRegion = new HashSet<>();
		Set<Projet> lesProjets = new HashSet<>();

		if (nomPartenaire != null) {
			Set<Partenaire> projetType = null;
			Set<ProjetPartenaireRegion> typeName = new HashSet<ProjetPartenaireRegion>();
			Partenaire parte = partenaireService.findByNomPartenaire(nomPartenaire);
			for (int i = 0; i < listProjet.size(); i++) {

				projetType = listProjet.get(i).getPartenaire();

				for (Partenaire typ : projetType) {
					if (parte.getNomPartenaire() == typ.getNomPartenaire()) {
						typeName.addAll(pprService.findByIdProjet(listProjet.get(i).getIdProjet()));
					}
				}
			}

			for (ProjetPartenaireRegion ppr : typeName) {
				try {
					location = geocodingService.search(ppr.getCommune().getNomCommune(),
							ppr.getRegion().getPays().getNomPays());
					listLat.add(location.getString("lat"));
					listLong.add(location.getString("lon"));
					listId.add(location.getLong("osm_id"));

					listName.add(ppr.getCommune().getNomCommune());

					listDesProjets.add(ppr.getProjet().getNomProjet());

					listDesProjetsSize.add(ppr.getProjet().getNomProjet());
					listPart.add(ppr.getPartenaire().getNomPartenaire());

					listPartSize.add(ppr.getPartenaire().getNomPartenaire());

					listCouleur.addAll(ppr.getProjet().getType());
					for (Type t : listCouleur) {
						listTypeParRegion.add(t.getCouleur());
					}
					for (Map.Entry<String, Set<String>> entry : hmap.entrySet()) {
						if (entry.getKey() == ppr.getCommune().getNomCommune()) {
							listPart.addAll(entry.getValue());
						}
					}

					for (Map.Entry<String, Set<String>> entry : hmapProjet.entrySet()) {
						if (entry.getKey() == ppr.getCommune().getNomCommune()) {
							listDesProjets.addAll(entry.getValue());
						}
					}
					for (Map.Entry<String, Set<String>> entry : hmapType.entrySet()) {
						if (entry.getKey() == ppr.getCommune().getNomCommune()) {
							listTypeParRegion.addAll(entry.getValue());
						}
					}
					hmap.put(ppr.getCommune().getNomCommune(), listPart);
					hmapProjet.put(ppr.getCommune().getNomCommune(), listDesProjets);
					hmapType.put(ppr.getCommune().getNomCommune(), listTypeParRegion);

					listDesProjets = new HashSet<>();
					listTypeParRegion = new HashSet<>();
					listPart = new HashSet<>();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			model.addAttribute("listId", listId);
			model.addAttribute("listLat", listLat);
			model.addAttribute("listLong", listLong);
			model.addAttribute("listName", listName);
			model.addAttribute("listPart", hmap);
			model.addAttribute("listPartSize", nomPartenaire);
			model.addAttribute("nomDuType", listCouleur.size());
			model.addAttribute("nombreProjets", listDesProjetsSize.size());
			model.addAttribute("listDesProjets", hmapProjet);
			model.addAttribute("listTypeParRegion", hmapType);

		}

		return "cartographie";
	}
}
