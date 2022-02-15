package com.ceiba.biblioteca.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="libros")
public class LibroModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Long id;
	
	@Column(name="titulo")
	@NotEmpty(message="El titulo no puede estar vacio")
	private String titulo;
	
	@Column(name="autor")
	@NotEmpty(message="El autor no puede estar vacio")
	private String autor;
	
	@Column(name="estado")
	@NotEmpty(message="El estado no puede estar vacio")
	private String estado;

	public LibroModel() { }

	public LibroModel(
			@NotEmpty(message = "El titulo no puede estar vacio") String titulo,
			@NotEmpty(message = "El autor no puede estar vacio") String autor,
			@NotEmpty(message = "El estado no puede estar vacio") String estado) {
		this.titulo = titulo;
		this.autor = autor;
		this.estado = estado;
	}

	public LibroModel(Long id, String titulo, String autor, String estado) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
