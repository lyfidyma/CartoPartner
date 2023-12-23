package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.carto.sn.entities.Projet;

public interface ProjetRepository extends JpaRepository<Projet, Long>{
	
	@Query("select p from Projet p group by (p.idProjet, p.nomProjet)")
	public List<Projet> findAllPartenaires();

}
