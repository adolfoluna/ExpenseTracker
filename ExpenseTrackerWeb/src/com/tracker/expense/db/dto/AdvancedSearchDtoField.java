package com.tracker.expense.db.dto;

import java.io.Serializable;

public class AdvancedSearchDtoField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3179581729026483663L;
	
	private String union; //and,or
	private String fieldName;
	private String comparator;//<,>,=,<=,>=,<>
	private String fieldValue;
	private Class<?> type;
	
	public AdvancedSearchDtoField() {
		
	}
	
	public void update(String fieldName,Class<?> type) {
		this.fieldName = fieldName;
		this.type = type;
	}

	public String getUnion() {
		return union;
	}

	public void setUnion(String union) {
		this.union = union;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getComparator() {
		return comparator;
	}

	public void setComparator(String comparator) {
		this.comparator = comparator;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

}
