package com.carto.sn.entities;

import java.util.Set;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Audited(withModifiedFlag = true)
public class Utilisateur {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUtilisateur;
	@NotEmpty(message="Renseigner le nom")
	private String nomUtilisateur;
	@NotEmpty(message="Renseigner le prénom")
	private String prenomUtilisateur;
	@NotEmpty(message="Renseigner le login")
	private String login;
	@NotAudited
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@NotAudited
    private Set<Profil> profil;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Projet> projet;
	
	public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String login, String password) {
		super();
		this.nomUtilisateur = nomUtilisateur;
		this.prenomUtilisateur = prenomUtilisateur;
		this.login = login;
		this.password = password;
	}
	public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String login, String password,
			Set<Profil> profil) {
		super();
		this.nomUtilisateur = nomUtilisateur;
		this.prenomUtilisateur = prenomUtilisateur;
		this.login = login;
		this.password = password;
		this.profil = profil;
	}
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	public String getNomUtilisateur() {
		return nomUtilisateur;
	}
	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}
	public String getPrenomUtilisateur() {
		return prenomUtilisateur;
	}
	public void setPrenomUtilisateur(String prenomUtilisateur) {
		this.prenomUtilisateur = prenomUtilisateur;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Profil> getProfil() {
		return profil;
	}
	public void setProfil(Set<Profil> profil) {
		this.profil = profil;
	}
	public Set<Projet> getProjet() {
		return projet;
	}
	public void setProjet(Set<Projet> projet) {
		this.projet = projet;
	}

	
	
	
	
	
	

}
