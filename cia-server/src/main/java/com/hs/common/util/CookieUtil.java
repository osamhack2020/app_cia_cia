package com.hs.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CookieUtil {
	
	/** 모든 쿠키 출력 */
	public static void logCookies() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName()+":"+cookie.getValue());
				
			}
		}
	}
	
	/** 모든 쿠키 제거 */
	public static void deleteAllCookite(HttpServletRequest request, HttpServletResponse response) {
		
		Cookie[] cookies = request.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
		if(cookies != null){ // 쿠키가 한개라도 있으면 실행
			for(int i=0; i< cookies.length; i++){ 
				cookies[i].setPath("/");
				cookies[i].setMaxAge(0); // 유효시간을 0으로 설정
				response.addCookie(cookies[i]); // 응답 헤더에 추가
			}
		}
	}
	
	/** 쿠키 제거 */
	public static void deleteCookite(String type, HttpServletResponse response) {
		
		Cookie myCookie = new Cookie(type, null);
	    myCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
	    myCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
	    response.addCookie(myCookie);
	}
	
	/** 쿠키 설정 
	 * @throws UnsupportedEncodingException */
	public static void setCookie(String type, String val, HttpServletResponse response) throws UnsupportedEncodingException {
		
		if(val==null) {
			return;
		} 
		
		Cookie cookie = new Cookie(type, URLEncoder.encode(val, "UTF-8"));
		cookie.setPath("/");
		
	    /* 2) 쿠키를 응답 헤더에 포함시켜 보내기
			HTTP/1.1 200
			Set-Cookie: cookieName=cookieVlaues;email=qlcskfgml78@naver.com      <----- 쿠키 데이터가 이렇게 헤더에 추가됨.
			Content-Type: text/plain;charset=UTF-8
			Content-Length: 29
			Date: Mon, 12 Jun 2017 03:35:03 GMT     
	     */
		response.addCookie(cookie); 
		response.setContentType("text/plain;charset=UTF-8");
	   
	}
	
	/** 사이트에서 사용되는 모든 쿠기 값을 가져온다. 
	 * @throws UnsupportedEncodingException */
	public static String getCookie(String cookieName, HttpServletRequest request) throws UnsupportedEncodingException{
		
		if(request==null) {
			request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		}
		
		Cookie[] cookies = request.getCookies();
		String value = null;
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					value = URLDecoder.decode(cookie.getValue(), "UTF-8");
					return value;
				}
			}
		}
		return null;
	}
	


}
