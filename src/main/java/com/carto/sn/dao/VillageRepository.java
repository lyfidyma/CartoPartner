package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carto.sn.entities.Village;

public interface VillageRepository extends JpaRepository<Village, Long>{
	
	@Query("Select v from Village v where v.commune.nomCommune=?1")
	public List<Village> findVillageByNomCommune(String nomCommune);
	
	public Village findByNomVillage(String nomVillage);

}
