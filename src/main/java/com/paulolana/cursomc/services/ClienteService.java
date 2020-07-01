package com.paulolana.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulolana.cursomc.domain.Cliente;
import com.paulolana.cursomc.repositories.ClienteRepository;
import com.paulolana.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Será automaticamente instanciada pelo spring
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não localizado. ID = " + id + ", Tipo = " + Cliente.class.getName()));
		
	}

}
