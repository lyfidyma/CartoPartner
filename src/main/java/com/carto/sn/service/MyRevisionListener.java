package com.carto.sn.service;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

import com.carto.sn.entities.MyRevisionEntity;

public class MyRevisionListener implements RevisionListener{

	@Override
	public void newRevision(Object revisionEntity) {
		MyRevisionEntity myRevisionEntity = (MyRevisionEntity) revisionEntity;
		myRevisionEntity.setUserModificateur(getUsername());
		
	}
	
	private String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
