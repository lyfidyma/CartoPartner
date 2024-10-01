package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.Privilege;

public interface PrivilegeService {
	public Privilege ajoutPrivilege(Long idProfil, String[] enumPrivilege);
	public List<Privilege> findPrivilegeByProfil(Long idProfil);

}
