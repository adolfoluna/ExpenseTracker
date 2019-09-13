package com.tracker.expense.db.dto;

import java.io.Serializable;

public class AdvancedSearchDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6378604787146234072L;
	
	private int limite;
	private int pagina;
	private String catalogo;

	private AdvancedSearchDtoGroup grupos[];
	
	public AdvancedSearchDto() {
		
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

	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public AdvancedSearchDtoGroup[] getGrupos() {
		return grupos;
	}

	public void setGrupos(AdvancedSearchDtoGroup[] grupos) {
		this.grupos = grupos;
	}
	
}
