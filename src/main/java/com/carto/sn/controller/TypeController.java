package com.carto.sn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.carto.sn.entities.Type;
import com.carto.sn.service.TypeService;

@Controller
@PreAuthorize("isAuthenticated()")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	@RequestMapping("type")
	public String type(@ModelAttribute("unType") Type unType, Model model) {
		List<Type> listType = typeService.tousLesTypes();
		model.addAttribute("listType", listType);
		return "type";
	}

	@RequestMapping("sauvegarderType")
	public String sauvegarderType(@ModelAttribute("unType") Type unType, Model model, String nomType, String couleur) {
		typeService.ajoutType(nomType, couleur);
		return "redirect:/type";
	}

	@RequestMapping("supprimerType")
	public String supprimerType(@ModelAttribute("unType") Type unType, Long idType) {
		typeService.supprimerType(idType);
		return "redirect:/type";
	}

}
