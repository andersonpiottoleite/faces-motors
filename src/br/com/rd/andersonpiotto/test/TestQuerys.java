package br.com.rd.andersonpiotto.test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.model.Automovel;
import br.com.rd.andersonpiotto.model.DataAtualDoBancoDeDados;

public class TestQuerys {

	public static void main(String[] args) {
		EntityManager em = ConnectionFactory.getEntityManager();

		em.getTransaction().begin();

		Automovel auto = new Automovel();
		auto.setNome("MERIVA");

		em.persist(auto);

		em.getTransaction().commit();

		// Query query = em.createQuery("select lower(a.nome) from Automovel a");
		// Query query = em.createQuery("select upper(a.nome) from Automovel a");
		// Query query = em.createQuery("select length(a.nome) from Automovel a");
		// Query query = em.createQuery("select TRIM(TRAILING 'A' FROM :param) from
		// Automovel a");
		// Query query = em.createQuery("select concat(:param1, :param2) from Automovel
		// a");
		Query query = em.createQuery("select substring(:param1, 0, 3) from Automovel a");
		query.setParameter("param1", auto.getNome());
		// query.setParameter("param2", auto.getNome());

		String nameLowerAuto = (String) query.getSingleResult();
		// Integer nameLowerAuto = (Integer) query.getSingleResult();

		System.out.println(nameLowerAuto);

		Query query1 = em.createNativeQuery("select CURRENT_TIMESTAMP", DataAtualDoBancoDeDados.class);

		DataAtualDoBancoDeDados data = (DataAtualDoBancoDeDados) query1.getSingleResult();

		System.out.println(data.getData());

	}

}
