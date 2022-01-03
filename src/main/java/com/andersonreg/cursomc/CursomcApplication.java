package com.andersonreg.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andersonreg.cursomc.domain.Categoria;
import com.andersonreg.cursomc.domain.Cidade;
import com.andersonreg.cursomc.domain.Cliente;
import com.andersonreg.cursomc.domain.Endereco;
import com.andersonreg.cursomc.domain.Estado;
import com.andersonreg.cursomc.domain.Pagamento;
import com.andersonreg.cursomc.domain.PagamentoComBoleto;
import com.andersonreg.cursomc.domain.PagamentoComCartao;
import com.andersonreg.cursomc.domain.Pedido;
import com.andersonreg.cursomc.domain.Produto;
import com.andersonreg.cursomc.domain.enums.EstadoPagamento;
import com.andersonreg.cursomc.domain.enums.TipoCliente;
import com.andersonreg.cursomc.repositories.CategoriaRepository;
import com.andersonreg.cursomc.repositories.CidadeRepository;
import com.andersonreg.cursomc.repositories.ClienteRepository;
import com.andersonreg.cursomc.repositories.EnderecoRepository;
import com.andersonreg.cursomc.repositories.EstadoRepository;
import com.andersonreg.cursomc.repositories.PagamentoRepository;
import com.andersonreg.cursomc.repositories.PedidoRepository;
import com.andersonreg.cursomc.repositories.ProdutoRepository;

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

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "98981435843", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("8534943913", "98981435843"));

		Endereco e1 = new Endereco(null, "rua Flores", "7", "apto 303", "Jardim", "65060540", cli1, c1);
		Endereco e2 = new Endereco(null, "rua holandeses", "10", "sala 1", "centro", "60437-240", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm");
		
		Pedido ped1 = new Pedido (null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido (null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 15:06"), null);
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
	
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
	}

}
