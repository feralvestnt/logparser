package com.ef.logparser.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;

public class LogDao {
	
	private static LogDao instance;

	private EntityManagerFactory entityManagerFactory;

	private LogDao() {
		entityManagerFactory =
				Persistence.createEntityManagerFactory("LogAccessPersistenceUnit");
	}

	public static final LogDao getInstance() {
		if(instance == null) {
			instance = new LogDao();
		}
		return instance;
	}

	public void save(Collection<?> logEntries) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		logEntries.forEach(entityManager::persist);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void close() {
		entityManagerFactory.close();
		this.entityManagerFactory = null;
		instance = null;
	}
}
