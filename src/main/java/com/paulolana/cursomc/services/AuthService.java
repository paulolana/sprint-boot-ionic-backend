package com.paulolana.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paulolana.cursomc.domain.Cliente;
import com.paulolana.cursomc.repositories.ClienteRepository;
import com.paulolana.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não localizado");
		}
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		clienteRepository.save(cliente);
		emailService.setNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i< vet.length-1; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
				
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		
		// Link para visualizar os unicodes: https://unicode-table.com
		
		if (opt == 0) { // gera um dígito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiúscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minúscula
			return (char) (rand.nextInt(26) + 97);
		}
		
	}
	
	
	

}
