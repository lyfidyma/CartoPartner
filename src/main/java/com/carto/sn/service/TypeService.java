package com.carto.sn.service;

import java.util.List;

import com.carto.sn.entities.Type;

public interface TypeService {
	public Type ajoutType(String type, String couleur);
	public void supprimerType(Long idType);
	public List <Type> tousLesTypes();
	public Type findByNomType(String nomType);
	public Type findByIdType(Long idType);
}
