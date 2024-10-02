package com.carto.sn.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("isAuthenticated()")
public class ParametresController {
	@RequestMapping("parametres")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String parametres() {
		return "parametres";
	}
}
