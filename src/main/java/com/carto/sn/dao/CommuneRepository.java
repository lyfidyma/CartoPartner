package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carto.sn.entities.Commune;

public interface CommuneRepository extends JpaRepository<Commune, Long>{
	
	@Query("Select c from Commune c where c.departement.nomDepartement=?1")
	public List<Commune> findCommuneByNomDepartement(String nomDepartement);
	
	public Commune findByNomCommune(String nomCommune);

}
