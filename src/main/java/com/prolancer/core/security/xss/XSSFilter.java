package com.prolancer.core.security.xss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * @author jaechulhan
 *
 */
@Component
@WebFilter
public class XSSFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest xssRequest = (HttpServletRequest) request;
		XSSRequestWrapper xssRequestWrapper = new XSSRequestWrapper(xssRequest);
		chain.doFilter(xssRequestWrapper, response);
	}

	@Override
	public void destroy() {
	}
}