package com.carto.sn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carto.sn.entities.Privilege;
import com.carto.sn.service.PrivilegeService;

@Controller
@PreAuthorize("isAuthenticated()")
public class PrivilegeController {
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@RequestMapping("sauvegarderPrivilege")
	public String sauvegarderPrivilege(Model model, Long idProfil, String checkboxPrivilege[]) {

		privilegeService.ajoutPrivilege(idProfil, checkboxPrivilege);
		return "redirect:nouveauProfil";

	}
	
	@RequestMapping("getPrivilegeByProfil")
	@ResponseBody
	public Object getPrivilegeByProfil(Long idProfil) {
		List<Privilege> listPrivileges = privilegeService.findPrivilegeByProfil(idProfil);
		List<String> listNomPrivileges = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		for(Privilege p : listPrivileges) {
			listNomPrivileges.add(p.getNomPrivilege());
		}
		object.put("listNomPrivileges", listNomPrivileges);
		return object;
	}

}
