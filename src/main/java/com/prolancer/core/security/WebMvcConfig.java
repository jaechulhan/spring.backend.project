/**
 * 
 */
package com.prolancer.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jaechulhan
 *
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;

	/**
	 * @param authInterceptor
	 * @param objectMapper
	 */
	public WebMvcConfig(AuthInterceptor authInterceptor) {
		this.authInterceptor = authInterceptor;
	}

	/**
	 *
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor).excludePathPatterns("/", "/index", "/login", "/logout", "/error**",
				"/rest/v1/auth", "/rest/v1/auth/refresh_token");
	}

}
