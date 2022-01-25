/**
 * 
 */
package com.prolancer.core.common.properties;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;

import io.jsonwebtoken.security.Keys;

/**
 * @author jaechulhan
 *
 */
@Configuration
@PropertySource({ 
	"classpath:config/properties/system-${spring.profiles.active:local}.properties"
})
public class SystemProperties {
	
	@Value("${access.control.allow.origin}")
	private String accessControlAllowOrigin;
	
	@Value("${jwt.auth.url}")
	private String jwtAuthUrl;
	
	@Value("${jwt.token.prefix}")
	private String jwtTokenPrefix;
	
	@Value("${jwt.secret.key}")
	private String jwtSecretKey;
	
	@Value("${jwt.token.expiration}")
	private long jwtTokenExpiration;
	
	@Value("${jwt.refresh.secret.key}")
	private String jwtRefreshSecretKey;
	
	@Value("${jwt.refresh.token.expiration}")
	private long jwtRefreshTokenExpiration;
	
	@Value("${jwt.access.token.cookie.name}")
    private String accessTokenCookieName;

    @Value("${jwt.refresh.token.cookie.name}")
    private String refreshTokenCookieName;

	/**
	 * @return the accessControlAllowOrigin
	 */
	public String getAccessControlAllowOrigin() {
		return accessControlAllowOrigin;
	}

	/**
	 * @param accessControlAllowOrigin the accessControlAllowOrigin to set
	 */
	public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
		this.accessControlAllowOrigin = accessControlAllowOrigin;
	}

	/**
	 * @return the jwtAuthUrl
	 */
	public String getJwtAuthUrl() {
		return jwtAuthUrl;
	}

	/**
	 * @param jwtAuthUrl the jwtAuthUrl to set
	 */
	public void setJwtAuthUrl(String jwtAuthUrl) {
		this.jwtAuthUrl = jwtAuthUrl;
	}

	/**
	 * @return the jwtTokenPrefix
	 */
	public String getJwtTokenPrefix() {
		return jwtTokenPrefix;
	}

	/**
	 * @param jwtTokenPrefix the jwtTokenPrefix to set
	 */
	public void setJwtTokenPrefix(String jwtTokenPrefix) {
		this.jwtTokenPrefix = jwtTokenPrefix;
	}

	/**
	 * @return the jwtSecretKey
	 */
	public String getJwtSecretKey() {
		return jwtSecretKey;
	}

	/**
	 * @param jwtSecretKey the jwtSecretKey to set
	 */
	public void setJwtSecretKey(String jwtSecretKey) {
		this.jwtSecretKey = jwtSecretKey;
	}

	/**
	 * @return the jwtTokenExpiration
	 */
	public long getJwtTokenExpiration() {
		return jwtTokenExpiration;
	}

	/**
	 * @param jwtTokenExpiration the jwtTokenExpiration to set
	 */
	public void setJwtTokenExpiration(Integer jwtTokenExpiration) {
		this.jwtTokenExpiration = jwtTokenExpiration;
	}

	/**
	 * @return the jwtRefreshSecretKey
	 */
	public String getJwtRefreshSecretKey() {
		return jwtRefreshSecretKey;
	}

	/**
	 * @param jwtRefreshSecretKey the jwtRefreshSecretKey to set
	 */
	public void setJwtRefreshSecretKey(String jwtRefreshSecretKey) {
		this.jwtRefreshSecretKey = jwtRefreshSecretKey;
	}

	/**
	 * @return the jwtRefreshTokenExpiration
	 */
	public long getJwtRefreshTokenExpiration() {
		return jwtRefreshTokenExpiration;
	}

	/**
	 * @param jwtRefreshTokenExpiration the jwtRefreshTokenExpiration to set
	 */
	public void setJwtRefreshTokenExpiration(Integer jwtRefreshTokenExpiration) {
		this.jwtRefreshTokenExpiration = jwtRefreshTokenExpiration;
	}

	/**
	 * @return the accessTokenCookieName
	 */
	public String getAccessTokenCookieName() {
		return accessTokenCookieName;
	}

	/**
	 * @param accessTokenCookieName the accessTokenCookieName to set
	 */
	public void setAccessTokenCookieName(String accessTokenCookieName) {
		this.accessTokenCookieName = accessTokenCookieName;
	}

	/**
	 * @return the refreshTokenCookieName
	 */
	public String getRefreshTokenCookieName() {
		return refreshTokenCookieName;
	}

	/**
	 * @param refreshTokenCookieName the refreshTokenCookieName to set
	 */
	public void setRefreshTokenCookieName(String refreshTokenCookieName) {
		this.refreshTokenCookieName = refreshTokenCookieName;
	}

	/**
	 * @return
	 */
	public String getAuthroizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}

	/**
	 * @return
	 */
	public SecretKey secretKey() {
		return Keys.hmacShaKeyFor(getJwtSecretKey().getBytes());
	}

	/**
	 * @return
	 */
	public SecretKey refreshSecretKey() {
		return Keys.hmacShaKeyFor(getJwtRefreshSecretKey().getBytes());
	}
}
