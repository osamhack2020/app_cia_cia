package com.hs.common.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SupportLog {
	
	// [IP] [URL] 리턴
	public static String getIpURL(HttpServletRequest request) {

		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest();
		}

		String ip = request.getHeader("X-FORWARDED-FOR"); // IP
		if (ip == null)
			ip = request.getRemoteAddr();
		String url = request.getRequestURI(); // URL

		return "[" + ip + "] [" + url + "]";

	}

	// 오류상세내용을 문자열로 치환 후 리턴
	public static String getStackTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		t.printStackTrace(printWriter);
		printWriter.close(); // surprise no IO exception here
		try {
			stringWriter.close();
		} catch (IOException e) {
		}
		return stringWriter.toString();
	}
	
	// 요청 파라미터 정보를 문자열로 가져옴.
	public static String getParameterInfo(HttpServletRequest request){
		
		String str = "";
		int i=0;

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			if(i!=0){
				str += ", ";
			}
			String name = (String) params.nextElement();
			str += name + ":" + request.getParameter(name);
			i++;
		}
		return str;
	}

	// 요청 헤더, 파라미터 정보를 콘솔에 출력
	public static void print(String perStr, HttpServletRequest request) {
		
		System.out.println("요청URL:"+request.getRequestURL());
		System.out.println("요청메서드:"+request.getMethod());
		System.out.println("요청스키마?:"+request.getScheme());
		
		Enumeration<String> em = request.getHeaderNames();

		System.out
				.println(perStr+"요청 헤더정보---------------------------------------------------------------------------");
		while (em.hasMoreElements()) {
			String name = em.nextElement();
			String val = request.getHeader(name);
			System.out.println(name + " : " + val);
		}
		System.out
				.println("------------------------------------------------------------------------------------");

		Enumeration params = request.getParameterNames();
		System.out
				.println(perStr+"요청 파라미터---------------------------------------------------------------------------");
		while (params.hasMoreElements()) {
			String name = (String) params.nextElement();
			System.out.println(name + " : " + request.getParameter(name));
		}
		System.out
				.println("------------------------------------------------------------------------------------");

	}
	

}
