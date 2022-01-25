/**
 * 
 */
package com.prolancer.core.security.xss;

import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author jaechulhan
 *
 */
public class XSSStringJsonDeserializer extends JsonDeserializer<String> {

	@Override
	public Class<String> handledType() {
		return String.class;
	}

	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		String value = jsonParser.getValueAsString();
		if (value != null) {
			return StringEscapeUtils.escapeHtml(value);
		}
		return null;
	}
}
