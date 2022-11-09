package com.min.app.dto;

import java.io.Serializable;

public class Log_Dto implements Serializable {

	private static final long serialVersionUID = -5046498505669588146L;

	private int log_seq;
	private String log_date;
	private String log_message;
	private String log_ip;

	public Log_Dto() {
	}

	public Log_Dto(String log_message, String log_ip) {
		super();
		this.log_message = log_message;
		this.log_ip = log_ip;
	}

	public void setLog_seq(int log_seq) {
		this.log_seq = log_seq;
	}

	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}

	public String getLog_message() {
		return log_message;
	}

	public void setLog_message(String log_message) {
		this.log_message = log_message;
	}

	public String getLog_ip() {
		return log_ip;
	}

	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}

	public int getLog_seq() {
		return log_seq;
	}

	public String getLog_date() {
		return log_date;
	}

	@Override
	public String toString() {
		return "Log_Dto [log_seq=" + log_seq + ", log_date=" + log_date + ", log_message=" + log_message + ", log_ip="
				+ log_ip + "]";
	}
	
}
