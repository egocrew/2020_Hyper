package com.min.app.model.log;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.min.app.dto.Log_Dto;

@Repository
public interface Log_IDao {
	
	//로그 전체 가져오기
	public List<Log_Dto> getLog();
	
	//로그 날짜별 가져오기
	public List<Log_Dto> getLog(String log_date);
	
	//로그 날짜별 상세 가져오기
	public List<Log_Dto> getLog(Log_Dto dto);
	
	//로그 입력하기
	public boolean setLog(Log_Dto dto);
	
}	
