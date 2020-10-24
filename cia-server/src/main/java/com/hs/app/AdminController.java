package com.hs.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hs.app.board.dao.BoardDao;
import com.hs.app.board.service.BoardService;
import com.hs.app.board.vo.BoardInfo;
import com.hs.app.jwt.service.JwtService;
import com.hs.app.user.dao.UserDao;
import com.hs.common.control.BaseController;

@Controller
@RequestMapping(value = "/coldbrew/")
public class AdminController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
		
	@Autowired private BoardService boardService;
	@Autowired private BoardDao boardDao;
	@Autowired private UserDao userDao;
	@Autowired private JwtService jwtService;
	@Autowired private BCryptPasswordEncoder passwordEncoder;

	
	
    /** 1 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAdminPage(HttpServletRequest request, ModelMap model) {
        
//		String token = request.getAttribute("HSID").toString();
//		String idxByToken = jwtService.getMemberId(token);		
//		UserInfo user = userDao.getUser(Integer.parseInt(idxByToken));
//		if( !(user.getAuthList()!=null && user.getAuthList().contains("ROLE_MASTER")) ) {
//			return "error/404";// 접근 불가
//		}
		
		
        return "redirect:/coldbrew/pg/user/list";
	}
	
	
	
	
	
	
	/** 1 */
	@RequestMapping(value = "pg/app", method = RequestMethod.GET)
	public String getAppPage(ModelMap model) {
        
        return "run:app";
	}
	
	
	
	/** 캐시 목록 PAGE */
	@RequestMapping(value = "/pg/cash/list", method = RequestMethod.GET)
	public String getCashesListPage(ModelMap model) {
        
        return "run:cash/list";
	}
	
	/****************************▽▽▽[결제]▽▽▽****************************/
	/** 목록 */
	@RequestMapping(value = "/pg/pay/list", method = RequestMethod.GET)
	public String getPayListPage(ModelMap model) {
        
        return "run:pay/list";
	}
	
	/** 목록 */
	@RequestMapping(value = "/pg/exchange/list", method = RequestMethod.GET)
	public String getExchangePage(ModelMap model) {
        
        return "run:exchange/list";
	}
	
	/****************************▽▽▽[회원]▽▽▽****************************/
    
	/** 쪽지 목록 PAGE */
	@RequestMapping(value = "/pg/notes/list", method = RequestMethod.GET)
	public String getNotesListPage(ModelMap model) {
        
        return "run:notes/list";
	}
	
	/** 회원 목록 PAGE */
	@RequestMapping(value = "/pg/user/list", method = RequestMethod.GET)
	public String getUserListPage(ModelMap model) {
        
        return "run:user/list";
	}
	
	/** 알바 목록 PAGE */
	@RequestMapping(value = "/pg/user/alba/list", method = RequestMethod.GET)
	public String getAlbaListPage(ModelMap model) {
        
        return "run:user/alba_list";
	}
	
	/** 회원 상세 PAGE */
	@RequestMapping(value = "/pg/user/view", method = RequestMethod.GET)
	public String getUserViewPage(@RequestParam(required=true) Integer idx, ModelMap model) {
//        
//		UserInfo info = userDao.getUser(idx);
//		if(info==null) {
//			return "redirect:/coldbrew/pg/user/list";
//		}
//        model.put("userInfo", info);
        return "run:user/view";
	}
	
	
    /****************************▽▽▽[게시판]▽▽▽****************************/
    
	/** 메뉴 관리 PAGE */
	@RequestMapping(value = "/pg/board/menu", method = RequestMethod.GET)
	public String getMenuManagePage(ModelMap model) {
        
        return "run:board/menu";
	}
	
    /** 게시판 목록 PAGE */
	@RequestMapping(value = "/pg/board/list", method = RequestMethod.GET)
	public String getListPage(ModelMap model) {
        
        Map<String,Object> rst = boardService.loadBoardList(1, null, 10, null);
        model.put("result", rst);
        return "run:board/list";
	}
	
    /** 게시판 등록 PAGE */
	@RequestMapping(value = "/pg/board/input", method = RequestMethod.GET)
	public String getInputpage(@RequestParam(required=false) Integer idx, ModelMap model) {
		
		BoardInfo boardInfo = null;
		if(idx!=null && idx>0) {
			boardInfo = boardDao.getBoardInfo(idx);
		}
		if(boardInfo==null) {
			boardInfo = new BoardInfo();
		}
		model.put("boardInfo", boardInfo);
        
        Map<String,Object> rst = boardService.loadBoardList(1, null, 10, null);
        model.put("result", rst);
        return "run:board/input";
	}
	
	/** 게시판 상세조회 PAGE */
	@RequestMapping(value = "/pg/board/view", method = RequestMethod.GET)
	public String getViewPage(@RequestParam(required=true) Integer idx, ModelMap model) {
        
		BoardInfo info = boardDao.getBoardInfo(idx);
		if(info==null) {
			return "redirect:/coldbrew/pg/board/list";
		}
        model.put("boardInfo", info);
        return "run:board/view";
	}
    


}
