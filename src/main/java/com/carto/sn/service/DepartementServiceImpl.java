package com.carto.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.carto.sn.dao.DepartementRepository;
import com.carto.sn.entities.Departement;

public class DepartementServiceImpl implements DepartementService {
	
	@Autowired
	private DepartementRepository departementRepository;
	
	@Override
	public List<Departement> tousLesDepartements() {
		return departementRepository.findAll();
	}

	@Override
	public List<Departement> findDepartementByNomRegion(String nomRegion) {
		return departementRepository.findDepartementByNomRegion(nomRegion);
	}

}
