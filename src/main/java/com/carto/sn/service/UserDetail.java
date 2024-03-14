package com.carto.sn.service;

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
import com.carto.sn.entities.Utilisateur;

@Service
public class UserDetail implements UserDetailsService{
	
	@Autowired
	UtilisateurRepository utilisateurReporitory;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Utilisateur> utilisateur = utilisateurReporitory.findByLogin(login);
        if(utilisateur==null){
               throw new UsernameNotFoundException("Utilisateur non trouvé");
             }
      Set<GrantedAuthority> authorities = utilisateur.get().getProfil().stream()
              .map((role) -> new SimpleGrantedAuthority(role.getNomProfil()))
              .collect(Collectors.toSet());
      return new org.springframework.security.core.userdetails.User(login, utilisateur.get().getPassword(),authorities);
  }


}
