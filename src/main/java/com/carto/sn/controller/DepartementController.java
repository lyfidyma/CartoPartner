package com.carto.sn.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carto.sn.entities.Departement;
import com.carto.sn.service.DepartementService;

@Controller
@PreAuthorize("isAuthenticated()")
public class DepartementController {
	@Autowired
	private DepartementService departementService;
	
	@RequestMapping("/getDepartement")
	@ResponseBody
	public Set<Departement> getDepartement(String nomRegion) {
		if (departementService.findDepartementByNomRegion(nomRegion) == null) {
			return null;
		}
		List<Departement> listDep = departementService.findDepartementByNomRegion(nomRegion);
		Set<Departement> listDepSet = new HashSet<>(listDep);
		return listDepSet;
	}
}
