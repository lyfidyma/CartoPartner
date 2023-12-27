package com.carto.sn.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carto.sn.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	
	Optional <Utilisateur> findByLogin(String login); 

}
