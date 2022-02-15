package com.ceiba.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EstadoLibroNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EstadoLibroNotFoundException(String mensaje){
        super(mensaje);
    }

}