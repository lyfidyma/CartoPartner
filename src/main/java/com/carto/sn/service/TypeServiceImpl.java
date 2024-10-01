package com.carto.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carto.sn.dao.TypeRepository;
import com.carto.sn.entities.Type;

@Service
public class TypeServiceImpl implements TypeService {
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Override
	public Type ajoutType(String type, String couleur) {
		return typeRepository.save(new Type(type, couleur));
	}

	@Override
	public void supprimerType(Long idType) {
		typeRepository.deleteById(idType);	
	}

	@Override
	public List<Type> tousLesTypes() {
		return typeRepository.findAll();
	}

	@Override
	public Type findByNomType(String nomType) {
		return typeRepository.findByNomType(nomType);
	}

	@Override
	public Type findByIdType(Long idType) {
		return typeRepository.findById(idType).orElse(null);
	}

}
