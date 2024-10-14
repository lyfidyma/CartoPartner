package com.carto.sn.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.PartenaireLocal;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetPartenaireRegion;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.service.PartenaireLocalService;
import com.carto.sn.service.PartenaireService;
import com.carto.sn.service.ProjetPartenaireRegionService;
import com.carto.sn.service.ProjetService;
import com.carto.sn.service.RegionService;
import com.carto.sn.service.TypeService;

import jakarta.validation.Valid;

public class PartenaireController {
	
	@Autowired
	private PartenaireService partenaireService;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private PartenaireLocalService partenaireLocalService;
	@Autowired
	private ProjetPartenaireRegionService pprService;
	@Autowired
	private TypeService typeService;
	
	@RequestMapping("partenaire")
	public String partenaire(Model model) {
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listLocalisation = regionService.toutesLesRegions();
		List<PartenaireLocal> listPartenaireLocal = partenaireLocalService.tousLesPartenairesLocaux();
		model.addAttribute("listPartenaireLocal", listPartenaireLocal);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listLocalisation", listLocalisation);
		return "partenaire";
	}
	
	@RequestMapping("sauvegarderPartenaire")
	public String sauvegarderPartenaire(@Valid @ModelAttribute("unPartenaire") Partenaire unPartenaire,
			BindingResult br, Long idPartenaire, String nomPartenaire, String adresse, RedirectAttributes ra) {

		if (br.hasErrors()) {
			return "nouveauPartenaire";
		}
		if (idPartenaire == null) {
			List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
			for (Partenaire part : listPartenaire) {
				if (part.getNomPartenaire().equals(nomPartenaire)) {
					ra.addFlashAttribute("messageDoublon", "Ce partenaire existe déjà");
					ra.addAttribute("nomPartenaire", nomPartenaire);
					return "redirect:/nouveauPartenaire";
				}
			}

		}

		partenaireService.ajoutPartenaire(idPartenaire, nomPartenaire, adresse);
		return "redirect:/partenaire";
	}
	
	@RequestMapping("supprimerPartenaire")
	public String supprimerPartenaire(Long idPartenaire) {
		partenaireService.supprimerPartenaire(idPartenaire);
		return "redirect:/partenaire";
	}

	@RequestMapping("getDonneesPartenaireAModifier")
	public String modifierPartenaire(@ModelAttribute("unPartenaire") Partenaire unPartenaire, Model model,
			Long idPartenaire) {
		model.addAttribute("idPartenaire", idPartenaire);
		unPartenaire = partenaireService.findPartenaireById(idPartenaire).get();
		model.addAttribute("unPartenaire", unPartenaire);
		return "nouveauPartenaire";
	}

	@RequestMapping("displayChoixPartenaire")
	public String choixPartenaire(@ModelAttribute("uneRegion") Region uneRegion,
			@ModelAttribute("unPartenaire") Partenaire unPartenaire, @ModelAttribute("unType") Type unType,
			@ModelAttribute("unProjet") Projet unProjet, Model model, @RequestParam(defaultValue = "") String nomProjet,
			@RequestParam(defaultValue = "") Long idProjet, @RequestParam(defaultValue = "") String nomPartenaire,
			@RequestParam(defaultValue = "") String pointFocal, @RequestParam(defaultValue = "") String nomType,
			String pageAAfficher, String flag) {
		// List <ProjetPartenaireRegion> listPpr =
		// iCarto.tousLesProjetsPartenairesRegions();
		List<Projet> listProjet = projetService.tousLesProjets();
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listRegion = regionService.toutesLesRegions();
		List<Projet> listProjetPoinFocal = projetService.groupByPointFocal();

		if (nomProjet.isEmpty() == false) {
			List<ProjetPartenaireRegion> projetName = null;
			Projet projetName1 = null;
			projetName1 = projetService.findByNomProjet(nomProjet);
			projetName = pprService.findByIdProjet(projetName1.getIdProjet());
			model.addAttribute("projetName1", projetName1);
			model.addAttribute("projetName", projetName);
		}

		if (pageAAfficher.equals("pageIndex")) {

			if (nomPartenaire.isEmpty() == false) {
				Partenaire partenaireName1;
				Set<Partenaire> partenaireProjet = new HashSet<Partenaire>();
				Set<Projet> projetsDunPartenaire = new HashSet<Projet>();
				Set<ProjetPartenaireRegion> partenaireName = new HashSet<ProjetPartenaireRegion>();

				// Set <Projet> projetPourType=new HashSet<Projet>();
				partenaireName1 = partenaireService.findByNomPartenaire(nomPartenaire);
				for (int i = 0; i < listProjet.size(); i++) {
					partenaireProjet = (listProjet.get(i).getPartenaire());
					for (Partenaire p : partenaireProjet) {
						if (partenaireName1.getNomPartenaire() == p.getNomPartenaire()) {
							projetsDunPartenaire.addAll(p.getProjet());
							for (Projet pr : projetsDunPartenaire) {
								partenaireName.addAll(pprService.findByIdProjet(pr.getIdProjet()));
							}
						}

					}
				}

				// model.addAttribute("projetPourType", projetPourType);
				model.addAttribute("partenaireName1", partenaireName1);
				model.addAttribute("partenaireName", partenaireName);
			}

			if (pointFocal.isEmpty() == false) {
				List<Projet> pointFocalName = null;
				pointFocalName = projetService.findByPointFocal(pointFocal);
				model.addAttribute("pointFocalName", pointFocalName);
			}

			if (nomType.isEmpty() == false) {
				Type typeName1 = null;
				Set<Type> projetType = null;
				Set<ProjetPartenaireRegion> typeName = new HashSet<ProjetPartenaireRegion>();
				typeName1 = typeService.findByNomType(nomType);

				for (int i = 0; i < listProjet.size(); i++) {

					projetType = listProjet.get(i).getType();

					for (Type typ : projetType) {
						if (typeName1.getNomType() == typ.getNomType()) {
							typeName.addAll(pprService.findByIdProjet(listProjet.get(i).getIdProjet()));

						}
					}
				}

				model.addAttribute("typeName", typeName);
				model.addAttribute("typeName1", typeName1);
			}
		}

		List<Type> listType = typeService.tousLesTypes();
		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listType", listType);
		model.addAttribute("listProjetPoinFocal", listProjetPoinFocal);
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listRegion", listRegion);
		if (nomProjet != null) {
			model.addAttribute("nomProjet", nomProjet);
			model.addAttribute("flag", flag);
		}
		// if(idProjet==null)
		// idProjet=iCarto.findOneIdByProjetName(nomProjet).get(0).getIdProjet();
		if (nomPartenaire != null)
			model.addAttribute("nomPartenaire", nomPartenaire);

		model.addAttribute("idProjet", idProjet);
		if (pageAAfficher.equals("pageIndex"))
			return "index";
		return "choixPartenaire";
	}

	@RequestMapping("nouveauPartenaire")
	public String nouveauPartenaire(@ModelAttribute("unPartenaire") Partenaire unPartenaire, Model model) {

		return "nouveauPartenaire";
	}


}
