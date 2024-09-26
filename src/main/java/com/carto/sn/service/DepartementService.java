package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.Departement;

public interface DepartementService {
	public List<Departement> tousLesDepartements();
	public List<Departement> findDepartementByNomRegion(String nomRegion);
}
