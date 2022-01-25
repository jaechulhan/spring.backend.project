/**
 * 
 */
package com.prolancer.core.common.exception;

import java.util.Date;

/**
 * @author jaechulhan
 *
 */
public class ExceptionResponse {

	private String error;
	private String message;
	private Date timestamp;
	
	/**
	 * @param error
	 * @param message
	 * @param timestamp
	 */
	public ExceptionResponse(String error, String message, Date timestamp) {
		this.error = error;
		this.message = message;
		this.timestamp = timestamp;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
