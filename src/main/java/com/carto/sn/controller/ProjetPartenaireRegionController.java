package com.carto.sn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.carto.sn.dto.ProjetPartenaireDTO;
import com.carto.sn.entities.Commune;
import com.carto.sn.entities.Departement;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.PartenaireLocal;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.Region;
import com.carto.sn.service.CommuneService;
import com.carto.sn.service.DepartementService;
import com.carto.sn.service.PartenaireLocalService;
import com.carto.sn.service.PartenaireService;
import com.carto.sn.service.ProjetService;
import com.carto.sn.service.RegionService;

@Controller
@PreAuthorize("isAuthenticated()")
public class ProjetPartenaireRegionController {
	
	@Autowired
	private ProjetService projetService;
	@Autowired
	private CommuneService communeService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private DepartementService departementService;
	@Autowired
	private PartenaireService partenaireService;
	@Autowired
	private PartenaireLocalService partenaireLocalService;
	
	@RequestMapping("lierPartenaire")
	public String projetPartenaireRegion(@ModelAttribute ProjetPartenaireDTO projetPartenaireDTO, Model model) {

		List<Projet> listProjet = projetService.tousLesProjets();
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listRegion = regionService.toutesLesRegions();
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listRegion", listRegion);

		if (projetPartenaireDTO.getNomCommune() == null) {
			model.addAttribute("messageErreur", "Renseigner la commune");
			return "liaisonPartenaireProjet";
		}
		String nomProjet = projetPartenaireDTO.getNomProjet();
		String[] nomCommune = projetPartenaireDTO.getNomCommune();
		String[] nomPartenaire = projetPartenaireDTO.getNomPartenaire();
		String[] nomRegion = projetPartenaireDTO.getNomRegion();
		String[] nomDepartement = projetPartenaireDTO.getNomDepartement();
		String[] nomVillage = projetPartenaireDTO.getNomVillage();
		String[] latitude = projetPartenaireDTO.getLatitude();
		String[] longitude = projetPartenaireDTO.getLongitude();
		String[] nomPartenaireLocal = projetPartenaireDTO.getNomPartenaireLocal();

		if (nomCommune.length >= 1) {
			if (nomPartenaireLocal.length == 0) {
				// for(int p=0; p < nomPartenaire.length; p++) {
				for (int i = 0; i < nomCommune.length; i++) {
					if (nomVillage.length == 0) {
						if (communeService.findByNomCommune(nomCommune[i]) != null) {
							Commune comm = communeService.findByNomCommune(nomCommune[i]);
							for (int b = 0; b < nomDepartement.length; b++) {
								if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
									for (int c = 0; c < nomRegion.length; c++) {
										if (comm.getDepartement().getRegion().getNomRegion().equals(nomRegion[c])) {
											projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c], nomRegion[c],
													nomDepartement[b], nomCommune[i], null, null, null, null);

										}
									}

								}

							}

						}

					} else {
						if (latitude.length == 0 || longitude.length == 0) {
							if (communeService.findByNomCommune(nomCommune[i]) != null) {
								Commune comm = communeService.findByNomCommune(nomCommune[i]);
								for (int b = 0; b < nomDepartement.length; b++) {
									if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
										for (int c = 0; c < nomRegion.length; c++) {
											if (comm.getDepartement().getRegion().getNomRegion().equals(nomRegion[c])) {
												projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
														nomRegion[c], nomDepartement[b], nomCommune[i], nomVillage[i],
														null, null, null);

											}
										}

									}

								}

							}

						} else {

							if (communeService.findByNomCommune(nomCommune[i]) != null) {
								Commune comm = communeService.findByNomCommune(nomCommune[i]);
								for (int b = 0; b < nomDepartement.length; b++) {
									if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
										for (int c = 0; c < nomRegion.length; c++) {
											if (comm.getDepartement().getRegion().getNomRegion().equals(nomRegion[c])) {
												projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
														nomRegion[c], nomDepartement[b], nomCommune[i], nomVillage[i],
														latitude[i], longitude[i], null);

											}
										}

									}

								}

							}

						}
					}
				}

			} else {

				for (int i = 0; i < nomCommune.length; i++) {
					for (int j = 0; j < nomPartenaireLocal.length; j++) {
						if (nomVillage.length == 0) {
							if (communeService.findByNomCommune(nomCommune[i]) != null) {
								Commune comm = communeService.findByNomCommune(nomCommune[i]);
								for (int b = 0; b < nomDepartement.length; b++) {
									if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
										for (int c = 0; c < nomRegion.length; c++) {
											if (comm.getDepartement().getRegion().getNomRegion().equals(nomRegion[c])) {
												projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
														nomRegion[c], nomDepartement[b], nomCommune[i], null, null,
														null, nomPartenaireLocal[j]);

											}
										}

									}

								}

							}

						} else {
							if (latitude.length == 0 || longitude.length == 0) {
								if (communeService.findByNomCommune(nomCommune[i]) != null) {
									Commune comm = communeService.findByNomCommune(nomCommune[i]);
									for (int b = 0; b < nomDepartement.length; b++) {
										if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
											for (int c = 0; c < nomRegion.length; c++) {
												if (comm.getDepartement().getRegion().getNomRegion()
														.equals(nomRegion[c])) {
													projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
															nomRegion[c], nomDepartement[b], nomCommune[i],
															nomVillage[i], null, null, nomPartenaireLocal[j]);

												}
											}

										}

									}

								}

							} else {

								if (communeService.findByNomCommune(nomCommune[i]) != null) {
									Commune comm = communeService.findByNomCommune(nomCommune[i]);
									for (int b = 0; b < nomDepartement.length; b++) {
										if (comm.getDepartement().getNomDepartement().equals(nomDepartement[b])) {
											for (int c = 0; c < nomRegion.length; c++) {
												if (comm.getDepartement().getRegion().getNomRegion()
														.equals(nomRegion[c])) {
													projetService.ajoutPartenaireAuProjet(nomProjet, nomPartenaire[c],
															nomRegion[c], nomDepartement[b], nomCommune[i],
															nomVillage[i], latitude[i], longitude[i],
															nomPartenaireLocal[j]);
												}
											}

										}

									}

								}

							}
						}
					}

				}
			}
		}

		Projet unProjet = projetService.findByNomProjet(projetPartenaireDTO.getNomProjet());

		model.addAttribute("idProjet", projetPartenaireDTO.getNomProjet());
		model.addAttribute("nomProjet", projetPartenaireDTO.getNomProjet());
		model.addAttribute("flag", "Succes");
		model.addAttribute("pageAAfficher", "choixPartenaire");
		model.addAttribute("unProjet", unProjet);
		return "choixPartenaire";

	}
	
	@RequestMapping("liaisonPartenaireProjet")
	public String projetPartenaireRegionLiaison(
			@ModelAttribute("projetPartenaireDTO") ProjetPartenaireDTO projetPartenaireDTO, Model model) {
		List<Projet> listProjet = projetService.tousLesProjets();
		List<Partenaire> listPartenaire = partenaireService.tousLesPartenaires();
		List<Region> listRegion = regionService.toutesLesRegions();
		List<Departement> listDepartement = departementService.tousLesDepartements();
		List<PartenaireLocal> listPartenaireLocal = partenaireLocalService.tousLesPartenairesLocaux();
		model.addAttribute("listPartenaire", listPartenaire);
		model.addAttribute("listProjet", listProjet);
		model.addAttribute("listRegion", listRegion);
		model.addAttribute("listDepartement", listDepartement);
		model.addAttribute("listPartenaireLocal", listPartenaireLocal);

		return "liaisonPartenaireProjet";
	}

}
