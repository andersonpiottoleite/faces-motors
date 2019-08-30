package br.com.rd.andersonpiotto.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Classe que representa a união de cliente e produto
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 */
@Entity
@IdClass(ClienteProdutoPK.class)
public class ClienteProduto {

	/*
	 * acordagem antiga, que fazia com que a query seja chamado com o .id antes da
	 * propriedade, exemplo : cliente.id.clienteId com
	 * o @IdClass(ClienteProdutoPK.class), podemos chamar o atributo diretamente,
	 * tendo em vista que temos esses atributos nas classes, algo como:
	 * cliente.clienteId
	 */

	// @EmbeddedId
	// private ClienteProdutoPK id;

	@Id
	private Integer clienteId;
	@Id
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

	/*
	 * public ClienteProdutoPK getId() { return id; }
	 * 
	 * public void setId(ClienteProdutoPK id) { this.id = id; }
	 */

}
