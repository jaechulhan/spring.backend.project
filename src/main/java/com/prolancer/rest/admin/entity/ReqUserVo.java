/**
 * 
 */
package com.prolancer.rest.admin.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author jaechulhan
 *
 */
public class ReqUserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8365630491836042155L;

	@JsonInclude(value = Include.NON_NULL)
	String username;
	@JsonInclude(value = Include.NON_NULL)
	String password;

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

	@Override
	public String toString() {
		return "ReqUserVo [username=" + username + ", password=" + password + "]";
	}

}
