package com.hs.app.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.app.basic.dao.BasicDao;
import com.hs.app.board.dao.BoardDao;
import com.hs.app.board.service.BoardService;
import com.hs.app.board.vo.BoardMenu;

@RestController
@RequestMapping(value = "/api/basic")
public class BasicRestController {
	
private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private BoardService boardService;	
	@Autowired private BoardDao boardDao;
	@Autowired private BasicDao basicDao;
	
	
	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public Map<String,Object> getBoardMenus(@RequestParam(required = false) Boolean addInfo) {
        
        Map<String,Object> rst = new HashMap<String,Object>();
        List<Map<String,Object>> list = basicDao.getFaqList();
        rst.put("list", list);
        return rst;
	}
	

}
