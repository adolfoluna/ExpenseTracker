package com.tracker.expense.db.home;

public class MonedaBaseValues {
	
	private static int monedaBase = 0;
	private static String monedaBaseNombre = "";
	
	public MonedaBaseValues() {
		
	}
	
	public static int getMonedaBase() {
		return monedaBase;
	}
	
	public static void setMonedaBase(int monedaBase) {
		MonedaBaseValues.monedaBase = monedaBase;
	}
	
	public static String getMonedaBaseNombre() {
		return monedaBaseNombre;
	}
	
	public static void setMonedaBaseNombre(String monedaBaseNombre) {
		MonedaBaseValues.monedaBaseNombre = monedaBaseNombre;
	}
	
	public static void clear() {
		MonedaBaseValues.setMonedaBase(0);
		MonedaBaseValues.setMonedaBaseNombre(null);
	}

}
