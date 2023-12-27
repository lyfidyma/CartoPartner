package com.carto.sn.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carto.sn.entities.Projet;

public interface ProjetRepository extends JpaRepository<Projet, Long>{
	
	@Query("select p from Projet p group by (p.idProjet, p.nomProjet)")
	public List<Projet> findAllPartenaires();
	
	public List <Projet> findByNomProjet(String nomProjet);
	
	@Query("select p from Projet p where CONCAT (p.nomProjet, ' ', p.responsable, ' ', p.type, ' ', p.statut, ' ', p.localisation.libelleLocalisation, ' ', p.partenaire.nomPartenaire, ' ', p.dateDebut, ' ', p.dateFin) like %?1%")
	public List<Projet> chercher(String motCle);

}
