package com.min.app.model.log;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.app.dto.Log_Dto;

@Repository
public class Log_Dao_impl implements Log_IDao{
	
	private final String NS = "com.min.app.model.log.";
	
	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public List<Log_Dto> getLog() {
		return session.selectList(NS+"getLogAll");
	}
	
	@Override
	public List<Log_Dto> getLog(String date) {
		return session.selectList(NS+"getLog", date);
	}
	
	@Override
	public List<Log_Dto> getLog(Log_Dto dto) {
		return session.selectList(NS+"getLogDetail", dto);
	}
	
	@Override
	public boolean setLog(Log_Dto dto) {
		return session.insert(NS+"setLog", dto)>0?true:false;
	}


}
