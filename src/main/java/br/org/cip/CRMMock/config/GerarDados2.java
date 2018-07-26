package br.org.cip.CRMMock.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.org.cip.CRMMock.model.theme.Config;
import br.org.cip.CRMMock.model.theme.ThemeType;

public class GerarDados2 {

	public static void main(String... args) {
		generateTheme();
		
	}

	public static void generateTheme() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		Config.getInstance().setThemeType(ThemeType.CRM);
		try {
			manager.getTransaction().begin();
			manager.persist(Config.getInstance());
			manager.getTransaction().commit();
		} finally {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();
		}
		manager.close();
		factory.close();
		
	}
}
