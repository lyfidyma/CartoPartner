package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.Categorie;

public interface CategorieService {
	public List<Categorie> toutesLesCategories();
	public Categorie ajoutCategorie(String nomCategorie);
	public Categorie findByNomCategorie(String nomCategorie);
	public Categorie findByIdCategorie(Long idCategorie);
	public void supprimerCategorie(Long idCategorie);

}
