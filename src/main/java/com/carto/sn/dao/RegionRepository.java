package com.carto.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carto.sn.entities.Region;

public interface RegionRepository extends JpaRepository<Region, Long>{
	
	Region findByNomRegion(String nomRegion);

}
