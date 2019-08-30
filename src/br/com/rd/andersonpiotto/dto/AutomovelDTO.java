package br.com.rd.andersonpiotto.dto;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/*import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;*/



@XmlRootElement
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "idCar")
public class AutomovelDTO implements Serializable {

	
	private Integer idCar;

	
	private String nome;

	private ModeloDTO modelo;

	List<RodaDTO> rodas;

	public List<RodaDTO> getRodas() {
		return rodas;
	}

	public void addRoda(RodaDTO roda) {
		if (rodas == null) {
			rodas = new ArrayList<RodaDTO>();
		}

		this.rodas.add(roda);
	}

	public Integer getIdCar() {
		return idCar;
	}

	public void setIdCar(Integer idCar) {
		this.idCar = idCar;
	}

	public void setRodas(List<RodaDTO> rodas) {
		this.rodas = rodas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ModeloDTO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloDTO modelo) {
		this.modelo = modelo;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();

		sb.append("Nome: " + getNome() + "\n");
		sb.append("Modelo: " + getModelo().getNome() + "\n");
		sb.append("Ano: " + getModelo().getAnoModelo() + "\n");
		sb.append("Marca: " + getModelo().getMarca().getNome() + "\n");

		return sb.toString();
	}

}
