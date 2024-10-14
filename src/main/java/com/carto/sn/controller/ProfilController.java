package com.carto.sn.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.carto.sn.entities.Privilege;
import com.carto.sn.entities.Profil;
import com.carto.sn.enums.EnumPrivilege;
import com.carto.sn.enums.EnumProfil;
import com.carto.sn.service.ProfilService;

import jakarta.validation.Valid;

@Controller
@PreAuthorize("isAuthenticated()")
public class ProfilController {
	
	@Autowired
	private ProfilService profilService;
	
	@RequestMapping("nouveauProfil")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String nouveauProfil(@ModelAttribute("unProfil") Profil unProfil, Model model) {
		List<EnumProfil> enumProfils = Arrays.asList(EnumProfil.values());
		List<EnumPrivilege> enumPrivileges = Arrays.asList(EnumPrivilege.values());
		Map<String, List<Privilege>> profilPrivilegeMap = new HashMap<>();
		List<Profil> listProfils = profilService.tousLesProfils();
		
		for(Profil prof : listProfils) {		
			profilPrivilegeMap.computeIfAbsent(prof.getNomProfil(), k-> new ArrayList<>())
			  .addAll(prof.getPrivilege());			
		}
		
		model.addAttribute("listProfil", profilService.tousLesProfils());
		model.addAttribute("enumProfils", enumProfils);
		model.addAttribute("enumPrivileges", enumPrivileges);
		model.addAttribute("profilPrivilegeMap", profilPrivilegeMap);
		return "nouveauProfil";
	}
	
	@RequestMapping("sauvegarderProfil")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String sauvegarderProfil(@Valid @ModelAttribute("unProfil") Profil unProfil, BindingResult br, Model model,
			String nomProfil, RedirectAttributes ra) {
		List<EnumPrivilege> enumPrivileges = Arrays.asList(EnumPrivilege.values());
		if (br.hasErrors()) {
			List<Profil> listProfil = profilService.tousLesProfils();
			model.addAttribute("listProfil", listProfil);
			model.addAttribute("enumPrivileges", enumPrivileges);
			return "nouveauProfil";
		}

		List<Profil> listProfil = profilService.tousLesProfils();
		for (Profil prof : listProfil) {
			if (prof.getNomProfil().equals(nomProfil)) {

				ra.addFlashAttribute("messageDoublon", "Ce profil existe déjà");
				ra.addFlashAttribute("nomProfil", nomProfil);
				return "redirect:/nouveauProfil";
			}
		}
		profilService.ajoutProfil(nomProfil);
		return "redirect:/nouveauProfil";
	}
	
	@RequestMapping("supprimerProfil")
	public String supprimerProfil(Long idProfil) {
		profilService.supprimerProfil(idProfil);
		return "redirect:/nouveauProfil";
	}


}
