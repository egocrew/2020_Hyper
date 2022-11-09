package com.min.app.model.event;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.app.dto.Day_Dto;
import com.min.app.dto.Key_Dto;
import com.min.app.dto.Memo_Dto;

@Repository
public class Event_Dao_Impl implements Event_IDao{

	@Autowired
	private SqlSessionTemplate session;
	
	private final String NS="com.min.app.model.event.";

	@Override
	public boolean settingKey(Key_Dto dto) {
		return session.insert(NS+"settingKey",dto)>0;
	}

	@Override
	public List<Key_Dto> selectKey(int user_seq) {
		return session.selectList(NS+"selectKey",user_seq);
	}

	@Override
	public boolean updateKey(Key_Dto dto) {
		return session.update(NS+"updateKey",dto)>0;
	}

	@Override
	public boolean insertDay(Day_Dto dto) {
		return session.insert(NS+"insertDay", dto)>0;
	}

	@Override
	public List<Day_Dto> selectDay(int user_seq) {
		return session.selectList(NS+"selectDay", user_seq);
	}

	@Override
	public boolean updateDay(Day_Dto dto) {
		return session.update(NS+"updateDay", dto)>0;
	}

	@Override
	public boolean deleteDay(int key_seq) {
		return session.delete(NS+"deleteDay", key_seq)>0;
	}

	@Override
	public List<Memo_Dto> getMemo(int user_seq) {
		return session.selectList(NS+"getMemo", user_seq);
	}

	@Override
	public boolean setMemo(Memo_Dto dto) {
		return session.insert(NS+"setMemo", dto)>0;
	}

	@Override
	public boolean removeMemo(int memo_seq) {
		return session.delete(NS+"removeMemo", memo_seq)>0;
	}

	@Override
	public boolean showDay(Day_Dto dto) {
		return session.update(NS+"showDay", dto)>0;
	}

	@Override
	public boolean resetDay(int user_seq) {
		return session.update(NS+"resetDay", user_seq)>0;
	}

}
