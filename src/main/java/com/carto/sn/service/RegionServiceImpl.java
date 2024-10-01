package com.carto.sn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.CommuneRepository;
import com.carto.sn.dao.DepartementRepository;
import com.carto.sn.dao.PaysRepository;
import com.carto.sn.dao.RegionRepository;
import com.carto.sn.entities.Commune;
import com.carto.sn.entities.Departement;
import com.carto.sn.entities.Pays;
import com.carto.sn.entities.Region;

@Service
public class RegionServiceImpl implements RegionService{
	
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private CommuneRepository communeRepository;
	@Autowired
	private PaysRepository paysRepository;

	@Override
	public Region ajoutRegion(String nomDepartement, String nomRegion, String nomPays, String nomCommune) {
		Pays unPays = null;
		if(paysRepository.findByNomPays(nomPays)!=null) {
			unPays = paysRepository.findByNomPays(nomPays);
		}else {
			unPays = paysRepository.save(new Pays(nomPays));
		}
		Region uneRegion = null;
		if(regionRepository.findByNomRegion(nomRegion)!=null) {
			uneRegion = regionRepository.findByNomRegion(nomRegion);
		}else {
			uneRegion = regionRepository.save(new Region(nomRegion, unPays));
		}
		 Departement unDepartement = null;
		 if(departementRepository.findByNomDepartement(nomDepartement)!=null) {
			 unDepartement = departementRepository.findByNomDepartement(nomDepartement);
		 }else {
			 unDepartement = departementRepository.save(new Departement(nomDepartement, uneRegion));
		 }
		 Commune uneCommune = null;
		 if(communeRepository.findByNomCommune(nomCommune)!=null) {
			 uneCommune = communeRepository.findByNomCommune(nomCommune);
		 }else {
			 uneCommune =  communeRepository.save(new Commune(nomCommune, unDepartement));
		 }	
		
		return uneRegion;
	}

	@Override
	public List<Region> toutesLesRegions() {
		return regionRepository.findAll();
	}

	@Override
	public void supprimerRegion(Long idRegion) {
		regionRepository.deleteById(idRegion);		
	}

	@Override
	public Optional<Region> findRegionById(Long idRegion) {
		return regionRepository.findById(idRegion);

	}

}
