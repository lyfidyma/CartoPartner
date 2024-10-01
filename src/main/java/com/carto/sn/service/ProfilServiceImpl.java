package com.carto.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.ProfilRepository;
import com.carto.sn.entities.Profil;

@Service
public class ProfilServiceImpl implements ProfilService{

	@Autowired
	private ProfilRepository profilRepository;
	
	@Override
	public Profil findByIdProfil(Long idProfil) {
		return profilRepository.findById(idProfil).orElseThrow(RuntimeException::new);
	}

	@Override
	public Profil ajoutProfil(String profil) {
		return profilRepository.save(new Profil(profil));
	}

	@Override
	public List<Profil> tousLesProfils() {
		return profilRepository.findAll();
	}

	@Override
	public void supprimerProfil(Long idProfil) {
		profilRepository.deleteById(idProfil);	
	}

}
