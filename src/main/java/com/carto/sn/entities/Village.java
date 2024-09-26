package com.carto.sn.entities;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Village {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idVillage;
	private String nomVillage;
	private String latitude;
	private String longitude;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idCommune")
	private Commune commune;
	
	
	public Village(String nomVillage, Commune commune) {
		super();
		this.nomVillage = nomVillage;
		this.commune = commune;
	}


	public Long getIdVillage() {
		return idVillage;
	}


	public void setIdVillage(Long idVillage) {
		this.idVillage = idVillage;
	}


	public String getNomVillage() {
		return nomVillage;
	}


	public void setNomVillage(String nomVillage) {
		this.nomVillage = nomVillage;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public Commune getCommune() {
		return commune;
	}


	public void setCommune(Commune commune) {
		this.commune = commune;
	}

}
