package com.carto.sn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Commune;
import com.carto.sn.entities.Departement;
import com.carto.sn.entities.Pays;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Village;
import com.carto.sn.service.CommuneService;
import com.carto.sn.service.DepartementService;
import com.carto.sn.service.RegionService;
import com.carto.sn.service.VillageService;

import jakarta.validation.Valid;

@Controller
@PreAuthorize("isAuthenticated()")
public class LocalisationController {
	
	@Autowired
	private CommuneService communeService;
	@Autowired
	private DepartementService departementService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private VillageService villageService;
	
	@RequestMapping("localisation")
	public String localisation(@ModelAttribute("uneRegion") Region uneRegion,
			@ModelAttribute("unDepartement") Departement unDepartement,
			@ModelAttribute("uneCommune") Commune uneCommune, @ModelAttribute("unVillage") Village unVillage,
			@ModelAttribute("unPays") Pays unPays, Model model) {
		List<Commune> listCommune = communeService.toutesLesCommunes();
		List<Departement> listDepartement = departementService.tousLesDepartements();
		List<Village> listVillage = villageService.tousLesVillages();

		model.addAttribute("listVillage", listVillage);
		model.addAttribute("listCommune", listCommune);
		model.addAttribute("listDepartement", listDepartement);
		return "localisation";
	}
	
	@RequestMapping("sauvegarderLocalisation")
	public String sauvegarderLocalisation(@Valid @ModelAttribute("uneRegion") Region uneRegion, BindingResult br,
			String nomDepartement, String nomRegion, String nomCommune, String nomPays, RedirectAttributes ra) {
		if (br.hasErrors()) {
			return "localisation";
		}
		/*
		 * List <Region> listRegion = iCarto.toutesLesRegions(); for(Region loc:listLoc)
		 * { if(loc.getNomRegion().equals(libelleRegion)) {
		 * ra.addFlashAttribute("messageDoublon", "Cette localisation existe déjà");
		 * ra.addFlashAttribute("libelleLocalisation", libelleRegion); return
		 * "redirect:/localisation"; } }
		 */
		List<Departement> listDepartement = departementService.tousLesDepartements();
		ra.addFlashAttribute("listDepartement", listDepartement);

		regionService.ajoutRegion(nomDepartement, nomRegion, nomPays, nomCommune);
		return "redirect:/localisation";
	}

}
