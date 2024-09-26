package com.carto.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.carto.sn.dao.CommuneRepository;
import com.carto.sn.entities.Commune;

public class CommuneServiceImpl implements CommuneService{
	
	@Autowired
	private CommuneRepository communeRepository;

	@Override
	public List<Commune> findCommuneByNomDepartement(String nomDepartement) {
		return communeRepository.findCommuneByNomDepartement(nomDepartement);
	}

	@Override
	public void supprimerCommune(Long idCommune) {
		communeRepository.deleteById(idCommune);
	}

	@Override
	public Commune findByNomCommune(String nomCommune) {
		return communeRepository.findByNomCommune(nomCommune);
	}

	@Override
	public List<Commune> toutesLesCommunes() {
		return communeRepository.findAll();
	}

}
