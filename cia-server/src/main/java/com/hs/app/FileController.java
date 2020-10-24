package com.hs.app;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hs.common.service.FileService;
import com.hs.common.vo.FileResult;

@Controller
@RequestMapping(value = "/file/")
public class FileController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private FileService fileService;
	

	@RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(MultipartHttpServletRequest mRequest, @RequestParam(required=false) String etype, HttpServletResponse response) {
		
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		
		FileResult fileResult = fileService.upload(mRequest, "/resources/img/", false, null);
		if(fileResult.isSuccess()){
			
			//logger.info("successed file save");
			//return fileResult.getFilePath() + fileResult.getFileName();
			/*
			 * File Full Path : fileResult.getFilePath() + fileResult.getFileName();
			 */
			
			if(etype!=null&&etype.equals("drag")) {
				return (fileResult.getFilePath() + fileResult.getFileName());
			}
			
			return "<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(1, '" + (fileResult.getFilePath() + fileResult.getFileName()) + "', '');</script>";
		}
		return null;
	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(MultipartHttpServletRequest mRequest, HttpServletResponse response) {
		
		response.setHeader("X-Frame-Options", "SAMEORIGIN"); // only same domain 
		System.out.println("Try upload files...");
		
		FileResult fileResult = fileService.upload(mRequest, "/resources/img/", false, null);
		if(fileResult.isSuccess()){
			return (fileResult.getFilePath() + fileResult.getFileName());		
		}
		return null;
	}

}
