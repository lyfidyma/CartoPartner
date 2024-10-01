package com.carto.sn.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.PrivilegeRepository;
import com.carto.sn.dao.ProfilRepository;
import com.carto.sn.entities.Privilege;
import com.carto.sn.entities.Profil;
import com.carto.sn.enums.EnumPrivilege;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	@Autowired
	ProfilRepository profilRepository;

	@Override
	public Privilege ajoutPrivilege(Long idProfil, String[] enumPrivilege) {
		Privilege nouveauPrivilege = null ;
		Profil profil = profilRepository.findById(idProfil).get();
		List<EnumPrivilege> enumPrivileges = Arrays.asList(EnumPrivilege.values());
		List <Privilege> privilege = privilegeRepository.findAll();
		/*Cette partie permet de remplir la table des privilèges. Elle n'est exécutée qu'une seule fois lorsque 
		 * la table privilèges est vide*/
		if(privilege.isEmpty()) {
			for(EnumPrivilege en : enumPrivileges) {
				privilegeRepository.save(new Privilege(en.toString()));
				privilege = privilegeRepository.findAll();
			}
			
		}
		/*Ajout des privilèges au profil*/
		for(String chPrivilege : enumPrivilege) {			
					for(Privilege pr : privilege) {
						if(chPrivilege.equals(pr.getNomPrivilege())) {	
							nouveauPrivilege = pr;
							profil.getPrivilege().add(pr);
									
					}
				}
			}
		
			return nouveauPrivilege;
	}

	@Override
	public List<Privilege> findPrivilegeByProfil(Long idProfil) {
		Profil profil = profilRepository.findById(idProfil).orElseThrow(()-> new RuntimeException("Profil not found"));
		List<Privilege> listPrivileges = profil.getPrivilege().stream().collect(Collectors.toList());
		return listPrivileges;
	}

}
