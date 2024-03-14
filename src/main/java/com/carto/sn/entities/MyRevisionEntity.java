package com.carto.sn.entities;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import com.carto.sn.service.MyRevisionListener;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@RevisionEntity(MyRevisionListener.class)
@Table(name="revinfo")
public class MyRevisionEntity extends DefaultRevisionEntity{
	private String userModificateur;
	

}
