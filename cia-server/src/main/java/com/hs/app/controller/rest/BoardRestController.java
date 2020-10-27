package com.hs.app.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.app.board.dao.BoardDao;
import com.hs.app.board.service.BoardService;
import com.hs.app.board.vo.BoardInfo;
import com.hs.app.board.vo.BoardMenu;
import com.nhncorp.lucy.security.xss.XssFilter;

@RestController
@RequestMapping(value = "/api/boards")
public class BoardRestController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private BoardService boardService;	
	@Autowired private BoardDao boardDao;
	
	/*
		＃BoardService   
		
			[게시물 등록] 			/api/boards 		[POST] 
			[게시물 다중 삭제] 		/api/boards 		[DELETE] 
			[게시물 목록 쿼리] 		/api/boards 		[GET]
			[게시물 수정] 			/api/boards/{idx} 	[PUT] 
			[게시물 쿼리]			/api/boards/{idx}	[GET]
			
			[게시판 메뉴 등록]		/api/boards/menus		[POST]
			[게시판 메뉴 수정]		/api/boards/menus/{idx}	[PUT]
			[게시판 메뉴 목록 쿼리] 	/api/boards/menus 		[GET]
			[게시판 메뉴 삭제]		/api/boards/menus		[DELETE]
		
	
	
	
		P.S. 추후 문서(Docs)로 작성 예정	
	*/
	    
    
	/**************************** BOARD ****************************/
	
	/** (list) 게시물 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Map<String,Object> getBoardItems(
        @RequestParam(required=false) Integer page,
        @RequestParam(required=false) Integer menuIdx,
        @RequestParam(required=false) String q) {
		
        page = (page==null||page<1)?1:page;
        Map<String,Object> rst = boardService.loadBoardList(page, menuIdx, 10, q);
        return rst;
	}
	
	/** (delete) 게시물 */
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public void deleteBoardItem(@RequestBody int[] idxArray,
            HttpServletRequest request, HttpServletResponse response) {
		
		for(int i=0;i<idxArray.length;i++) {		
			Integer idx = idxArray[i];
			BoardInfo info = boardDao.getBoardInfo(idx);
			if(info==null) {
				logger.error("존재하지 않은 게시물이므로 삭제할 수 없습니다.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			Integer r = boardDao.deleteBoardInfo(idx);
			if(r==null || r<1) {
				logger.error("게시물이 정상적으로 삭제되지 않았습니다.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
        
        response.setStatus(HttpServletResponse.SC_OK);       
	}
	
	
    /** (insert) 게시물 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Map<String,Object> updateBoardItem(@RequestBody BoardInfo info,
            HttpServletRequest request, HttpServletResponse response) {
		
		info.setIdx(null);// Important**
		if(info!=null && info.getTitle()!=null && info.getMenuIdx()!=null && info.getNote()!=null) {			
			
			// [1] check description (tags, title, note, thumbImg) & length & Xss filtering 		
			XssFilter filter = XssFilter.getInstance("xss/lucy-xss-superset.xml");
			String clean = filter.doFilter(info.getNote());
			info.setNote(clean);
			
			// [2] check user (writer, userIdx, authority)
			info.setUserIdx(1);
			info.setWriter("작성자");
			// [3] update (+check menu)
			Map<String,Object> result = new HashMap<String,Object>();
	        if(boardService.updateBoardInfo(info)) {
	        	response.setStatus(HttpServletResponse.SC_OK);
	        	result.put("boardInfo", info);
	        	return result;
	        }
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return null;
	}
	
	 /** (update) 게시물 */
	@RequestMapping(value = "/{idx}", method = RequestMethod.PUT)
	public Map<String,Object> updateBoardItem(
			@PathVariable int idx, @RequestBody BoardInfo info,
            HttpServletRequest request, HttpServletResponse response) {
        
		info.setIdx(idx);// Important** 
		if(info!=null && info.getTitle()!=null && info.getMenuIdx()!=null && info.getNote()!=null) {
			
			// [1] check description (tags, title, note, thumbImg) & length & Xss filtering 
			XssFilter filter = XssFilter.getInstance("xss/lucy-xss-superset.xml");
			String clean = filter.doFilter(info.getNote());
			info.setNote(clean);
			
			// [2] check user (writer, userIdx, authority)
			info.setUserIdx(1);
			info.setWriter("작성자");
			// [3] update (+check menu)
			Map<String,Object> result = new HashMap<String,Object>();
	        if(boardService.updateBoardInfo(info)) {
	        	response.setStatus(HttpServletResponse.SC_OK);
	        	result.put("boardInfo", info);
	        	return result;
	        }
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return null;
	}
	
	/** (view) 게시물 */
	@RequestMapping(value = "/{idx}", method = RequestMethod.GET)
	public BoardInfo getBoardItem(@PathVariable int idx) {
		
		return boardDao.getBoardInfo(idx);
	}
    
    
	/**************************** MENU ****************************/
    
    /** (list) 메뉴 */
	@RequestMapping(value = "/menus", method = RequestMethod.GET)
	public Map<String,Object> getBoardMenus(@RequestParam(required = false) Boolean addInfo) {
        
        Map<String,Object> rst = new HashMap<String,Object>();
        List<BoardMenu> menuList = boardDao.getBoardMenuList(addInfo);
        rst.put("list", menuList);
        return rst;
	}
	
	/** (delete) 메뉴 */
	@RequestMapping(value = "/menus", method = RequestMethod.DELETE)
	public void deleteBoardMenu(@RequestBody int[] idxArray, HttpServletResponse response) {

		for(int i=0;i<idxArray.length;i++) {
			Integer idx = idxArray[i];			
			Integer r = boardDao.deleteBoardMenu(idx);
			if(r==null || r<1) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
        response.setStatus(HttpServletResponse.SC_OK);       
	}
	
	/** (insert) 메뉴 */
	@RequestMapping(value = "/menus", method = RequestMethod.POST)
	public void insertBoardMenu(@RequestBody BoardMenu boardMenu, HttpServletResponse response) {
        
		if(boardMenu!=null) {			
			Integer r = boardDao.insertBoardMenu(boardMenu);
    		if(r!=null&&r>0) {
    			response.setStatus(HttpServletResponse.SC_OK);
    			return;
    		}
		}
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
	}
	
	/** (update) 메뉴 */
	@RequestMapping(value = "/menus/{idx}", method = RequestMethod.PUT)
	public void updateBoardMenu(@PathVariable int idx,
			@RequestBody BoardMenu boardMenu, HttpServletResponse response) { 
		
		if(boardMenu!=null) {		
			boardMenu.setIdx(idx);
			Integer r = boardDao.updateBoardMenu(boardMenu);
    		if(r!=null&&r>0) {
    			response.setStatus(HttpServletResponse.SC_OK);
    			return;
    		}
		}
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
	}

}
