package br.org.cip.CRMMock.model.form;

public class FeriadoForm {

	private Long id;
	private String tipoRequisicao;
	private String data;
	private String situacao;
	private String tipoFeriado;
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoRequisicao() {
		return tipoRequisicao;
	}

	public void setTipoRequisicao(String tipoRequisicao) {
		this.tipoRequisicao = tipoRequisicao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTipoFeriado() {
		return tipoFeriado;
	}

	public void setTipoFeriado(String tipoferiado) {
		this.tipoFeriado = tipoferiado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
