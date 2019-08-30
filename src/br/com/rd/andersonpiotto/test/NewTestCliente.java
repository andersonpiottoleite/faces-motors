package br.com.rd.andersonpiotto.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.model.Cliente;
import br.com.rd.andersonpiotto.model.Dependente;
import br.com.rd.andersonpiotto.model.DetalheCliente;
import br.com.rd.andersonpiotto.model.Endereco;
import br.com.rd.andersonpiotto.model.Matriz;
import br.com.rd.andersonpiotto.model.Opcional;

public class NewTestCliente {
	
	public static void main(String[] args) {
		
		EntityManager entityManager = ConnectionFactory.getEntityManager(); 
		
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
		
		for (int i = 0; i < 100; i++) {
			Dependente dependente = new Dependente();
			dependente.setNome("Metusalém " + i);
			dependente.setIdade(969);
			c.addDependente(dependente);
		}
		
		// preenchendo as tags
		c.getTagsCliente().add("anderson");
		c.getTagsCliente().add("piotto");
		c.getTagsCliente().add("leite");
		
		Opcional opcional = new Opcional();
		opcional.setDescricao("Descrição");
		
		entityManager.getTransaction().begin();
		entityManager.persist(c);
		entityManager.getTransaction().commit();
		
		//Cliente find = entityManager.find(Cliente.class, 51);
		
		// testando a pesquisa com as tags
		Query createQuery = entityManager.createQuery("select c from Cliente c join c.tagsCliente tc where tc like :name");
		String name = "leite";
		createQuery.setParameter("name", "%"+name+"%");
		
		List<Dependente> dependentes = new ArrayList<Dependente>();
		
		for (Object object : createQuery.getResultList()) {
			Cliente cliente  = (Cliente) object;
			System.out.println(cliente);
			dependentes.addAll(cliente.getDependentes());
			
		}
		
		
	}

}
