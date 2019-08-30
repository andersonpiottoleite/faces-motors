package br.com.rd.andersonpiotto.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.rd.andersonpiotto.dao.DAO;
import br.com.rd.andersonpiotto.model.Cliente;

/**
 * Classe que representa um ManagedBean
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 */
// @ManagedBean(name="myClientBean")
@ManagedBean
public class ClienteBean {

	private Cliente cliente = new Cliente();

	private DAO clienteDAO = new DAO();

	private List<Cliente> clientes;

	private String observacoes;
	
	@PostConstruct
	public void init() throws Exception {
		throw new Exception();
	}

	public void salva() {
		System.out.println("Salvando cliente...");
		System.out.println("Cliente : " + cliente.toString());
		System.out.println("Observações: " + getObservacoes());

		clienteDAO.gravaCliente(cliente);
	}

	/*
	 * Usando passagem de parâmetro public void salva(Cliente cliente) {
	 * System.out.println("Salvando cliente...");
	 * System.out.println("Cliente : " + cliente.toString());
	 * System.out.println("Observações: " + getObservacoes()); }
	 */

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		// evitando várias consultas pelo Hibernate
		if(clientes == null){
			clientes = clienteDAO.getClientes();
		}
		
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public void excluirCliente(Cliente cliente){
		System.out.println("Removendo cliente");
		clienteDAO.excluirCliente(cliente);
		clientes = null;
		getClientes();
	}

}
