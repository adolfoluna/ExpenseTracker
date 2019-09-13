package com.tracker.expense.db.dto;

import java.io.Serializable;

public class SearchDtoField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5465627340015859768L;
	
	private String fieldName;
	private String value;
	
	public SearchDtoField() {
		
	}

	public SearchDtoField(String fieldName, String value) {
		super();
		this.fieldName = fieldName;
		this.value = value;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
