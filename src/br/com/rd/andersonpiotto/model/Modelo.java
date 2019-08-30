package br.com.rd.andersonpiotto.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import br.com.rd.andersonpiotto.rest.ITypeHierarchyAdapterGson;


/*mport com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;*/

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "idModelo")
public class Modelo implements Serializable, ITypeHierarchyAdapterGson{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idModelo;

	private String nome;

	@Column(name = "ANO_MODELO")
	private Integer anoModelo;

	@ManyToOne(cascade = CascadeType.ALL)
	private Marca marca;

	//@JsonBackReference
	@OneToMany(mappedBy = "modelo")
	
	private List<Automovel> automoveis;

	public Integer getIdModelo() {
		return idModelo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Automovel> getAutomoveis() {
		return automoveis;
	}

	public void addAutomovel(Automovel automovel) {
		if(automoveis == null) {
			automoveis = new ArrayList<Automovel>();
		}
		this.automoveis.add(automovel);
	}

}
