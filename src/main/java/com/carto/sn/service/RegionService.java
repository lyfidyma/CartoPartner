package com.carto.sn.service;

import java.util.List;
import java.util.Optional;

import com.carto.sn.entities.Region;

public interface RegionService {
	public Region ajoutRegion(String nomDepartement, String nomRegion, String nomPays, String nomCommune);
	public List <Region> toutesLesRegions();
	public void supprimerRegion(Long idRegion);
	public Optional <Region> findRegionById(Long idRegion);
}
