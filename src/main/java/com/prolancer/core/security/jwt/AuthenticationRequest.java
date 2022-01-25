/**
 * 
 */
package com.prolancer.core.security.jwt;

/**
 * @author jaechulhan
 *
 */
public class AuthenticationRequest {
	
	private String username;
	private String password;
	
	/**
	 * @param username
	 * @param password
	 */
	public AuthenticationRequest() {
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}