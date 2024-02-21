package com.carto.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carto.sn.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{
	
	public Categorie findByNomCategorie(String nomCategorie);

}
