/**
 * 
 */
package com.prolancer.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prolancer.core.entity.LoginUserVo;
import com.prolancer.core.mapper.LoginUserMapper;

/**
 * @author jaechulhan
 *
 */
@Service
public class LoginUserService implements UserDetailsService {

	@Autowired
	LoginUserMapper loginUserMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUserVo loginUser = loginUserMapper.selectUserByUsername(username);
		
		if (loginUser == null) {
			throw new UsernameNotFoundException(String.format("A user doesn't exist. [%s]", username));
		}
		
		UserDetails userDetails = new User(loginUser.getUsername(), 
				loginUser.getPassword(), 
				true, true, true, true,
				loginUserMapper.selectRoleByUsername(username));
		
		return userDetails;
	}

}
