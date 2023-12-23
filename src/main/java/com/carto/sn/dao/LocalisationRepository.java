package com.carto.sn.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carto.sn.entities.Localisation;

@Repository
public interface LocalisationRepository extends JpaRepository<Localisation, Long>{
	
	public Localisation findByLibelleLocalisation(String libelleLocalisation);

}
