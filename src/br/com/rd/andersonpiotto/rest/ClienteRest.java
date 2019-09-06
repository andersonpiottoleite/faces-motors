package br.com.rd.andersonpiotto.rest;

import static br.com.rd.andersonpiotto.rest.JsonUtil.getJsonTo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/webServiceTest/v1")
public class ClienteRest {
	
	public static void main(String[] args) throws Exception {
		
		URL url = new URL("http://localhost:8080/faces-motors/rest/cliente/cliente");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("KEY", "myKey");
		conn.setRequestProperty("VALUE", "myValue");
		conn.setRequestProperty("TOKEN", "ece40d50-b22b-4b33-a728-343be730d85e");

		if (conn.getResponseCode() == 401) {
			System.out.println("Não autorizado");

		}else if (conn.getResponseCode() == 200){

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		}

		conn.disconnect();
		

		String name = "meuUsuario!";
        String password = "minhaSenha!";
        String authString = name + ":" + password;
        
        //String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
        
        //String authStringEnc = DatatypeConverter.printBase64Binary
        //(authString.getBytes(StandardCharsets.UTF_8));
        String authStringEnc = DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8"));
        		
        System.out.println("Base64 encoded auth string: " + authStringEnc);
        
        URL url2 = new URL("http://localhost:8080/faces-motors/rest/cliente/cliente2");
		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
		conn2.setRequestMethod("GET");
		conn2.setRequestProperty("Accept", "application/json");
		String basicAuth = "Basic " +authStringEnc;
		conn2.setRequestProperty ("Authorization", basicAuth);
		
		

		if (conn2.getResponseCode() == 401) {
			System.out.println("Não autorizado");

		}else if (conn2.getResponseCode() == 200){

			BufferedReader br = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
		
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		}
		
		conn2.disconnect();
		
	}
	
	
	@GET
	@Path("cliente")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getClienteJSON(@Context HttpHeaders httpHeaders, @HeaderParam("authorization") String authString) throws IOException {
		
		if(httpHeaders != null) {
			for (Entry<String, List<String>> entry : httpHeaders.getRequestHeaders().entrySet()) {
				
				String key = entry.getKey();
				String value = entry.getValue().get(0);
				
				if(key.equals("key")) {
					if(!value.toString().equals("myKey")) {
			 
						return getResponseComStatusNaoAutorizado();
					}
				}
				
				if(key.equals("value")) {
					if(!value.toString().equals("myValue")) {
						
						return getResponseComStatusNaoAutorizado();
						
					}
				}
				
				if(key.equals("token")) {
					if(!value.toString().equals("ece40d50-b22b-4b33-a728-343be730d85e")) {
						
						return getResponseComStatusNaoAutorizado();
						
					}
				}
				
			}
		}
		
		if(authString != null) {
			String decodedAuth = "";
			
	        String[] authParts = authString.split("\\s+");
	        
	        String authInfo = authParts[1];
	        
	        byte[] bytes = null;
	        bytes = Base64.getDecoder().decode(authInfo);
	        decodedAuth = new String(bytes);
	        
	        System.out.println("In service: " + decodedAuth);
	        
	        String[] dados = decodedAuth.split(":");
	        
	        String usuario = dados[0];
	        String senha = dados[1];
	        
	        if(!usuario.equals("meuUsuario!") || !senha.equals("minhaSenha!")) {
	        	return getResponseComStatusNaoAutorizado();
	        }
		}
		
		GsonBuilder builder = new GsonBuilder(); 
	    Gson gson = builder.create();
	    
	    Cliente cliente = new Cliente();
		
		cliente.setNome("Anderson Piotto 2");
		cliente.setIdade(44);
		cliente.setEmail("piotto2k10@gmail.com");
		cliente.setCdContrato(11603);
		cliente.setCdPlano(5388);
		cliente.setVivo(Boolean.TRUE);
		cliente.setFortuna(50);
		
		
	    
        String result = gson.toJson(cliente);
        return Response
                .status(Status.OK)
                .entity(result)
                .build();
	}

	@GET
	@Path("cliente")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response getClienteXML(@Context HttpHeaders httpHeaders, @HeaderParam("authorization") String authString) throws IOException, JAXBException {
		
		if(httpHeaders != null) {
			for (Entry<String, List<String>> entry : httpHeaders.getRequestHeaders().entrySet()) {
				
				String key = entry.getKey();
				String value = entry.getValue().get(0);
				
				if(key.equals("key")) {
					if(!value.toString().equals("myKey")) {
			 
						return getResponseComStatusNaoAutorizado();
					}
				}
				
				if(key.equals("value")) {
					if(!value.toString().equals("myValue")) {
						
						return getResponseComStatusNaoAutorizado();
						
					}
				}
				
				if(key.equals("token")) {
					if(!value.toString().equals("ece40d50-b22b-4b33-a728-343be730d85e")) {
						
						return getResponseComStatusNaoAutorizado();
						
					}
				}
				
			}
		}
		
		if(authString != null) {
			String decodedAuth = "";
			
	        String[] authParts = authString.split("\\s+");
	        
	        String authInfo = authParts[1];
	        
	        byte[] bytes = null;
	        bytes = Base64.getDecoder().decode(authInfo);
	        decodedAuth = new String(bytes);
	        
	        System.out.println("In service: " + decodedAuth);
	        
	        String[] dados = decodedAuth.split(":");
	        
	        String usuario = dados[0];
	        String senha = dados[1];
	        
	        if(!usuario.equals("meuUsuario!") || !senha.equals("minhaSenha!")) {
	        	return getResponseComStatusNaoAutorizado();
	        }
		}
	    
		Cliente cliente = new Cliente();
		
		cliente.setNome("Anderson Piotto 2");
		cliente.setIdade(44);
		cliente.setEmail("piotto2k10@gmail.com");
		cliente.setCdContrato(11603);
		cliente.setCdPlano(5388);
		cliente.setVivo(Boolean.TRUE);
		cliente.setFortuna(50);
	    
		JAXBContext jaxbContext = JAXBContext.newInstance(Cliente.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		StringWriter stringWriter = new StringWriter();
		jaxbMarshaller.marshal(cliente, stringWriter);

	    String result = stringWriter.toString();
	    
        return Response
                .status(Status.OK)
                .entity(result)
                .build();
        
        
	}
	
	@GET
	@Path("beneficiarios")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBeneficiariosJSON(@Context HttpHeaders httpHeaders, @HeaderParam("authorization") String authString) throws IOException, JAXBException {
		
		if(! validaApiKeyEHeader(httpHeaders)) {
			return getResponseComStatusNaoAutorizado();
			
		}
		
		if(! validaBasicAuth(authString)) {
			return getResponseComStatusNaoAutorizado();
			
		}
	    
		Gson gson = criaGson();
	    
	    ContentArcelor contentArcelor = new ContentArcelor();
	    
        String result = gson.toJson(contentArcelor);
        
        return Response
                .status(Status.OK)
                .entity(result)
                .build();
        
        
	}


	private Gson criaGson() {
		GsonBuilder builder = new GsonBuilder(); 
		// com formatação
	    Gson gson = builder.setPrettyPrinting().create();
	    
	    // sem formatação
	    //Gson gson = builder.create();
	    
		return gson;
	}
	
	@GET
	@Path("beneficiarios")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response getBeneficiariosXML(@Context HttpHeaders httpHeaders, @HeaderParam("authorization") String authString) throws IOException, JAXBException {
		
		if(! validaApiKeyEHeader(httpHeaders)) {
			return getResponseComStatusNaoAutorizado();
			
		}
		
		if(! validaBasicAuth(authString)) {
			return getResponseComStatusNaoAutorizado();
			
		}
	    
		Marshaller jaxbMarshaller = criaJAXBContext(ContentArcelor.class);
		
		ContentArcelor contentArcelor = new ContentArcelor();
		
		StringWriter stringWriter = new StringWriter();
		jaxbMarshaller.marshal(contentArcelor, stringWriter);

	    String result = stringWriter.toString();
	    
        return Response
                .status(Status.OK)
                .entity(result)
                .build();
        
        
	}


	private Marshaller criaJAXBContext(Class<?> classe) throws JAXBException, PropertyException {
		JAXBContext jaxbContext = JAXBContext.newInstance(classe);
		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		// formatação
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		return jaxbMarshaller;
	}

	private boolean validaBasicAuth(String authString) {
		
		if(authString != null) {
			String decodedAuth = "";
			
	        String[] authParts = authString.split("\\s+");
	        
	        String authInfo = authParts[1];
	        
	        byte[] bytes = null;
	        bytes = Base64.getDecoder().decode(authInfo);
	        decodedAuth = new String(bytes);
	        
	        System.out.println("In service: " + decodedAuth);
	        
	        String[] dados = decodedAuth.split(":");
	        
	        String usuario = dados[0];
	        String senha = dados[1];
	        
	        if(!usuario.equals("meuUsuario") || !senha.equals("minhaSenha")) {
	        	return false;
	        }
		}
		
		return true;
	}


	private boolean validaApiKeyEHeader(HttpHeaders httpHeaders) {
		
		if(httpHeaders != null) {
			for (Entry<String, List<String>> entry : httpHeaders.getRequestHeaders().entrySet()) {
				
				String key = entry.getKey();
				String value = entry.getValue().get(0);
				
				if(key.equals("key")) {
					if(!value.toString().equals("myKey")) {
			 
						return false;
					}
				}
				
				if(key.equals("value")) {
					if(!value.toString().equals("myValue")) {
						
						return false;
						
					}
				}
				
				if(key.equals("token")) {
					if(!value.toString().equals("ece40d50-b22b-4b33-a728-343be730d85e")) {
						
						return false;
						
					}
				}
				
			}
		
		}
		
		return true;
	}
	
	@GET
	@Path("clienteTeste")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCliente(@Context HttpHeaders httpHeaders, @HeaderParam("authorization") String authString) throws IOException {
		
		if(httpHeaders != null) {
			for (Entry<String, List<String>> entry : httpHeaders.getRequestHeaders().entrySet()) {
				
				String key = entry.getKey();
				String value = entry.getValue().get(0);
				
				if(key.equals("key")) {
					if(!value.toString().equals("myKey")) {
			 
						return getResponseComStatusNaoAutorizado();
					}
				}
				
				if(key.equals("value")) {
					if(!value.toString().equals("myValue")) {
						
						return getResponseComStatusNaoAutorizado();
						
					}
				}
				
				if(key.equals("token")) {
					if(!value.toString().equals("ece40d50-b22b-4b33-a728-343be730d85e")) {
						
						return getResponseComStatusNaoAutorizado();
						
					}
				}
				
			}
		}
		
		if(authString != null) {
			String decodedAuth = "";
			
	        String[] authParts = authString.split("\\s+");
	        
	        String authInfo = authParts[1];
	        
	        byte[] bytes = null;
	        bytes = Base64.getDecoder().decode(authInfo);
	        decodedAuth = new String(bytes);
	        
	        System.out.println("In service: " + decodedAuth);
	        
	        String[] dados = decodedAuth.split(":");
	        
	        String usuario = dados[0];
	        String senha = dados[1];
	        
	        if(!usuario.equals("meuUsuario!") || !senha.equals("minhaSenha!")) {
	        	return getResponseComStatusNaoAutorizado();
	        }
		}

		Cliente cliente = new Cliente();
		
		cliente.setNome("Anderson Piotto");
		cliente.setIdade(34);
		cliente.setEmail("piottok10@gmail.com");
		
		String  json = null;
		try {
			
			json = getJsonTo(cliente);

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.status(Response.Status.OK).entity(json).build();

	}
	
	private Response getResponseComStatusNaoAutorizado(){
		
        return Response
                .status(Status.UNAUTHORIZED)
                .build();
	}
	
	@XmlRootElement
	public static class Cliente{
		
		private int cdContrato;
		private int cdPlano;
		private String nome;
		private int idade;
		private String email;
		private boolean vivo = true;
		private double fortuna;
		
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public int getIdade() {
			return idade;
		}
		public void setIdade(int idade) {
			this.idade = idade;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		public int getCdContrato() {
			return cdContrato;
		}
		public void setCdContrato(int cdContrato) {
			this.cdContrato = cdContrato;
		}
		public int getCdPlano() {
			return cdPlano;
		}
		public void setCdPlano(int cdPlano) {
			this.cdPlano = cdPlano;
		}
		
		public boolean isVivo() {
			return vivo;
		}
		public void setVivo(boolean vivo) {
			this.vivo = vivo;
		}
		public double getFortuna() {
			return fortuna;
		}
		public void setFortuna(double fortuna) {
			this.fortuna = fortuna;
		}
		@Override
		public String toString() {
			
			return getNome() + " " + getIdade() + " " + getEmail();
		}
	}
	
	@XmlRootElement
	public static class ContentArcelor{
		
		public ContentArcelor() {
			
			for (int i = 0; i < 3; i++) {
				Beneficiario beneficiario = new Beneficiario();
				beneficiarios.add(beneficiario);
			}
		}
		
		private List<Beneficiario> beneficiarios = new ArrayList<Beneficiario>();
		private boolean status = true;
		private String observacao = "msg de observação";
		
		
		public List<Beneficiario> getBeneficiarios() {
			return beneficiarios;
		}
		public void setBeneficiarios(List<Beneficiario> beneficiarios) {
			this.beneficiarios = beneficiarios;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public String getObservacao() {
			return observacao;
		}
		public void setObservacao(String observacao) {
			this.observacao = observacao;
		}
		
		
	}
	
	@XmlRootElement
	public static class Beneficiario{
		
		private int cdPlano = 15;
		private String matricula = "010130596";
		private String carteira = "00101305960101";
		private String nome = "Anderson Piotto";
		private String cpf = "35461848826";
		private int tipo = 1; // 1-TITULAR | 2-DEPENDENTE | 3-PROCURADOR DEPENDENTE
		private int grauParentesco = 1; //1- TITULAR | 2- MÃE | 3- PAI | 4- FILHO | 5- CONJUGE | 6- COMPANHEIRO
		private String dataNascimento = "12/03/1985"; //PADRÃO DD/MM/AAAA
		private String sexo = "M"; //M ou F (M-MASCULINO ou F-FEMININO)
		private int limite = 100; //Exs.: 100.00 | 101.02 | 350.70
		private int operacao = 1; // 1-Inclusão 2-Alteração (somente de dados cadastrais, não altera plano.) 3-Bloqueio Definitivo (Exclusão) //4-Bloqueio Temporário 5-Desbloqueio de Clientes com Bloqueio Temporário
		
		public int getCdPlano() {
			return cdPlano;
		}
		public void setCdPlano(int cdPlano) {
			this.cdPlano = cdPlano;
		}
		public String getMatricula() {
			return matricula;
		}
		public void setMatricula(String matricula) {
			this.matricula = matricula;
		}
		public String getCarteira() {
			return carteira;
		}
		public void setCarteira(String carteira) {
			this.carteira = carteira;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getCpf() {
			return cpf;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
		public int getTipo() {
			return tipo;
		}
		public void setTipo(int tipo) {
			this.tipo = tipo;
		}
		public int getGrauParentesco() {
			return grauParentesco;
		}
		public void setGrauParentesco(int grauParentesco) {
			this.grauParentesco = grauParentesco;
		}
		public String getDataNascimento() {
			return dataNascimento;
		}
		public void setDataNascimento(String dataNascimento) {
			this.dataNascimento = dataNascimento;
		}
		public String getSexo() {
			return sexo;
		}
		public void setSexo(String sexo) {
			this.sexo = sexo;
		}
		public int getLimite() {
			return limite;
		}
		public void setLimite(int limite) {
			this.limite = limite;
		}
		public int getOperacao() {
			return operacao;
		}
		public void setOperacao(int operacao) {
			this.operacao = operacao;
		}
	}
}
