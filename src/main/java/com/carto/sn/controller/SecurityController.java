package com.carto.sn.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SecurityController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@RequestMapping("/profile")
	public Authentication authentication(Authentication authentication) {
		return authentication;
	}
	
}
