package br.com.rd.andersonpiotto.dto;

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


/*import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;*/

@XmlRootElement
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "idModelo")
public class ModeloDTO implements Serializable{

	private Integer idModelo;

	private String nome;

	private Integer anoModelo;

	private MarcaDTO marca;

	private List<AutomovelDTO> automoveis;

	public Integer getIdModelo() {
		return idModelo;
	}
	
	

	public void setIdModelo(Integer idModelo) {
		this.idModelo = idModelo;
	}



	public void setAutomoveis(List<AutomovelDTO> automoveis) {
		this.automoveis = automoveis;
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

	public MarcaDTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaDTO marca) {
		this.marca = marca;
	}

	public List<AutomovelDTO> getAutomoveis() {
		return automoveis;
	}

	public void addAutomovel(AutomovelDTO automovel) {
		if(automoveis == null) {
			automoveis = new ArrayList<AutomovelDTO>();
		}
		this.automoveis.add(automovel);
	}

}
