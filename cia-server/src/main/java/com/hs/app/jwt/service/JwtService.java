package com.hs.app.jwt.service;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hs.app.jwt.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final String SALT =  "luvookSecret";
	
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	
	public <T> String create(String key, T data, String subject){
		
		logger.info("키:"+key+",데이터:"+data+",주제:"+subject);  
		
		String jwt = Jwts.builder()  
						 .setHeaderParam("typ", "JWT")
						 .setHeaderParam("regDate", System.currentTimeMillis())
						 .setSubject(subject)
						 .claim(key, data) // 로그인 중 마음껏 이용할 회원 정보들을 저장 (아이디,이메일,닉네임 등)  
						 .signWith(SignatureAlgorithm.HS256, this.generateKey())
						 .compact(); // 직렬화
		return jwt;
	}	
		
	/** jwt토큰값이 유효한지 검사 */
	public boolean isUsable(String jwt) {
		try{
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(jwt);
			return true;
			
		}catch (Exception e) {
			throw new UnauthorizedException();
			/*
				1) ExpiredJwtException : 		JWT를 생성할 때 지정한 유효기간 초과할 때.
				2) UnsupportedJwtException : 	예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때	
				3) MalformedJwtException : 		JWT가 올바르게 구성되지 않았을 때				
				4) SignatureException :  		JWT의 기존 서명을 확인하지 못했을 때				
				5) IllegalArgumentException
			 */
		}
	}

	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();			
		}
		return key;
	}
	
	/** JWT에 넣어놓은 데이터를 가져오는 코드 */
	public Map<String, Object> get(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("Authorization");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
		return value;
	}
	public Map<String, Object> get(String key, String jwt) {
		
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
		return value;
	}
	
	
	public String getMemberId() {
		return this.get("member").get("idx").toString();
	}
	
	public String getMemberId(String jwt) {
		return this.get("member", jwt).get("idx").toString();
	}
	
	public Map<String, Object> getMember(String jwt) {
		return this.get("member", jwt);
	}
}
