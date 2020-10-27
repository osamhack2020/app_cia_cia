package com.hs.app.controller.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.app.board.dao.BoardDao;
import com.hs.app.jwt.service.JwtService;
import com.hs.app.user.dao.UserDao;
import com.hs.app.user.service.UserService;
import com.hs.app.user.vo.ClassCat;
import com.hs.app.user.vo.ClassInfo;
import com.hs.app.user.vo.ClassStudent;
import com.hs.app.user.vo.CurInfo;
import com.hs.app.user.vo.StudyCat;
import com.hs.app.user.vo.StudyInfo;
import com.hs.app.user.vo.StudyStudent;
import com.hs.app.user.vo.UserInfo;

@RestController
@RequestMapping(value = "/api")
public class UserRestController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private UserService userService;	
	@Autowired private UserDao userDao;
	@Autowired private BoardDao boardDao;
	@Autowired private JwtService jwtService;
	@Autowired private BCryptPasswordEncoder passwordEncoder;

/**
 * 
 * 
-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------
	
	
사진/영상 업로드 		/file/upload 					[POST]

로그인: 				/api/users/signin 				[POST] 						요청[email,password] 응답헤더[HSID]
회원가입: 				/api/users/signup 				[POST] 						요청[name,email,phonenm,password]
프로필정보 가져오기: 		/api/users/profile 				[GET][Authorization] 		응답[user(idx,password,name,phonenm,regdate,img)]
프로필정보 수정 			/api/users/profile 				[POST][Authorization] 		요청[name,phonenm,img]


-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------


스터디등록:			/api/study/regist 				[POST][Authorization] 		요청[title,img,note,station,signdate,maxPeople,catIdx] 응답[insertedIdx]
스터디목록 가져오기 		/api/study/ 					[GET] 						요청[page,rowBlockCount(1페이지당 가져올 컨텐츠수)] 응답[list,pageNav(페이징정보)]
스터디수정 				/api/study/{스터디PK} 			[POST][Authorization] 		요청[title,img,note,station,signdate,maxPeople]
스터디삭제 				/api/study/{스터디PK} 			[DELETE][Authorization]		 	
스터디상세정보 가져오기 	/api/study/{스터디PK} 			[GET] 						응답[idx,title,img,note,station,signdate,maxPeople]

스터디카테고리목록조회 	/api/study/cat 					[GET] 
스터디 추천목록 가져오기 	/api/study/recomend 			[GET] 						요청[limitCount(몇개 가져올지)]

스터디 수강회원 목록조회 	/api/study/{스터디PK}/students 	[GET] 						응답[list]
스터디 가입 			/api/study/{스터디PK}/students 	[POST][Authorization]
스터디 탈퇴 			/api/study/{스터디PK}/students 	[POST][Authorization]

스터디 조회수+1			/api/study/{studyIdx}/views 	[POST]


-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------


클래스 정보조회 			/api/class/{classIdx} 			[GET]
클래스 카테고리 목록조회 	/api/class/cat 					[GET] 
클래스 추천 목록조회 		/api/class/recommend 			[GET] 						요청[limitCount]
클래스 목록조회 			/api/class 						[GET] 						요청[page, rowBlockCount, q]

클래스 삭제 			/api/class/{classIdx} 			[DELETE][Authorization] 
클래스 등록				/api/class/regist	 			[POST][Authorization] 		요청[img, title, note, tags, catIdx] 응답[insertedIdx]
클래스 수정 			/api/class/{classIdx} 			[POST][Authorization] 		요청[img, title, note, tags, catIdx]
클래스 조회수+1 		/api/class/{classIdx}/views 	[POST]

회차 등록 				/api/class/curriculum/regist	[POST] 						요청[title,note,img,videoPath,numb,classIdx]
회차 삭제				/api/class/curriculum/{idx} 	[DELETE]	
클래스 회차 목록조회		/api/class/{classIdx}/curriculum[GET]						응답[list]

클래스 수강회원 목록조회	/api/class/{classIdx}/students 	[GET] 						응답[list]
클래스 가입 			/api/class/{classIdx}/students 	[POST][Authorization]
클래스 탈퇴 			/api/class/{classIdx}/students 	[POST][Authorization]


-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------

내 클래스 목록 			/api/class/me 					[GET][Authorization]		응답[list]
내 스터디 목록 			/api/study/me 					[GET][Authorization]		응답[list]

내가 개설한 클래스 목록 	/api/class/manage 				[GET][Authorization]		응답[list]
내가 개설한 스터디 목록 	/api/study/manage 				[GET][Authorization]		응답[list]

-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------

[어드민 추가]

추천강의 목록  			/api/adm/class/recommend		[GET]						응답[list]
추천스터디 목록			/api/adm/study/recommend		[GET]						응답[list]
회원 상세 정보 			/api/adm/user/{idx}				[GET]						응답[user,list1,list2]
통계정보(스터디수강률) 	/api/stat/1						[GET]						응답[list]
통계정보(강의수강률)		/api/stat/2						[GET]						응답[list]
통계정보(스터디점유율)	/api/stat/3						[GET]						응답[list]
통계정보(강의점유율) 		/api/stat/4						[GET]						응답[list]
통계정보(가입경로) 		/api/stat/5						[GET]						응답[list]
통계정보(가입자수) 		/api/stat/6						[GET]						응답[list] 

-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------

	
*/
	
	
	/** 추천 강의 목록 로드 */
	@RequestMapping(value = "adm/class/recommend", method = RequestMethod.GET)
	public Map<String, Object> loadClassReco22(
			@RequestParam(required = false) Integer limitCount,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		List<ClassInfo> l = userDao.getRecoClassList(limitCount);
		rst.put("list", l);
		return rst;
	}
	/** 추천 스터디 목록 로드 */
	@RequestMapping(value = "adm/study/recommend", method = RequestMethod.GET)
	public Map<String, Object> loadStudyRec2(
			@RequestParam(required = false) Integer limitCount,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		List<StudyInfo> l = userDao.getRecoStudyList(limitCount);
		rst.put("list", l);
		return rst;
	}
	@RequestMapping(value = "/stat/5", method = RequestMethod.GET)
	public Map<String, Object> getStat5(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("list", userDao.loadJoinRate1());
		return rst;
	}
	@RequestMapping(value = "/stat/6", method = RequestMethod.GET)
	public Map<String, Object> getStat6(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("list", userDao.loadJoinRate2());
		return rst;
	}

	@RequestMapping(value = "/stat/3", method = RequestMethod.GET)
	public Map<String, Object> getStat3(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("list", userDao.loadClassRateByCat2());
		return rst;
	}
	@RequestMapping(value = "/stat/4", method = RequestMethod.GET)
	public Map<String, Object> getStat4(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("list", userDao.loadStudyRateByCat2());
		return rst;
	}
	
	
	
	@RequestMapping(value = "/stat/2", method = RequestMethod.GET)
	public Map<String, Object> getStat2(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("list", userDao.loadClassRateByCat());
		return rst;
	}
	@RequestMapping(value = "/stat/1", method = RequestMethod.GET)
	public Map<String, Object> getStat1(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("list", userDao.loadStudyRateByCat());
		return rst;
	}
	  
	
	/** 회원정보 쿼리  */
	@RequestMapping(value = "/adm/user/{idx}", method = RequestMethod.GET)
	public Map<String, Object> myinsddsfo(
			@PathVariable Integer idx,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserInfo userInfo = userDao.getUser(idx);
		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("user", userInfo);
		
		
		rst.put("list1", userDao.loadRegistClassByUserIdx(idx)); // 수강 클래스
		rst.put("list2", userDao.loadRegistStudyByUserIdx(idx)); // 수강 스터디
		
		
		return rst;
	}
	
	
	
	
	
	
	
	
	
	/** 내가 개설한 클래스 목록 쿼리 */
	@RequestMapping(value = "/class/manage", method = RequestMethod.GET)
	public Map<String, Object> loadMyClass(HttpServletRequest request, HttpServletResponse response) {
		
		Integer userIdx = null;
		try {
			String token = request.getAttribute("HSID").toString();
			userIdx = Integer.parseInt(jwtService.getMemberId(token));
		}catch(Exception ec) {
			System.out.println("[ERROR] 권한오류");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		Map<String,Object> rst = new HashMap<String,Object>();
		List<ClassInfo> lists = userDao.loadMyClass(userIdx);
		rst.put("list", lists);
		return rst;
	}
	/** 내가 개설한 스터디 목록 쿼리 */
	@RequestMapping(value = "/study/manage", method = RequestMethod.GET)
	public Map<String, Object> loadMyStudy(HttpServletRequest request, HttpServletResponse response) {
		
		Integer userIdx = null;
		try {
			String token = request.getAttribute("HSID").toString();
			userIdx = Integer.parseInt(jwtService.getMemberId(token));
		}catch(Exception ec) {
			System.out.println("[ERROR] 권한오류");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		Map<String,Object> rst = new HashMap<String,Object>();
		List<StudyInfo> lists = userDao.loadMyStudy(userIdx);
		rst.put("list", lists);
		return rst;
	}
	
	/** 내가 수강한 클래스 목록 쿼리 */
	@RequestMapping(value = "/class/me", method = RequestMethod.GET)
	public Map<String, Object> loadClassMe(HttpServletRequest request, HttpServletResponse response) {
		
		Integer userIdx = null;
		try {
			String token = request.getAttribute("HSID").toString();
			userIdx = Integer.parseInt(jwtService.getMemberId(token));
		}catch(Exception ec) {
			System.out.println("[ERROR] 권한오류");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		Map<String,Object> rst = new HashMap<String,Object>();
		List<ClassInfo> lists = userDao.loadRegistClassByUserIdx(userIdx);
		rst.put("list", lists);
		return rst;
	}
	/** 내가 수강한 스터디 목록 쿼리 */
	@RequestMapping(value = "/study/me", method = RequestMethod.GET)
	public Map<String, Object> loadStudyMe(HttpServletRequest request, HttpServletResponse response) {
		
		Integer userIdx = null;
		try {
			String token = request.getAttribute("HSID").toString();
			userIdx = Integer.parseInt(jwtService.getMemberId(token));
		}catch(Exception ec) {
			System.out.println("[ERROR] 권한오류");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		Map<String,Object> rst = new HashMap<String,Object>();
		List<StudyInfo> lists = userDao.loadRegistStudyByUserIdx(userIdx);
		rst.put("list", lists);
		return rst;
	}
	
	
	
	/** 클래스 수강회원 목록 쿼리 */
	@RequestMapping(value = "/class/{classIdx}/students", method = RequestMethod.GET)
	public Map<String, Object> loadClassStudents(
			@PathVariable Integer classIdx,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		List<ClassStudent> lists = userDao.getClassStudents(classIdx);
		rst.put("list", lists);
		return rst;
	}
	/** 클래스수강 가입 */
	@RequestMapping(value = "/class/{classIdx}/students", method = RequestMethod.POST)
	public void registClassStudents(@PathVariable Integer classIdx,
			HttpServletRequest request, HttpServletResponse response) {
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		if(!userDao.insertClassStudent(classIdx, userIdx)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	/** 클래스수강 탈퇴 */
	@RequestMapping(value = "/class/{classIdx}/students", method = RequestMethod.DELETE)
	public void withdrawClassStudents(
			@PathVariable Integer classIdx,
			HttpServletRequest request, HttpServletResponse response) {
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		if(!userDao.deleteClassStudent(classIdx, userIdx)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	
	
	/** 특정 클래스의 회차 목록 로드 */
	@RequestMapping(value = "/class/{classIdx}/curriculum", method = RequestMethod.GET)
	public Map<String, Object> loadClassCurriculum(
			@PathVariable Integer classIdx,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		List<CurInfo> l = userDao.loadClassCurriculum(classIdx);
		rst.put("list", l);
		return rst;
	}
	/** 회차 등록 */
	@RequestMapping(value = "/class/curriculum/regist", method = RequestMethod.POST)
	public void insertClassCurriculum(
			@RequestParam(required = false) String img,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String videoPath,
			@RequestParam(required = false) Integer numb,
			@RequestParam(required = false) Integer classIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
//		String token = request.getAttribute("HSID").toString();
//		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		CurInfo curInfo = new CurInfo(classIdx, numb, title, note, videoPath, img);
		if(!userDao.insertCur(curInfo)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	/** 회차 삭제 */
	@RequestMapping(value = "/class/curriculum/{idx}", method = RequestMethod.DELETE)
	public void deleteClassCurriculum(@PathVariable Integer idx, HttpServletRequest request, HttpServletResponse response) {
		
//		String token = request.getAttribute("HSID").toString();
//		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		if(!userDao.deleteCur(idx)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}else {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	
	
	
	
	
	
	
	/** 클래스 조회수 증가 */
	@RequestMapping(value = "/class/{classIdx}/views", method = RequestMethod.POST)
	public void insc123(@PathVariable Integer classIdx, HttpServletRequest request, HttpServletResponse response) {
		userDao.increaseViewOfClass(classIdx);
	}
	/** 클래스 수정 */
	@RequestMapping(value = "/class/{classIdx}", method = RequestMethod.POST)
	public void updateClass(
			@PathVariable Integer classIdx,
			@RequestParam(required = false) String img,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String tags,
			@RequestParam(required = false) Integer catIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		ClassInfo classInfo = userDao.getClassInfo(classIdx);
		if(classInfo.getUserIdx() != userIdx) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		if(img!=null) classInfo.setImg(img);
		if(title!=null) classInfo.setTitle(title);
		if(note!=null) classInfo.setNote(note);
		if(tags!=null) classInfo.setTags(tags);
		if(catIdx!=null) classInfo.setCatIdx(catIdx);
		
		if(!userDao.updateClass(classInfo)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	/** 클래스 등록 */
	@RequestMapping(value = "/class/regist", method = RequestMethod.POST)
	public Map<String,Object> insertClass(
			@RequestParam(required = false) String img,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String tags,
			@RequestParam(required = false) Integer catIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		ClassInfo classInfo = new ClassInfo(userIdx, img, title, note, tags, catIdx);
		if(userDao.insertClass(classInfo)) {
			int idx = userDao.getInsertedClassIdx(classInfo);
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("insertedIdx", idx); // 방금 등록된 Row PK values
			return result;
		}
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}
	/** 클래스 삭제 */
	@RequestMapping(value = "/class/{classIdx}", method = RequestMethod.DELETE)
	public void deleteClass(@PathVariable Integer classIdx, HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		ClassInfo classInfo = userDao.getClassInfo(classIdx);
		if(classInfo!=null && classInfo.getUserIdx() == userIdx) {
			if(!userDao.deleteClass(classIdx)) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}else {
				response.setStatus(HttpServletResponse.SC_OK);
			}
			return;
		}
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}
	/** 클래스 정보 조회 */
	@RequestMapping(value = "/class/{classIdx}", method = RequestMethod.GET)
	public Map<String, Object> getCalssInfomation(
			@PathVariable Integer classIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,Object> rst = new HashMap<String,Object>();
		ClassInfo classInfo = userDao.getClassInfo(classIdx);
		rst.put("info", classInfo);
		return rst;
	}
	/** 클래스 카테고리 목록 쿼리 */
	@RequestMapping(value = "/class/cat", method = RequestMethod.GET)
	public Map<String, Object> loadClassCat(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		List<ClassCat> l = userDao.getClassCatList();
		rst.put("list", l);
		response.setStatus(HttpServletResponse.SC_OK);
		return rst;
	}
	/** 추천 강의 목록 로드 */
	@RequestMapping(value = "/class/recommend", method = RequestMethod.GET)
	public Map<String, Object> loadClassReco(
			@RequestParam(required = false) Integer limitCount,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rst = new HashMap<String,Object>();
		List<ClassInfo> l = userDao.getRecoClassList(limitCount);
		rst.put("list", l);
		return rst;
	}
	/** 클래스 목록 */
	@RequestMapping(value = "/class", method = RequestMethod.GET)
	public Map<String, Object> loadClass(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String q,
			@RequestParam(required = false) Integer rowBlockCount,
			HttpServletRequest request, HttpServletResponse response) {
		
		page = page==null?1:page;
		rowBlockCount = rowBlockCount==null?15:(rowBlockCount>50||rowBlockCount<2)?15:rowBlockCount;
		
		Map<String,Object> rst = new HashMap<String,Object>();
		rst = userService.loadClass(page, rowBlockCount, q);
	
		response.setStatus(HttpServletResponse.SC_OK);
		return rst;
	}
	
	
	

	/** (list) 회원 */
	@RequestMapping(value = "/users/all")
	public Map<String,Object> getUserList(
        @RequestParam(required=false) Integer page,
        @RequestParam(required=false) String q) {
		
        page = (page==null||page<1)?1:page;
        Map<String,Object> rst = userService.loadUserList(page, 10, q);
        return rst;
	}
	
	/** 회원 로그인 */
	@RequestMapping(value = "/users/signin")//, method = RequestMethod.POST)
	public void login(
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String password,
			HttpServletRequest request, HttpServletResponse response, 
			ModelMap model) throws UnsupportedEncodingException {
		
		if(!userService.signIn(email, password, response)) {
			model.put("email", email);
			model.put("error", "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}else {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	/** 회원 가입  */
	@RequestMapping(value = "/users/signup")//, method = RequestMethod.POST)
	public Map<String, Object> signUp(/* @RequestBody UserInfo userInfo, */
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String phonenm,
			@RequestParam String password,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setName(name);
		userInfo.setEmail(email);
		userInfo.setPhonenm(phonenm);
		userInfo.setPassword(password);
		
		
		logger.info(userInfo.toString());
		
		Map<String,Object> rst = new HashMap<String,Object>();
		
		UserInfo signedUser = userService.signUp(userInfo);
		if(signedUser==null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			userInfo.setPassword("");
			rst.put("user", userInfo);
			return rst;
		}
		response.setStatus(HttpServletResponse.SC_OK);
		return null;
	}
	
	/** 회원정보 수정 */
	@RequestMapping(value = "/users/profile", method = RequestMethod.POST)
	public void updateUser(
			@RequestParam(required = false) String img,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String phonenm,
			HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		userDao.updateUserInfo(userIdx, name, phonenm, img);
		
	}
	/** 회원정보 쿼리  */
	@RequestMapping(value = "/users/profile", method = RequestMethod.GET)
	public Map<String, Object> myinfo(HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		UserInfo userInfo = userDao.getUser(userIdx);
		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("user", userInfo);
		return rst;
	}
	
	
	
	/** 스터디 삭제 */
	@RequestMapping(value = "/study/{idx}", method = RequestMethod.DELETE)
	public void deleteStudy(
			@PathVariable Integer idx,
			HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		StudyInfo studyInfo = userDao.getStudy(idx);
		if(studyInfo!=null && studyInfo.getUserIdx() == userIdx) {
			if(!userDao.deleteStudy(idx)) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}else {
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
			
		}
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
		
	}
	
	/** 스터디 조회수 증가 */
	@RequestMapping(value = "/study/{studyIdx}/views", method = RequestMethod.POST)
	public void incS(@PathVariable Integer studyIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		userDao.increaseViewOfStudy(studyIdx);
	}

	/** 스터디 수정 */
	@RequestMapping(value = "/study/{studyIdx}", method = RequestMethod.POST)
	public void updateStudy(
			@RequestParam(required = false) String img,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String station,
			@RequestParam(required = false) String signdate,
			@RequestParam(required = false) Integer maxPeople,
			@PathVariable Integer studyIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		StudyInfo studyInfo = userDao.getStudy(studyIdx);
		
		if(studyInfo.getUserIdx() != userIdx) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		if(img!=null) studyInfo.setImg(img);
		if(title!=null) studyInfo.setTitle(title);
		if(note!=null) studyInfo.setNote(note);
		if(station!=null) studyInfo.setStation(station);
		if(signdate!=null) studyInfo.setSigndate(signdate);
		if(maxPeople!=null) studyInfo.setMaxPeople(maxPeople);
		
		if(!userDao.updateStudy(studyInfo)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	/** 스터디 등록 */
	@RequestMapping(value = "/study/regist", method = RequestMethod.POST)
	public Map<String,Object> insertStudy(
			@RequestParam(required = false) String img,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String station,
			@RequestParam(required = false) String signdate,
			@RequestParam(required = false) Integer maxPeople,
			@RequestParam(required = false) String tags,
			@RequestParam(required = false) Integer catIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
		
		StudyInfo studyInfo = new StudyInfo(userIdx, title, note, img, station, signdate, maxPeople, tags, catIdx);
		if(userDao.insertStudy(studyInfo)) {
			int idx = userDao.getInsertedStudyIdx(studyInfo);
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("insertedIdx", idx); // 방금 등록된 Row PK values
			return result;
		}
		
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}
	
	/** 스터디 정보 조회 */
	@RequestMapping(value = "/study/{studyIdx}", method = RequestMethod.GET)
	public Map<String, Object> getStudy(
			@PathVariable Integer studyIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,Object> rst = new HashMap<String,Object>();
		StudyInfo studyInfo = userDao.getStudy(studyIdx);
		rst.put("info", studyInfo);
		return rst;
	}
	
	/** 스터디 카테고리 목록 쿼리 */
	@RequestMapping(value = "/study/cat", method = RequestMethod.GET)
	public Map<String, Object> loadStudyCat(HttpServletRequest request, HttpServletResponse response) {
	
		Map<String,Object> rst = new HashMap<String,Object>();
		List<StudyCat> l = userDao.getStudyCatList();
		rst.put("list", l);
	
		response.setStatus(HttpServletResponse.SC_OK);
		return rst;
	}
	
	/** 추천 스터디 목록 로드 */
	@RequestMapping(value = "/study/recommend", method = RequestMethod.GET)
	public Map<String, Object> loadStudyReco(
			@RequestParam(required = false) Integer limitCount,
			HttpServletRequest request, HttpServletResponse response) {
	
		Map<String,Object> rst = new HashMap<String,Object>();
		List<StudyInfo> l = userDao.getRecoStudyList(limitCount);
		rst.put("list", l);
	
		response.setStatus(HttpServletResponse.SC_OK);
		return rst;
	}
	
	/** 스터디 목록 쿼리 */
	@RequestMapping(value = "/study", method = RequestMethod.GET)
	public Map<String, Object> loadStudy(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String q,
			@RequestParam(required = false) Integer rowBlockCount,
			HttpServletRequest request, HttpServletResponse response) {
		
		page = page==null?1:page;
		rowBlockCount = rowBlockCount==null?15:(rowBlockCount>50||rowBlockCount<2)?15:rowBlockCount;
		
		Map<String,Object> rst = new HashMap<String,Object>();
		rst = userService.loadStudy(page, rowBlockCount, q);
	
		response.setStatus(HttpServletResponse.SC_OK);
		return rst;
	}
	
	
	
	/** 스터디 수강회원 목록 쿼리 */
	@RequestMapping(value = "/study/{studyIdx}/students", method = RequestMethod.GET)
	public Map<String, Object> loadStudyStudents(
			@PathVariable Integer studyIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
	
		Map<String,Object> rst = new HashMap<String,Object>();
		List<StudyStudent> lists = userDao.getStudyStudents(studyIdx);
		rst.put("list", lists);
	
		response.setStatus(HttpServletResponse.SC_OK);
		return rst;
	}
	
	
	/** 스터디 가입 */
	@RequestMapping(value = "/study/{studyIdx}/students", method = RequestMethod.POST)
	public void registStudyStudents(
			@PathVariable Integer studyIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
	
		if(!userDao.insertStudent(studyIdx, userIdx)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		
	}
	
	/** 스터디 탈퇴 */
	@RequestMapping(value = "/study/{studyIdx}/students", method = RequestMethod.DELETE)
	public void withdrawStudyStudents(
			@PathVariable Integer studyIdx,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		String token = request.getAttribute("HSID").toString();
		int userIdx = Integer.parseInt(jwtService.getMemberId(token));
	
		if(!userDao.deleteStudent(studyIdx, userIdx)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	/** (다중 삭제) 회원 */
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public void deleteUsers(@RequestBody int[] idxArray, HttpServletResponse response) {
	
		for(int i=0;i<idxArray.length;i++) {		
			Integer idx = idxArray[i];
			if(!userDao.deleteUser(idx)) {
				logger.error("정상적으로 삭제되지 않았습니다.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		}      
	}
	
	
	
	/** (삭제) 회원 */
	@RequestMapping(value = "/{idx}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable Integer idx, HttpServletResponse response) {
		
		if(!userDao.deleteUser(idx)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/** (패스워드 수정) 회원 */
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public void updatePassword(@RequestBody Map<String,Object> pMap, HttpServletResponse response) {
		
		Integer idx = Integer.parseInt(pMap.get("idx").toString());
		String password = pMap.get("password").toString();
		
		if(!userDao.updateUserPassword(idx, password)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	/** (탈퇴여부 수정) 회원 */
	@RequestMapping(value = "/enable", method = RequestMethod.PUT)
	public void updateEFlag(@RequestBody Map<String,Object> pMap, HttpServletResponse response) {
		
		Integer idx = Integer.parseInt(pMap.get("idx").toString());
		boolean eFlag = Boolean.parseBoolean(pMap.get("eFlag").toString());
		
		if(!userDao.updateUserEnable(idx, eFlag)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	/** (삭제) 권한 */
	@RequestMapping(value = "/auth", method = RequestMethod.DELETE)
	public void delAuth(@RequestBody Map<String,Object> pMap, HttpServletResponse response) {
		
		Integer idx = Integer.parseInt(pMap.get("idx").toString());
		String authName = pMap.get("authName").toString().trim();
		authName = "ROLE_"+authName;
		
		if(!userDao.deleteAuth(idx, authName)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	
	

	
}
