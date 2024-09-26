package com.carto.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.CategorieRepository;
import com.carto.sn.entities.Categorie;

@Service
public class CategorieServiceImpl implements CategorieService{
	
	@Autowired
	private CategorieRepository categorieRepository;

	@Override
	public List<Categorie> toutesLesCategories() {
		return categorieRepository.findAll();
	}

	@Override
	public Categorie ajoutCategorie(String nomCategorie) {
		Categorie categorie = null;
		categorie = categorieRepository.save(new Categorie(nomCategorie));
		return categorie;
	}

	@Override
	public Categorie findByNomCategorie(String nomCategorie) {
		return categorieRepository.findByNomCategorie(nomCategorie);
	}

	@Override
	public Categorie findByIdCategorie(Long idCategorie) {
		return categorieRepository.findById(idCategorie).orElse(null);
	}

	@Override
	public void supprimerCategorie(Long idCategorie) {
		categorieRepository.deleteById(idCategorie);		
	}

}
