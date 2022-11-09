package com.min.app.model.log;

import java.util.List;

import org.springframework.stereotype.Service;

import com.min.app.dto.Log_Dto;

@Service
public interface Log_IService {
	
	//로그 전체 가져오기
	public List<Log_Dto> getLog();
	
	//로그 날짜별 가져오기
	public List<Log_Dto> getLog(String log_date);
	
	//로그 날짜별 상세 가져오기
	public List<Log_Dto> getLog(String log_date, String log_message);

	//로그 입력하기
	public boolean setLog(String message, String ip);
	
}
