package com.carto.sn.service;

import java.util.List;
import java.util.Optional;

import com.carto.sn.entities.Partenaire;

public interface PartenaireService {
	public Partenaire ajoutPartenaire(Long idPartenaire, String nomPartenaire, String adresse);
	public List <Partenaire> tousLesPartenaires();
	public void supprimerPartenaire(Long idPartenaire);
	public Partenaire modifierPartenaire(String nomPartenaire, String nouveauNomPartenaire, String nouvelleAdresse);
	public Optional <Partenaire> findPartenaireById(Long id);
	public Partenaire findByNomPartenaire(String nomPartenaire);
	public Partenaire findPartenaireByIdPartenaire(Long idPartenaire);



}
