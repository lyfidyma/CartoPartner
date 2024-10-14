package com.carto.sn.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class HomeController {
	
	@Autowired
	private ProjetService projetService;
	@Autowired
	private PartenaireService partenaireService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private ProjetPartenaireRegionService pprService;
	@Autowired
	private GeocodingService geocodingService;
	

	@RequestMapping("/index")
	public String index(@ModelAttribute("uneRegion") Region uneRegion, @ModelAttribute("unType") Type unType,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unProjet") Projet unProjet,
			Model model, String nomProjet, Long idProjet) {
		List<Projet> listProjetPoinFocal = projetService.groupByPointFocal();
		List<Projet> listProjet = projetService.tousLesProjets();

		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listRegion = regionService.toutesLesRegions();
		List<Type> listType = typeService.tousLesTypes();
		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listType", listType);

		model.addAttribute("listProjetPoinFocal", listProjetPoinFocal);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listRegion", listRegion);
		if (nomProjet != null)
			model.addAttribute("nomProjet", nomProjet);

		model.addAttribute("idProjet", idProjet);

		List<ProjetPartenaireRegion> listPpr = pprService.tousLesProjetsPartenairesRegions();

		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listRegion", listRegion);
		model.addAttribute("listType", listType);
		JSONObject location;
		List<String> listLat = new ArrayList<>();
		List<String> listLong = new ArrayList<>();
		List<String> listName = new ArrayList<>();
		Set<String> listPart = new HashSet<>();
		// HashMap <String, List<String>> listTypeRegion = new HashMap<String,
		// List<String>>();
		// List<String> uneListeType = new ArrayList<>();
		Set<Commune> listProjetsEtRegions = new HashSet<>();
		// Set <Type> listProjetsEtType = null;
		HashSet<String> listNombreRegions = new HashSet<String>();
		Set<Projet> listNombreProjets = new HashSet<>();

		for (int i = 0; i < listPpr.size(); i++) {

			listProjetsEtRegions.add(listPpr.get(i).getCommune());

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

		}

		Set<Partenaire> partDesProjets = new HashSet<Partenaire>();
		for (Projet pro : listNombreProjets) {

			partDesProjets.addAll(pro.getPartenaire());
		}

		for (Partenaire p : partDesProjets) {
			listPart.add(p.getNomPartenaire());
		}

		List<String> listDesProjets = new ArrayList<>();
		for (Projet p : listNombreProjets) {
			listDesProjets.add(p.getNomProjet());
		}
		model.addAttribute("listDesProjets", listDesProjets);
		model.addAttribute("nombreProjets", listDesProjets.size());
		model.addAttribute("nombreRegions", listNombreRegions.size());
		model.addAttribute("listName", listName);
		model.addAttribute("listLat", listLat);
		model.addAttribute("listLong", listLong);
		model.addAttribute("listPart", listPart);
		model.addAttribute("listPartSize", partDesProjets.size());
		model.addAttribute("nomDuType", listType.size());

		return "index";
	}

}
