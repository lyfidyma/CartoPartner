package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetPartenaireRegion;

public interface ProjetPartenaireRegionRepository extends JpaRepository<ProjetPartenaireRegion, Long>{
	
	@Query("select p from ProjetPartenaireRegion p where p.projet.idProjet=?1 and p.partenaire.idPartenaire=?2 and p.region.idRegion=?3")
	public  List <ProjetPartenaireRegion> findProjetPartenaireRegion(Long idProjet, Long idPartenaire, Long idRegion);
	
	@Query("select p from ProjetPartenaireRegion p where p.projet.idProjet=?1")
	public List <ProjetPartenaireRegion> findByIdProjet(Long idProjet);
	@Query("select p from ProjetPartenaireRegion p where p.partenaire.idPartenaire=?1")
	public List <ProjetPartenaireRegion> findByIdPartenaire(Long idPartenaire);
	@Query("select p from ProjetPartenaireRegion p where p.region.idRegion=?1")
	public List <ProjetPartenaireRegion> findByIdRegion(Long idPRegion);
	
}
