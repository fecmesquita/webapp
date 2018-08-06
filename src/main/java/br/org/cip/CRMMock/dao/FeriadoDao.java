package br.org.cip.CRMMock.dao;

import java.util.List;

import br.org.cip.CRMMock.model.Feriado;

public interface FeriadoDao {

	long incluir(String tipoRequisicao, String data, String situacao, String tipoFeriado, String descricao);
	
	long alterar(Long id, String tipoRequisicao, String data, String situacao, String tipoFeriado, String descricao);
	
	long inativar(long id, String tipoRequisicao);
	
	long ativar(long id, String tipoRequisicao);
	
	Feriado consultar(long id);

	List<Feriado> getAllFeriados();

	Feriado alterar(Feriado feriado);
	
//	long excluir(long id);
	
	
}
