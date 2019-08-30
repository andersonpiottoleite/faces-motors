package br.com.rd.andersonpiotto.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class Prova {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private double resultado;

	@ManyToOne
	// criando uma chave estrangeira composta na tabela dessa entidade, composta de dois atributos da entidade <code>Aluno</code>
	@JoinColumns({ @JoinColumn(name = "aluno_nome", referencedColumnName = "nome"),
			@JoinColumn(name = "aluno_nome_Mae", referencedColumnName = "nomeDaMae") })
	private Aluno aluno;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getResultado() {
		return resultado;
	}

	public void setResultado(double resultado) {
		this.resultado = resultado;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
