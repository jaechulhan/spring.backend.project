/**
 * 
 */
package com.prolancer.core.security.xss;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author jaechulhan
 *
 */
public class XSSObjectMapper extends ObjectMapper {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8520916221875738712L;

	public XSSObjectMapper() {
        SimpleModule module = new SimpleModule("XSSStringJsonDeserializer");
        module.addDeserializer(String.class, new XSSStringJsonDeserializer());
        this.registerModule(module);
    }
}