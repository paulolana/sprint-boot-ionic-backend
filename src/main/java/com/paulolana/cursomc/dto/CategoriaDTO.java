package com.paulolana.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.paulolana.cursomc.domain.Categoria;


public class CategoriaDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@Length(min=5, max=80, message="Nome deve ter entre 5 e 80 caracteres")
	@NotEmpty(message = "Favor informar o nome da categoria")
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
