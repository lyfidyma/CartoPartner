package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carto.sn.entities.Departement;

public interface DepartementRepository extends JpaRepository<Departement, Long>{
	
	@Query("Select d from Departement d where d.region.nomRegion=?1")
	public List <Departement> findDepartementByNomRegion(String nomRegion);
	
	public Departement findByNomDepartement(String nomDepartement);
}
