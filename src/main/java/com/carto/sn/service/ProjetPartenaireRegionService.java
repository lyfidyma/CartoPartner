package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.ProjetPartenaireRegion;

public interface ProjetPartenaireRegionService {
	public List <ProjetPartenaireRegion> findByIdProjet(Long idProjet);
	public List <ProjetPartenaireRegion> findByIdPartenaire(Long idPartenaire);
	public List <ProjetPartenaireRegion> findByIdRegion(Long idRegion);
	public List <ProjetPartenaireRegion> tousLesProjetsPartenairesRegions();
}
