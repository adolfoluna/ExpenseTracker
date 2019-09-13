package com.tracker.expense.web.rest.service;

import java.io.Serializable;

public class OperationRestResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5269570032169990534L;
	
	private boolean success = false;
	private String message = null;
	private int results = 0;
	private Object data;
	
	public OperationRestResult() {
		
	}

	public OperationRestResult(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public OperationRestResult(boolean success, String message,int results) {
		super();
		this.success = success;
		this.message = message;
		this.results = results;
	}
	
	public OperationRestResult(Object data) {
		this.message = null;
		this.success = true;
		this.data = data;
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

	public int getResults() {
		return results;
	}

	public void setResults(int results) {
		this.results = results;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
