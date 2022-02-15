package com.ceiba.biblioteca.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ceiba.biblioteca.models.LibroModel;
import com.ceiba.biblioteca.services.ILibroService;

@RestController
@RequestMapping("/api")
public class LibroController {
	
	@Autowired
	private ILibroService libroService;
	
	@GetMapping("/libros")
	@ResponseStatus(HttpStatus.OK)
	public List<LibroModel> getLibros(){
		return libroService.buscarTodos();		
	}
	
	@GetMapping("/libros/{id}")
	@ResponseStatus(HttpStatus.OK)
	public LibroModel getLibroById(@PathVariable(name="id") long id) {
		return libroService.buscarLibroPorId(id);		
	}
	
	@GetMapping("/libros/estado/{estado}")
	@ResponseStatus(HttpStatus.OK)
	public List<LibroModel> getLibrosByEstado(@Valid @PathVariable(name="estado") String estado){
		return libroService.listarPorEstado(estado);
	}
	
	@PostMapping("/libros")
	public ResponseEntity<?> saveLibro(@Valid @RequestBody LibroModel libro){		
		libroService.guardar(libro);
		
		Map<String, Object> response = new HashMap<>();
		response.put("status", "CREATED");
		response.put("mensaje", "Libro guardado exitosamente");
		response.put("objetoLibro", libro);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);	
	}
	
	@PutMapping("/libros")
	public ResponseEntity<?> updateLibro(@Valid @RequestBody LibroModel libro){		
		libroService.guardar(libro);
		
		Map<String, Object> response = new HashMap<>();
		response.put("status", "OK");
		response.put("mensaje", "Libro modificado exitosamente");
		response.put("objetoLibro", libro);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/libros/{id}")
	public ResponseEntity<?> deleteLibro(@PathVariable(name="id") Long id){
		libroService.eliminar(id);
		return ResponseEntity.status(HttpStatus.OK).body("Libro eliminado exitosamente");		
	}
	
}
