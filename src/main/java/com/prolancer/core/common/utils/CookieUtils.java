/**
 * 
 */
package com.prolancer.core.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.prolancer.core.common.properties.SystemProperties;

/**
 * @author jaechulhan
 *
 */
@Component
public class CookieUtils {
	
	private final SystemProperties systemProperties;

	/**
	 * @param systemProperties
	 */
	public CookieUtils(SystemProperties systemProperties) {
		this.systemProperties = systemProperties;
	}
	
    /**
     * @param token
     * @param duration
     * @return
     */
    public Cookie createAccessTokenCookie(HttpServletRequest request, String token, int duration) {
        Cookie authenticationCookie = new Cookie(systemProperties.getAccessTokenCookieName(), token);
        authenticationCookie.setPath("/");
        authenticationCookie.setHttpOnly(true);
        authenticationCookie.setSecure(request.isSecure());
        authenticationCookie.setMaxAge(duration);
        return authenticationCookie;
    }

    /**
     * @param token
     * @param duration
     * @return
     */
    public Cookie createRefreshTokenCookie(HttpServletRequest request, String token, int duration) {
        Cookie authenticationCookie = new Cookie(systemProperties.getRefreshTokenCookieName(), token);
        authenticationCookie.setPath("/");
        authenticationCookie.setHttpOnly(true);
        authenticationCookie.setSecure(request.isSecure());
        authenticationCookie.setMaxAge(duration);
        return authenticationCookie;
    }

    /**
     * @return
     */
    public Cookie deleteAccessTokenCookie(HttpServletRequest request) {
        Cookie removeSessionCookie = new Cookie(systemProperties.getAccessTokenCookieName(), "");
        removeSessionCookie.setPath("/");
        removeSessionCookie.setMaxAge(0);
        removeSessionCookie.setHttpOnly(true);
        removeSessionCookie.setSecure(request.isSecure());
        return removeSessionCookie;
    }

}
