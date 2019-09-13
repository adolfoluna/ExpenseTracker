package com.tracker.expense.db.dto;

import java.io.Serializable;

public class AdvancedSearchDtoGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1365549216257841563L;
	
	private String union;//and,or
	private AdvancedSearchDtoField fields[];
	
	public AdvancedSearchDtoGroup() {
		
	}

	public String getUnion() {
		return union;
	}

	public void setUnion(String union) {
		this.union = union;
	}

	public AdvancedSearchDtoField[] getFields() {
		return fields;
	}

	public void setFields(AdvancedSearchDtoField[] fields) {
		this.fields = fields;
	}

}
