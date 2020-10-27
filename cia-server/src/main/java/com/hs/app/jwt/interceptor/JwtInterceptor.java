package com.hs.app.jwt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hs.app.jwt.service.JwtService;
import com.hs.app.user.dao.UserDao;

public class JwtInterceptor implements HandlerInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String HEADER_AUTH = "Authorization";

	@Autowired private JwtService jwtService;
	@Autowired private UserDao userDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		final String token = request.getHeader(HEADER_AUTH);// api형식 로그인의 경우에 사용..	
//		final String token = CookieUtil.getCookie("HSID", request);
//		System.out.println("JwtInterceptor::TK=>"+token);
		
		if(token != null && jwtService.isUsable(token)){
			System.out.println("사용가능.."+token);
//			System.out.println("JwtInterceptor::TK=>"+token);
			request.setAttribute("HSID", token);
			return true;
		}else{
			System.out.println("사용불가.."+token);
			request.setAttribute("HSID", null);
//			response.sendRedirect("/signin");
			return true;
//			throw new UnauthorizedException();
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
//		final String token = CookieUtil.getCookie("HSID", request);
//		modelAndView.addObject("HSID", token);
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
