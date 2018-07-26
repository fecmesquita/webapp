package br.org.cip.CRMMock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cip.CRMMock.dao.FeriadoDao;
import br.org.cip.CRMMock.model.Feriado;

@Service
public class FeriadoService {

	@Autowired
	private FeriadoDao feriadoDao;

	public long incluir(String tipoRequisicao, String data, String situacao, String tipoFeriado, String descricao) {
		return feriadoDao.incluir(tipoRequisicao, data, situacao, tipoFeriado, descricao);
	}

	public long alterar(Long id, String tipoRequisicao, String data, String situacao, String tipoFeriado, String descricao) {
		return feriadoDao.alterar(id, tipoRequisicao, data, situacao, tipoFeriado, descricao);
	}
	
	public Feriado getFeriado(long id) {
		return feriadoDao.consultar(id);
	}
	
	public long ativar(Long id, String tipoRequisicao) {
		return feriadoDao.ativar(id, tipoRequisicao);
	}
	
	public long inativar(Long id, String tipoRequisicao) {
		return feriadoDao.inativar(id, tipoRequisicao);
	}

	/*public long delete(long feriadoId) {
		return feriadoDao.excluir(feriadoId);
	}*/

}
