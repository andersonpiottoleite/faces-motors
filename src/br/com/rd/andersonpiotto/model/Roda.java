package br.com.rd.andersonpiotto.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import com.google.gson.annotations.Expose;

import br.com.rd.andersonpiotto.rest.ITypeHierarchyAdapterGson;

/*import com.fasterxml.jackson.annotation.JsonIdentityInfo;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
*/


@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Roda implements Serializable, ITypeHierarchyAdapterGson{
	
	/*@SequenceGenerator(
			name = "mySequence",
			sequenceName = "MY_SEQ",
			allocationSize = 10,
			initialValue = 14)
			*/

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySequence")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nome;
	
	//@JsonBackReference
	//@JsonIgnoreProperties("rodas")
	@ManyToOne
	@JoinColumn(name = "ID_AUTOMOVEL", nullable = false)
	private Automovel automovel;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public Automovel getAutomovel() {
		return automovel;
	}

	public void setAutomovel(Automovel automovel) {
		this.automovel = automovel;
	}
	
	

}
