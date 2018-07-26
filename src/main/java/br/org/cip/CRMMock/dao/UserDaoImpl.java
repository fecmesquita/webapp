package br.org.cip.CRMMock.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

import br.org.cip.CRMMock.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	public User save(User user) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		User saved = null;
		try {
			manager.getTransaction().begin();
			saved = manager.merge(user); // Sem (ou id errado) id o ragistro sera criado, com id o registro sera
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
	public User findByName(String name) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		
		User conta = (User) manager.createQuery("select c from User c WHERE c.username = :username")
				.setParameter("username", name).getSingleResult();
		manager.close();
		factory.close();
		return conta;
	}

	@Override
	public User findByNamePassword(String username, String password) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		@SuppressWarnings("unchecked")
		List<User> userList = manager
				.createQuery("select c from User c WHERE c.username = :username AND c.password = :password")
				.setParameter("username", username).setParameter("password", password).getResultList();
		manager.close();
		factory.close();
		if(userList.isEmpty()) {
			return null;
		}else
			return userList.get(0);
	}

}