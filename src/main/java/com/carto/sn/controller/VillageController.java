package com.carto.sn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carto.sn.entities.Village;
import com.carto.sn.service.VillageService;

@Controller
@PreAuthorize("isAuthenticated()")
public class VillageController {
	
	@Autowired
	private VillageService villageService;
	
	@RequestMapping("/getVillage")
	@ResponseBody
	public Object getVillage(String nomCommune) {

		if (villageService.findVillageByNomCommune(nomCommune) == null) {
			return null;
		}
		List<Village> listVil = villageService.findVillageByNomCommune(nomCommune);
		return listVil;

	}

}
