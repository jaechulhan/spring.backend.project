/**
 * 
 */
package com.prolancer.core.security.xss;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

/**
 * @author jaechulhan
 *
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
	 private HttpServletRequest request;

	/**
	 * @param servletRequest
	 * @throws IOException 
	 */
	public XSSRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.request = request;
	}

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
		if (!StringUtils.isEmpty(value)) {
			value = StringEscapeUtils.escapeHtml(value);
		}
		return value;
    }

	@Override
	public String getParameter(String name) {
		String value = request.getParameter(name);
		if (!StringUtils.isEmpty(value)) {
			value = StringEscapeUtils.escapeHtml(value);
		}
		return value;
	}

	@Override
	public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = StringEscapeUtils.escapeHtml(values[i]);
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
	}
	
    @Override
    public Object getAttribute(String name) {
        if (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE.equals(name)) {
            @SuppressWarnings("unchecked")
			Map<String, Object> uriTemplateVars = (Map<String, Object>) super.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (Objects.isNull(uriTemplateVars)) {
                return uriTemplateVars;
            }
            Map<String, Object> newMap = new LinkedHashMap<>();
            uriTemplateVars.forEach((key, value) -> {
                if (value instanceof String) {
                    newMap.put(key, StringEscapeUtils.escapeHtml((String) value));
                } else {
                    newMap.put(key, value);
                }
            });
            return newMap;
        } else {
            return super.getAttribute(name);
        }
    }

}
