package com.paulolana.cursomc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paulolana.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	//findBy<nome do campo> faz com que o Spring gere uma consulta pelo campo automaticamente
	//Documentação: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods
	
	@Transactional(readOnly=true) 
	Cliente findByEmail(String email);

}
