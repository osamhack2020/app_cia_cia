package com.hs.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hs.app.jwt.service.JwtService;
import com.hs.app.user.dao.UserDao;
import com.hs.app.user.service.UserService;
import com.hs.common.util.CookieUtil;
/*
 * armyacc2020!
 */
@Controller
public class MainController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired private JwtService jwtService;
	@Autowired private UserService userService;	
	@Autowired private UserDao userDao;
	

	@RequestMapping(value = "signout", method = RequestMethod.GET)
	public String signout(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		CookieUtil.deleteAllCookite(request, response);
        return "redirect:/signin";
	}
	
	
	@RequestMapping(value = "stat/basic", method = RequestMethod.GET)
	public String getAppPage(ModelMap model) {
        
        return "run:app";
	}
	
	
	
}
