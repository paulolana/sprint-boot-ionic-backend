package com.paulolana.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paulolana.cursomc.domain.Categoria;
import com.paulolana.cursomc.domain.Cidade;
import com.paulolana.cursomc.domain.Cliente;
import com.paulolana.cursomc.domain.Endereco;
import com.paulolana.cursomc.domain.Estado;
import com.paulolana.cursomc.domain.Pagamento;
import com.paulolana.cursomc.domain.PagamentoComBoleto;
import com.paulolana.cursomc.domain.PagamentoComCartao;
import com.paulolana.cursomc.domain.Pedido;
import com.paulolana.cursomc.domain.Produto;
import com.paulolana.cursomc.domain.enums.EstadoPagamento;
import com.paulolana.cursomc.domain.enums.TipoCliente;
import com.paulolana.cursomc.repositories.CategoriaRepository;
import com.paulolana.cursomc.repositories.CidadeRepository;
import com.paulolana.cursomc.repositories.ClienteRepository;
import com.paulolana.cursomc.repositories.EnderecoRepository;
import com.paulolana.cursomc.repositories.EstadoRepository;
import com.paulolana.cursomc.repositories.PagamentoRepository;
import com.paulolana.cursomc.repositories.PedidoRepository;
import com.paulolana.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired 
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlância", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		List<Categoria> categorias = Arrays.asList(cat1, cat2);
		List<Produto> produtos = Arrays.asList(p1, p2, p3);
		List<Estado> estados = Arrays.asList(est1, est2);
		List<Cidade> cidades = Arrays.asList(c1, c2, c3);
		List<Endereco> enderecos = Arrays.asList(e1, e2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		List<Pedido> pedidos = Arrays.asList(ped1, ped2);
		List<Pagamento> pagamentos = Arrays.asList(pagto1, pagto2);
		
		
		categoriaRepository.saveAll(categorias);
		produtoRepository.saveAll(produtos);
		estadoRepository.saveAll(estados);
		cidadeRepository.saveAll(cidades);
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(enderecos);
		pedidoRepository.saveAll(pedidos);
		pagamentoRepository.saveAll(pagamentos);
		
		
	}

}
