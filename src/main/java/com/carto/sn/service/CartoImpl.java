package com.carto.sn.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carto.sn.dao.LocalisationRepository;
import com.carto.sn.dao.PartenaireRepository;
import com.carto.sn.dao.ProjetRepository;
import com.carto.sn.entities.Localisation;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Projet;

@Service
@Transactional
public class CartoImpl implements ICarto{
	
	@Autowired
	private LocalisationRepository localisationRepository;
	@Autowired
	private PartenaireRepository partenaireRepository;
	@Autowired
	private ProjetRepository projetRepository;

	@Override
	public Localisation ajoutLocalisation(String libelle) {
		Localisation localisation = null;
		localisation = localisationRepository.save(new Localisation(libelle));
		return localisation;
	}

	@Override
	public Partenaire ajoutPartenaire(String nomPartenaire, String adresse) {
		Partenaire partenaire = null;
		partenaire = partenaireRepository.save(new Partenaire(nomPartenaire, adresse));
		return partenaire;
	}

	@Override
	public List<Partenaire> tousLesPartenaires() {
		return partenaireRepository.findAll();
	}

	@Override
	public List<Localisation> toutesLesLocalisations() {
		return localisationRepository.findAll();
	}
	
	@Override
	public List<Projet> tousLesProjets() {
		return projetRepository.findAll();
	}

	@Override
	public Projet ajoutProjet(String nomProjet, String responsable, String nomPartenaire, String libelleLocalisation, 
			String description, String type, String statut, LocalDate dateDebut, LocalDate dateFin) {
		
		Projet proj = null;
		Localisation loc = localisationRepository.findByLibelleLocalisation(libelleLocalisation);
		Partenaire part = partenaireRepository.findByNomPartenaire(nomPartenaire);
		proj = projetRepository.save(new Projet(nomProjet, dateDebut, dateFin, responsable, description, type, statut, part, loc));
		return proj;
	}

	@Override
	public Projet ajouterPartenaireAuProjet(String nomProjet, String responsable, String nomPartenaire,
			String libelleLocalisation, String description, String type, String statut, LocalDate dateDebut,
			LocalDate dateFin) {
		Projet proj = null;
		Localisation loc = localisationRepository.findByLibelleLocalisation(libelleLocalisation);
		Partenaire part = partenaireRepository.findByNomPartenaire(nomPartenaire);
		proj = projetRepository.save(new Projet(nomProjet, dateDebut, dateFin, responsable, description, type, statut, part, loc));
		return proj;
	}

	@Override
	public void supprimerPartenaire(Long idPartenaire) {
		partenaireRepository.deleteById(idPartenaire);
	}

	

	

}
