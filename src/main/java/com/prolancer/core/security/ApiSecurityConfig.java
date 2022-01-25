/**
 * 
 */
package com.prolancer.core.security;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import com.prolancer.core.common.constant.CommonValue;
import com.prolancer.core.common.properties.SystemProperties;
import com.prolancer.core.security.jwt.JWTAccessDeniedHandler;
import com.prolancer.core.security.jwt.JWTAuthenticationEntryPoint;
import com.prolancer.core.security.jwt.JWTAuthenticationFilter;
import com.prolancer.core.security.jwt.JWTTokenProvider;
import com.prolancer.core.security.jwt.JWTTokenVerifierFilter;
import com.prolancer.core.service.LoginUserService;

/**
 * @author jaechulhan
 *
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	private final LoginUserService loginUserService;
	private final JWTTokenProvider jWTTokenProvider;
	private final SystemProperties systemProperties;

	/**
	 * @param passwordEncoder
	 * @param loginUserService
	 * @param jwtConfig
	 * @param jwtSecretKey
	 */
	@Autowired
	public ApiSecurityConfig(PasswordEncoder passwordEncoder, 
			LoginUserService loginUserService, JWTTokenProvider jWTTokenProvider,
			SystemProperties systemProperties) {
		this.passwordEncoder = passwordEncoder;
		this.loginUserService = loginUserService;
		this.jWTTokenProvider = jWTTokenProvider;
		this.systemProperties = systemProperties;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher(CommonValue.REST_API_PREFIX.concat("/**")) //<= Security only available for /rest/**
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, CommonValue.REST_API_PREFIX.concat("/auth")).permitAll()
				.antMatchers(HttpMethod.POST, CommonValue.REST_API_PREFIX.concat("/auth/refresh_token")).permitAll()
				.anyRequest().authenticated();
		
		http
			.cors().configurationSource(request -> {
				CorsConfiguration cors = new CorsConfiguration();
				cors.setAllowedOrigins(Arrays.asList("*"));
				cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
				cors.setAllowedHeaders(Arrays.asList("*"));
				return cors;
		    });
		
		http
			.csrf().disable()
			.formLogin().disable()
            .httpBasic().disable();
		
		http
			.exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
			.and()
				.exceptionHandling().accessDeniedHandler(new JWTAccessDeniedHandler());
		
		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		JWTAuthenticationFilter jWTAuthenticationFilter 
				= new JWTAuthenticationFilter(authenticationManager(), jWTTokenProvider);
		jWTAuthenticationFilter.setFilterProcessesUrl(systemProperties.getJwtAuthUrl());
		
		http
			.addFilter(jWTAuthenticationFilter)
			.addFilterAfter(new JWTTokenVerifierFilter(systemProperties), JWTAuthenticationFilter.class);
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
