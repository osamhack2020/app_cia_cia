package com.hs.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerResourceInterceptor extends HandlerInterceptorAdapter {
	
//	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
    
    // 전처리 : 클라이언트에서 -> 컨트롤러로 요청할때
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
   
    	//================================================================================================= MDC설정(LOG4J)
//    	String uid = "x";		// UID
//    	String ip = request.getHeader("X-FORWARDED-FOR");		// IP
//    	if (ip == null)
//			ip = request.getRemoteAddr();
//		String uri = request.getRequestURI();					// URI	
//		MDC.put("ip", ip);
//		MDC.put("uri", uri);
//		MDC.put("uid", uid);
		//=================================================================================================
		
        return true;
    }
  

}
