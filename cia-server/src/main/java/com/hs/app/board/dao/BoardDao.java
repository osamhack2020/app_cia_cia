package com.hs.app.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hs.app.board.vo.BoardInfo;
import com.hs.app.board.vo.BoardMenu;

import org.mybatis.spring.support.SqlSessionDaoSupport;


public class BoardDao extends SqlSessionDaoSupport {
    
	public Integer updateBoardMenu(BoardMenu boardMenu) {
		return getSqlSession().update("board.updateBoardMenu", boardMenu);
	}
	
	public Integer insertBoardMenu(BoardMenu boardMenu) {
		return getSqlSession().insert("board.insertBoardMenu", boardMenu);
	}
	
	public Integer deleteBoardMenu(int idx) {
		return getSqlSession().delete("board.deleteBoardMenu", idx);
	}
	
	public Integer deleteBoardInfo(int idx) {
		return getSqlSession().delete("board.deleteBoardInfo", idx);
	}
	
	public Integer getInsertedBaordInfoIdx(BoardInfo boardInfo) {
		return getSqlSession().selectOne("board.getInsertedBaordInfoIdx", boardInfo);
	}
    
    public Integer insertBoardInfo(BoardInfo boardInfo) {
        return getSqlSession().insert("board.insertBoardInfo", boardInfo);
    }
    
    public Integer updateBoardInfo(BoardInfo boardInfo) {
        return getSqlSession().update("board.updateBoardInfo", boardInfo);
    }
    
    
    /** 조회수 증가 */
    public void updateBoardViews(int idx) {
        Integer r = getSqlSession().update("board.updateBoardViews", idx);
        if(r==null || r<1) {
            System.out.println("조회수 증가 실패..");
        }
    }
    
    /** 메뉴 목록 쿼리 */
    public List<BoardMenu> getBoardMenuList(Boolean addInfo) {
    	Map<String,Object> pMap = new HashMap<String,Object>();
    	pMap.put("addInfo", addInfo);
        return getSqlSession().selectList("board.getBoardMenuList", pMap);
    }
    
    /** 게시판메뉴 정보 조회하기 */
    public BoardMenu getBoardMenu(Integer idx) {
        return getSqlSession().selectOne("board.getBoardMenu", idx);
    }
    
    /** 게시물수 가져오기 */
    public int getBoardSize(Integer menuIdx, String q) {
    	Map<String,Object> pMap = new HashMap<String,Object>();
    	pMap.put("menuIdx", menuIdx);
    	pMap.put("q", q);
        return getSqlSession().selectOne("board.getBoardSize", pMap);
    }
    
    /** 목록 가져오기 */
    public List<BoardInfo> getBoardList(Integer menuIdx, int startRow, int rowBlockCount, String q) {
        Map<String,Object> pMap = new HashMap<String,Object>();
        pMap.put("menuIdx", menuIdx);
        pMap.put("startRow", startRow);
        pMap.put("rowBlockCount", rowBlockCount);
        pMap.put("q", q);
        return getSqlSession().selectList("board.getBoardList", pMap);
    }
    
    public BoardInfo getBoardInfo(int idx) {
    	return getSqlSession().selectOne("board.getBoardInfo", idx);
    }
    
    
}