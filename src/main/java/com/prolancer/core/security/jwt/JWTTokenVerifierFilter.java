/**
 * 
 */
package com.prolancer.core.security.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prolancer.core.common.exception.ExceptionResponse;
import com.prolancer.core.common.properties.SystemProperties;
import com.prolancer.core.common.utils.SecurityCipher;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * @author jaechulhan
 *
 */
public class JWTTokenVerifierFilter extends OncePerRequestFilter {
	
	private final Logger logger = LoggerFactory.getLogger(JWTTokenVerifierFilter.class);
	
	private final SystemProperties systemProperties;
	
	/**
	 * @param systemProperties
	 */
	public JWTTokenVerifierFilter(SystemProperties systemProperties) {
		this.systemProperties = systemProperties;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request
			, HttpServletResponse response
			, FilterChain filterChain) throws ServletException, IOException {
		
		String accessToken = null;
		String authorizationHeader = request.getHeader(systemProperties.getAuthroizationHeader());
		
		// 01. Check Header Token value
		/* Removed - JH - 05/29/2021 - No more use - Cookie
		if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(systemProperties.getJwtTokenPrefix())) {
			// 02. If no header token, check cookie value
			accessToken = WebUtils.getCookie(request, systemProperties.getAccessTokenCookieName()).getValue();
			if(Strings.isNullOrEmpty(accessToken)) {
				filterChain.doFilter(request, response);
				return;	
			}
			logger.info("[COOKIE] access_token : {}", accessToken);
		} else {
			accessToken = authorizationHeader.replace(systemProperties.getJwtTokenPrefix(), "");
			logger.info("[HEADER] access_token : {}", accessToken);
		}
		*/

		accessToken = authorizationHeader.replace(systemProperties.getJwtTokenPrefix(), "");
		logger.info("[HEADER] access_token : {}", accessToken);
		
		try {
			String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
			
			Jws<Claims> claimsJws = Jwts.parserBuilder()
				    .setSigningKey(systemProperties.secretKey())
				    .build() 
				    .parseClaimsJws(decryptedAccessToken);
			
			Claims body = claimsJws.getBody();
			
			String username = body.getSubject();
			
			@SuppressWarnings("unchecked")
			List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
			
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
						.map(m -> new SimpleGrantedAuthority(m.get("authority")))
						.collect(Collectors.toSet());
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					username, 
					null,
					simpleGrantedAuthorities
			);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			filterChain.doFilter(request, response);
		} catch (IllegalArgumentException | JwtException e) {
			// Response : Header + JSON Body
			PrintWriter out = response.getWriter();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			ExceptionResponse exceptionResponse = new ExceptionResponse("Token Validation Error", accessToken, new Date());
			String responseJson = new ObjectMapper().writeValueAsString(exceptionResponse);
			out.print(responseJson);
			out.flush();
			return;
		}

	}

}
