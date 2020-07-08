package com.paulolana.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paulolana.cursomc.domain.Cliente;
import com.paulolana.cursomc.domain.ItemPedido;
import com.paulolana.cursomc.domain.PagamentoComBoleto;
import com.paulolana.cursomc.domain.Pedido;
import com.paulolana.cursomc.domain.enums.EstadoPagamento;
import com.paulolana.cursomc.repositories.ClienteRepository;
import com.paulolana.cursomc.repositories.ItemPedidoRepository;
import com.paulolana.cursomc.repositories.PagamentoRepository;
import com.paulolana.cursomc.repositories.PedidoRepository;
import com.paulolana.cursomc.security.UserSS;
import com.paulolana.cursomc.services.exceptions.AuthorizationException;
import com.paulolana.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	PedidoRepository repo;
	@Autowired
	BoletoService boletoService;
	@Autowired
	PagamentoRepository pagamentoRepository;
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	@Autowired
	ProdutoService produtoService;
	@Autowired
	ClienteService clienteService;
	
	/*
	 * na classe TestConfig ou DevConfig está definido qual classe será instanciada para a interface EmailService
	 */
	@Autowired
	EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido não localizado! Id " + id + ", Tipo " + Pedido.class.getName()));
		
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		/*
		 * A instância do tipo de pagamento é feita automaticamente através das anotações @JsonTypeInfo e @JsonTypeName nas classes de pagamento
		 * como o auxílio da classe JacksonConfig
		 */
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido item : obj.getItens()) {
			item.setProduto(produtoService.find(item.getProduto().getId()));
			
			item.setDesconto(0.0);
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		
		emailService.sendOrderConfirmationHtmlEmail(obj);
		
		return obj;
		
	}
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
		
	}

}
