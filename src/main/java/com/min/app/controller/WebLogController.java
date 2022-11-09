package com.min.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.app.dto.Log_Dto;
import com.min.app.dto.User_Dto;
import com.min.app.model.log.Log_IService;

@Controller
@RequestMapping(value="/log")
public class WebLogController {
	
	@Autowired
	private Log_IService service;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String init() {
		return "redirect:/log/report";
	}

	// 로그 전체조회
	@RequestMapping(value="/report", method = RequestMethod.GET)
	public String getLogAll(HttpSession session, Model model) {
		User_Dto user_dto = (User_Dto)session.getAttribute("Ldto");
		if (user_dto==null) {
			return "redirect:/";
		}else if(user_dto.getSetting_nck().equalsIgnoreCase("Hyper")) {
			List<Log_Dto> list = service.getLog();
			model.addAttribute("log",list);
			return "component/log/report";
		}else {
			return "redirect:/";
		}
	}
	
	// 로그 날짜조회
	@RequestMapping(value="/date", method = RequestMethod.POST)
	public String getLog(Model model, String log_date) {
		List<Log_Dto> list = service.getLog(log_date);
		model.addAttribute("log",list);
		return "component/log/report";
	}
	
	// 로그 상세조회
	@RequestMapping(value="/detail", method = RequestMethod.POST)
	public String getLogDetail(Model model, String log_date, String log_message) {
		List<Log_Dto> list = service.getLog(log_date, log_message);
		model.addAttribute("log",list);
		return "component/log/report";
	}
}
