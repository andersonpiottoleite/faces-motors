package br.com.rd.andersonpiotto.test;

import javax.persistence.EntityManager;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.model.Cliente;
import br.com.rd.andersonpiotto.model.ClienteProduto;
import br.com.rd.andersonpiotto.model.ClienteProdutoPK;
import br.com.rd.andersonpiotto.model.Produto;

/**
 * Classe que representa um case de teste
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 */
public class TestChaveComposta {

	public static void main(String[] args) {
		
		EntityManager em = ConnectionFactory.getEntityManager();

//		Produto produto = new Produto();
//		produto.setNome("Produto 1");
//		produto.setValor(10);
//		
//		Cliente cliente  = new Cliente();
//		cliente.setNome("Anderson");
//		cliente.setSobreNome("Piotto");
//		cliente.setIdade(34);
//		
//		em.getTransaction().begin();
//		em.persist(produto);
//		em.persist(cliente);
//		em.getTransaction().commit();

		// instanciando classe que representa uma chave composta
		ClienteProdutoPK clienteProdutoPK = new ClienteProdutoPK();

		// setando os valores das chaves
		clienteProdutoPK.setClienteId(1);
		clienteProdutoPK.setProdutoId(1);
		
		ClienteProduto cp  = new ClienteProduto();
		// setando a chave composta no id do cliente
		//cp.setId(clienteProdutoPK);
		
		// para executar a query pelo id do cliente é preciso que haja a navegação através de "cp.id.clienteId" 
		em.createQuery("select cp from ClienteProduto where cp.id.clienteId =: clienteId");

	}

}
