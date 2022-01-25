/**
 * 
 */
package com.prolancer.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prolancer.core.common.constant.CommonValue;
import com.prolancer.core.common.properties.SystemProperties;
import com.prolancer.core.common.utils.SecurityCipher;
import com.prolancer.core.security.jwt.JWTTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * @author jaechulhan
 *
 */
@RestController
@RequestMapping(CommonValue.REST_API_PREFIX + "/auth")
public class AuthController {
	
	@Autowired
	SystemProperties systemProperties;
	
	@Autowired
	JWTTokenProvider jWTTokenProvider;

	@RequestMapping(value = "/refresh_token", method=RequestMethod.POST)
	public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, Object> param) {
        Map<String, String> resMap = new HashMap<String, String>();
        
        String refresh_token = (String) param.get("refresh_token");
        
		try {
			String decryptedRefreshToken = SecurityCipher.decrypt(refresh_token);
			
			Jws<Claims> claimsJws = Jwts.parserBuilder()
				    .setSigningKey(jWTTokenProvider.refreshSecretKey())
				    .build() 
				    .parseClaimsJws(decryptedRefreshToken);
			
			Claims body = claimsJws.getBody();
			
			String username = body.getSubject();
			
			@SuppressWarnings("unchecked")
			List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
			
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
						.map(m -> new SimpleGrantedAuthority(m.get("authority")))
						.collect(Collectors.toSet());
			
			String access_token = jWTTokenProvider.createToken(username, simpleGrantedAuthorities);
			
	        resMap.put("access_token", access_token);
	        resMap.put("refresh_token", refresh_token);
		} catch (IllegalArgumentException | JwtException e) {
			throw new AccessDeniedException(String.format("Token %s cannot be trust", refresh_token));
		}
		
		return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
	}
}
