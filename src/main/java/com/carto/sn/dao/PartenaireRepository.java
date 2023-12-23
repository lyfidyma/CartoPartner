package com.carto.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carto.sn.entities.Partenaire;

@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire, Long>{
	
	public Partenaire findByNomPartenaire(String nomPartenaire);

}
