package br.org.cip.CRMMock.config;

import br.org.cip.CRMMock.dao.FeriadoDao;
import br.org.cip.CRMMock.dao.implementation.FeriadoDaoImpl;

public class GerarDados {

	public static void main(String... args) {
		geraDados();
		geraDados();
		geraDados();
		geraDados();
		geraDados();
		geraDados();
	}

	public static void geraDados() {
		FeriadoDao feriadoDao = new FeriadoDaoImpl();

		String tipoRequisicao = "efetivar";
		String descricao = "Dia Nacinal de Alguma Coisa 1";
		String data = "18/07/2018";
		String situacao = "Ativo(a)";
		String tipoFeriado = "Nacional";
		feriadoDao.incluir(tipoRequisicao, data, situacao, tipoFeriado, descricao);

		tipoRequisicao = "efetivar";
		descricao = "Dia Nacinal de Alguma Coisa 2";
		data = "18/07/2018";
		situacao = "Ativo(a)";
		tipoFeriado = "Nacional";
		feriadoDao.incluir(tipoRequisicao, data, situacao, tipoFeriado, descricao);

		tipoRequisicao = "efetivar";
		descricao = "Dia Nacinal de Alguma Coisa 3";
		data = "18/07/2018";
		situacao = "Ativo(a)";
		tipoFeriado = "Nacional";
		feriadoDao.incluir(tipoRequisicao, data, situacao, tipoFeriado, descricao);
	}
}
