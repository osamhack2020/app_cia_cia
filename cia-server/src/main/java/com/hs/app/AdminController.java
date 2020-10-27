package com.hs.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

	@RequestMapping(value = "stat/basic", method = RequestMethod.GET)
	public String getAppPage(ModelMap model) {
        
        return "run:app";  
	}
	
}
