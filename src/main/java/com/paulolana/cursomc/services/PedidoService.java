package com.paulolana.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paulolana.cursomc.domain.ItemPedido;
import com.paulolana.cursomc.domain.PagamentoComBoleto;
import com.paulolana.cursomc.domain.Pedido;
import com.paulolana.cursomc.domain.enums.EstadoPagamento;
import com.paulolana.cursomc.repositories.ItemPedidoRepository;
import com.paulolana.cursomc.repositories.PagamentoRepository;
import com.paulolana.cursomc.repositories.PedidoRepository;
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
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido não localizado! Id " + id + ", Tipo " + Pedido.class.getName()));
		
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		/*
		 * A instância do tipo de pagamento é feita automaticamente através das anotações @JsonTypeInfo e @JsonTypeName nas classes de pagamento
		 */
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setPreco(produtoService.find(item.getProduto().getId()).getPreco());
			item.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
		
	}

}
