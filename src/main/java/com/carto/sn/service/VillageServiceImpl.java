package com.carto.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.VillageRepository;
import com.carto.sn.entities.Village;

@Service
public class VillageServiceImpl implements VillageService {
	
	@Autowired
	private VillageRepository villageRepository;
	
	@Override
	public List<Village> tousLesVillages() {
		return villageRepository.findAll();
	}

	@Override
	public List<Village> findVillageByNomCommune(String nomCommune) {
		return villageRepository.findVillageByNomCommune(nomCommune);
	}

}
