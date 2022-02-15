package com.ceiba.biblioteca.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceiba.biblioteca.models.LibroModel;
import org.springframework.data.jpa.repository.Query;

public interface LibroRepository extends JpaRepository<LibroModel, Long>{
	
	@Query("select l from LibroModel l where l.titulo = ?1")
	public Optional<LibroModel> findByTitulo(String estado);
	
	@Query("select l from LibroModel l where l.estado = ?1")
	public List<LibroModel> findByEstado(String estado);	
}
