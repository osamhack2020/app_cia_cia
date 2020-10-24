package com.hs.common.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/error/")
public class ErrorController extends BaseController {
	
	// HTTP 404 ( Not Found )
	@RequestMapping(value = "404", method = RequestMethod.GET)
	public String noteFoundError() {
		
		return "error/404";
	}

	// HTTP 500 ( Runtime Error )
	@RequestMapping(value = "500", method = RequestMethod.GET)
	public String runtimeError() {
		
		return "error/500";
	}
	
	// HTTP 403 ( Access Denied )
	@RequestMapping(value = "403", method = RequestMethod.GET)
	public String accessDenied(HttpServletRequest request,ModelMap model) {
			
		return "error/403";
	}

}
