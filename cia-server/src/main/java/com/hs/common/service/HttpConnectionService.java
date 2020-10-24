package com.hs.common.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hs.common.support.SupportLog;

public class HttpConnectionService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private int connectTimeout = 10000;
	
	
//	public static void main(String[] args) throws Exception {
//		
//		String access_token = "jupbHuaOg7-7-4dkToImBypFjmqXEHWJWkiELQo8BkMAAAFfXrUUSQ";
//		String url = "https://kapi.kakao.com/v1/user/signup";
//		url = "https://kapi.kakao.com//v1/user/me";
//		
//		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//		conn.setRequestProperty("Authorization", "Bearer jupbHuaOg7-7-4dkToImBypFjmqXEHWJWkiELQo8BkMAAAFfXrUUSQ");
//		conn.setRequestMethod("POST");
//		conn.setUseCaches(false);
//		conn.setDoOutput(true);
//		
//	    String responseData = "";
//		int responseCode = conn.getResponseCode();
//		String responseMessage = conn.getResponseMessage();
//		System.out.println("code: " + responseCode + ", message: "+ responseMessage);
//		if(responseCode==200) {
//			BufferedReader in = null;
//			try{
//				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//				String temp = null;
//				while((temp = in.readLine()) != null) {
//					responseData += temp;
//					System.out.println(temp);
//				}
//			}catch(IOException ec) {
//				ec.printStackTrace();
//			}finally {
//				if(in!=null) {in.close();} 
//			}
//		}
//		
//	}
//	
	
	/** HTTP GET 요청 후 응답 데이터를 문자열로 가져온다. */
	public String getResponseForGet(String url, String[]... headers) throws IOException {
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(connectTimeout);
		conn.setRequestMethod("GET");
		if(headers!=null) {
			for(String[] item : headers) {
				conn.setRequestProperty(item[0], item[1]);
			}
		}
	
	    String responseData = "";
		int responseCode = conn.getResponseCode();
		String responseMessage = conn.getResponseMessage();
		logger.debug("code: " + responseCode + ", message: "+ responseMessage);
		if(responseCode==200) {
			BufferedReader in = null;
			try{
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String temp = null;
				while((temp = in.readLine()) != null) {
					responseData += temp;
					System.out.println(temp);
				}
			}catch(IOException ec) {
				logger.error(SupportLog.getStackTrace(ec));
			}finally {
				if(in!=null) {in.close();} 
			}
		}
		
		logger.debug("[GET]최종 응답 데이터▼");
		logger.debug(responseData);
		
		return responseData;
	}

	/** HTTP POST 요청 후 응답 데이터를 문자열로 가져온다. */
	public String getResponseForPost(String url, String parameter, String[]... headers) throws IOException {
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(connectTimeout);
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		if(headers!=null) {
			for(String[] item : headers) {
				conn.setRequestProperty(item[0], item[1]);
			}
		}
		if(parameter!=null){
			conn.setDoOutput(true);
			OutputStream out_stream = conn.getOutputStream();
		    out_stream.write(parameter.getBytes("UTF-8"));
		    out_stream.flush();
		    out_stream.close();
		}
		
	    String responseData = "";
		int responseCode = conn.getResponseCode();
		String responseMessage = conn.getResponseMessage();
		logger.debug("code: " + responseCode + ", message: "+ responseMessage);
		if(responseCode==200) {
			BufferedReader in = null;
			try{
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String temp = null;
				while((temp = in.readLine()) != null) {
					responseData += temp;
					System.out.println(temp);
				}
			}catch(IOException ec) {
				logger.error(SupportLog.getStackTrace(ec));
			}finally {
				if(in!=null) {in.close();} 
			}
		}
		
		logger.debug("[POST]최종 응답 데이터▼");
		logger.debug(responseData);
		
		return responseData;
	}
	
	/** HTTP POST 요청 후 응답 데이터를 문자열로 가져온다. */
	public String getResponseForPost2(String url, JSONObject jsonObj, String[]... headers) throws IOException {
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(connectTimeout);
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		if(headers!=null) {
			for(String[] item : headers) {
				conn.setRequestProperty(item[0], item[1]);
			}
		}
		if(jsonObj!=null){
			String json = jsonObj.toString();
			conn.setDoOutput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes("utf-8"));
			os.flush();
			os.close();
		}
		
	    String responseData = "";
		int responseCode = conn.getResponseCode();
		String responseMessage = conn.getResponseMessage();
		logger.debug("code: " + responseCode + ", message: "+ responseMessage);
		if(responseCode==200) {
			BufferedReader in = null;
			try{
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String temp = null;
				while((temp = in.readLine()) != null) {
					responseData += temp;
					//logger.debug(temp);
				}
			}catch(IOException ec) {
				logger.error(SupportLog.getStackTrace(ec));
			}finally {
				if(in!=null) {in.close();} 
			}
		}
		
		//logger.debug("[POST]최종 응답 데이터▼");
		//logger.debug(responseData);
		
		return responseData;
	}
	
	/** HTTP POST 요청 후 응답 데이터를 문자열로 가져온다. */
	public String getResponseVer3(String method, String url, String json, String[]... headers) throws IOException {
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(connectTimeout);
		conn.setRequestMethod(method);
		conn.setUseCaches(false);
		if(headers!=null) {
			for(String[] item : headers) {
				conn.setRequestProperty(item[0], item[1]);
			}
		}
		
		//System.out.println("요청 메서드: "+ method);
		//System.out.println("요청 URL: "+ url);
		
		
		//String st = conn.getRequestProperty("userid");
		//System.out.println("st : "+ st);
		
		if(json!=null){
			conn.setDoOutput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes("utf-8"));
			os.flush();
			os.close();
		}
		
	    String responseData = "";
		int responseCode = conn.getResponseCode();
		String responseMessage = conn.getResponseMessage();
		//System.out.println("code: " + responseCode + ", message: "+ responseMessage);
		if(responseCode==200) {
			BufferedReader in = null;
			try{
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String temp = null;
				while((temp = in.readLine()) != null) {
					responseData += temp;
					//System.out.println(temp);
				}
			}catch(IOException ec) {
				logger.error(SupportLog.getStackTrace(ec));
			}finally {
				if(in!=null) {in.close();} 
			}
		}
		
		//logger.debug("[POST]최종 응답 데이터▼");
		//logger.debug(responseData);
		
		return responseData;
	}

}
