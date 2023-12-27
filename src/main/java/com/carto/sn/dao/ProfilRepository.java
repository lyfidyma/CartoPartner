package com.carto.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carto.sn.entities.Profil;

public interface ProfilRepository extends JpaRepository<Profil, Long>{
	
	Profil findByNomProfil(String nomProfil);

}
