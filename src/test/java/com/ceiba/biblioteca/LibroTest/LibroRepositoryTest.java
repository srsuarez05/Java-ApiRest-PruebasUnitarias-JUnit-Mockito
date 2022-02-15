package com.ceiba.biblioteca.LibroTest;

import com.ceiba.biblioteca.models.LibroModel;
import com.ceiba.biblioteca.repositories.LibroRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibroRepositoryTest {

    @Autowired
    private LibroRepository libroRepository;
   
   @Test
   void testFindByTitulo() {
	   Optional<LibroModel> libro = libroRepository.findByTitulo("TituloUno");			   
	   assertTrue(libro.isPresent()); 
	   assertEquals("TituloUno", libro.orElseThrow().getTitulo());
   }
   
   @Test
   void testFindByTituloThrowException() {
	   Optional<LibroModel> libro = libroRepository.findByTitulo("Titulo");	
	   /*assertThrows(NoSuchElementException.class, () ->{
		   libro.orElseThrow();
	   });*/
	   //assertThrows(NoSuchElementException.class, () -> libro.orElseThrow());
	   assertThrows(NoSuchElementException.class, libro::orElseThrow);
	   assertFalse(libro.isPresent());
   }
   
   @Test
   void testFindByEstado() {
	   List<LibroModel> libros = libroRepository.findByEstado("Disponible");
	   assertEquals(2, libros.size());
   }
   
   @Test
   void testFindByEstadoNoExiste() {
	   List<LibroModel> libros = libroRepository.findByEstado("Disponibilidad");
	   assertTrue(libros.isEmpty());
   }   
   
}