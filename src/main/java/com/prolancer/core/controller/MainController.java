/**
 * 
 */
package com.prolancer.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jaechulhan
 *
 */
@Controller
public class MainController {
	
	@RequestMapping(value = {"/", "index"}, method=RequestMethod.GET)
	public String index() {
		return "index";
	}

}
