package com.hs.app.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hs.app.user.vo.ClassCat;
import com.hs.app.user.vo.ClassInfo;
import com.hs.app.user.vo.ClassStudent;
import com.hs.app.user.vo.CurInfo;
import com.hs.app.user.vo.StudyCat;
import com.hs.app.user.vo.StudyInfo;
import com.hs.app.user.vo.StudyStudent;
import com.hs.app.user.vo.UserInfo;

public class UserDao extends SqlSessionDaoSupport {
	
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	
	
	
	public List<Map<String,Object>> loadJoinRate2() {
		return getSqlSession().selectList("user.loadJoinRate2");
	}
	public List<Map<String,Object>> loadJoinRate1() {
		return getSqlSession().selectList("user.loadJoinRate1");
	}
	
	public List<Map<String,Object>> loadClassRateByCat2() {
		return getSqlSession().selectList("user.loadClassRateByCat2");
	}
	
	public List<Map<String,Object>> loadStudyRateByCat2() {
		return getSqlSession().selectList("user.loadStudyRateByCat2");
	}
	
	
	
	
	
	
	
	
	
	/** 회원 목록 크기 가져오기 */
    public int getUserSize(String q) {
    	Map<String,Object> pMap = new HashMap<String,Object>();
    	pMap.put("q", q);
        return getSqlSession().selectOne("user.getUserSize", pMap);
    }
    
    /** 회원 목록 가져오기 */
    public List<UserInfo> getUserList(int startRow, int rowBlockCount, String q) {
        Map<String,Object> pMap = new HashMap<String,Object>();
        pMap.put("startRow", startRow);
        pMap.put("rowBlockCount", rowBlockCount);
        pMap.put("q", q);
        return getSqlSession().selectList("user.getUserList", pMap);
    }
	
	public List<Map<String,Object>> loadClassRateByCat() {
		return getSqlSession().selectList("user.loadClassRateByCat");
	}
	
	public List<Map<String,Object>> loadStudyRateByCat() {
		return getSqlSession().selectList("user.loadStudyRateByCat");
	}
	
	public List<StudyInfo> loadMyStudy(int userIdx) {
		return getSqlSession().selectList("user.loadMyStudy", userIdx);
	}
	
	public List<ClassInfo> loadMyClass(int userIdx) {
		return getSqlSession().selectList("user.loadMyClass", userIdx);
	}
	
	
	public List<ClassInfo> loadRegistClassByUserIdx(int userIdx) {
		return getSqlSession().selectList("user.loadRegistClassByUserIdx", userIdx);
	}
	
	public List<StudyInfo> loadRegistStudyByUserIdx(int userIdx) {
		return getSqlSession().selectList("user.loadRegistStudyByUserIdx", userIdx);
	}
	
	public List<ClassStudent> getClassStudents(int classIdx) {
		return getSqlSession().selectList("user.getClassStudents", classIdx);
	}
	
	public boolean deleteClassStudent(int classIdx, int userIdx) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("classIdx", classIdx);
		pMap.put("userIdx", userIdx);
		Integer r =  getSqlSession().delete("user.deleteClassStudent", pMap);
		if(r!=null&&r>0) {
			return true;
		}
		return false;
	}
	
	public boolean insertClassStudent(int classIdx, int userIdx) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("classIdx", classIdx);
		pMap.put("userIdx", userIdx);
		Integer r =  getSqlSession().insert("user.insertClassStudent", pMap);
		if(r!=null&&r>0) {
			return true;
		}
		return false;
	}
	
	public boolean deleteCur(int idx) {
		Integer r =  getSqlSession().delete("user.deleteCur", idx);
		if(r!=null && r > 0) {
			return true;
		}
		return false;
	}
	
	public boolean insertCur(CurInfo curInfo) {
		Integer r =  getSqlSession().insert("user.insertCur", curInfo);
		if(r!=null && r > 0) {
			return true;
		}
		return false;
	}
	
	public List<CurInfo> loadClassCurriculum(int classIdx) {
		return getSqlSession().selectList("user.loadClassCurriculum", classIdx);
	}
	
	public void increaseViewOfClass(int idx) {
		getSqlSession().update("user.increaseViewOfClass", idx);
	}
	
	public boolean deleteClass(Integer idx) {
		Integer r =  getSqlSession().delete("user.deleteClass", idx);
		if(r!=null && r > 0) {
			return true;
		}
		return false;
	}
	
	public boolean updateClass(ClassInfo classInfo) {
		Integer r =  getSqlSession().update("user.updateClass", classInfo);
		if(r!=null && r > 0) {
			return true;
		}
		return false;
	}
	
	public boolean insertClass(ClassInfo classInfo) {
		Integer r = getSqlSession().insert("user.insertClass", classInfo);
		if(r != null && r > 0) {
			return true;
		}
		return false;
	}
	
	public Integer getInsertedClassIdx(ClassInfo classInfo) {
		return getSqlSession().selectOne("user.getInsertedClassIdx", classInfo);
	}
	
	public ClassInfo getClassInfo(Integer idx) {
		return getSqlSession().selectOne("user.getClassInfo", idx); 
	}
	
	public List<ClassInfo> getRecoClassList(Integer limitCount) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("limitCount", limitCount);
		return getSqlSession().selectList("user.getRecoClassList", pMap);
	}
	
	public List<ClassCat> getClassCatList() {
		return getSqlSession().selectList("user.getClassCatList");
	}
	
	public int getClassSize(String q) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("q", q);
		return getSqlSession().selectOne("user.getClassSize",pMap);
	}
	
	public List<ClassInfo> loadClass(int startRow, int rowBlockCount, String q) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("startRow", startRow);
		pMap.put("q", q);
		pMap.put("rowBlockCount", rowBlockCount);
		return getSqlSession().selectList("user.loadClass", pMap);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<StudyInfo> getRecoStudyList(Integer limitCount) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("limitCount", limitCount);
		return getSqlSession().selectList("user.getRecoStudyList", pMap);
	}
	
	public List<StudyCat> getStudyCatList() {
		return getSqlSession().selectList("user.getStudyCatList");
	}
	
	public StudyInfo getStudy(int idx) {
		return getSqlSession().selectOne("user.getStudy", idx);
	}
	
	public boolean deleteStudy(int idx) {
		Integer r = getSqlSession().delete("user.deleteStudy", idx);
		if(r!=null&&r>0) {
			return true;
		}
		return false;
	}
	
	public boolean updateStudy(StudyInfo studyInfo) {
		Integer r = getSqlSession().update("user.updateStudy", studyInfo);
		if(r!=null&&r>0) {
			return true;
		}
		return false;
	}
	
	public boolean insertStudy(StudyInfo studyInfo) {
		Integer r = getSqlSession().insert("user.insertStudy", studyInfo);
		if(r!=null&&r>0) {
			return true;
		}
		return false;
	}
	
	public void increaseViewOfStudy(int idx) {
		getSqlSession().update("user.increaseViewOfStudy", idx);
	}
	
	public int getInsertedStudyIdx(StudyInfo studyInfo) {
		return getSqlSession().selectOne("user.getInsertedStudyIdx", studyInfo);
	}
	
	public int getStudySize(String q) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("q", q);
		return getSqlSession().selectOne("user.getStudySize",pMap);
	}
	
	public List<StudyInfo> loadStudy(int startRow, int rowBlockCount, String q) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("startRow", startRow);
		pMap.put("q", q);
		pMap.put("rowBlockCount", rowBlockCount);
		return getSqlSession().selectList("user.loadStudy", pMap);
	}
	
	public List<StudyStudent> getStudyStudents(int studyIdx) {
		return getSqlSession().selectList("user.getStudyStudents", studyIdx);
	}
	
	public boolean deleteStudent(int studyIdx, int userIdx) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("studyIdx", studyIdx);
		pMap.put("userIdx", userIdx);
		Integer r =  getSqlSession().delete("user.deleteStudent", pMap);
		if(r!=null&&r>0) {
			return true;
		}
		return false;
	}
	
	public boolean insertStudent(int studyIdx, int userIdx) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("studyIdx", studyIdx);
		pMap.put("userIdx", userIdx);
		Integer r =  getSqlSession().insert("user.insertStudent", pMap);
		if(r!=null&&r>0) {
			return true;
		}
		return false;
	}
	
	public UserInfo getUser(int idx) {
		return getSqlSession().selectOne("user.getUser", idx);
	}
	
	
	
	
	
	
	

	
	public Integer updateEnableCheckEmail(int idx) {
		return getSqlSession().update("user.updateEnableCheckEmail", idx);
	}
	
	public Integer hasCheckEmail(int userIdx, String signKey) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("userIdx", userIdx);
		pMap.put("signKey", signKey);
		return getSqlSession().selectOne("user.hasCheckEmail", pMap);
	}
	
	public boolean insertCheckEmail(int userIdx, String signKey) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("userIdx", userIdx);
		pMap.put("signKey", signKey);
		Integer r = getSqlSession().insert("user.insertCheckEmail", pMap);
		if(r != null && r > 0) {
			return true;
		}
		return false;
	}


	
	
	public boolean updateUserInfo(int userIdx, String name, String phonenm, String img) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("userIdx", userIdx);
		pMap.put("name", name);
		pMap.put("phonenm", phonenm);
		pMap.put("img", img);
		Integer r = getSqlSession().update("user.updateUserInfo", pMap);
		if(r != null && r > 0) {
			return true;
		}
		return false;
	}
	

	
	/** 이메일로 회원번호 찾기 */
	public UserInfo getUserIdxByEmail(String email) {
		return getSqlSession().selectOne("user.getUserIdxByEmail", email);
	}
	
	/** 회원 등록 */
	public UserInfo insertUserInfo(UserInfo userInfo) {
		Integer r = getSqlSession().insert("user.insertUserInfo", userInfo);
		if(r!=null&&r>0) {
			UserInfo dbInfo = getSqlSession().selectOne("user.getUserIdxByEmail", userInfo.getEmail());
			if(dbInfo!=null) {
				return dbInfo;
			}
		}
		return null;
	}
	
	/** 회원 삭제 */
	public boolean deleteUser(int idx) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("idx", idx);
	
		Integer r = getSqlSession().delete("user.deleteUser", pMap);
		if(r != null && r > 0) {
			return true;
		}
		return false;
	}
	
	/** 패스워드 변경 */
	public boolean updateUserPassword(int idx, String password) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("idx", idx);
		
		// 패스워드 단방향 해시알고리즘 인코딩
		String encodedPassword = passwordEncoder.encode(password);
		pMap.put("password", encodedPassword);
		
		Integer r = getSqlSession().update("user.updateUserPassword", pMap);
		if(r != null && r > 0) {
			return true;
		}
		return false;
	}
	
	/** 회원 탈퇴플래그값 변경 */
	public boolean updateUserEnable(int idx, boolean eFlag) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("idx", idx);
		pMap.put("eFlag", eFlag);
		Integer r = getSqlSession().update("user.updateUserEnable", pMap);
		if(r != null && r > 0) {
			return true;
		}
		return false;
	}
	
	/** 권한 삭제 */
	public boolean deleteAuth(int userIdx, String authName) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("userIdx", userIdx);
		pMap.put("authName", authName);
		Integer r = getSqlSession().insert("user.deleteAuth", pMap);
		if(r != null && r > 0) {
			return true;
		}
		return false;
	}
	
	/** 권한 등록 */
	public boolean insertAuth(int userIdx, String authName) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("userIdx", userIdx);
		pMap.put("authName", authName);
		Integer r = getSqlSession().insert("user.insertAuth", pMap);
		if(r != null && r > 0) {
			return true;
		}
		return false;
	}
	
	
	
    
  
    
}
