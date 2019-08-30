package br.com.rd.andersonpiotto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import br.com.rd.andersonpiotto.rest.ITypeHierarchyAdapterGson;

//import org.codehaus.jackson.annotate.JsonTypeInfo;

/*import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;*/

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class Marca implements Serializable, ITypeHierarchyAdapterGson{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nome;

	
	@OneToMany(mappedBy = "marca")
	private List<Modelo> modelos;

	public Integer getId() {
		return id;
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

	public List<Modelo> getModelos() {
		return modelos;
	}

	public void addModelo(Modelo modelo) {
		if(modelos == null) {
			modelos = new ArrayList<Modelo>();
		}
		modelos.add(modelo);
	}
	
	@Override
	public String toString() {
	
		return getNome();
	}

}
