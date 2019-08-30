package br.com.rd.andersonpiotto.connection;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe que representa uma conection factory
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 */
public class ConnectionFactory {

	/*
	 * private static EntityManager entityManager;
	 * 
	 * // Singleton public static EntityManager getEntityManager() { if
	 * (entityManager == null) { EntityManagerFactory createEntityManagerFactory
	 * = Persistence .createEntityManagerFactory("persitUnit");
	 * 
	 * entityManager = createEntityManagerFactory.createEntityManager();
	 * 
	 * return entityManager; }
	 * 
	 * return entityManager; }
	 */

	private static final EntityManager entityManager = Persistence
			.createEntityManagerFactory("persitUnit")
			.createEntityManager();

	public static EntityManager getEntityManager() {
		return entityManager;
	}
	
	

}
