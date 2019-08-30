package br.com.rd.andersonpiotto.model;

import javax.persistence.Embeddable;

// Entidade inserivel, fara parte de uma tabela na base de dados.
@Embeddable
public class Endereco {

	private String rua;
	private String cep;
	private String bairro;

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

}
