package com.aig.object;

import com.aig.constant.ServiceStatus;

public class ServiceResponse {
	
	private Object data;
	private ServiceStatus status;
	private String message;
	
	public ServiceResponse() {
		status = ServiceStatus.Success;
	}

	public ServiceResponse(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
