package com.min.app.model.event;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.min.app.dto.Day_Dto;
import com.min.app.dto.Key_Dto;
import com.min.app.dto.Memo_Dto;

@Repository
public interface Event_IDao {
//	단축키 생성
	public boolean settingKey(Key_Dto dto);
	
//	단축키 조회
	public List<Key_Dto> selectKey(int user_seq);
	
//	단축키 수정
	public boolean updateKey(Key_Dto dto);

//	디데이 생성
	public boolean insertDay(Day_Dto dto);
	
//	디데이 조회
	public List<Day_Dto> selectDay(int user_seq);
	
//	디데이 수정
	public boolean updateDay(Day_Dto dto);
	
//	디데이 삭제
	public boolean deleteDay(int day_seq);
	
// 대표 디데이 설정
	public boolean showDay(Day_Dto dto);
	
// 대표 디데이 초기화
	public boolean resetDay(int user_seq);

//  메모 조회
	public List<Memo_Dto> getMemo(int user_seq);

//  메모 작성
	public boolean setMemo(Memo_Dto dto);
	
//  메모 삭제
	public boolean removeMemo(int memo_seq);
	
}
