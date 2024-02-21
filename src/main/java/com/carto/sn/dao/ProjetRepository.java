package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carto.sn.entities.Projet;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long>{
	
	
	@Query("select p from Projet p where CONCAT (p.nomProjet, ' ', p.pointFocal, ' ', p.type, ' ', p.statut, ' ', p.dateDebut, ' ', p.dateFin) like %?1%")
	public List<Projet> chercher(String motCle);
	
	@Query("select p from Projet p where p.nomProjet =?1")
	public  List <Projet> findOneIdByProjetName(String nomProjet);
	
	Projet findByNomProjet(String nomProjet);
	
	@Query("select p from Projet p where p.pointFocal =?1")
	List <Projet> findByPointFocal(String pointFocal);
	
	@Query("select p from Projet p group by p.pointFocal")
	List <Projet> groupByPointFocal();
	
	
	/*
	 * @Query("Select p from Projet p where p.utilisateur.idUtilisateur=?1")
	 * List<Projet> findProjetByIdUtilisateur(Long idUtilisateur);
	 */
	 
	
}
