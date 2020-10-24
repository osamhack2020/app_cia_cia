package com.hs.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.hs.common.support.SupportLog;


//@ControllerAdvice
public class AnnotationExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/*
	 * [전체] Exception  
	 * 
	 */
	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
		
		logger.error("{}{}{}", new Object[]{"[Exception] [\n", SupportLog.getStackTrace(e), "]"});

    	ModelAndView mnv = new ModelAndView("error/500");	
        return mnv;
    }
    
	/*
	 * [런타임] RuntimeException  
	 * 
	 */
    @ExceptionHandler(RuntimeException.class) 
    public ModelAndView handleRuntimeException(RuntimeException e) {
    	
    	logger.error("{}{}{}", new Object[]{"[RuntimeException] [\n", SupportLog.getStackTrace(e), "]"});

    	ModelAndView mnv = new ModelAndView("error/500");
        return mnv;
    }
    
    /*
	 * [접근거루] AccessDeniedException  
	 * 
	 * 이건 403으로 처리하는게 맞다. 
	 * 
	 */
    @ExceptionHandler(AccessDeniedException.class) 
    public ModelAndView handleAccessDeniedException(AccessDeniedException e) {
       
    	logger.error("{}{}{}", new Object[]{"[AccessDeniedException] [\n", SupportLog.getStackTrace(e), "]"});

    	ModelAndView mnv = new ModelAndView("error/403");
        return mnv;
    }
    
    /*
	 * [요청파라미터 타입에러] TypeMismatchException  
	 * 
	 * 이건 404로 처리하는게 맞다.
	 * 
	 */
    @ExceptionHandler(TypeMismatchException.class) 
    public ModelAndView typeMismatchException(TypeMismatchException e) {
       
    	//logger.error("{}{}{}", new Object[]{"[TypeMismatchException] [\n", SupportLog.getStackTrace(e), "]"});
    	logger.error("{}{}{}", new Object[]{"[TypeMismatchException] [\n", e.getMessage(), "]"});
    	
    	ModelAndView mnv = new ModelAndView("error/404");
        return mnv;
    }
    
    /*
	 * [GET 또는 POST Not Supported 에러] HttpRequestMethodNotSupportedException  
	 * 
	 * 이건 404로 처리하는게 맞다.
	 * 
	 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class) 
    public ModelAndView httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

    	//logger.error("{}{}{}", new Object[]{"[HttpRequestMethodNotSupportedException] [\n", SupportLog.getStackTrace(e), "]"});
    	logger.error("{}{}{}", new Object[]{"[HttpRequestMethodNotSupportedException] [\n", e.getMessage(), "]"});
    	
    	ModelAndView mnv = new ModelAndView("error/404");
        return mnv;
    }
	
}
