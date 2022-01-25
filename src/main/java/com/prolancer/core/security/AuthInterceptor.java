/**
 * 
 */
package com.prolancer.core.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jaechulhan
 *
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	private static final String REQ_URI_METHOD_FORMAT = "%s:%s";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(String.format("URI :{%s}, METHOD : {%s}", 
				request.getRequestURI(), request.getMethod()));
		
		Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
		
		if (authenticated != null) {
			for (GrantedAuthority authority : authenticated.getAuthorities()) {
				System.out.println("Auth : " + ((SimpleGrantedAuthority) authority).getAuthority());
			}
		}
		
		boolean isAuthorized = false;
		
		String reqMethodAndUri = String.format(REQ_URI_METHOD_FORMAT, 
				request.getMethod(), request.getRequestURI().substring(request.getContextPath().length()));
		
		if (authenticated != null) {
			for (GrantedAuthority authority : authenticated.getAuthorities()) {
				if (authority != null && reqMethodAndUri
						.startsWith((((SimpleGrantedAuthority) authority).getAuthority()).toString())) {
					isAuthorized = true;
					break;
				}
			}
		}
		
		if (!isAuthorized) {
			response.sendError(HttpStatus.FORBIDDEN.value(), "Access is denied.");
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
