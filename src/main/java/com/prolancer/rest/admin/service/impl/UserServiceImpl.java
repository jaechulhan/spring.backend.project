/**
 * 
 */
package com.prolancer.rest.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prolancer.rest.admin.entity.ReqUserVo;
import com.prolancer.rest.admin.entity.ResUserVo;
import com.prolancer.rest.admin.mapper.UserMapper;
import com.prolancer.rest.admin.service.UserService;

/**
 * @author jaechulhan
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper userMapper;

	/**
	 *
	 */
	@Override
	public ResUserVo getUserInfo(ReqUserVo reqUserVo) {
		return userMapper.selectUserInfo(reqUserVo);
	}

}
