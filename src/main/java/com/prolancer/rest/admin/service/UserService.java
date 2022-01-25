/**
 * 
 */
package com.prolancer.rest.admin.service;

import com.prolancer.rest.admin.entity.ReqUserVo;
import com.prolancer.rest.admin.entity.ResUserVo;

/**
 * @author jaechulhan
 *
 */
public interface UserService {
	
	/**
	 * @param reqUserVo
	 * @return
	 */
	public ResUserVo getUserInfo(ReqUserVo reqUserVo);

}
