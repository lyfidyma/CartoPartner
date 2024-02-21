package com.carto.sn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.carto.sn.service.UserDetail;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	
	@Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetail userDetail;
    
    @Autowired
    public void configureUserDetailAuthentication( AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
        .withUser("admin")
        .password("$2a$10$au1KPeWof3cvsjfKUGpWMOcssSQz2BFVYKzcar3hIOZF0RuJm9f.q")
        .roles("USER", "ADMIN");
        auth.userDetailsService(userDetail);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
    	
    	 
         
        httpSecurity.formLogin(formLogin -> (formLogin
        		.loginPage("/login")
        		.defaultSuccessUrl("/index", true))
        		.permitAll())
        		.logout(logout -> logout.invalidateHttpSession(true)
        		.clearAuthentication(true).logoutRequestMatcher((RequestMatcher)new AntPathRequestMatcher("/logout"))
        		.logoutSuccessUrl("/login?logout")
        		.permitAll());
        httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(Customizer.withDefaults())
        .anonymous((anonymous) ->
			anonymous.disable());
        httpSecurity
        .authorizeHttpRequests(auth -> auth
        		.requestMatchers(AntPathRequestMatcher.antMatcher("https://maxcdn.bootstrapcdn.com/**" ))
        		.permitAll()
       
        		.requestMatchers(AntPathRequestMatcher.antMatcher("/img/**"))
        		.permitAll()
       
        		.requestMatchers(AntPathRequestMatcher.antMatcher("https://nominatim.openstreetmap.org/**"))
        		.permitAll()
        
        		.requestMatchers(AntPathRequestMatcher.antMatcher("http://{s}.tile.openstreetmap.org/**"))
        		.permitAll()
       
        		.requestMatchers(AntPathRequestMatcher.antMatcher("https://{s}.tile.openstreetmap.org/**"))
				.permitAll()
       
        		.requestMatchers(AntPathRequestMatcher.antMatcher("/user/**" )).hasAuthority("USER")
       
        		.requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**" )).hasAuthority("ADMIN")
        
        		.anyRequest().authenticated());
      
        return httpSecurity.build();
    }
 

}
