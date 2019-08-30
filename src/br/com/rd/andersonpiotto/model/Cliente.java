package br.com.rd.andersonpiotto.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Classe que representa um Cliente.
 * 
 * @author Anderson Piotto
 * @version 1.0.0
 */
@Entity
@Table(name = "CLIENTE")
public class Cliente {

	// @Id @GeneratedValue(strategy = GenerationType.AUTO)
	// @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

	// criando uma sequencia única para um cliente (senão, será uma sequencia
	// compartilhada entre todos)
//	@SequenceGenerator(
//			name = "mySequence",
//			sequenceName = "MY_SEQ",
//			allocationSize = 10)
	// referenciando essa sequencia unica
	// @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "mySequence")

	// criando uma tabela que irá gerenciar a sequence no banco de dados.
	@TableGenerator(name = "myTableGenerator", table = "MY_TABLE", pkColumnValue = "CLIENTE", initialValue = 50)

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myTableGenerator")
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	private Integer id;
	@Column(name = "NOME", nullable = false, length = 15, unique = false, updatable = true)
	private String nome;
	@Column(name = "SOBRENOME", nullable = false, length = 15, unique = false, updatable = true)
	private String sobreNome;
	// grava data e hora na base de dados
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataDeDascimento = Calendar.getInstance();

	// não usando o ALL
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
	// customizando a chave estrangeira de matriz em Cliente
	@JoinColumn(name="matriz_fk")
	private Matriz matriz;

	@OneToOne(cascade = CascadeType.ALL)
	private DetalheCliente detalheCliente;

	@Version
	private Long dataDaUltimaAlteracao;

	// não persistindo o atributo na base de dados
	@javax.persistence.Transient
	private int idade;

	// inserivel, vai fazer parte da mesma tabela na base de dados, porém esta
	// representada como uma entidade distinta de <code>Cliente</code>.
	@Embedded
	private Endereco endereco;

	// uma lista de dependentes - varios dependentes
	// deveria ser Lazy por padrão. Porém esta carregando como eager
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Dependente> dependentes;

	// ver a configuração do geter para arquivos grandes
	private byte[] fotografia;

	// para tags
	@ElementCollection
	private List<String> tagsCliente = new ArrayList<String>();

	// esse cara que atrualiza a chave estrangeira, ele dever ter os objetos populados e inseridos no sistema.
	@ManyToMany
	// customizando a tabela auxiliar/associativa
	@JoinTable(name="Cliente_Opcional", 
	joinColumns = @JoinColumn(name="Cliente_id"),
	inverseJoinColumns = @JoinColumn(name="Opcional_id"))
	private List<Opcional> opcionais = new ArrayList<Opcional>();

	private static List<Cliente> clientes = new ArrayList<Cliente>();

	/*
	 * static { for (int i = 0; i < 5; i++) { Cliente c = new Cliente();
	 * c.setNome("Nome " + i); c.setSobreNome("Sobrenome " + i); c.setIdade(i);
	 * 
	 * clientes.add(c); }
	 * 
	 * }
	 */

	public Integer getId() {
		return id;
	}

	public List<String> getTagsCliente() {
		return tagsCliente;
	}

	public void setTagsCliente(List<String> tagsCliente) {
		this.tagsCliente = tagsCliente;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void addDependente(Dependente dependente) {
		if (this.dependentes == null) {
			this.dependentes = new ArrayList<Dependente>();
		}

		this.dependentes.add(dependente);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// não será carregado por padrão
	@Basic(fetch = FetchType.LAZY)
	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(" " + getId());
		stringBuilder.append(" " + getNome());
		stringBuilder.append(" " + getSobreNome());
		stringBuilder.append(" " + getDataDaUltimaAlteracao());
		stringBuilder.append(" " + getMatriz().getNome());
		stringBuilder.append(" " + getDetalheCliente().getCpf());
		stringBuilder.append(" " + getEndereco().getCep());
		stringBuilder.append(" " + getEndereco().getRua());
		stringBuilder.append(" " + getEndereco().getBairro());

		/*
		 * for (Dependente dependente : getDependentes()) {
		 * 
		 * stringBuilder.append(" Dependente: " + dependente.getNome());
		 * stringBuilder.append(" Dependente: " + dependente.getIdade()); }
		 */

		return stringBuilder.toString();
	}

	public static List<Cliente> getClientes() {
		return clientes;
	}

	public Calendar getDataDeDascimento() {
		return dataDeDascimento;
	}

	public void setDataDeDascimento(Calendar dataDeDascimento) {
		this.dataDeDascimento = dataDeDascimento;
	}

	public Matriz getMatriz() {
		return matriz;
	}

	public void setMatriz(Matriz matriz) {
		this.matriz = matriz;
	}

	public Long getDataDaUltimaAlteracao() {
		return dataDaUltimaAlteracao;
	}

	public void setDataDaUltimaAlteracao(Long dataDaUltimaAlteracao) {
		this.dataDaUltimaAlteracao = dataDaUltimaAlteracao;
	}

	public DetalheCliente getDetalheCliente() {
		return detalheCliente;
	}

	public void setDetalheCliente(DetalheCliente detalheCliente) {
		// fazendo a amarração entre as entidades fracas e fortes e evitando FK vazias.
		this.detalheCliente = detalheCliente;

		if (detalheCliente.getCliente() != null) {
			return;
		}

		detalheCliente.setCliente(this);
	}

	public static void setClientes(List<Cliente> clientes) {
		Cliente.clientes = clientes;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	// para arquivos muito grandes, LOB, e carregamento sob demanda com BASIC
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public byte[] getFotografia() {
		return fotografia;
	}

	public void setFotografia(byte[] fotografia) {
		this.fotografia = fotografia;
	}

	public List<Opcional> getOpcionais() {
		return opcionais;
	}
	
	public void addOpcional(Opcional opcional) {
		if (this.opcionais == null) {
			this.opcionais = new ArrayList<Opcional>();
		}

		this.opcionais.add(opcional);
	}

}
