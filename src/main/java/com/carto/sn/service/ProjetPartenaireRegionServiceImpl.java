package com.carto.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.ProjetPartenaireRegionRepository;
import com.carto.sn.entities.ProjetPartenaireRegion;

@Service
public class ProjetPartenaireRegionServiceImpl implements ProjetPartenaireRegionService {

	@Autowired
	private ProjetPartenaireRegionRepository pprRepository;
	@Override
	public List<ProjetPartenaireRegion> findByIdProjet(Long idProjet) {
		return pprRepository.findByIdProjet(idProjet);
	}

	@Override
	public List<ProjetPartenaireRegion> findByIdPartenaire(Long idPartenaire) {
		return pprRepository.findByIdProjet(idPartenaire);
	}

	@Override
	public List<ProjetPartenaireRegion> findByIdRegion(Long idRegion) {
		return pprRepository.findByIdProjet(idRegion);
	}

	@Override
	public List<ProjetPartenaireRegion> tousLesProjetsPartenairesRegions() {
		return pprRepository.findAll();
	}

}
