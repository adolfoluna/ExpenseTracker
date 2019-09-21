package com.tracker.expense.db.dto;

import java.io.Serializable;

public class GraphDataDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 764396238585973400L;
	private String label;
	private long value;
	private double dvalue;
	
	public GraphDataDto() {
		
	}

	public GraphDataDto(String label, double dvalue) {
		this.label = label;
		this.dvalue = dvalue;
	}
	
	public GraphDataDto(String label, long value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public double getDvalue() {
		return dvalue;
	}

	public void setDvalue(double dvalue) {
		this.dvalue = dvalue;
	}
	
	

	
}
