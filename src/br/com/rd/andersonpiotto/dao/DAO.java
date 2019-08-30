package br.com.rd.andersonpiotto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.model.Automovel;
import br.com.rd.andersonpiotto.model.Cliente;
import br.com.rd.andersonpiotto.model.Marca;

/***Clase que representa uma Data Acess Object
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 */
public class DAO {
	
	public void gravaCliente(Cliente cliente){
		System.out.println("Gravando...." + cliente);
		
		EntityManager em = ConnectionFactory.getEntityManager();
		
		em.getTransaction().begin();
		// colocando o objeto como gerenciavel
		em.persist(cliente);
		// persistindo de fato o objeto na base de dados
		em.getTransaction().commit();
		
	}

	public List<Cliente> getClientes() {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		// retornando objetos gerenciados
		TypedQuery<Cliente> createQuery = em.createQuery("select c from Cliente c", Cliente.class);
		
		return createQuery.getResultList();
	}

	public void excluirCliente(Cliente cliente) {
		EntityManager em = ConnectionFactory.getEntityManager();
		em.getTransaction().begin();
		// tornando o objeto gerenciável
		Cliente clienteFind = em.find(Cliente.class, cliente.getId());
		// colocado em estado removed
		em.remove(clienteFind);
		// de fato removido
		em.getTransaction().commit();
	}
	
	public List<Automovel> getAutomovelPeloNomeDaMarca(String nomeDaMarca) {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		// navegando na estrutura do objeto, isso é valido se não formos navegar em listas
		TypedQuery<Automovel> createQuery = em.createQuery("select a from Automovel a where a.modelo.marca.nome =: nomeMarca", Automovel.class);
		
		createQuery.setParameter("nomeMarca", nomeDaMarca);
		
		List<Automovel> resultList = createQuery.getResultList();
		
		return resultList;
	}
	
	// nesse caso, como passamos o objeto, o id da marca é levado em consideração para fazer as comparações.
	public List<Automovel> getAutomovelPelaMarca(Marca marca) {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		// navegando na estrutura do objeto, isso é valido se não formos navegar em listas
		TypedQuery<Automovel> createQuery = em.createQuery("select a from Automovel a where a.modelo.marca =: marca", Automovel.class);
		
		createQuery.setParameter("marca", marca);
		
		List<Automovel> resultList = createQuery.getResultList();
		
		return resultList;
	}
	
	public List<Marca> getMarcaPeloNomeDoModelo(String nomeDoModelo) {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		// usando o join para realizar a query, isso é valido se formos navegar em listas, pois não é possivel na navegação direta, precisa navegar na estrutura da collection.
		TypedQuery<Marca> createQuery = em.createQuery("select m from Marca m left join Modelo md on m.id = md.marca.id where md.nome =: nomeModelo", Marca.class);
		
		// não funciona
		//TypedQuery<Marca> createQuery = em.createQuery("select m from Marca m where m.modelos.nome =: nomeModelo", Marca.class);
		
		createQuery.setParameter("nomeModelo", nomeDoModelo);
		
		List<Marca> resultList = createQuery.getResultList();
		
		return resultList;
	}
	
	// função de agregação
	public long countAutomoveis() {
		EntityManager em = ConnectionFactory.getEntityManager();
		TypedQuery<Long> createQuery = em.createQuery("select count (a) from Automovel a", Long.class);
		
		Long singleResult = createQuery.getSingleResult();
		
		return singleResult.longValue();
		
	}
	
	// função de agregação
	public double somaValoresTodosAutomoveis() {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		TypedQuery<Double> createQuery = em.createQuery("select sum(a.valorDeTabela) from Automovel a", Double.class);
		
		Double singleResult = createQuery.getSingleResult();
		
		return singleResult.doubleValue();
		
	}
	
	// função de agregação
	public double mediaDosValoresTodosAutomoveis() {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		TypedQuery<Double> createQuery = em.createQuery("select avg(a.valorDeTabela) from Automovel a", Double.class);
		
		Double singleResult = createQuery.getSingleResult();
		
		return singleResult.doubleValue();
		
	}
	
	// função de agregação
	public double maiorValorDeTodosAutomoveis() {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		TypedQuery<Double> createQuery = em.createQuery("select max(a.valorDeTabela) from Automovel a", Double.class);
		
		Double singleResult = createQuery.getSingleResult();
		
		return singleResult.doubleValue();
		
	}
	
	// função de agregação
	public double menorValorDeTodosAutomoveis() {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		TypedQuery<Double> createQuery = em.createQuery("select min(a.valorDeTabela) from Automovel a", Double.class);
		
		Double singleResult = createQuery.getSingleResult();
		
		return singleResult.doubleValue();
		
	}
	
	// subquery extraindo a media de preco, para passar como parametro para a consulta principal
	public List<Automovel> consultaAutomoveisAcimaDaMediaDeValorDeTabela() {
		EntityManager em = ConnectionFactory.getEntityManager();
		
		String jpql = "select a from Automovel a where a.valorDeTabela > (select avg(auto.valorDeTabela) from Automovel auto)";
		
		TypedQuery<Automovel> createQuery = em.createQuery(jpql, Automovel.class);
		
		List<Automovel> resultList = createQuery.getResultList();
		
		return resultList;
		
	}
	
	// subquery extraindo as marcas ligadas ao automovel com valor maior queo informado por parametro
		public List<Marca> consultaMarcasComAutomoveisComPrecoAcimaDe(double valor) {
			EntityManager em = ConnectionFactory.getEntityManager();
			
			String jpql = "select m from Marca m where exists (select auto from Automovel auto where auto.modelo.marca = m and auto.valorDeTabela > :valor)";
			
			TypedQuery<Marca> createQuery = em.createQuery(jpql, Marca.class);
			createQuery.setParameter("valor", valor);
			
			List<Marca> resultList = createQuery.getResultList();
			
			return resultList;
			
		}
		
		// em desenvolvimento, eta com bug
	    public void buscaQuantidadeDeAutomoveisPorMarca() {
	    	EntityManager em = ConnectionFactory.getEntityManager();
	    	
	    	Query query = em.createQuery("select a.marca, COUNT(a) from Automovel a GROUP BY a.marca HAVING COUNT(a) > 10");
	    	
	    	List<Object[]> resultList = query.getResultList();
	    	
	    	for (Object[] object : resultList) {
	    		Marca marca = (Marca )object[0];
	    		Long count = (Long )object[1];
	    		
	    		System.out.println("Marca: " + marca.getNome());
	    		System.out.println("Count: " + count);
			}	
	    }
	
	
//	public List<Cliente> getClientes(){
//		return Cliente.getClientes();
//	}
}
