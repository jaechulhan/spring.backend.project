/**
 * 
 */
package com.prolancer.rest.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.prolancer.rest.admin.entity.ReqUserVo;
import com.prolancer.rest.admin.entity.ResUserVo;

/**
 * @author jaechulhan
 *
 */
@Mapper
public interface UserMapper {
	
	/**
	 * @param reqUserVo
	 * @return
	 */
	ResUserVo selectUserInfo(ReqUserVo reqUserVo);

}
