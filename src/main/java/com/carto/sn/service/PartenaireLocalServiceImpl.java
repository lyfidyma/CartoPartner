package com.carto.sn.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.carto.sn.dao.PartenaireLocalRepository;
import com.carto.sn.dao.PartenaireRepository;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.PartenaireLocal;

public class PartenaireLocalServiceImpl implements PartenaireLocalService{
	
	@Autowired
	private PartenaireLocalRepository partenaireLocalRepository;
	private PartenaireRepository partenaireRepository;
	
	@Override
	public List<PartenaireLocal> tousLesPartenairesLocaux() {
		return partenaireLocalRepository.findAll();
	}

	@Override
	public PartenaireLocal ajoutPartenaireLocalAPartenaire(String nomPartenaireLocal, Long idPartenaire) {
		Partenaire part= partenaireRepository.findById(idPartenaire).orElse(null);
		PartenaireLocal partLoc = null;
		
		if(partenaireLocalRepository.findByNomPartenaireLocal(nomPartenaireLocal)!=null) {
			partLoc = partenaireLocalRepository.findByNomPartenaireLocal(nomPartenaireLocal);
		}else {
			Set<Partenaire> sPartenaire = Stream.of(part)
                .collect(Collectors.toCollection(HashSet::new));
				partLoc = partenaireLocalRepository.save(new PartenaireLocal(nomPartenaireLocal, sPartenaire));
		}

		
		part.getPartenaireLocal().add(partLoc);
		
		return partLoc;
	}

}
