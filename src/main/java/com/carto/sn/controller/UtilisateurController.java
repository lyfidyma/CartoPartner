package com.carto.sn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Utilisateur;
import com.carto.sn.service.ProfilService;
import com.carto.sn.service.UtilisateurService;

import jakarta.validation.Valid;

@Controller
@PreAuthorize("isAuthenticated()")
public class UtilisateurController {
	
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private ProfilService profilService;
	
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
	
	@RequestMapping("operations")
	public String UserOperations(Model model, String login) {

		List<Utilisateur> userOperations = utilisateurService.getAllOperationsOfUser(login);
		model.addAttribute("userOperations", userOperations);
		return "userOperation";
	}
	
	@RequestMapping("enableOrDisableUserAccount")
	@ResponseBody
	public String enableOrDisableUserAccount(Long idUtilisateur, boolean isEnabled) {
		utilisateurService.enableOrDisableUserAccount(idUtilisateur, isEnabled);
		return "redirect:utilisateur";
	}

}
