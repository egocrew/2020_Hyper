package com.min.app.dto;

import java.io.Serializable;

public class Day_Dto implements Serializable{

	private static final long serialVersionUID = -7785140416981500871L;

	private int user_seq;
	private String day_title;
	private String day_date;
	private String day_oldDate;
	private int day_seq;
	private String day_show;
	
	public Day_Dto() {
	}
	
	public Day_Dto(int user_seq, String day_title, String day_date, String day_oldDate, int day_seq, String day_show) {
		super();
		this.user_seq = user_seq;
		this.day_title = day_title;
		this.day_date = day_date;
		this.day_oldDate = day_oldDate;
		this.day_seq = day_seq;
		this.day_show = day_show;
	}

	public int getUser_seq() {
		return user_seq;
	}

	public void setUser_seq(int user_seq) {
		this.user_seq = user_seq;
	}

	public String getDay_title() {
		return day_title;
	}

	public void setDay_title(String day_title) {
		this.day_title = day_title;
	}

	public String getDay_date() {
		return day_date;
	}

	public void setDay_date(String day_date) {
		this.day_date = day_date;
	}

	public int getDay_seq() {
		return day_seq;
	}

	public String getDay_show() {
		return day_show;
	}

	public void setDay_show(String day_show) {
		this.day_show = day_show;
	}

	public void setDay_seq(int day_seq) {
		this.day_seq = day_seq;
	}

	public String getDay_oldDate() {
		return day_oldDate;
	}

	public void setDay_oldDate(String day_oldDate) {
		this.day_oldDate = day_oldDate;
	}

	@Override
	public String toString() {
		return "Day_Dto [user_seq=" + user_seq + ", day_title=" + day_title + ", day_date=" + day_date
				+ ", day_oldDate=" + day_oldDate + ", day_seq=" + day_seq + ", day_show=" + day_show + "]";
	}

}
