package com.hs.common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hs.common.support.SupportLog;

public class GeneralUtil {
	
	public class TIME_MAXIMUM {
		public static final int SEC = 60;
	    public static final int MIN = 60;
	    public static final int HOUR = 24;
	    public static final int DAY = 30;
	    public static final int MONTH = 12;
	}
	private static final Logger logger = LoggerFactory.getLogger(GeneralUtil.class);
	
	
	
	/** 요청 주소 정보를 가져온다.(0:전체URL,1:요청패스만) */
	public static String[] getCurrentUrl(HttpServletRequest request) {
	    
		try {
			URL url = new URL(request.getRequestURL().toString());
		    String host  = url.getHost();
		    String userInfo = url.getUserInfo();
		    String scheme = url.getProtocol();
		    int port = url.getPort();
		    String path = (String) request.getAttribute("javax.servlet.forward.request_uri");
		    String query = (String) request.getAttribute("javax.servlet.forward.query_string");
		    
			return new String[] { new URI(scheme, userInfo, host, port, path, query, "").toString(), path };
		} catch (Exception e) {
			logger.error(SupportLog.getStackTrace(e));
		}
	    return null;
	}
	
	/** 사이트별 검색키워드 파라미터 변수명 가져오기 */
	public static String getSearchParamBySite(String domain) {
		String paramName = null;
		if(domain.contains("naver.com")) {
			paramName = "query";
		}else if(domain.contains("google.co.")||domain.contains("google.com")||
				domain.contains("daum.net")||domain.contains("bing.com")) {
			paramName = "q";
		}
		return paramName;
	}
	/** 유입주소로 부터 검색키워드 추출 */
	public static String getQueryKeyword(String domain, String referer) {
		
		if(domain==null||referer==null){
			return null;
		}
		
		URL url = null; 
		String queryStr = null;
		String keyword = null;
		String keywordParam = getSearchParamBySite(domain);
		boolean isNaver = domain.contains("naver.com");
		
		try {
			url = new URL(referer);
			queryStr = url.getQuery();
			
			String topReferer = null;
			String[] params = queryStr.split("&");
			for (String param: params) {
			    String key = param.substring(0, param.indexOf('=')).trim();
			    String val = param.substring(param.indexOf('=') + 1).trim();
//			    System.out.println(key+" : "+ URLDecoder.decode(val, "UTF-8"));
			    if(keywordParam.equals(key)) {
			    	keyword = URLDecoder.decode(val, "UTF-8");
			    	break;
			    }
			    if(isNaver&&key.equals("topReferer")) {
			    	topReferer = URLDecoder.decode(val, "UTF-8");
			    }
			}
			
			// 네이버인경우 topReferer에서 다시 한번 검색키워드 추출
			if(isNaver&&topReferer!=null) {
				url = new URL(topReferer.trim());
				queryStr = url.getQuery();  
				params = queryStr.split("&");
				for (String param: params) {
				    String key = param.substring(0, param.indexOf('=')).trim();
				    String val = param.substring(param.indexOf('=') + 1).trim();
//				    System.out.println(key+" : "+ URLDecoder.decode(val, "UTF-8"));
				    if(keywordParam.equals(key)) {
				    	keyword = keyword==null?
				    			URLDecoder.decode(val, "UTF-8"):keyword+","+URLDecoder.decode(val, "UTF-8");
				    	break;
				    }
				}
			}
			
			
		} catch (Exception e) {
			//logger.error(SupportLog.getStackTrace(e));
		}
		
		return keyword;
	}
	

	
	
	/**
	 * 현재 Request에 저장되어있는 세션변수들을 모두 출력한다.
	 */
	public static void printSotredSessionNames() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession();	
		
		Enumeration params = session.getAttributeNames();
		while (params.hasMoreElements()) {

			String name = (String) params.nextElement();
			logger.info(name);
		}
	}
	
	/**
	 * 운영체제, 브라우저 정보를 리턴한다.
	 * @param browserDetails
	 * @return
	 * 		[0]:운영체제,[1]:브라우저
	 */
	public static String[] getOsBrowser(String browserDetails){
		
		String  userAgent       =   browserDetails;
        String  user            =   userAgent.toLowerCase();

        String os = "";
        String browser = "";

        //logger.info("User Agent for the request is===>"+browserDetails);
        //=================OS=======================
         if (userAgent.toLowerCase().indexOf("windows") >= 0 )
         {
             os = "Windows";
         } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
         {
             os = "Mac";
         } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
         {
             os = "Unix";
         } else if(userAgent.toLowerCase().indexOf("android") >= 0)
         {
             os = "Android";
         } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
         {
             os = "IPhone";
         }else{
             os = "UnKnown";//"UnKnown, More-Info: "+userAgent;
         }
         //===============Browser===========================
        if (user.contains("msie"))
        {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
        if(user.contains("opera"))
        	browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        else if(user.contains("opr"))
        	browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
        {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
            browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
            browser="IE";
        } else
        {
            browser = "UnKnown";//"UnKnown, More-Info: "+userAgent;
        }
        //logger.info("Operating System======>"+os);
        //logger.info("Browser Name==========>"+browser);
        return new String[]{os,browser};
	}
	
	
	public static String getPlayTimeString(int sec){
		
		String s = "";
		int temp_hour = sec/(60*60);
		int temp_min = (sec - (temp_hour*(60*60)))/60;
		int temp_sec = sec - (temp_min*60) - (temp_hour*(60*60));
		if(temp_hour>0)
			s += temp_hour+"시간 ";	
		if(temp_min>0)
			s += temp_min+"분 ";		
		if(!(temp_hour>0))
			s += temp_sec+"초";
		return s;
		
	}
	
	public static int getPlayTimeHour(int sec){
		String s = "";
		int temp_hour = sec/(60*60);
		int temp_min = (sec - (temp_hour*(60*60)))/60;
		int temp_sec = sec - (temp_min*60) - (temp_hour*(60*60));
		if(temp_hour>0)
			return temp_hour;	
		else
			return 0;
	}
	public static String getPlayTimeStringColor(int sec){
		String s = "";
		int temp_hour = sec/(60*60);
		int temp_min = (sec - (temp_hour*(60*60)))/60;
		int temp_sec = sec - (temp_min*60) - (temp_hour*(60*60));
		if(temp_hour>0)
			s += "<span class='csc_font_i'>"+temp_hour+"</span>시간 ";	
		if(temp_min>0)
			s += "<span class='csc_font_i'>"+temp_min+"</span>분 ";		
		if(!(temp_hour>0))
			s += "<span class='csc_font_i'>"+temp_sec+"</span>초";
		return s;
	}
	
	// 로컬날짜축약형으로 가져오기
	public static String getShortDate(String signdate) {

		DateFormat df_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df_local.setTimeZone(TimeZone.getDefault());

		String localSigndate = "";
		Date d = null;

		try {
			localSigndate = df_local.format(df_local.parse(signdate));
			d = df_local.parse(localSigndate);
		} catch (ParseException e) {
			return "0000-00-00";
		}

		String msg = null;

		long curTime = System.currentTimeMillis();
		long regTime = d.getTime();
		long diffTime = (curTime - regTime) / 1000;

		if (diffTime < 0) {// 미래인 경우

			diffTime = -diffTime;

			if (diffTime < TIME_MAXIMUM.SEC) {
				msg = (diffTime) + "초후";
			} else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
				msg = diffTime + "분후";
			} else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
				msg = (diffTime) + "시간후";
			} else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
				msg = (diffTime) + "일후";
			} else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
				msg = (diffTime) + "달후";
			} else {
				diffTime /= TIME_MAXIMUM.MONTH;
				msg = (diffTime) + "년후";
			}

		} else if (diffTime < TIME_MAXIMUM.SEC) {
			msg = "방금전";
		} else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			msg = diffTime + "분전";
		} else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			msg = (diffTime) + "시간전";
		} else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			msg = (diffTime) + "일전";
		} else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
			msg = (diffTime) + "달전";
		} else {
			diffTime /= TIME_MAXIMUM.MONTH;
			msg = (diffTime) + "년전";
		}

		return msg;

	}

	// 로컬날짜 하루가 지났으면 TRUE 안지났으면 FALSE
	public static boolean isOverOneDay(String signdate) {
		
		DateFormat df_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df_local.setTimeZone(TimeZone.getDefault());

		String localSigndate = "";
		Date d = null;

		try {
			localSigndate = df_local.format(df_local.parse(signdate));
			d = df_local.parse(localSigndate);
		} catch (ParseException e) {
			// 오류시 하루가 지난걸로 간주..
			return true;
		}

		// 실제 d변수갖고 비교시작..
		long curTime = System.currentTimeMillis();
		long regTime = d.getTime();
		long diffTime = (curTime - regTime) / 1000;
		if (diffTime < TIME_MAXIMUM.SEC) {
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			if (((int) diffTime) < 2) {
				return false;
			}
		}

		return true;
	}
	
	
	
	// 로컬날짜 한달이 지났으면 TRUE 안지났으면 FALSE
	public static boolean isOverOneMonth(String signdate) {
		
		DateFormat df_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df_local.setTimeZone(TimeZone.getDefault());

		String localSigndate = "";
		Date d = null;

		try {
			localSigndate = df_local.format(df_local.parse(signdate));
			d = df_local.parse(localSigndate);
		} catch (ParseException e) {
			// 오류시 한달 지난걸로 간주..
			return true;
		}

		long curTime = System.currentTimeMillis();
		long regTime = d.getTime();
		long diffTime = (curTime - regTime) / 1000;
		if (diffTime < TIME_MAXIMUM.SEC) {
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			return false;// 몇일전
		} else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
			return true;// 몇달전
		}
		return true;
	}
	
	
	
	// 로컬날짜 한달이 지났으면 TRUE 안지났으면 FALSE
	public static boolean isOverManyMonths(String signdate, int month) {
		
		DateFormat df_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df_local.setTimeZone(TimeZone.getDefault());

		String localSigndate = "";
		Date d = null;

		try {
			localSigndate = df_local.format(df_local.parse(signdate));
			d = df_local.parse(localSigndate);
		} catch (ParseException e) {
			//e.printStackTrace();
			// 오류시 한달 지난걸로 간주..
			return true;
		}

		long curTime = System.currentTimeMillis();
		long regTime = d.getTime();
		long diffTime = (curTime - regTime) / 1000;
		if (diffTime < TIME_MAXIMUM.SEC) {// 방금전(60초도 안지남)
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			return false;
		} else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			return false;// 몇일전
		} else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
			
			if(diffTime<=month) {
				return false;
			}
			return true;
		}
		return true;
	}
	
	/*public static void main(String[] args) {
		
		System.out.println(isOverManyMonths("2018-03-19 00:00:00", 3));
	}*/
	

	/**
	 * 로컬시간 가져오기(0000-00-00 00:00:00)
	 * @return
	 */
	public static String getDateTime() {
		TimeZone tz;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		tz = TimeZone.getDefault();// !important
		df.setTimeZone(tz);

		return df.format(new Date());
	}

	// 현재사이트 URL 가져오기
	public static String getSiteURL() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
		
		
		boolean isSecure = request.isSecure();
		String protocol = isSecure?"https":"http";
		
		int port = request.getServerPort();
		String serverName = request.getServerName();

		if (port == 80) {
			return protocol + "://" + serverName + "/";
		} else {
			return protocol + "://" + serverName + ":" + port + "/";
		}
	}

	// IP 가져오기
	public static String getIP() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();

		String ip = request.getHeader("X-FORWARDED-FOR"); // IP
		if (ip == null)
			ip = request.getRemoteAddr();

		return ip;
	}
	// IP 가져오기
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR"); // IP
		if (ip == null)
			ip = request.getRemoteAddr();
		return ip;
	}

	// 주문번호 생성
	public static String getOrderNo() {
		String no = "";
//		no += UUID.randomUUID().toString();
		no += getRandomSerialKey("", 7);
		no += (new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
				.getInstance().getTime())) + "";
		return no;
	}
	
	

	// 랜덤시리얼키 생성 (0~9,A~Z조합)
	public static String getRandomSerialKey(String preFix, int length) {
		char[] a1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		String random_str = preFix;
		for (int j = 1; j <= length; j++) {
			char ch = a1[(int) ((Math.random() * 36) + 0)];
			random_str += ch;
		}
		return random_str;
	}
	
	

	// 랜덤시리얼키 생성 (0~9,A~Z조합)
	public static String getRandomSerialKey(int length) {
		char[] a1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		String random_str = "";
		for (int j = 1; j <= length; j++) {
			char ch = a1[(int) ((Math.random() * 36) + 0)];
			random_str += ch;
		}
		return random_str;
	}

	// 랜덤키 생성
	public static String getRandomKey(int length) {
		String random_str = "";
		// 소문자 a-z 랜덤 알파벳 생성
		for (int j = 1; j <= length; j++) {
			char ch = (char) ((Math.random() * 26) + 97);
			random_str += ch;
		}
		return random_str;
	}
	
	
	/* 앞에 변수가 크면 1, 작으면 -1, 같으면 0 */
	public static int compareDate(String d1, String d2) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, Integer.parseInt(d1.split("-")[0]));
        calendar1.set(Calendar.MONTH, Integer.parseInt(d1.split("-")[1])-1);
        calendar1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d1.split("-")[2]));
         
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, Integer.parseInt(d2.split("-")[0]));
        calendar2.set(Calendar.MONTH, Integer.parseInt(d2.split("-")[1])-1);
        calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d2.split("-")[2]));
         
        //앞에 변수가 크면 1, 작으면 -1, 같으면 0
        int result1 = calendar1.compareTo(calendar2);
        int result2 = calendar2.compareTo(calendar1);
		
        return result1;
	}
	
	

}
