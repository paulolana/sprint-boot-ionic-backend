package com.paulolana.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulolana.cursomc.domain.Pedido;
import com.paulolana.cursomc.repositories.PedidoRepository;
import com.paulolana.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido não localizado! Id " + id + ", Tipo " + Pedido.class.getName()));
		
	}

}
