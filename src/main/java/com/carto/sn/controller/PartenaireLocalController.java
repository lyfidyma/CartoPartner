package com.carto.sn.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.PartenaireLocal;
import com.carto.sn.entities.Region;
import com.carto.sn.service.PartenaireLocalService;
import com.carto.sn.service.PartenaireService;
import com.carto.sn.service.RegionService;

@Controller
@PreAuthorize("isAuthenticated()")
public class PartenaireLocalController {
	
	@Autowired
	private PartenaireService partenaireService;
	@Autowired
	private RegionService regionService;
	private PartenaireLocalService partenaireLocalService;
	
	@RequestMapping("lierPartenaireLocalAPartenaire")
	public String lierPartenaireLocalAPartenaire(String nomPartenaireLocal, Long idPartenaire, Model model) {

		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listLocalisation = regionService.toutesLesRegions();
		List<PartenaireLocal> listPartenaireLocal = partenaireLocalService.tousLesPartenairesLocaux();
		model.addAttribute("listPartenaireLocal", listPartenaireLocal);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);

		if (nomPartenaireLocal.isEmpty()) {
			model.addAttribute("messageErreur", "Le partenaire communautaire n'est pas renseigné");
			return "partenaire";
		}

		if (partenaireService.findPartenaireById(idPartenaire) != null) {
			Partenaire part = partenaireService.findPartenaireByIdPartenaire(idPartenaire);
			if (part.getPartenaireLocal() != null) {
				Set<PartenaireLocal> paLo = part.getPartenaireLocal();
				for (PartenaireLocal pc : paLo) {
					if (pc.getNomPartenaireLocal().equals(nomPartenaireLocal)) {
						model.addAttribute("messageErreur",
								part.getNomPartenaire() + " est déjà lié à " + nomPartenaireLocal);
						return "partenaire";
					}
				}
			}
		}

		partenaireLocalService.ajoutPartenaireLocalAPartenaire(nomPartenaireLocal, idPartenaire);

		return "redirect:/partenaire";

	}

}
