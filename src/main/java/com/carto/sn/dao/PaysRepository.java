package com.carto.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carto.sn.entities.Pays;

public interface PaysRepository extends JpaRepository<Pays, Long>{
	
	public Pays findByNomPays(String nomPays);

}
