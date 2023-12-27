package com.carto.sn.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {
	/**
	 * Emplacement de sauvegarde des images
	 */
	private String location = "C:/Users/dell/eclipse-workspace/CartoPartner/src/main/resources/static/img";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
