package com.carto.sn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carto.sn.entities.Commune;
import com.carto.sn.service.CommuneService;

@Controller
@PreAuthorize("isAuthenticated()")
public class CommuneController {
	@Autowired
	private CommuneService communeService;
	@RequestMapping("/getCommune")
	@ResponseBody
	public Object getCommune(String nomDepartement) {

		if (communeService.findCommuneByNomDepartement(nomDepartement) == null) {
			return null;
		}
		List<Commune> listCom = communeService.findCommuneByNomDepartement(nomDepartement);

		return listCom;

	}
	
	@RequestMapping("supprimerCommune")
	public String supprimerCommune(Long idCommune) {
		communeService.supprimerCommune(idCommune);
		return "redirect:/localisation";
	}

}
