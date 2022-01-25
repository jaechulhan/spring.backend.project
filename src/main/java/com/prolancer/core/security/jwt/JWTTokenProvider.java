/**
 * 
 */
package com.prolancer.core.security.jwt;

import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.prolancer.core.common.properties.SystemProperties;
import com.prolancer.core.common.utils.SecurityCipher;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * @author jaechulhan
 *
 */
@Component
public class JWTTokenProvider {
	private final SystemProperties systemProperties;
	
	/**
	 * @param systemProperties
	 */
	public JWTTokenProvider(SystemProperties systemProperties) {
		this.systemProperties = systemProperties;
	}

	/**
	 * @param authResult
	 * @return
	 */
	public String createToken(String username, Collection<? extends GrantedAuthority> grantedAuthorities) {
        Date createdDate = new Date();
        Long tokenDuration = createdDate.getTime() + systemProperties.getJwtTokenExpiration();
        Date tokenExpiryDate = new Date(tokenDuration);
		
		String accessToken = Jwts.builder()
				.setSubject(username)
				.claim("authorities", grantedAuthorities)
				.setIssuedAt(new Date())
				.setExpiration(tokenExpiryDate)
				.signWith(secretKey())
				.compact();

		return SecurityCipher.encrypt(accessToken);
	}

	/**
	 * @param authResult
	 * @return
	 */
	public String createRefreshToken(String username, Collection<? extends GrantedAuthority> grantedAuthorities) {
        Date createdDate = new Date();
        Long tokenDuration = createdDate.getTime() + systemProperties.getJwtRefreshTokenExpiration();
        Date tokenExpiryDate = new Date(tokenDuration);
		
		String refreshToken = Jwts.builder()
				.setSubject(username)
				.claim("authorities", grantedAuthorities)
				.setIssuedAt(new Date())
				.setExpiration(tokenExpiryDate)
				.signWith(refreshSecretKey())
				.compact();

		return SecurityCipher.encrypt(refreshToken);
	}

	/**
	 * @return
	 */
	public SecretKey secretKey() {
		return Keys.hmacShaKeyFor(systemProperties.getJwtSecretKey().getBytes());
	}

	/**
	 * @return
	 */
	public SecretKey refreshSecretKey() {
		return Keys.hmacShaKeyFor(systemProperties.getJwtRefreshSecretKey().getBytes());
	}

}
