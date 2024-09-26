package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.Commune;

public interface CommuneService {
	public List<Commune> findCommuneByNomDepartement(String nomDepartement);
	public void supprimerCommune(Long idCommune);
	public Commune findByNomCommune(String nomCommune);
	public List<Commune> toutesLesCommunes();
}
