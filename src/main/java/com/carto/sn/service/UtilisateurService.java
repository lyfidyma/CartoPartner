package com.carto.sn.service;

import java.util.List;
import java.util.Optional;

import com.carto.sn.entities.Utilisateur;

public interface UtilisateurService {
	public Utilisateur ajoutUtilisateur(Long idUtilisateur, String nom, String prenom, String login, String password, String profil);
	public List <Utilisateur> tousLesUtilisateurs();
	public void supprimerUtilisateur(Long idUtilisateur);
	public Optional<Utilisateur> findUtilisateurById(Long idUtilisateur);
	public List<Utilisateur> getAllOperationsOfUser(String login);
	public void enableOrDisableUserAccount(Long idUtilisateur, boolean isEnabled);

}
