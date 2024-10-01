package com.carto.sn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.PartenaireRepository;
import com.carto.sn.entities.Partenaire;

@Service
public class PartenaireServiceImpl implements PartenaireService{
	
	@Autowired
	private PartenaireRepository partenaireRepository;

	@Override
	public Partenaire ajoutPartenaire(Long idPartenaire, String nomPartenaire, String adresse) {

		Partenaire partenaire = null;
		if(idPartenaire==null) {
		partenaire = partenaireRepository.save(new Partenaire(nomPartenaire, adresse));
		return partenaire;
		}else {
			partenaire = partenaireRepository.findById(idPartenaire).orElse(partenaire);
			partenaire.setNomPartenaire(nomPartenaire);
			partenaire.setAdresse(adresse);
			partenaireRepository.save(partenaire);
			return partenaire;
		}
	}

	@Override
	public List<Partenaire> tousLesPartenaires() {
		return partenaireRepository.findAll();
	}

	@Override
	public void supprimerPartenaire(Long idPartenaire) {
		partenaireRepository.deleteById(idPartenaire);	
	}

	@Override
	public Partenaire modifierPartenaire(String nomPartenaire, String nouveauNomPartenaire, String nouvelleAdresse) {
		Partenaire partenaire  = null;
		partenaire = partenaireRepository.findByNomPartenaire(nomPartenaire);
		partenaire.setNomPartenaire(nouveauNomPartenaire);
		partenaire.setAdresse(nouvelleAdresse);
		partenaireRepository.save(partenaire);
		return partenaire;
	}

	@Override
	public Optional<Partenaire> findPartenaireById(Long id) {
		return partenaireRepository.findById(id);
	}

	@Override
	public Partenaire findByNomPartenaire(String nomPartenaire) {
		return partenaireRepository.findByNomPartenaire(nomPartenaire);
	}

	@Override
	public Partenaire findPartenaireByIdPartenaire(Long idPartenaire) {
		return partenaireRepository.findById(idPartenaire).orElse(null);
	}

}
