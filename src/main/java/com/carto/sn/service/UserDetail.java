package com.carto.sn.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.UtilisateurRepository;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Utilisateur;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserDetail implements UserDetailsService{
	
	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Utilisateur> utilisateur = utilisateurRepository.findByLogin(login);
        if(utilisateur == null || !utilisateur.get().isEnabled()){
               throw new UsernameNotFoundException("Utilisateur non trouvé");
             }
//      Set<GrantedAuthority> authorities = utilisateur.get().getProfil().stream()
//              .map((role) -> new SimpleGrantedAuthority(role.getNomProfil()))
//              .collect(Collectors.toSet());
        
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Profil profil : utilisateur.get().getProfil()) {
        	authorities.add(new SimpleGrantedAuthority(profil.getNomProfil()));
        	authorities.addAll(profil.getPrivilege()
        				.stream()
        				.map(p-> new SimpleGrantedAuthority(p.getNomPrivilege()))
        				.collect(Collectors.toList()));
        	
        }
      
        log.info("Utilisateur "+login+" s'est connecté avec les autorisations "+authorities);
      return new org.springframework.security.core.userdetails.User(login, utilisateur.get().getPassword(),authorities);
  }


}
