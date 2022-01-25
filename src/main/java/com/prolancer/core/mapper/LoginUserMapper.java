/**
 * 
 */
package com.prolancer.core.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prolancer.core.entity.LoginUserVo;

/**
 * @author jaechulhan
 *
 */
@Mapper
public interface LoginUserMapper {
	
	public LoginUserVo selectUserByUsername(String username) throws UsernameNotFoundException;
	
	public Set<SimpleGrantedAuthority> selectRoleByUsername(String username);
	
}
