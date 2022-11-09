package com.min.app.model.file;

import org.springframework.stereotype.Repository;

import com.min.app.dto.File_Dto;

@Repository
public interface File_IDao {
//  이미지 추가
	public boolean imageIn(String user_email);
	
//  이미지 수정
	public boolean imageUp(File_Dto dto);
	
//  이미지 이름 가져오기
	public File_Dto imageSe(int user_seq);
}
