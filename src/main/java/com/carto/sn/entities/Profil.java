package com.carto.sn.entities;

import java.util.Set;

import jakarta.persistence.Entity;
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
	
	@ManyToMany
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
	
	

}
