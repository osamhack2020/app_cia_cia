package com.hs.app.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hs.app.board.dao.BoardDao;
import com.hs.app.board.vo.BoardInfo;
import com.hs.app.board.vo.BoardMenu;
import com.hs.common.util.PageUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BoardService {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired private BoardDao boardDao;
    
	
    
    /** 게시물 등록 및 수정 */
    public boolean updateBoardInfo(BoardInfo boardInfo) {
    	
    	BoardMenu menu = boardDao.getBoardMenu(boardInfo.getMenuIdx());
		if(menu==null) {
			logger.error("menuIdx="+boardInfo.getMenuIdx()+"에 해당하는 메뉴 DB정보가 없습니다.");
			return false;		
		}
    	
    	Integer r = null;
    	if(boardInfo.getIdx()==null) {
    		r = boardDao.insertBoardInfo(boardInfo);
    		if(r!=null&&r>0) {
    			int idx = boardDao.getInsertedBaordInfoIdx(boardInfo); // 등록한 게시물 IDX쿼리
    			boardInfo.setIdx(idx);
    		}
    	}else {
    		r = boardDao.updateBoardInfo(boardInfo);
    	}
    	logger.debug("r = "+r);
    	if(r==null||r<1) {
    		logger.error("게시물 등록/수정이 정상적으로 처리되지 않았습니다.");
    		return false;
    	}
        return true;
    }
    
    /** 게시판 목록 로드 */
    public Map<String,Object> loadBoardList(int page, Integer menuIdx, int rowBlockCount, String q) {
        
        page = page<1?1:page;
        if(menuIdx != null) {
            BoardMenu menu = boardDao.getBoardMenu(menuIdx);
            if(menu==null) {
            	menuIdx = null;
            }
        }
        if(q!=null&&q!="") {q = q.trim(); /*logger.debug("검색어: "+q+"로 게시물 검색..");*/}
        else {q=null;}
        
        PageUtil pu = new PageUtil(page,boardDao.getBoardSize(menuIdx, q),rowBlockCount,10);			
		List<BoardInfo> lists = boardDao.getBoardList(menuIdx, pu.getStartRow(), pu.getRowBlockCount(), q);		
		
        Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("pageNav", pu);
		rst.put("list", lists);
        return rst;
    }
    
    
    
    
    
    
    
    
    
}