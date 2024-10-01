package com.carto.sn.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Profil {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProfil;
	@NotBlank(message="Renseigner le profil")
	private String nomProfil;
	
	@ManyToMany(mappedBy="profil")
	private Set<Utilisateur> utilisateur;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Privilege> privilege;
	public Profil(String nomProfil) {
		super();
		this.nomProfil = nomProfil;
	}


	public Long getIdProfil() {
		return idProfil;
	}


	public void setIdProfil(Long idProfil) {
		this.idProfil = idProfil;
	}


	public String getNomProfil() {
		return nomProfil;
	}


	public void setNomProfil(String nomProfil) {
		this.nomProfil = nomProfil;
	}


	public Set<Utilisateur> getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Set<Utilisateur> utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Set<Privilege> getPrivilege() {
		return privilege;
	}


	public void setPrivilege(Set<Privilege> privilege) {
		this.privilege = privilege;
	}


	public Profil(Long idProfil, Set<Privilege> privilege) {
		super();
		this.idProfil = idProfil;
		this.privilege = privilege;
	}
	
	

}
