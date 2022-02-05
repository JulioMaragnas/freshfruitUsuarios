package com.trabajodegrado.freshfruitusuarios.modelos.dto;

import java.util.List;

public class RespuestaPaginada<E> {

	private List<E> lista;
	private Number total;
	private Number totalPaginas;

	

	public List<E> getLista() {
		return lista;
	}

	public void setLista(List<E> lista) {
		this.lista = lista;
	}

	public Number getTotal() {
		return total;
	}

	public void setTotal(Number total) {
		this.total = total;
	}
	
	public Number getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(Number totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

}
