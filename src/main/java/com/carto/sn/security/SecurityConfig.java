package com.carto.sn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.carto.sn.service.UserDetail;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	
	
    @Autowired
    UserDetail userDetail; 
//    @Autowired
//    public void configureUserDetailAuthentication( AuthenticationManagerBuilder auth) throws Exception {
//    	auth.inMemoryAuthentication()
//        .withUser("admin")
//        .password("$2a$10$au1KPeWof3cvsjfKUGpWMOcssSQz2BFVYKzcar3hIOZF0RuJm9f.q")
//        .roles("USER", "ADMIN");
//         auth.userDetailsService(userDetail);
//    }
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception{
    	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    	
    	authenticationManagerBuilder.inMemoryAuthentication()
    	.withUser("admin")
    	.password("$2a$10$au1KPeWof3cvsjfKUGpWMOcssSQz2BFVYKzcar3hIOZF0RuJm9f.q")
    	.roles("USER", "ADMIN");
    	authenticationManagerBuilder.userDetailsService(userDetail);
    	return authenticationManagerBuilder.build();
    } 
    
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity, AuthenticationManager authManager) throws Exception {    	
       //  MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        httpSecurity
        .formLogin(formLogin -> (formLogin
        		.loginPage("/login")
        		.defaultSuccessUrl("/index", true))
        		.permitAll())
        		.logout(logout -> logout.invalidateHttpSession(true)
        		.clearAuthentication(true).logoutRequestMatcher((RequestMatcher)new AntPathRequestMatcher("/logout"))
        		.logoutSuccessUrl("/login?logout")
        		.permitAll())
		.csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(Customizer.withDefaults())
        .anonymous((anonymous) ->	anonymous.disable())
        .authorizeHttpRequests(auth -> auth
//        		.requestMatchers(AntPathRequestMatcher.antMatcher("https://maxcdn.bootstrapcdn.com/**" ))
//        		.permitAll()
       
        		.requestMatchers(AntPathRequestMatcher.antMatcher("/img/**"))
        		.permitAll()
       
        		.requestMatchers(AntPathRequestMatcher.antMatcher("https://nominatim.openstreetmap.org/**"))
        		.permitAll()
        
        		.requestMatchers(AntPathRequestMatcher.antMatcher("http://{s}.tile.openstreetmap.org/**"))
        		.permitAll()
       
        		.requestMatchers(AntPathRequestMatcher.antMatcher("https://{s}.tile.openstreetmap.org/**"))
				.permitAll()
				
				.requestMatchers(AntPathRequestMatcher.antMatcher("/actuator/**"))
				.permitAll()
       
//        		.requestMatchers(AntPathRequestMatcher.antMatcher("/user/**" )).hasAuthority("USER")
//       
//        		.requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**" )).hasAuthority("ADMIN")
        		.anyRequest().authenticated())
        .authenticationManager(authManager);
        
      
        return httpSecurity.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   
}
