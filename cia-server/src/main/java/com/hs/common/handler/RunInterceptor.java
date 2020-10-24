package com.hs.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hs.app.jwt.service.JwtService;
import com.hs.app.user.dao.UserDao;
import com.hs.app.user.vo.UserInfo;
import com.hs.common.util.CookieUtil;

public class RunInterceptor implements HandlerInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String HEADER_AUTH = "Authorization";

	@Autowired private JwtService jwtService;
	@Autowired private UserDao userDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		final String token = CookieUtil.getCookie("HSID", request);

		if(token != null && jwtService.isUsable(token)){
			
			String idxByToken = jwtService.getMemberId(token);		
			UserInfo user = userDao.getUser(Integer.parseInt(idxByToken));
			if(user.getAuthList()!=null && user.getAuthList().contains("ROLE_MASTER")) {
				return true;	
			}else {
				logger.info("관리자만 접근할 수 있습니다.");
			}
			
		}else {
			logger.info("로그인 후 접근할 수 있습니다.");
		}
		response.sendRedirect("/error/404");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
