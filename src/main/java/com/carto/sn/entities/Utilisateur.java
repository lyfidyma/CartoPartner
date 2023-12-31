package com.carto.sn.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUtilisateur;
	//@NotBlank(message="Renseigner le nom")
	private String nomUtilisateur;
	//@NotBlank(message="Renseigner le prénom")
	private String prenomUtilisateur;
	private String login;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
    private Set<Profil> profil;
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
	
	

}
