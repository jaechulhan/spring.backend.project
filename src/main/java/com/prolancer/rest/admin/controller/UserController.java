/**
 * 
 */
package com.prolancer.rest.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prolancer.core.common.constant.CommonValue;
import com.prolancer.rest.admin.entity.ReqUserVo;
import com.prolancer.rest.admin.entity.ResUserVo;
import com.prolancer.rest.admin.service.UserService;

/**
 * @author jaechulhan
 *
 */
@RestController
@RequestMapping(CommonValue.REST_API_PREFIX + "/user")
public class UserController {
	
	@Autowired
	UserService userService;

	/**
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/info", method=RequestMethod.GET)
	public ResUserVo getUserInfo(@RequestParam String username) {
		ReqUserVo reqUserVo = new ReqUserVo();
		reqUserVo.setUsername(username);
		return userService.getUserInfo(reqUserVo);
	}

	/**
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/info/{username}", method=RequestMethod.GET)
	public ResUserVo getUserInfoByPathVariable(@PathVariable("username") String username) {
		ReqUserVo reqUserVo = new ReqUserVo();
		reqUserVo.setUsername(username);
		return userService.getUserInfo(reqUserVo);
	}

	/**
	 *
	 * @param reqUserVo
	 * @return
	 */
	@RequestMapping(value = "/info_body", method={RequestMethod.GET, RequestMethod.POST})
	public ResUserVo getUserInfoByRequestBody(@RequestBody ReqUserVo reqUserVo) {
		ResUserVo resUserVo = new ResUserVo();
		resUserVo.setUsername(reqUserVo.getUsername());
		resUserVo.setPassword(reqUserVo.getPassword());
		return resUserVo;
	}

	/**
	 *
	 * @param reqUserVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exception", method={RequestMethod.GET, RequestMethod.POST})
	public ResUserVo getException(@RequestBody ReqUserVo reqUserVo) throws Exception {
		ResUserVo resUserVo = new ResUserVo();
		resUserVo.setUsername(reqUserVo.getUsername());
		resUserVo.setPassword(reqUserVo.getPassword());
		throw new Exception("GlobalExceptionHandler Testring ...");
	}
}
