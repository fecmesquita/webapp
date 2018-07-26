package br.org.cip.CRMMock.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GeradorTabelas {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("test");
		System.out.println("Tabela gerada!");
		factory.close();
	}

}
