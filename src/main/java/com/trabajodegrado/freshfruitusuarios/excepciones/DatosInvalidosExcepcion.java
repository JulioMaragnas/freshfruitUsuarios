package com.trabajodegrado.freshfruitusuarios.excepciones;

public class DatosInvalidosExcepcion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatosInvalidosExcepcion(String customMessage) {
        super(customMessage);
    }

}
