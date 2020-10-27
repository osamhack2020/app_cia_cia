package com.hs.app.user.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hs.app.jwt.service.JwtService;
import com.hs.app.jwt.vo.JwtUser;
import com.hs.app.user.dao.UserDao;
import com.hs.app.user.vo.ClassInfo;
import com.hs.app.user.vo.StudyInfo;
import com.hs.app.user.vo.UserInfo;
import com.hs.common.util.GeneralUtil;
import com.hs.common.util.PageUtil;

public class UserService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private UserDao userDao;
	@Autowired private JwtService jwtService;
	
	
	/** 회원 목록 로드 */
    public Map<String,Object> loadUserList(int page, int rowBlockCount, String q) {
        
        page = page<1?1:page;   
        if(q!=null&&q!="") {q = q.trim(); logger.debug("검색어: "+q+"로 게시물 검색..");}
        else {q=null;}
        
        PageUtil pu = new PageUtil(page,userDao.getUserSize(q),rowBlockCount,10);			
		List<UserInfo> lists = userDao.getUserList(pu.getStartRow(), pu.getRowBlockCount(), q);		
		
        Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("pageNav", pu);
		rst.put("list", lists);
        return rst;
    }
	
	public Map<String,Object> loadClass(int page, int rowBlockCount, String q) {
		
        page = page<1?1:page;   
        PageUtil pu = new PageUtil(page,userDao.getClassSize(q),rowBlockCount,10);		
        
        if(q!=null) {
        	q = q.trim();
        	if(q=="") {
        		q = null;
        	}
        }
        
		List<ClassInfo> lists = userDao.loadClass(pu.getStartRow(), pu.getRowBlockCount(), q);		
        Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("pageNav", pu);
		rst.put("list", lists);
        return rst;
    }
	
	public Map<String,Object> loadStudy(int page, int rowBlockCount, String q) {
		
        page = page<1?1:page;   
        PageUtil pu = new PageUtil(page,userDao.getStudySize(q),rowBlockCount,10);		
        
        if(q!=null) {
        	q = q.trim();
        	if(q=="") {
        		q = null;
        	}
        }
        
		List<StudyInfo> lists = userDao.loadStudy(pu.getStartRow(), pu.getRowBlockCount(), q);		
        Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("pageNav", pu);
		rst.put("list", lists);
        return rst;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	public boolean secureEmail(int userIdx, String signkey) throws Exception {
		
		boolean isSuccess = false;
		
		Integer idx = userDao.hasCheckEmail(userIdx, signkey);
		if(idx!=null){
			if(userDao.updateEnableCheckEmail(idx)>0){
				userDao.insertAuth(userIdx,"ROLE_EMAIL");
				return true;
			}
		}else{
			return false;//!important
		}
		
		
		if(!isSuccess){
			throw new Exception();
		}
		return isSuccess;
	}
	

	
	public boolean autoSignIn(int userIdx, HttpServletResponse response) throws UnsupportedEncodingException {
		
		UserInfo userInfo = userDao.getUser(userIdx);
		if(userInfo != null) {
				
			boolean isRoleAlba = false;
			boolean isRoleEmail = false;
			boolean isRoleProfile = false;
			if(userInfo.getAuthList()!=null) {
				if(userInfo.getAuthList().contains("ROLE_ALBA")) {
					isRoleAlba = true; 
				}
				if(userInfo.getAuthList().contains("ROLE_EMAIL")) {
					isRoleEmail = true; 
				}
				if(userInfo.getAuthList().contains("ROLE_PROFILE")) {
					isRoleProfile = true; 
				}
			}
			
			// JsonWebToken 생성
			JwtUser jwtUser = new JwtUser(userInfo.getIdx(), userInfo.getEmail(), 
					userInfo.getName(), userInfo.getImg(), userInfo.getPhonenm());
			String token = jwtService.create("member", jwtUser, "user");
			
//			setLoginUserCookie(token, jwtUser, response);
			
//			Map<String,Object> rMap = new HashMap<String,Object>();
//			rMap.put("jwtUser", jwtUser);
//			rMap.put("token", token);
//			return rMap;
			return true;
			
		}else {
			logger.debug("not found user information");
		}
		return false;
	}
	
	/** signin User 
	 * @throws UnsupportedEncodingException */
	public boolean signIn(String email, String rawPassword, HttpServletResponse response) throws UnsupportedEncodingException {
		
	
		UserInfo userInfo = userDao.getUserIdxByEmail(email);
		if(userInfo != null) {
			if(passwordEncoder.matches(rawPassword, userInfo.getPassword())) {
				
				if(userInfo.getAuthList()!=null) {

				}
				
				// JsonWebToken 생성
				JwtUser jwtUser = new JwtUser(userInfo.getIdx(), userInfo.getEmail(), 
						userInfo.getName(), userInfo.getImg(), userInfo.getPhonenm());
				String token = jwtService.create("member", jwtUser, "user");
				
//				setLoginUserCookie(token, jwtUser, response);
				
				// 마지막 접속일이 null이면 가입 후 처음 로그인한걸로 간주..
//				String findate = userInfo.getFindate();
//				if(findate==null) {
//					userDao.insertUserNotice(userInfo.getIdx(), "JOIN_SUCCESS", "가입이 완료되었습니다.", false, null);
//					userDao.updateUserFindate(userInfo.getIdx());
//				}
				
				
				
		        //response.setHeader("Authorization", token);// View없이 응답해줄때 사용
				//CookieUtil.setCookie("HSID", token, response);
				
//				Map<String,Object> rMap = new HashMap<String,Object>();
//				rMap.put("jwtUser", jwtUser);
//				rMap.put("token", token);
//				return rMap;
				return true;
				 
			}else {
				logger.debug("invalide password");
			}
		}else {
			logger.debug("not found email");
		}
		return false;
	}
	
	/** signup User (모든 필드 유효성 검사**) */
	public UserInfo signUp(UserInfo userInfo) {
		
		String rawPassword = passwordEncoder.encode(userInfo.getPassword());
		userInfo.setPassword(rawPassword);
		
//		// 이미 해당 닉네임으로 가입된 회원정보가 존재
//		if(userDao.isUserByName(userInfo.getName())>0) {
//			logger.info("이미 해당 닉네임으로 가입된 회원정보가 존재");
//			//userInfo.setErrorMsg("nicname");
//			return null;
//		}
		
		// 이미 해당메일로 가입된 회원정보가 존재
		if(userDao.getUserIdxByEmail(userInfo.getEmail())!=null) {
			logger.info("이미 해당메일로 가입된 회원정보가 존재");
			//userInfo.setErrorMsg("email");
			return null;
		}
		
		UserInfo signedUser = userDao.insertUserInfo(userInfo);
		if(signedUser!=null) {
			
			String key = GeneralUtil.getRandomKey(50);
			userDao.insertAuth(signedUser.getIdx(), "ROLE_USER");
//			if(userDao.insertCheckEmail(signedUser.getIdx(), key)) {
				
				//2. 가입인증메일발송
//				String joinSecureUrl = GeneralUtil.getSiteURL() + "secure-mail" + "?u="+ signedUser.getIdx() + "&s="+ key;
//				Email email = new Email();
//				Map<String,Object> emailMap = new HashMap<String,Object>();
//				emailMap.put("joinSecureUrl", joinSecureUrl);		// 인증링크		
//				email.setHtmlYn(true);                          	// html 형식으로 세팅
//				email.setReceiver(signedUser.getEmail());			// 받는사람
//	            email.setSubject("히든골퍼 가입 인증메일입니다.");			// 메일 제목 
//	            email.setTemplate("join_mail.vm");        			// 템플릿 파일명
//	            email.setResultMap(emailMap);						// 컨텐츠내용 데이터
//	            emailService.sendEmail(email);
//			}
			
			return signedUser;
		}
		return null;
	}
	
	
    
   
}
