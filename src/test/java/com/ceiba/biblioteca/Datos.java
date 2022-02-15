package com.ceiba.biblioteca;

import java.util.Optional;

import com.ceiba.biblioteca.models.LibroModel;

public class Datos {
	
	public static Optional<LibroModel> crearLibro01(){
		return Optional.of(new LibroModel(1L, "TituloUno", "AutorUno", "Disponible"));
	}
	
	public static Optional<LibroModel> crearLibro02(){
		return Optional.of(new LibroModel(2L, "TituloDos", "AutorDos", "Disponible"));
	}
	
	public static Optional<LibroModel> crearLibro03(){
		return Optional.of(new LibroModel(3L, "TituloTres", "AutorTres", "Inactivo"));
	}

}
