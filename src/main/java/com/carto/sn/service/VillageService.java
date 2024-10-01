package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.Village;

public interface VillageService {
	public List <Village> tousLesVillages();
	public List <Village> findVillageByNomCommune(String nomCommune);
}
