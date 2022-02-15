package com.ceiba.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ceiba.biblioteca.exceptions.EstadoLibroNotFoundException;
import com.ceiba.biblioteca.exceptions.LibroNotFoundException;
import com.ceiba.biblioteca.models.LibroModel;
import com.ceiba.biblioteca.repositories.LibroRepository;

@Service
public class LibroServiceImpl implements ILibroService{

	public static final String EL_ESTADO_NO_EXISTE = "Los sentimos, el estado solicitado no existe.";

	@Autowired
	private LibroRepository libroRepository;

	@Override
	@Transactional(readOnly = true)
	public List<LibroModel> buscarTodos() {
		return libroRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public LibroModel buscarLibroPorId(Long idLibro) {				
		return libroRepository.findById(idLibro).orElseThrow(
			() -> new LibroNotFoundException(String.format("El libro %d no existe", idLibro)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<LibroModel> listarPorEstado(String estado) {	
		List<LibroModel> listado = libroRepository.findByEstado(estado);
		
		if (listado == null || listado.isEmpty()) {
			throw new EstadoLibroNotFoundException(EL_ESTADO_NO_EXISTE);
		}
		return listado;
	}
	
	@Override
	@Transactional
	public LibroModel guardar(LibroModel libro) {
		return libroRepository.save(libro);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {				
		libroRepository.findById(id).orElseThrow(
				() -> new LibroNotFoundException(String.format("El libro %d no existe", id)));
		
		libroRepository.deleteById(id);	
	}

}
