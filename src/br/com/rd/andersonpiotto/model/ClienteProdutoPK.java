package br.com.rd.andersonpiotto.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.IdClass;

/**
 * Classe que representa uma chave composta da entidade
 * <code>ClienteProduto</code>
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 *
 */

// deve implementar serializable para a JPA entender o ID
// na abordagem do @IdClass(ClienteProdutoPK.class) não precisa da notação: @Embeddable
public class ClienteProdutoPK implements Serializable {

	private Integer clienteId;
	private Integer produtoId;

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

}
