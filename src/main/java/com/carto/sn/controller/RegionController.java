package com.carto.sn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.carto.sn.service.RegionService;

@Controller
@PreAuthorize("isAthenticated()")
public class RegionController {
	
	@Autowired
	private RegionService regionService;
	
	@RequestMapping("supprimerRegion")
	public String supprimerRegion(Long idRegion) {
		regionService.supprimerRegion(idRegion);
		return "redirect:/localisation";
	}

}
