package com.trabajodegrado.freshfruitusuarios.excepciones;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConflictException(String customMessage) {
        super(customMessage);
    }

}
