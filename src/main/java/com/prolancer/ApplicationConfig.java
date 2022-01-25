/**
 * 
 */
package com.prolancer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author jaechulhan
 *
 */
@Configuration
public class ApplicationConfig {
	
	private static final String MESSAGE_SOURCE_BASE = "classpath:config/messages/message";
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_SOURCE_BASE);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(-1);
		return messageSource;
	}
	
	@Bean
	public MessageSourceAccessor getMesssageSourceAccessor() {
		ReloadableResourceBundleMessageSource messageSource = messageSource();
		return new MessageSourceAccessor(messageSource);
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		CommonsRequestLoggingFilter logFilter = new CommonsRequestLoggingFilter();
		logFilter.setIncludeQueryString(true);
		logFilter.setIncludePayload(true);
		logFilter.setMaxPayloadLength(10000);
		logFilter.setIncludeHeaders(true);
		logFilter.setBeforeMessagePrefix("[BEFORE] REQUEST DATA : ");
		logFilter.setAfterMessagePrefix("[AFTER] REQUEST DATA : ");
		return logFilter;
	}

}
