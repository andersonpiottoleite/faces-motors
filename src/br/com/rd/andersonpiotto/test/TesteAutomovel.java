package br.com.rd.andersonpiotto.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.dao.DAO;
import br.com.rd.andersonpiotto.model.Automovel;
import br.com.rd.andersonpiotto.model.Marca;
import br.com.rd.andersonpiotto.model.Modelo;

public class TesteAutomovel {
	
	public static void main(String[] args) {
		
		EntityManager em = ConnectionFactory.getEntityManager();
		
		em.getTransaction().begin();
		
		Automovel auto = new Automovel();
		auto.setNome("MERIVA");
		auto.setValorDeTabela(50000);
		
		Modelo modelo = new Modelo();
		modelo.setAnoModelo(1985);
		modelo.setNome("Popular");
		
		Marca marca = new Marca();
		marca.setNome("CHEVROLET");
		
		// amarrando as entidades
		auto.setMarca(marca);
		auto.setModelo(modelo);
		modelo.addAutomovel(auto);
		modelo.setMarca(marca);
		marca.addModelo(modelo);
		
		
		em.persist(auto);
		
		em.getTransaction().commit();
		
		String nome = "MERIVA";
		
		TypedQuery<Automovel> query = em.createQuery("select a from Automovel a where nome =:nome", Automovel.class);
		query.setParameter("nome", nome);
		
		for (Automovel automovel : query.getResultList()) {
			System.out.println(automovel);
			
			
		}
		
		
		DAO dao = new DAO();
		
		List<Automovel> automovelPeloNomeDaMarca = dao.getAutomovelPeloNomeDaMarca("CHEVROLET");
		
		for (Automovel automovel : automovelPeloNomeDaMarca) {
			System.out.println("Result pelo nome da Marca: " + automovel);
		}
		
		List<Marca> marcaPeloNomeDoModelo = dao.getMarcaPeloNomeDoModelo("Popular");
		
		 for (Marca marca2 : marcaPeloNomeDoModelo) {
			 
			 System.out.println("Vindo do getMarcaPeloNomeDoModelo: " + marca2);
			
		}
		 
		 List<Automovel> automovelPelaMarca = dao.getAutomovelPelaMarca(marca);
		 
		 for (Automovel automovel : automovelPelaMarca) {
			 
			 System.out.println("Vindo do getAutomovelPelaMarca: " + automovel);
			
		}
		 
		 // testando funcoes de agregação
		 System.out.println("Quantidade de automoveis:"+ dao.countAutomoveis());
		 System.out.println("Soma dos valores dos automoveis: " + dao.somaValoresTodosAutomoveis());
		 System.out.println("Media dos valores dos automoveis: " + dao.mediaDosValoresTodosAutomoveis());
		 System.out.println("Maior valor dos automoveis: " + dao.maiorValorDeTodosAutomoveis());
		 System.out.println("Menor valor dos automoveis: " + dao.menorValorDeTodosAutomoveis());
		 
		 List<Automovel> automoveisAcimaDaMediaDeValorDeTabela = dao.consultaAutomoveisAcimaDaMediaDeValorDeTabela();
		 
		 for (Automovel automovel : automoveisAcimaDaMediaDeValorDeTabela) {
			 
			 System.out.println("Vindo do consultaAutomoveisAcimaDaMediaDeValorDeTabela: " + automovel);
			
		 }
		 
		 List<Marca> marcasComAutomoveisComPrecoAcimaDe = dao.consultaMarcasComAutomoveisComPrecoAcimaDe(5990);
			
		 for (Marca marca1 : marcasComAutomoveisComPrecoAcimaDe) {
			 
			 System.out.println("Vindo do consultaMarcasComAutomoveisComPrecoAcimaDe: " + marca1);
			
		}
		 
		 dao.buscaQuantidadeDeAutomoveisPorMarca();
		
	}

}
