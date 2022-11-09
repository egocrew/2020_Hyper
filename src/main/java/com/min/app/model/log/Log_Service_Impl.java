package com.min.app.model.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.app.dto.Log_Dto;

@Service
public class Log_Service_Impl implements Log_IService{

	@Autowired
	private Log_IDao dao;
	
	@Override
	public List<Log_Dto> getLog() {
		return dao.getLog();
	}
	
	@Override
	public List<Log_Dto> getLog(String date) {
		return dao.getLog(date);
	}
	
	@Override
	public List<Log_Dto> getLog(String log_date, String log_message) {
		Log_Dto dto = new Log_Dto();
		dto.setLog_date(log_date);
		dto.setLog_message(log_message);
		return dao.getLog(dto);
	}
	
	@Override
	public boolean setLog(String message, String ip) {
		Log_Dto dto = new Log_Dto(message, ip);
		return dao.setLog(dto);
	}


}
