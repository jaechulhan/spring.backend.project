/**
 * 
 */
package com.prolancer.core.security.xss;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jaechulhan
 *
 */
@Configuration
public class XSSFilterConfig implements WebMvcConfigurer {

    /**
     * @return
     */
    @Bean
    public FilterRegistrationBean<XSSFilter> xssFiltrRegister() {
        FilterRegistrationBean<XSSFilter> registration = new FilterRegistrationBean<XSSFilter>();
        registration.setFilter(new XSSFilter());
        registration.addUrlPatterns("/*");
        registration.setName("XssFilter");
        registration.setOrder(1);
        return registration;
    }

	/**
	 *
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		WebMvcConfigurer.super.configureMessageConverters(converters);
		converters.add(htmlEscapingConveter());
	}

	/**
	 * @return
	 */
	private HttpMessageConverter<?> htmlEscapingConveter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
		MappingJackson2HttpMessageConverter htmlEscapingConverter = new MappingJackson2HttpMessageConverter();
		htmlEscapingConverter.setObjectMapper(objectMapper);
		return htmlEscapingConverter;
	}

	/**
	 * @author jaechulhan
	 *
	 */
	public static class HTMLCharacterEscapes extends CharacterEscapes {
		
		private static final long serialVersionUID = 528161737523148284L;
		private final int[] asciiEscapes;

		public HTMLCharacterEscapes() {
			asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
			asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
			asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
			asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
			asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
			asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
		}

		@Override
		public int[] getEscapeCodesForAscii() {
			return asciiEscapes;
		}

		// and this for others; we don't need anything special here
		@Override
		public SerializableString getEscapeSequence(int ch) {
			return new SerializedString(StringEscapeUtils.escapeHtml(Character.toString((char) ch)));
		}

	}
	
}
