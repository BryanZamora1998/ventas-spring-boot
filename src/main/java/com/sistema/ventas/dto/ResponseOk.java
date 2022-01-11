package com.sistema.ventas.dto;

import java.io.Serializable;

import javax.ws.rs.core.Response.Status;

public class ResponseOk implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;
	private boolean success;
	private String message;
	private Object data;
	public static String messageDataFound = "Data found";
	public static String messageDataNotFound = "Data not found";
	
	
	public ResponseOk(String message, Object data) {
		super();
		this.code = Status.OK.getStatusCode();
		this.success = true;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
