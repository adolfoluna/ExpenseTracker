package com.tracker.expense.db.dto;

import java.io.Serializable;

public class ListaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -768482688688693172L;
	private int id;
	private int id2;
	private String name;
	
	public ListaDto() {
		id = 0;
		id2 = 0;
		name = null;
	}
	
	public ListaDto(int id,int id2,String name) {
		this.id = id;
		this.id2 = id2;
		this.name = name;
	}
	
	public ListaDto(int id,String name) {
		this.id = id;
		this.id2 = 0;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
