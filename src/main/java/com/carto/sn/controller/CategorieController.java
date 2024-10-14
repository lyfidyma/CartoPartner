package com.carto.sn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.carto.sn.entities.Categorie;
import com.carto.sn.service.CategorieService;

import jakarta.validation.Valid;

@Controller
@PreAuthorize("isAuthenticated()")
public class CategorieController {
	
	@Autowired
	private CategorieService categorieService;
	
	@RequestMapping("sauvegarderCategorie")
	public String sauvegarderCategorie(@Valid @ModelAttribute("uneCategorie") Categorie uneCategorie, BindingResult br,
			Model model, String nomCategorie) {
		List<Categorie> listCategorie = categorieService.toutesLesCategories();

		if (br.hasErrors()) {
			model.addAttribute("listCategorie", listCategorie);
			return "categorie";
		}
		if (categorieService.findByNomCategorie(nomCategorie) != null) {
			model.addAttribute("messageDoublon", "Cette catégorie existe");
			return "categorie";
		}
		categorieService.ajoutCategorie(nomCategorie);
		return "redirect:/categorie";
	}
	
	@RequestMapping("supprimerCategorie")
	public String supprimerCategorie(Long idCategorie) {
		categorieService.supprimerCategorie(idCategorie);
		return "redirect:/categorie";
	}
	
	@RequestMapping("categorie")
	public String categorie(@ModelAttribute("uneCategorie") Categorie uneCategorie, Model model) {
		List<Categorie> listCategorie = categorieService.toutesLesCategories();
		model.addAttribute("listCategorie", listCategorie);
		return "categorie";
	}

}
