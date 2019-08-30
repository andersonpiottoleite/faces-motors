package br.com.rd.andersonpiotto.test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.model.Cliente;

/**
 * Classe para testar ciclos de vida do JPA.
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 */
public class TesteCicloDeVidaJPA {

	public static void main(String[] args) {

		EntityManager em = ConnectionFactory.getEntityManager();
		Query query = null;
		// new
		Cliente c = new Cliente();
		c.setNome("Anderson");
		c.setSobreNome("Piotto");
		c.setIdade(34);

		em.getTransaction().begin();

		// estado gerenciavel
		em.persist(c);
		// deve vir 0
		query = em.createQuery("select c from Cliente c", Cliente.class);
		System.out.println(query.getResultList().size());

		// persistindo o objeto
		em.getTransaction().commit();
		// deve vir 1
		query = em.createQuery("select c from Cliente c", Cliente.class);
		System.out.println(query.getResultList().size());

		// retornando o objeto em um estado gerenciavel
		Cliente forDetached = em.find(Cliente.class, 1);

		// desafixando o objeto
		//em.detach(forDetached);
		em.clear();
		// n�o deve remover, pois n�o esta mais em estado gerenci�vel
		try {
			em.remove(forDetached);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// retornando o objeto para um estado gerenci�vel
		// sempre devolve um objeto gerenciavel, diferente do desanexado
		// (detached), mantem sempre um gerenci�vel em instancia

		Cliente merge = em.merge(forDetached);

		if (forDetached == merge) {
			System.out.println("Objetos gerenci�veis!");
		} else {
			System.out.println("N�o gerenci�veis");
		}

		Cliente findGerenciavel = em.find(Cliente.class, 1);

		if (findGerenciavel == merge) {
			System.out.println("Objetos gerenci�veis!");
		} else {
			System.out.println("N�o gerenci�veis");
		}

		// agora deve remover
		em.getTransaction().begin();
		em.remove(merge);
		em.flush();

		query = em.createQuery("select c from Cliente c", Cliente.class);
		System.out.println(query.getResultList().size());
	}

}
