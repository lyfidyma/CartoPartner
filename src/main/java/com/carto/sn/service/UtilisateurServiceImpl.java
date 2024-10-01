package com.carto.sn.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.ProfilRepository;
import com.carto.sn.dao.UtilisateurRepository;
import com.carto.sn.entities.Profil;
import com.carto.sn.entities.Utilisateur;

import jakarta.persistence.EntityManagerFactory;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private ProfilRepository profilRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EntityManagerFactory factory;
	
	@Override
	public Utilisateur ajoutUtilisateur(Long idUtilisateur, String nom, String prenom, String login, String password,
			String profil) {
		Utilisateur utilisateur = null;
		Profil profilRole = profilRepository.findByNomProfil(profil);
		Set<Profil> roles = Stream.of(profilRole)
                .collect(Collectors.toCollection(HashSet::new));
		if(idUtilisateur == null) {
			utilisateur = utilisateurRepository.save(new Utilisateur(nom, prenom, login, passwordEncoder.encode(password), true));
			
		}else if(idUtilisateur!=null){
			utilisateur = utilisateurRepository.findById(idUtilisateur).orElse(utilisateur);
			utilisateur.setNomUtilisateur(nom);
			utilisateur.setPrenomUtilisateur(prenom);
			utilisateur.setLogin(login);
			if(password.isBlank()==false)
				utilisateur.setPassword(passwordEncoder.encode(password));
				utilisateurRepository.save(utilisateur);
		}
		utilisateur.setProfil(roles);
		
		return utilisateur;
	}

	@Override
	public List<Utilisateur> tousLesUtilisateurs() {
		return utilisateurRepository.findAll();
	}

	@Override
	public void supprimerUtilisateur(Long idUtilisateur) {
		 utilisateurRepository.deleteById(idUtilisateur);		
	}

	@Override
	public Optional<Utilisateur> findUtilisateurById(Long idUtilisateur) {
		return utilisateurRepository.findById(idUtilisateur);
	}

	@Override
	public List<Utilisateur> getAllOperationsOfUser(String login) {
		AuditReader reader = AuditReaderFactory.get(factory.createEntityManager());
		AuditQuery queryOperationsOfUser = reader.createQuery()
				.forRevisionsOfEntity(Utilisateur.class, true, false)
				//.forRevisionsOfEntityWithChanges(Utilisateur.class, true)
				.add(AuditEntity.revisionProperty("userModificateur").eq(login));
		List <Utilisateur> listOperationsOfUser = queryOperationsOfUser.getResultList();
		
		return listOperationsOfUser;
	}

	@Override
	public void enableOrDisableUserAccount(Long idUtilisateur, boolean isEnabled) {
		Optional <Utilisateur> utilisateur = utilisateurRepository.findById(idUtilisateur);
		utilisateur.get().setEnabled(isEnabled);
	}

}
