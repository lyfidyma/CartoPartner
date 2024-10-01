package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.Profil;

public interface ProfilService {
	public Profil findByIdProfil(Long idProfil);
	public Profil ajoutProfil(String profil);
	public List <Profil> tousLesProfils();
	public void supprimerProfil(Long idProfil);

}
