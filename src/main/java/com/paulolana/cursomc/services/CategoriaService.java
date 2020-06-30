package com.paulolana.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulolana.cursomc.domain.Categoria;
import com.paulolana.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired // Será automaticamente instanciada pelo spring
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
		
	}

}
