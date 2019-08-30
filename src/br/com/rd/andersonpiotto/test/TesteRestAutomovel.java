package br.com.rd.andersonpiotto.test;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.model.Automovel;
import br.com.rd.andersonpiotto.model.Marca;
import br.com.rd.andersonpiotto.model.Modelo;
import br.com.rd.andersonpiotto.model.Roda;
import br.com.rd.andersonpiotto.rest.JsonUtil;


public class TesteRestAutomovel {

	public static void main(String[] args) throws IllegalArgumentException, IOException {

		criaAutomovel();
		
		Automovel automovel = null;
			
		 automovel = buscaPorId(Integer.parseInt("1"));
		 
		 //Response convertToJson = JsonUtil.convertToJson(automovel);
		 
		// System.out.println(convertToJson.toString());
		 //String convertToJson = JsonUtil.convertToJson(automovel);
		 //System.out.println(convertToJson);
	}
	
	private static void criaAutomovel() {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		em.getTransaction().begin();

		Automovel auto = new Automovel();
		auto.setNome("MERIVA");

		Modelo modelo = new Modelo();
		modelo.setAnoModelo(1985);
		modelo.setNome("Popular");

		Marca marca = new Marca();
		marca.setNome("CHEVROLET");
		
		Roda roda1 = new Roda();
		roda1.setNome("Cromada");
		
		Roda roda2 = new Roda();
		roda2.setNome("Cromada 2");

		// amarrando as entidades
		auto.setModelo(modelo);
		modelo.addAutomovel(auto);
		modelo.setMarca(marca);
		marca.addModelo(modelo);
		auto.addRoda(roda1);
		roda1.setAutomovel(auto);
		auto.addRoda(roda2);
		roda2.setAutomovel(auto);
		
		

		em.persist(auto);

		em.getTransaction().commit();
	}

	private static Automovel buscaPorId(int idAutomovel) {
		EntityManager em = ConnectionFactory.getEntityManager();

		TypedQuery<Automovel> query = em.createQuery("select a from Automovel a where a.idCar = :param", Automovel.class);
		query.setParameter("param", idAutomovel);

		return query.getSingleResult();

	}

}
