package com.carto.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carto.sn.entities.PartenaireLocal;

public interface PartenaireLocalRepository extends JpaRepository<PartenaireLocal, Long> {
	
	public PartenaireLocal findByNomPartenaireLocal(String nomPartenaireLocal);

}
