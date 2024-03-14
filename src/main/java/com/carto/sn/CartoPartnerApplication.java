package com.carto.sn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class CartoPartnerApplication  extends SpringBootServletInitializer{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
    {
        return application.sources(CartoPartnerApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(CartoPartnerApplication.class, args);
	}
	
	 
	

}
