package br.com.rd.andersonpiotto.test;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.model.Cliente;
import br.com.rd.andersonpiotto.model.ClienteProdutoPK;
import br.com.rd.andersonpiotto.model.Dependente;
import br.com.rd.andersonpiotto.model.DetalheCliente;
import br.com.rd.andersonpiotto.model.Endereco;
import br.com.rd.andersonpiotto.model.Matriz;
import br.com.rd.andersonpiotto.model.Produto;

/**
 * Classe que representa um test de manipulação de Cliente
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 */
public class TestCliente {

	public static void main(String[] args) throws SQLException {

		EntityManager em = ConnectionFactory.getEntityManager();

		Cliente c = new Cliente();
		c.setNome("Anderson");
		c.setSobreNome("Piotto");
		c.setIdade(34);

		Matriz matriz = new Matriz();
		matriz.setNome("Minha Matriz");

		c.setMatriz(matriz);

		DetalheCliente detalheCliente = new DetalheCliente();
		detalheCliente.setCpf("35461848826");

		// fechando os dois lados do relacionamento
		c.setDetalheCliente(detalheCliente);
		//detalheCliente.setCliente(c);
		
		Endereco endereco = new Endereco();
		endereco.setRua("Minha RUA");
		endereco.setCep("06226436");
		endereco.setBairro("Meu Bairro");
		c.setEndereco(endereco);
		
		Dependente dependente1 = new Dependente();
		dependente1.setNome("Benjamim");
		dependente1.setIdade(3);
		
		Dependente dependente2 = new Dependente();
		dependente2.setNome("Luciana");
		dependente2.setIdade(32);
		
		c.addDependente(dependente1);
		c.addDependente(dependente2);

		em.getTransaction().begin();
		// salvando pelo lado fraco da entidade (tem cascade ALL, o importante é que a
		// entidade forte conheca a entidade fraca)
		//em.persist(detalheCliente);
		// salvando pelo lado forte da entidade
		em.persist(c);

		em.getTransaction().commit();

		Query query = em.createQuery("select c from Cliente c", Cliente.class);

		// Query query = em.createQuery("select dc from DetalheCliente dc where
		// dc.cliente.id =: idCliente", DetalheCliente.class);
		// query.setParameter("idCliente", 51);

		System.out.println(query.getResultList().size());

		@SuppressWarnings("unchecked")
		// List<DetalheCliente> clientes = (List<DetalheCliente>) query.getResultList();
		List<Cliente> clientes = (List<Cliente>) query.getResultList();

		Integer idCliente = 0;

		for (Cliente cliente : clientes) {
			System.out.println(cliente);
			idCliente = cliente.getId();
		}

		// pegando um objeto gerenciado
		Cliente clienteReference = em.getReference(Cliente.class, idCliente);

		clienteReference.setNome("Anderson 2");

		em.getTransaction().begin();
		em.merge(clienteReference);
		em.getTransaction().commit();
		
		TypedQuery<Cliente> createQuery = em.createQuery("select c from Cliente c where c.endereco.bairro =: paramBairro", Cliente.class);
		createQuery.setParameter("paramBairro", "Meu Bairro");
		
		for (Cliente cliente : createQuery.getResultList()) {
			System.out.println(cliente);
		}
		
		Cliente clienteJaPersistindoComDependentes = em.find(Cliente.class, 51);
		
		System.out.println(clienteJaPersistindoComDependentes);
		
		em.close();

	}

}
