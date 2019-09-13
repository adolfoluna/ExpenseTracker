package com.tracker.expense.db.dto;

import java.io.Serializable;
import java.util.HashMap;

public class SearchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7826923669896506401L;
	
	private int limite;
	private int pagina;
	private String catalogo;
	private SearchDtoField searchFields[];
	
	private AdvancedSearchDtoGroup advancedSearchGroups[];
	
	private String orderFields[];
	
	private HashMap<String, String> searhFieldsMap = new HashMap<String, String>();
	
	public SearchDto() {
		
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public SearchDtoField[] getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(SearchDtoField searchFields[]) {
		this.searchFields = searchFields;
		
		searhFieldsMap.clear();
		
		if( searchFields == null)
			return;
		
		for( SearchDtoField field : searchFields )
			searhFieldsMap.put(field.getFieldName(), field.getValue());
			
	}
	
	public void addSearchFieldMap(String key,String value) {
		searhFieldsMap.put(key, value);
	}

	public HashMap<String, String> getSearhFieldsMap() {
		return searhFieldsMap;
	}

	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public String[] getOrderFields() {
		return orderFields;
	}

	public void setOrderFields(String orderFields[]) {
		this.orderFields = orderFields;
	}
	
	public boolean isOrderByField(String fieldName) {
		if( orderFields == null ) return false;
		
		for(String temp : orderFields ) {
			if( temp.equals(fieldName))
				return true;
		}
		
		return false;
	}

	public AdvancedSearchDtoGroup[] getAdvancedSearchGroups() {
		return advancedSearchGroups;
	}

	public void setAdvancedSearchGroups(AdvancedSearchDtoGroup[] advancedSearchGroups) {
		this.advancedSearchGroups = advancedSearchGroups;
	}

}
