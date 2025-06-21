package br.ufscar.dc.dsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.dao.IVeiculoDAO;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class VeiculosMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeiculosMvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IVeiculoDAO veiculoDAO, ILojaDAO lojaDAO, IClienteDAO clienteDAO, IPropostaDAO propostaDAO, IUsuarioDAO usuarioDAO, BCryptPasswordEncoder encoder) {
		return (args) -> {

			Usuario a1 = new Usuario();
			a1.setEmail("admin@email.com");
			a1.setPassword(encoder.encode("123456"));
			a1.setNome("Admin");
			a1.setPapel("ADMIN");
			usuarioDAO.save(a1);

			Loja l1 = new Loja();
			l1.setEmail("loja@email.com");
			l1.setPassword(encoder.encode("123456"));
			l1.setNome("Loja de Veículos A");
			l1.setPapel("LOJA");
			l1.setCnpj("12.345.678/0001-90");
			l1.setDescricao("Loja especializada em veículos novos e usados.");
			lojaDAO.save(l1);

			Cliente c1 = new Cliente();
			c1.setEmail("cliente@email.com");
			c1.setPassword(encoder.encode("123456"));
			c1.setNome("Cliente A");
			c1.setPapel("CLIENTE");
			c1.setCpf("123.456.789-00");
			c1.setTelefone("1234567890");
			c1.setSexo(Cliente.Sexo.M);
			c1.setDataNascimento(LocalDate.of(1990, 1, 1));
			clienteDAO.save(c1);

			Cliente c2 = new Cliente();
			c2.setEmail("fulano@email.com");
			c2.setPassword((encoder.encode("123456")));
			c2.setNome("Fulano de Tal");
			c2.setPapel("CLIENTE");
			c2.setCpf("987.654.321-00");
			c2.setTelefone("0987654321");
			c2.setSexo(Cliente.Sexo.F);
			c2.setDataNascimento(LocalDate.of(1985, 5, 15));
			clienteDAO.save(c2);

			Veiculo v1 = new Veiculo();
			v1.setPlaca("ABC1234");
			v1.setModelo("Fusca");
			v1.setChassi("9BWZZZ377VT004251");
			v1.setAno(1980);
			v1.setQuilometragem(120000);
			v1.setDescricao("Fusca clássico, em bom estado de conservação.");
			v1.setValor(BigDecimal.valueOf(50000));
			v1.setLoja(l1);
			v1.setFotos(null);
			veiculoDAO.save(v1);

			Veiculo v2 = new Veiculo();
			v2.setPlaca("XYZ5678");
			v2.setModelo("Civic");
			v2.setChassi("1HGCM82633A123456");
			v2.setAno(2020);
			v2.setQuilometragem(15000);
			v2.setDescricao("Honda Civic 2020, excelente estado, com garantia.");
			v2.setValor(BigDecimal.valueOf(90000));
			v2.setLoja(l1);
			v2.setFotos(null);
			veiculoDAO.save(v2);

			Proposta p1 = new Proposta();
			p1.setCliente(c1);
			p1.setVeiculo(v1);
			p1.setValor(BigDecimal.valueOf(45000));
			p1.setCondicoesPgto("Pagamento à vista com 10% de desconto.");
			p1.setDataCompra(LocalDate.now());
			p1.setStatus(Proposta.Status.ABERTO);
			propostaDAO.save(p1);
		};
	}
}