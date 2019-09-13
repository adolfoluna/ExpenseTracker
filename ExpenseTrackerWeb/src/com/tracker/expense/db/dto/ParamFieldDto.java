package com.tracker.expense.db.dto;

import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ParamFieldDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1294642395532852388L;
	
	private String name;
	private String value;
	private Class<?> type;
	
	
	public ParamFieldDto() {
		
	}

	public ParamFieldDto(String name, String value, Class<?> type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}
	
	public Object getParamValue() {
		if( type == String.class ) return value;
		if( type == Integer.class ) return Integer.parseInt(value);
		if( type == Long.class ) return Long.parseLong(value);
		if( type == Double.class ) return Double.parseDouble(value);
		if( type == Float.class ) return Float.parseFloat(value);
		if( type == Byte.class ) return Byte.parseByte(value);
		if( type == Date.class ) {
			try {
				Date d = new SimpleDateFormat("YYYY-MM-dd").parse(value);
				return d;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
