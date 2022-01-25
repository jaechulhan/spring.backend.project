/**
 * 
 */
package com.prolancer.core.entity;

import java.io.Serializable;

/**
 * @author jaechulhan
 *
 */
public class LoginUserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8587561366557665907L;

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
