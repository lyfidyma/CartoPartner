package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carto.sn.entities.Partenaire;

@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire, Long>{
	
	@Query("select p from Partenaire p where p.nomPartenaire=?1")
	public Partenaire findByNomPartenaire(String nomPartenaire);
	
	@Query("select p from Partenaire p where p.nomPartenaire=?1")
	public List<Partenaire> findByNomPartenaireList(String nomPartenaire);

}
