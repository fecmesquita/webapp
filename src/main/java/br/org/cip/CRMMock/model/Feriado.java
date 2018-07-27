package br.org.cip.CRMMock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "feriado")
public class Feriado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "tiporequisicao")
	private String tipoRequisicao;
	@Column(name = "data")
	private String data;
	@Column(name = "situacao")
	private String situacao;
	@Column(name = "tipoferiado")
	private String tipoFeriado;
	@Column(name = "descricao")
	private String descricao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public void setTipoFeriado(String tipoFeriado) {
		this.tipoFeriado = tipoFeriado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Feriado [id=" + id + ", data=" + data + ", situacao=" + situacao + ", tipoFeriado=" + tipoFeriado
				+ ", descricao=" + descricao + "]";
	}

}
