package com.ceiba.biblioteca.services;

import java.util.List;

import com.ceiba.biblioteca.models.*;

public interface ILibroService {
	
	public List<LibroModel> buscarTodos();
	
	public LibroModel buscarLibroPorId(Long idLibro);
	
	public List<LibroModel> listarPorEstado(String estado);	
	
	public LibroModel guardar(LibroModel libro);
	
	public void eliminar(Long id);
}
