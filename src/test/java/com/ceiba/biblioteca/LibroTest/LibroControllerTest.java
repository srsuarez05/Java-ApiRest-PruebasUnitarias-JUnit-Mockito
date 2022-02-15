package com.ceiba.biblioteca.LibroTest;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ceiba.biblioteca.controllers.LibroController;
import com.ceiba.biblioteca.models.LibroModel;
import com.ceiba.biblioteca.services.LibroServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LibroController.class)
public class LibroControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private LibroServiceImpl libroService;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		objectMapper = new ObjectMapper();
	}
	
	@Test
	void getLibros() throws Exception {
		// Given
		List<LibroModel> libros = Arrays.asList(new LibroModel(1L, "TituloUnoPrueba", "AutorUno", "Disponible"),
												new LibroModel(2L, "TituloDosEjemplo", "AutorDos", "Disponible"),
												new LibroModel(3L, "TituloTres", "AutorTres", "Inactivo"),
												new LibroModel(4L, "TituloCuatro", "AutorCuatro", "Inactivo"));
		
		when(libroService.buscarTodos()).thenReturn(libros);
		
		// When
		mvc.perform(get("/api/libros").contentType(MediaType.APPLICATION_JSON))
		// Then
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].titulo").value("TituloUnoPrueba"))
		.andExpect(jsonPath("$[1].titulo").value("TituloDosEjemplo"))
		.andExpect(jsonPath("$", hasSize(4)))
		.andExpect(content().json(objectMapper.writeValueAsString(libros)));
		
		verify(libroService).buscarTodos();		
	}
	
	@Test
	void getLibroById() throws Exception {		
		LibroModel libro = new LibroModel();
		libro.setId(1L);
		libro.setTitulo("TituloPrueba");
		libro.setAutor("AutorPrueba");
		libro.setEstado("Disponible");
		
		// Given
		when(libroService.buscarLibroPorId(1L)).thenReturn(libro);
		
		// When
		mvc.perform(get("/api/libros/1").contentType(MediaType.APPLICATION_JSON))
		// Then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.titulo").value("TituloPrueba"))
				.andExpect(jsonPath("$.autor").value("AutorPrueba"));
		
		verify(libroService).buscarLibroPorId(1L);
	}
	
	@Test
	void getLibrosByEstado() throws Exception {
		// Given
		String estado = "Disponible";
		
		List<LibroModel> libros = Arrays.asList(new LibroModel(1L, "TituloUnoPrueba", "AutorUno", "Disponible"),
												new LibroModel(2L, "TituloDosEjemplo", "AutorDos", "Disponible"),
												new LibroModel(3L, "TituloTres", "AutorTres", "Inactivo"),
												new LibroModel(4L, "TituloCuatro", "AutorCuatro", "Inactivo"));
		
		List<LibroModel> librosPorEstado = libros.stream().filter(l -> l.getEstado() == estado).collect(Collectors.toList());
		
		when(libroService.listarPorEstado(estado)).thenReturn(librosPorEstado);
		
		// When
		mvc.perform(get("/api/libros/estado/Disponible").contentType(MediaType.APPLICATION_JSON))
		// Then
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(content().json(objectMapper.writeValueAsString(librosPorEstado)));
		
		verify(libroService).listarPorEstado(estado);		
	}
	
	@Test
	void saveLibro() throws Exception {
		// Given
		LibroModel libro = new LibroModel(5L, "Ejemplo", "Ejemplo", "Disponible");
		
		 Map<String, Object> response = new HashMap<>();
		 response.put("status", "CREATED");
		 response.put("mensaje", "Libro guardado exitosamente");
		 response.put("objetoLibro", libro);
		  
		when(libroService.guardar(any())).thenReturn(libro);
		
		// When
		mvc.perform(post("/api/libros").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(libro)))
		// Then
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("CREATED")))
				.andExpect(jsonPath("$.mensaje", is("Libro guardado exitosamente")))
				.andExpect(jsonPath("$.objetoLibro.id", is(5)))
				.andExpect(jsonPath("$.objetoLibro.titulo", is("Ejemplo")));
		
		verify(libroService).guardar(any());				
	}
	
	@Test
	void updateLibro() throws Exception {
		// Given
		LibroModel libro = new LibroModel(8L, "Ejemplo", "Ejemplo", "Disponible");
		
		 Map<String, Object> response = new HashMap<>();
		 response.put("status", "OK");
		 response.put("mensaje", "Libro modificado exitosamente");
		 response.put("objetoLibro", libro);
		  
		when(libroService.guardar(any())).thenReturn(libro);
		
		// When
		mvc.perform(put("/api/libros").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(libro)))
		// Then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("OK")))
				.andExpect(jsonPath("$.mensaje", is("Libro modificado exitosamente")))
				.andExpect(jsonPath("$.objetoLibro.id", is(8)))
				.andExpect(jsonPath("$.objetoLibro.titulo", is("Ejemplo")));
		
		verify(libroService).guardar(any());				
	}
	
	@Test
	void eliminarLibro() throws Exception {
		// Given
		LibroModel libro = new LibroModel();
		libro.setId(1L);
		libro.setTitulo("TituloPrueba");
		libro.setAutor("AutorPrueba");
		libro.setEstado("Disponible");
		
		// When
		when(libroService.buscarLibroPorId(1L)).thenReturn(libro);
		
		mvc.perform(delete("/api/libros/1"))
		// Then
				.andExpect(status().isOk())
				.andExpect(content().string("Libro eliminado exitosamente"));
		
		verify(libroService).eliminar(anyLong());				
	}

}
