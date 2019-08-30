package br.com.rd.andersonpiotto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

//import org.codehaus.jackson.annotate.JsonTypeInfo;

/*import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;*/

@XmlRootElement
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class MarcaDTO implements Serializable{

	private Integer id;

	private String nome;

	private List<ModeloDTO> modelos;

	public Integer getId() {
		return id;
	}

	public void setModelos(List<ModeloDTO> modelos) {
		this.modelos = modelos;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ModeloDTO> getModelos() {
		return modelos;
	}

	public void addModelo(ModeloDTO modelo) {
		if(modelos == null) {
			modelos = new ArrayList<ModeloDTO>();
		}
		modelos.add(modelo);
	}

}
