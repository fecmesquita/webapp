package br.org.cip.CRMMock.dao.implementation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

import br.org.cip.CRMMock.dao.ThemeDao;
import br.org.cip.CRMMock.model.theme.Config;

@Repository
public class ThemeDaoImpl implements ThemeDao{

	@Override
	public Config update(Config theme) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		
		theme.setId(1L);
		Config saved = null;
		try {
			manager.getTransaction().begin();
			saved = manager.merge(theme); // Sem (ou id errado) id o ragistro sera criado, com id o registro sera
										// atualizado!
			manager.getTransaction().commit();
		} finally {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();
		}
		manager.close();
		factory.close();
		return saved;
	}

	@Override
	public Config getTheme() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		
//		@SuppressWarnings("unchecked")
//		List<Theme> theme = manager.createQuery("select t from Theme t").getResultList();
		
		Config theme = (Config) manager.createQuery("select t from Config t where t.id = :id").setParameter("id", 1L).getSingleResult();
		manager.close();
		factory.close();
		return theme;
	}

	

}
