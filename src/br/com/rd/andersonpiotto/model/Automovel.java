package br.com.rd.andersonpiotto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.rd.andersonpiotto.rest.ITypeHierarchyAdapterGson;



@Entity
@Table(name = "T_AUTOMOVEL")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "idCar")
public class Automovel implements Serializable, ITypeHierarchyAdapterGson{

	/*@SequenceGenerator(
			name = "mySequenceCar",
			sequenceName = "MY_SEQ_CAR",
			allocationSize = 10,
			initialValue = 9)
			*/

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySequenceCar")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idCar;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "VALOR_DE_TABELA")
	private double valorDeTabela;

	//@JsonIgnoreProperties("automoveis")
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Modelo modelo;

	//@JsonManagedReference
	//@JsonIgnore
	//@JsonIgnoreProperties("automovel")
	@OneToMany(mappedBy = "automovel", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	List<Roda> rodas;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Marca marca;

	public List<Roda> getRodas() {
		return rodas;
	}

	public void addRoda(Roda roda) {
		if (rodas == null) {
			rodas = new ArrayList<Roda>();
		}

		this.rodas.add(roda);
	}

	public Integer getIdCar() {
		return idCar; 
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public double getValorDeTabela() {
		return valorDeTabela;
	}

	public void setValorDeTabela(double valorDeTabela) {
		this.valorDeTabela = valorDeTabela;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();

		sb.append("Nome: " + getNome() + "\n");
		sb.append("Modelo: " + getModelo().getNome() + "\n");
		sb.append("Ano: " + getModelo().getAnoModelo() + "\n");
		sb.append("Marca 1: " + getModelo().getMarca().getNome() + "\n");
		sb.append("Valor de tabela: " + getValorDeTabela() + "\n");

		return sb.toString();
	}

}
