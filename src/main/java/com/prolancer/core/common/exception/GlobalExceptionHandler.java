/**
 * 
 */
package com.prolancer.core.common.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author jaechulhan
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception exception, WebRequest webRequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(),
				webRequest.getDescription(false), new Date());
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	public Object handleException(Exception e) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
		ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), "Unknown Exception", new Date());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
