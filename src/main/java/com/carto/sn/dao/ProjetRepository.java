package com.carto.sn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carto.sn.entities.Projet;
import com.carto.sn.entities.ProjetGroupByNomProjetTypeLocalisation;

public interface ProjetRepository extends JpaRepository<Projet, Long>{
	
	@Query("Select NEW com.carto.sn.entities.ProjetGroupByNomProjetTypeLocalisation(p.localisation.idLocalisation, p.partenaire.idPartenaire, p.nomProjet, p.type, p.localisation.libelleLocalisation, p.partenaire.nomPartenaire) from Projet p where p.nomProjet =?1 group by p.localisation.idLocalisation, p.partenaire.idPartenaire, p.nomProjet, p.type, p.localisation.libelleLocalisation, p.partenaire.nomPartenaire")
	public List <ProjetGroupByNomProjetTypeLocalisation> findByNomProjet(String nomProjet);
	
	@Query("select p from Projet p where CONCAT (p.nomProjet, ' ', p.responsable, ' ', p.type, ' ', p.statut, ' ', p.localisation.libelleLocalisation, ' ', p.partenaire.nomPartenaire, ' ', p.dateDebut, ' ', p.dateFin) like %?1%")
	public List<Projet> chercher(String motCle);

}
