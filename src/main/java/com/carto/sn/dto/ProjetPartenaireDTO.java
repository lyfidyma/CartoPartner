package com.carto.sn.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjetPartenaireDTO {
	private String nomProjet ;
	private String[] nomPartenaire;
	private String[] nomRegion;
	private String[] nomDepartement;
	private String[] nomCommune;
	private String[] nomVillage;
	private String[] latitude;
	private String[] longitude;
	private String[] nomPartenaireLocal;
	
	

}
