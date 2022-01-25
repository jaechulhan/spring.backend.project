/**
 * 
 */
package com.prolancer.core.security.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jaechulhan
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JWTTokenProvider jWTTokenProvider;
	
	/**
	 * @param authenticationManager
	 */
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, 
			JWTTokenProvider jWTTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jWTTokenProvider = jWTTokenProvider;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request
			, HttpServletResponse response) throws AuthenticationException {
		try {
			AuthenticationRequest authenticationRequest = new ObjectMapper()
					.readValue(request.getInputStream(), AuthenticationRequest.class);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), 
					authenticationRequest.getPassword()
			);
			
			Authentication authenticated = authenticationManager.authenticate(authentication);
			
			return authenticated;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request
			, HttpServletResponse response
			, FilterChain chain
			, Authentication authResult) throws IOException, ServletException {
		
		String access_token = jWTTokenProvider.createToken(authResult.getName(), authResult.getAuthorities());
		String refresh_token = jWTTokenProvider.createRefreshToken(authResult.getName(), authResult.getAuthorities());

		// Response : Header + JSON Body
		PrintWriter out = response.getWriter();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		/* Removed - JH - 05/29/2021 - No more use - Cookie
		response.addCookie(cookieUtils.createAccessTokenCookie(request, access_token,
				(int) systemProperties.getJwtTokenExpiration() / 1000));
		response.addCookie(cookieUtils.createRefreshTokenCookie(request, refresh_token,
				(int) systemProperties.getJwtRefreshTokenExpiration() / 1000));
		*/
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("access_token", access_token);
		resMap.put("refresh_token", refresh_token);
		String responseJson = new ObjectMapper().writeValueAsString(resMap);
		out.print(responseJson);
		out.flush();
	}

}
