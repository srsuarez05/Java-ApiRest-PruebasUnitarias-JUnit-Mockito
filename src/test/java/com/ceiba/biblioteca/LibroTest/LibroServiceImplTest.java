package com.ceiba.biblioteca.LibroTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ceiba.biblioteca.exceptions.EstadoLibroNotFoundException;
import com.ceiba.biblioteca.exceptions.LibroNotFoundException;
import com.ceiba.biblioteca.models.LibroModel;
import com.ceiba.biblioteca.repositories.LibroRepository;
import com.ceiba.biblioteca.services.LibroServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest(classes = LibroModel.class)
class LibroServiceImplTest {

	 @Mock
	 LibroRepository libroRepository;
	 
	 @InjectMocks
	 LibroServiceImpl libroService;
   
	 @Test
	 void testBuscarTodos() {
		List<LibroModel> libros = Arrays.asList(new LibroModel(1L, "TituloUno", "AutorUno", "Disponible"),
												new LibroModel(2L, "TituloDos", "AutorDos", "Disponible"),
												new LibroModel(3L, "TituloTres", "AutorTres", "Inactivo"),
												new LibroModel(4L, "TituloCuatro", "AutorCuatro", "Inactivo"));

		when(libroRepository.findAll()).thenReturn(libros);
		assertEquals(4, libroService.buscarTodos().size());
		verify(libroRepository, times(1)).findAll();
	}
	 
	@Test
	void testBuscarLibroPorId() {	
		LibroModel libro = new LibroModel();
		libro.setId(1L);
		libro.setTitulo("TituloPrueba");
		libro.setAutor("AutorPrueba");
		libro.setEstado("Disponible");
		
		when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));			
		Optional<LibroModel> libroActual = Optional.of(libroService.buscarLibroPorId(1L));		
		assertEquals("TituloPrueba", libroActual.get().getTitulo());		
		verify(libroRepository, times(1)).findById(1L);
	}	
	
	@Test
	void testBuscarLibroPorIdNotFoundException() {				
		when(libroRepository.findById(5L)).thenReturn(Optional.empty());		
		LibroNotFoundException exception = assertThrows(LibroNotFoundException.class, ()-> libroService.buscarLibroPorId(5L));
		assertEquals(String.format("El libro %d no existe", 5L), exception.getMessage());			
		verify(libroRepository, times(1)).findById(5L);
	}	
	
	@Test
	void testListarPorEstado() {
		String estado = "Disponible";
		List<LibroModel> libros = Arrays.asList(new LibroModel(1L, "TituloUnoP", "AutorUno", "Disponible"),
												new LibroModel(2L, "TituloDos", "AutorDos", "Disponible"),
												new LibroModel(3L, "TituloTres", "AutorTres", "Inactivo"),
												new LibroModel(4L, "TituloCuatro", "AutorCuatro", "Inactivo"));
		
		List<LibroModel> librosPorEstado = libros.stream().filter(l -> l.getEstado() == estado).collect(Collectors.toList());
				
		when(libroRepository.findByEstado(estado)).thenReturn(librosPorEstado);
		
		List<LibroModel> librosDisponibles = libroService.listarPorEstado(estado);
		
		assertEquals(2, librosDisponibles.size());
		verify(libroRepository).findByEstado(estado);
	}	
	
	@Test
	void testListarPorEstadoNotFound() {		
		String estado = "Disponibilidad";
		
		when(libroRepository.findByEstado(estado)).thenReturn(Collections.emptyList());		
		EstadoLibroNotFoundException exception = assertThrows(EstadoLibroNotFoundException.class, ()-> libroService.listarPorEstado(estado));
		assertEquals("Los sentimos, el estado solicitado no existe.", exception.getMessage());			
		verify(libroRepository).findByEstado(estado);
	}	
	
	@Test
	void testListarPorEstadoNull() {		
		String estado = "Disponibilidad";
		
		when(libroRepository.findByEstado(estado)).thenReturn(null);		
		EstadoLibroNotFoundException exception = assertThrows(EstadoLibroNotFoundException.class, ()-> libroService.listarPorEstado(estado));
		assertEquals("Los sentimos, el estado solicitado no existe.", exception.getMessage());			
		verify(libroRepository).findByEstado(estado);
	}
	
	@Test
	void testGuardar() {
		LibroModel libro = new LibroModel();
		libro.setId(1L);
		libro.setTitulo("TituloPrueba");
		libro.setAutor("AutorPrueba");
		libro.setEstado("Disponible");
		
		when(libroRepository.save(any(LibroModel.class))).thenReturn(libro);
		assertNotNull(libroService.guardar(libro/*new LibroModel()*/));	
		assertEquals("AutorPrueba", libro.getAutor());
	}
	
	@Test
	void testEliminar() {
		
		LibroModel libro = new LibroModel();
		libro.setId(1L);
		libro.setTitulo("TituloPrueba");
		libro.setAutor("AutorPrueba");
		libro.setEstado("Disponible");
		
		when(libroRepository.findById(libro.getId())).thenReturn(Optional.of(libro));
		
		libroService.eliminar(1L);
		
		verify(libroRepository).deleteById(1L);		
		
	}
	
	@Test
	void testEliminarException() {	
		when(libroRepository.findById(5L)).thenReturn(Optional.empty());
		LibroNotFoundException exception = assertThrows(LibroNotFoundException.class, ()-> libroService.eliminar(5L));
		assertEquals(String.format("El libro %d no existe", 5L), exception.getMessage());
		
	}


}