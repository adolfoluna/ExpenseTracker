package com.tracker.expense.web.rest.service;

public enum Role {
	
	ADMIN(1),
	OPERATOR(2),
	READ_ONLY(3),
	ACCOUNTANT(4);
	
	private int num;
	
	private Role(int num) {
		this.num = num;
	}
	
	public int getRoleNum() {
		return num;
	}

}
