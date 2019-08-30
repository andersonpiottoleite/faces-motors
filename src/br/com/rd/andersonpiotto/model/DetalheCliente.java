package br.com.rd.andersonpiotto.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class DetalheCliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String cpf;

	@OneToOne(mappedBy = "detalheCliente", cascade = CascadeType.ALL)
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	// fazendo a amarração entre as entidades fracas e fortes e evitando FK vazias.
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		
		if(cliente.getDetalheCliente() != null) {
			return;
		}
		
		cliente.setDetalheCliente(this);
		return;
	}

	public Integer getId() {
		return id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
