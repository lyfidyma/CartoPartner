package com.carto.sn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.carto.sn.dao.PaysRepository;
import com.carto.sn.entities.Pays;

public class PaysServiceImpl implements PaysService{

	@Autowired
	private PaysRepository paysRepository;
	
	@Override
	public List<Pays> tousLesPays() {
		return paysRepository.findAll();
	}

}
