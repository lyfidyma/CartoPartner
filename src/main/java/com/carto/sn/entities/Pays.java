package com.carto.sn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pays {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPays;
	@NotBlank(message="Renseigner le nom du pays")
	private String nomPays;
	
	
	public Pays(String nomPays) {
		super();
		this.nomPays = nomPays;
	}


	public Long getIdPays() {
		return idPays;
	}


	public void setIdPays(Long idPays) {
		this.idPays = idPays;
	}


	public String getNomPays() {
		return nomPays;
	}


	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}
	

}
