package com.hs.common.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hs.app.jwt.service.JwtService;
import com.hs.common.util.CookieUtil;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String HEADER_AUTH = "Authorization";
	
	@Autowired private JwtService jwtService;
	
    // 전처리 : 클라이언트에서 -> 컨트롤러로 요청할때
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	
//    	final String token = request.getHeader(HEADER_AUTH);
//
//		if(token != null && jwtService.isUsable(token)){
//			return true;
//		}else{
//			logger.error("Not Found Token!");
//			throw new UnauthorizedException();
//		}
        
//    	SignUser signUser = SecurityUtil.getSignUser();
    	//================================================================================================= MDC설정(LOG4J)
//    	String uid = signUser!=null?signUser.getId():"x";		// UID
//    	String ip = request.getHeader("X-FORWARDED-FOR");		// IP
//    	if (ip == null)
//			ip = request.getRemoteAddr();
//		String uri = request.getRequestURI();					// URI	
//		MDC.put("ip", ip);
//		MDC.put("uri", uri);
//		MDC.put("uid", uid);
		//=================================================================================================
    
		//logger.info("[요청]");	
		//SupportLog.print("[개발/로컬전용][요청] ", request);	// 개발시에만..	

		return true;
    }
    
    // 후처리 : 컨트롤러에서 -> 클라이언트로 응답할때
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    
		/*final String token = CookieUtil.getCookie("HSID", request);
    	if(token!=null) {
    		
    		Map<String, Object> user = jwtService.getMember(token);
    		modelAndView.addObject("SUSER", user);
    		
//    		logger.debug(user.get("id")+"");
//    		logger.debug(user.get("pw")+"");
//    		logger.debug(user.get("name")+"");
    		
    		
    		//logger.debug("Email : "+ email + ".. "+request.getRequestURI());
    	
//        	JwtUser userInfo = (JwtUser) jwtService.get("member", token);
        	
    		
    	}*/
        	
    
    	
		
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    	super.afterCompletion(request, response, handler, ex);
//    }
   
}
