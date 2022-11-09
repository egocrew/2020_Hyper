package com.min.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.app.model.log.Log_IService;

@Controller
public class ErrorController {
	
	@Autowired
	Log_IService lService;
	
//	private static final Logger log = LoggerFactory.getLogger(ErrorController.class);
	
//	String erMsg01 = "404 에러 발생";
//	String erMsg02 = "500 에러 발생";
//	String erTime = "[\t{}]";
	
	@RequestMapping(value="/404", method = RequestMethod.GET)
	public String error404(HttpServletRequest request) {
		lService.setLog("404 Error", getClientIP(request));
//		log.info(erTime+erMsg01, new Date());
		return "redirect:/";
//		return "/component/error/error404";
	}
	
	@RequestMapping(value="/500", method = RequestMethod.GET)
	public String error500(HttpServletRequest request) {
		lService.setLog("500 Error", getClientIP(request));
//		log.info(erTime+erMsg02, new Date());
		return "redirect:/";
//		return "/component/error/error500";
	}
	
	//IP 확인
	public String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR"); 
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getHeader("Proxy-Client-IP");
		}
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getHeader("WL-Proxy-Client-IP");  // 웹로직
		}
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getRemoteAddr() ;
		}
		
		return ip;
		
	}
	
}
