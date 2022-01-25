/**
 * 
 */
package com.prolancer.core.security;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import com.prolancer.core.common.properties.SystemProperties;
import com.prolancer.core.service.LoginUserService;

/**
 * @author jaechulhan
 *
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	private final LoginUserService loginUserService;
	private final SystemProperties systemProperties;

	@Autowired
	public WebSecurityConfig(PasswordEncoder passwordEncoder,
			LoginUserService loginUserService,
			SystemProperties systemProperties) {
		this.passwordEncoder = passwordEncoder;
		this.loginUserService = loginUserService;
		this.systemProperties = systemProperties;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**")
			.and().debug(false);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().configurationSource(request -> {
					CorsConfiguration cors = new CorsConfiguration();
					cors.setAllowedOrigins(Arrays.asList(systemProperties.getAccessControlAllowOrigin()));
					cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					cors.setAllowedHeaders(Arrays.asList("*"));
					return cors;
			    });
		http
			.csrf().disable();
		
		http
			.formLogin()
			.and()
				.authorizeRequests()
					.antMatchers("/", "/index").permitAll()
					.anyRequest().authenticated();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(loginUserService);
		return provider;
	}
	
}
