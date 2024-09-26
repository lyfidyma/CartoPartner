package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.PartenaireLocal;

public interface PartenaireLocalService {
	public List<PartenaireLocal> tousLesPartenairesLocaux();
	public PartenaireLocal ajoutPartenaireLocalAPartenaire(String nomPartenaireLocal, Long idPartenaire);
}
