package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carto.sn.entities.Projet;
import com.carto.sn.entities.Type;

public interface TypeRepository extends JpaRepository<Type, Long>{
	
	Type findByNomType(String nomType);

}
