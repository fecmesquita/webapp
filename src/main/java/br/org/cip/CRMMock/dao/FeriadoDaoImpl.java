package br.org.cip.CRMMock.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

import br.org.cip.CRMMock.model.Feriado;

@Repository
public class FeriadoDaoImpl implements FeriadoDao {

	@Override
	public long incluir(String tipoRequisicao, String data, String situacao, String tipoFeriado, String descricao) {
		return alterar(null, tipoRequisicao, data, situacao, tipoFeriado, descricao);
	}

	@Override
	public long alterar(Long id, String tipoRequisicao, String data, String situacao, String tipoFeriado,
			String descricao) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();

		Feriado feriado = new Feriado();
		if (id != null)
			feriado.setId(id);
		feriado.setData(data);
		feriado.setTipoRequisicao(tipoRequisicao);
		feriado.setSituacao(situacao);
		feriado.setTipoFeriado(tipoFeriado);
		feriado.setDescricao(descricao);
		try {
			manager.getTransaction().begin();
			feriado = manager.merge(feriado);
			manager.getTransaction().commit();
		} finally {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();
		}
		manager.close();
		factory.close();
		return feriado.getId();
	}

	@Override
	public long inativar(long id, String tipoRequisicao) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		manager.createQuery("UPDATE Feriado f SET f.tiporequisicao = :tiporesquisicao WHERE f.id = :id")
				.setParameter("id", id).setParameter("tiporequisicao", tipoRequisicao).executeUpdate();
		manager.close();
		factory.close();
		return id;
	}

	@Override
	public long ativar(long id, String tipoRequisicao) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		manager.createQuery("UPDATE Feriado f SET f.tiporequisicao = :tiporesquisicao WHERE f.id = :id")
				.setParameter("id", id).setParameter("tiporequisicao", tipoRequisicao).executeUpdate();
		manager.close();
		factory.close();
		return id;
	}

	@Override
	public Feriado consultar(long id) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		Feriado feriado = (Feriado) manager.createQuery("select f from Feriado f WHERE f.id = :id")
				.setParameter("id", id).getSingleResult();
		manager.close();
		factory.close();
		return feriado;
	}

	@Override
	public List<Feriado> getAllFeriados() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webappdb");
		EntityManager manager = factory.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Feriado> feriados = manager.createQuery("select f from Feriado f").getResultList();
		manager.close();
		factory.close();
		return feriados;
	}

	/*@Override
	public long excluir(long id) {
		// TODO Auto-generated method stub
		return 0;
	}*/

}
