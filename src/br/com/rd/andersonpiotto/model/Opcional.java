package br.com.rd.andersonpiotto.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Opcional {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String descricao;
	@ManyToMany(mappedBy = "opcionais")
	List<Cliente> clientes  = new ArrayList<Cliente>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public void addCliente(Cliente cliente) {
		if (this.clientes == null) {
			this.clientes = new ArrayList<Cliente>();
		}

		this.clientes.add(cliente);
	}
}
