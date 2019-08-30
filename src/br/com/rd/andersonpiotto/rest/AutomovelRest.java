package br.com.rd.andersonpiotto.rest;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.TextNode;
import org.codehaus.jackson.node.ValueNode;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentCollectionConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedSetConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernateProxyConverter;
import com.thoughtworks.xstream.hibernate.mapper.HibernateMapper;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.AbstractJsonWriter.Type;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import br.com.rd.andersonpiotto.connection.ConnectionFactory;
import br.com.rd.andersonpiotto.dto.AutomovelDTO;
import br.com.rd.andersonpiotto.dto.MapperObjectToObjectUtils;
import br.com.rd.andersonpiotto.model.Automovel;
import br.com.rd.andersonpiotto.model.Marca;
import br.com.rd.andersonpiotto.model.Modelo;
import br.com.rd.andersonpiotto.model.Roda;
import br.com.rd.andersonpiotto.test.Teste;

@Path("/automovel")
public class AutomovelRest {

	@GET
	@Path("getAutomovelPorId/{idAutomovel}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAutomovelPorId(@PathParam("idAutomovel") String idAutomovel, @Context UriInfo ui) throws IOException {
		
		/*
		 * System.out.println(ui.getPath()); System.out.println(ui.getAbsolutePath());
		 * System.out.println(ui.getAbsolutePathBuilder());
		 * System.out.println(ui.getBaseUri());
		 * System.out.println(ui.getBaseUriBuilder());
		 * System.out.println(ui.getMatchedResources());
		 * System.out.println(ui.getMatchedURIs());
		 * System.out.println(ui.getRequestUri());
		 * System.out.println(ui.getRequestUriBuilder());
		 */
		
		
		MultivaluedMap<String, String> pathParameters = ui.getPathParameters();
		
		for (Entry<String, List<String>> amp : pathParameters.entrySet()) {
			System.out.println(amp.getKey() + " " + amp.getValue());
		}
		
		 final String path;
		 final String path2;
		 
		System.out.println("No metodo");
		criaAutomovel();

		Automovel automovel = null;

		try {

			automovel = buscaPorId(Integer.parseInt(idAutomovel));

		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.status(Response.Status.OK).entity(automovel).build();

	}

	/*
	 * @GET
	 * 
	 * @Path("getAutomovelPorId2/{idAutomovel}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * getAutomovelPorId2(@PathParam("idAutomovel") String idAutomovel) {
	 * System.out.println("No metodo"); criaAutomovel();
	 * 
	 * Automovel automovel = null; AutomovelDTO automovelDTO = null;
	 * 
	 * try {
	 * 
	 * automovel = buscaPorId(Integer.parseInt(idAutomovel)); automovelDTO = new
	 * AutomovelDTO();
	 * 
	 * MapperObjectToObjectUtils.objetoToObjectDTO(automovel, automovelDTO);
	 * 
	 * } catch (Exception e) { return
	 * Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	 * }
	 * 
	 * return Response.status(Response.Status.OK).entity(automovelDTO).build();
	 * 
	 * }
	 */

	/*
	 * @GET
	 * 
	 * @Path("getAutomovelPorId2/{idAutomovel}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * getAutomovelPorId2(@PathParam("idAutomovel") String idAutomovel) {
	 * System.out.println("No metodo"); criaAutomovel();
	 * 
	 * Automovel automovel = null; //AutomovelDTO automovelDTO = null;
	 * 
	 * GsonBuilder builder = new GsonBuilder ();
	 * builder.excludeFieldsWithoutExposeAnnotation (); Gson gson = builder.create
	 * ();
	 * 
	 * 
	 * GsonBuilder gson = new GsonBuilder(); //
	 * .registerTypeHierarchyAdapter(ITypeHierarchyAdapterGson.class, new //
	 * MyTypeAdapterGson<ITypeHierarchyAdapterGson>()) //
	 * .disableInnerClassSerialization () // .setExclusionStrategies(new
	 * DomainExclusionStrategy())
	 * 
	 * new
	 * GraphAdapterBuilder().addType(Automovel.class).addType(Roda.class).addType(
	 * Modelo.class).addType(Marca.class).registerOn(gson);
	 * 
	 * Gson create = gson.setPrettyPrinting().create();
	 * 
	 * String json = null;
	 * 
	 * try {
	 * 
	 * automovel = buscaPorId(Integer.parseInt(idAutomovel)); // automovelDTO = new
	 * AutomovelDTO();
	 * 
	 * // MapperObjectToObjectUtils.objetoToObjectDTO(automovel, automovelDTO);
	 * 
	 * json = create.toJson(automovel);
	 * 
	 * }catch( Exception e) { return
	 * Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	 * }
	 * 
	 * // return Response.status(Response.Status.OK).entity(automovelDTO).build();
	 * return Response.status(Response.Status.OK).entity(json).build();
	 * 
	 * }
	 */

	
	/*
	@GET
	@Path("getAutomovelPorId3/{idAutomovel}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAutomovelPorId3(@PathParam("idAutomovel") String idAutomovel) throws JAXBException {
		Map<String, Object> properties = new HashMap<String, Object>();
       // properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
       // properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, true);
       // properties.put(JAXBContextProperties.JSON_VALUE_WRAPPER, "$");
        JAXBContext jc = JAXBContext.newInstance(new Class[] {Automovel.class}, properties);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        StringWriter sw = new StringWriter();
        String result = null;
        
		System.out.println("No metodo");
		criaAutomovel();

		Automovel automovel = null;
		
		try {
			automovel = buscaPorId(Integer.parseInt(idAutomovel));
			
			marshaller.marshal(automovel, sw);

		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.status(Response.Status.OK).entity(sw.toString()).build();

	}*/
	
	@GET
	@Path("getAutomovelPorId/{idAutomovel}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAutomovelPorId(@PathParam("idAutomovel") String idAutomovel) {
		
		criaAutomovel();

		Automovel automovel = null;
		String jsonString = null;
		
		try {

			automovel = buscaPorId(Integer.parseInt(idAutomovel));
			
			jsonString = JsonUtil.getJsonTo(automovel);

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.status(Response.Status.OK).entity(jsonString).build();

	}
	
	@GET
	@Path("getTesteDesconsideraNull")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTesteDesconsideraNull() {

		String jsonString = null;
		
		try {
			
			Teste t = new Teste();
			
			t.setName1(null);
			t.setName2("");
			
			//t.getList1().add("item 1");
			//t.getList1().add("item 2");
			t.getList1().add("item 1");
			t.getList1().add("item 2");
			
			//t.getList2().add("item 3");
			//t.getList2().add("item 4");
			
			
			jsonString = JsonUtil.getJsonTo(t);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.status(Response.Status.OK).entity(jsonString).build();

	}
	

	@GET
	@Path("getRodaPorId/{idRoda}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRodaPorId(@PathParam("idRoda") String idRoda) {
		System.out.println("No metodo");
		criaAutomovel();
		Roda roda = buscaRodaPorId(Integer.parseInt(idRoda));
		try {

		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.status(Response.Status.OK).entity(roda).build();

	}

	private void criaAutomovel() {
		EntityManager em = ConnectionFactory.getEntityManager();

		em.getTransaction().begin();

		Automovel auto = new Automovel();
		auto.setNome("MERIVA");

		Modelo modelo = new Modelo();
		modelo.setAnoModelo(1985);
		modelo.setNome("Popular");

		Marca marca = new Marca();
		marca.setNome("CHEVROLET");

		Roda roda1 = new Roda();
		roda1.setNome("Cromada");

		Roda roda2 = new Roda();
		roda2.setNome("Cromada 2");

		// amarrando as entidades
		auto.setModelo(modelo);
		modelo.addAutomovel(auto);
		modelo.setMarca(marca);
		marca.addModelo(modelo);
		auto.addRoda(roda1);
		roda1.setAutomovel(auto);
		auto.addRoda(roda2);
		roda2.setAutomovel(auto);

		em.persist(auto);

		em.getTransaction().commit();
	}

	private Automovel buscaPorId(int idAutomovel) {
		EntityManager em = ConnectionFactory.getEntityManager();

		TypedQuery<Automovel> query = em.createQuery("select a from Automovel a where a.idCar = :param",
				Automovel.class);
		query.setParameter("param", idAutomovel);

		return query.getSingleResult();

	}

	private Roda buscaRodaPorId(int idRoda) {
		EntityManager em = ConnectionFactory.getEntityManager();

		TypedQuery<Roda> query = em.createQuery("select r from Roda r where r.id = :param", Roda.class);
		query.setParameter("param", idRoda);

		return query.getSingleResult();

	}
	
	private String jsonString ="{\r\n" + 
			"    \"br.com.raiadrogasil.bluesky.entity.cliente.Cliente\": {\r\n" + 
			"        \"@id\": \"1\",\r\n" + 
			"        \"idCliente\": 18117,\r\n" + 
			"        \"nmCliente\": \"WILLIAN WALLACE DE SOUZA\",\r\n" + 
			"        \"nrCpf\": 0,\r\n" + 
			"        \"nrIdentCliente\": \"500\",\r\n" + 
			"        \"cdOperadorCadastro\": 31903,\r\n" + 
			"        \"dtCadastro\": {\r\n" + 
			"            \"@id\": \"2\",\r\n" + 
			"            \"$\": \"2015-03-27 13:52:22.0 UTC\"\r\n" + 
			"        },\r\n" + 
			"        \"tipoClienteBloqueio\": {\r\n" + 
			"            \"@id\": \"3\",\r\n" + 
			"            \"cdTpBloqueio\": 1,\r\n" + 
			"            \"dsTpBloqueio\": \"ATIVO\"\r\n" + 
			"        },\r\n" + 
			"        \"clienteCartoes\": [\r\n" + 
			"            {\r\n" + 
			"                \"@id\": \"5\",\r\n" + 
			"                \"cdCartao\": 11057,\r\n" + 
			"                \"cdOperadorAlteracao\": 0,\r\n" + 
			"                \"cdOperadorCadastro\": 31903,\r\n" + 
			"                \"dtCadastro\": {\r\n" + 
			"                    \"@id\": \"6\",\r\n" + 
			"                    \"$\": \"2015-03-27 03:00:00.0 UTC\"\r\n" + 
			"                },\r\n" + 
			"                \"dtIniVigencia\": {\r\n" + 
			"                    \"@id\": \"7\",\r\n" + 
			"                    \"$\": \"2015-03-27 03:00:00.0 UTC\"\r\n" + 
			"                },\r\n" + 
			"                \"flCartaoTitular\": true,\r\n" + 
			"                \"nrVia\": 1,\r\n" + 
			"                \"cliente\": {\r\n" + 
			"                    \"@reference\": \"1\"\r\n" + 
			"                },\r\n" + 
			"                \"clienteCartaoSituacao\": {\r\n" + 
			"                    \"@id\": \"8\",\r\n" + 
			"                    \"cdSituacao\": 1,\r\n" + 
			"                    \"dsSituacao\": \"ATIVO\",\r\n" + 
			"                    \"clienteCartoes\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"11\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 2,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"15\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_SITUACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CLIENTE_CARTAO_SITUACAO.CD_SITUACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@id\": \"16\",\r\n" + 
			"                                                                \"name\": \"TB_BF_CLIENTE_CARTAO_SITUACAO\",\r\n" + 
			"                                                                \"tableQualifier\": \"\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_CLIENTE_CARTAO_SITUACAO\",\r\n" + 
			"                                                                \"uniqueConstraints\": [],\r\n" + 
			"                                                                \"useDelimiters\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 0,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"18\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DS_SITUACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CLIENTE_CARTAO_SITUACAO.DS_SITUACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"16\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                            \"sqlType\": 12,\r\n" + 
			"                                                            \"index\": 1,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 2,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        \"ATIVO\"\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        2\r\n" + 
			"                                    ],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"clienteCartoes\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ]\r\n" + 
			"                },\r\n" + 
			"                \"clienteCartaoMotivo\": {\r\n" + 
			"                    \"@id\": \"21\",\r\n" + 
			"                    \"cdMotivo\": 5,\r\n" + 
			"                    \"dsMotivo\": \"OUTRO\",\r\n" + 
			"                    \"clienteCartoes\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"24\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 2,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"28\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_MOTIVO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CLIENTE_CARTAO_MOTIVO.CD_MOTIVO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@id\": \"29\",\r\n" + 
			"                                                                \"name\": \"TB_BF_CLIENTE_CARTAO_MOTIVO\",\r\n" + 
			"                                                                \"tableQualifier\": \"\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_CLIENTE_CARTAO_MOTIVO\",\r\n" + 
			"                                                                \"uniqueConstraints\": [],\r\n" + 
			"                                                                \"useDelimiters\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 0,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"31\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DS_MOTIVO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CLIENTE_CARTAO_MOTIVO.DS_MOTIVO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"29\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                            \"sqlType\": 12,\r\n" + 
			"                                                            \"index\": 1,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 2,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        5,\r\n" + 
			"                                                        \"OUTRO\"\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        2\r\n" + 
			"                                    ],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"clienteCartoes\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ]\r\n" + 
			"                }\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"dependentes\": [],\r\n" + 
			"        \"clienteConta\": {\r\n" + 
			"            \"@id\": \"35\",\r\n" + 
			"            \"idCliente\": 18117,\r\n" + 
			"            \"cdOperadorCadastro\": 31903,\r\n" + 
			"            \"dtCadastro\": {\r\n" + 
			"                \"@id\": \"36\",\r\n" + 
			"                \"$\": \"2015-03-27 03:00:00.0 UTC\"\r\n" + 
			"            },\r\n" + 
			"            \"vlLimite\": 100,\r\n" + 
			"            \"vlSublimite\": 0,\r\n" + 
			"            \"vlLimiteAdicinal\": 0,\r\n" + 
			"            \"cliente\": {\r\n" + 
			"                \"@reference\": \"1\"\r\n" + 
			"            },\r\n" + 
			"            \"plano\": {\r\n" + 
			"                \"@id\": \"37\",\r\n" + 
			"                \"pk\": {\r\n" + 
			"                    \"@id\": \"38\",\r\n" + 
			"                    \"cdContrato\": 10130,\r\n" + 
			"                    \"cdPlano\": 3847\r\n" + 
			"                },\r\n" + 
			"                \"cdPlanoMigra\": 0,\r\n" + 
			"                \"cdTpBloqueio\": 1,\r\n" + 
			"                \"tipoPlanoPagto\": {\r\n" + 
			"                    \"@id\": \"39\",\r\n" + 
			"                    \"cdTpPagamento\": 3,\r\n" + 
			"                    \"dsTpPagamento\": \"DEBITO EM FOLHA E A VISTA\",\r\n" + 
			"                    \"planos\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"42\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 2,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"46\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_PAGAMENTO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_TP_PLANO_PAGAMENTO.CD_TP_PAGAMENTO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@id\": \"47\",\r\n" + 
			"                                                                \"name\": \"TB_BF_TP_PLANO_PAGAMENTO\",\r\n" + 
			"                                                                \"tableQualifier\": \"\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_PLANO_PAGAMENTO\",\r\n" + 
			"                                                                \"uniqueConstraints\": [],\r\n" + 
			"                                                                \"useDelimiters\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 0,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"49\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DS_TP_PAGAMENTO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_TP_PLANO_PAGAMENTO.DS_TP_PAGAMENTO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"47\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                            \"sqlType\": 12,\r\n" + 
			"                                                            \"index\": 1,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 2,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        3,\r\n" + 
			"                                                        \"DEBITO EM FOLHA E A VISTA\"\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        2\r\n" + 
			"                                    ],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"planos\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ]\r\n" + 
			"                },\r\n" + 
			"                \"dtIniVigencia\": {\r\n" + 
			"                    \"@id\": \"52\",\r\n" + 
			"                    \"$\": \"2009-08-03 03:00:00.0 UTC\"\r\n" + 
			"                },\r\n" + 
			"                \"flExigeListaEspecProd\": false,\r\n" + 
			"                \"flSubsidio\": false,\r\n" + 
			"                \"flUniversoAmplo\": true,\r\n" + 
			"                \"flVigenciaIdenticaContrato\": true,\r\n" + 
			"                \"nmPlano\": \"PLANO BASICO\",\r\n" + 
			"                \"qtDiasAvisoTermino\": 0,\r\n" + 
			"                \"vlLimiteMaximo\": 1500,\r\n" + 
			"                \"vlLimiteMinimo\": 1,\r\n" + 
			"                \"flVincNomeReceita\": false,\r\n" + 
			"                \"flExigeReceita\": false,\r\n" + 
			"                \"dtCadastro\": {\r\n" + 
			"                    \"@id\": \"53\",\r\n" + 
			"                    \"$\": \"2017-08-17 21:30:44.0 UTC\"\r\n" + 
			"                },\r\n" + 
			"                \"cdOperadorCadastro\": 31903,\r\n" + 
			"                \"flProvisorio\": false,\r\n" + 
			"                \"flAplicarDescontoContrato\": true,\r\n" + 
			"                \"flBloqueiaVendaVista\": false,\r\n" + 
			"                \"flNaoUtilizaLimite\": false,\r\n" + 
			"                \"contrato\": {\r\n" + 
			"                    \"@id\": \"54\",\r\n" + 
			"                    \"cdContrato\": 10130,\r\n" + 
			"                    \"cdTpIdentClienteVenda\": 1,\r\n" + 
			"                    \"flAgrupaFatura\": false,\r\n" + 
			"                    \"flEmiteCartaoDepPrincipal\": false,\r\n" + 
			"                    \"flEmiteCartaoDependente\": false,\r\n" + 
			"                    \"flEmiteCartaoTitular\": true,\r\n" + 
			"                    \"flVigenIndeterminada\": true,\r\n" + 
			"                    \"qtMesesVigencia\": 0,\r\n" + 
			"                    \"dtAssinatura\": {\r\n" + 
			"                        \"@id\": \"55\",\r\n" + 
			"                        \"$\": \"2009-08-03 03:00:00.0 UTC\"\r\n" + 
			"                    },\r\n" + 
			"                    \"dtAtivacao\": {\r\n" + 
			"                        \"@id\": \"56\",\r\n" + 
			"                        \"$\": \"2017-08-17 21:30:44.0 UTC\"\r\n" + 
			"                    },\r\n" + 
			"                    \"dtInicioVigencia\": {\r\n" + 
			"                        \"@id\": \"57\",\r\n" + 
			"                        \"$\": \"2009-08-03 03:00:00.0 UTC\"\r\n" + 
			"                    },\r\n" + 
			"                    \"cdOperadorGestor\": 3221,\r\n" + 
			"                    \"cdOperadorImplantacao\": 3481,\r\n" + 
			"                    \"cdOperadorProspeccao\": 3441,\r\n" + 
			"                    \"canalVendas\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"60\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 37,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"64\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": true,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_CONTRATO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_CONTRATO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@id\": \"65\",\r\n" + 
			"                                                                \"name\": \"TB_BF_CONTRATO\",\r\n" + 
			"                                                                \"tableQualifier\": \"\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_CONTRATO\",\r\n" + 
			"                                                                \"uniqueConstraints\": [],\r\n" + 
			"                                                                \"useDelimiters\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 0,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"67\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_EMITE_CARTAO_DEPENDENTE\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.FL_EMITE_CARTAO_DEPENDENTE\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 1,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"68\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_INICIO_VIGEN_VERSAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_INICIO_VIGEN_VERSAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 2,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"69\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_PROSPECCAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_PROSPECCAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 3,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"70\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_CRIA_CONTA_DEPENDENTE\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.FL_CRIA_CONTA_DEPENDENTE\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 4,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"71\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_FIM_VIGENCIA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_FIM_VIGENCIA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 5,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"72\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_ATIVACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_ATIVACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 6,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"73\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_PROSPECCAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_OPERADOR_PROSPECCAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 7,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"74\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_GESTOR\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_OPERADOR_GESTOR\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 8,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"75\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_INICIO_VIGENCIA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_INICIO_VIGENCIA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 9,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"76\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 10,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"77\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 9,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"QT_MESES_VIGENCIA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.QT_MESES_VIGENCIA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 11,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"78\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_ATIVACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_OPERADOR_ATIVACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 12,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"79\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_ULT_ALTERACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_ULT_ALTERACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 13,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"80\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_EMITE_CARTAO_TITULAR\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.FL_EMITE_CARTAO_TITULAR\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 14,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"81\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_CADASTRO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_CADASTRO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 15,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"82\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_VIGEN_INDETERMINADA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.FL_VIGEN_INDETERMINADA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 12,\r\n" + 
			"                                                            \"index\": 16,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"83\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_PRIMEIRO_APROVADOR\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_OPERADOR_PRIMEIRO_APROVADOR\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 17,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"84\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_BLOQUEIO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_BLOQUEIO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 18,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"85\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_ULT_VALIDACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_ULT_VALIDACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 19,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"86\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_AGRUPA_FATURA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.FL_AGRUPA_FATURA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 20,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"87\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_ASSINATURA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.DT_ASSINATURA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 21,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"88\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CDS_SALES_FORCE\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CDS_SALES_FORCE\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                            \"sqlType\": 12,\r\n" + 
			"                                                            \"index\": 22,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"89\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_BLOQUEIO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_OPERADOR_BLOQUEIO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 23,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"90\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_IDENT_CLIENTE_VENDA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_TP_IDENT_CLIENTE_VENDA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 24,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"91\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 25,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"92\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_EMITE_CARTAO_DEP_PRINCIPAL\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.FL_EMITE_CARTAO_DEP_PRINCIPAL\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 26,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"93\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_IMPLANTACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_OPERADOR_IMPLANTACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 27,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"94\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_SIT_CONTRATO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_SIT_CONTRATO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 28,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"95\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_CONTRATO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_TP_CONTRATO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 29,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"96\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_ORIGEM\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_TP_ORIGEM\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 30,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"97\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_BLOQUEIO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_TP_BLOQUEIO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 31,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"98\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_VENDA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_TP_VENDA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 32,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"99\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_ORIGEM_CONTRATO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_ORIGEM_CONTRATO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 33,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"100\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_CONTRATO_ESPECIF\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_TP_CONTRATO_ESPECIF\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 34,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"101\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_PRODUTO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_TP_PRODUTO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 35,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"102\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_NEGOCIO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTRATO.CD_TP_NEGOCIO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"65\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 36,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 37,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        10130,\r\n" + 
			"                                                        0,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        0,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"105\",\r\n" + 
			"                                                            \"$\": \"2017-08-17 21:30:44\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        3441,\r\n" + 
			"                                                        3221,\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"106\",\r\n" + 
			"                                                            \"$\": \"2009-08-03\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        0,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"107\",\r\n" + 
			"                                                            \"$\": \"2017-08-17 21:30:44\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"1\",\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        0,\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"108\",\r\n" + 
			"                                                            \"$\": \"2009-08-03\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        31903,\r\n" + 
			"                                                        0,\r\n" + 
			"                                                        3481,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        37\r\n" + 
			"                                    ],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"canalVendas\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"contratoSituacao\": {\r\n" + 
			"                        \"@id\": \"109\",\r\n" + 
			"                        \"cdSitContrato\": 1,\r\n" + 
			"                        \"dsSitContrato\": \"ATIVO\"\r\n" + 
			"                    },\r\n" + 
			"                    \"contratoOrigem\": {\r\n" + 
			"                        \"@id\": \"110\",\r\n" + 
			"                        \"cdOrigemContrato\": 1,\r\n" + 
			"                        \"dsOrigemContrato\": \"RAIA DROGASIL\"\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoContratoBloqueio\": {\r\n" + 
			"                        \"@id\": \"111\",\r\n" + 
			"                        \"cdTpBloqueio\": 1,\r\n" + 
			"                        \"dsTpBloqueio\": \"SEM BLOQUEIO\"\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoContrato\": {\r\n" + 
			"                        \"@id\": \"112\",\r\n" + 
			"                        \"cdTpContrato\": 1,\r\n" + 
			"                        \"dsTpContrato\": \"CONVENIO NORMAL\"\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoOrigemVenda\": {\r\n" + 
			"                        \"@id\": \"113\",\r\n" + 
			"                        \"cdTpOrigem\": 1,\r\n" + 
			"                        \"dsTpOrigem\": \"MRP\"\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoVenda\": {\r\n" + 
			"                        \"@id\": \"114\",\r\n" + 
			"                        \"cdTpVenda\": 1,\r\n" + 
			"                        \"dsTpVenda\": \"VENDAS CORPORATIVAS DIRETAS\"\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoProduto\": {\r\n" + 
			"                        \"@id\": \"115\",\r\n" + 
			"                        \"cdTpProduto\": 1,\r\n" + 
			"                        \"dsTpProduto\": \"RAIA BENEFICIO FARMACIA\"\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoNegocio\": {\r\n" + 
			"                        \"@id\": \"116\",\r\n" + 
			"                        \"cdTpNegocio\": 1,\r\n" + 
			"                        \"dsTpNegocio\": \"VENDAS CORPORATIVAS\"\r\n" + 
			"                    },\r\n" + 
			"                    \"contratoAcesso\": {\r\n" + 
			"                        \"@id\": \"117\",\r\n" + 
			"                        \"cdContrato\": 10130,\r\n" + 
			"                        \"cdTpAutenticacaoVendaSec\": 2,\r\n" + 
			"                        \"flCapturaCrm\": false,\r\n" + 
			"                        \"flCapturaReceita\": false,\r\n" + 
			"                        \"flEmiteCartao\": true,\r\n" + 
			"                        \"cdOperadorCadastro\": 31903,\r\n" + 
			"                        \"dtCadastro\": {\r\n" + 
			"                            \"@id\": \"118\",\r\n" + 
			"                            \"$\": \"2017-08-17 21:30:44.0 UTC\"\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoCartao\": {\r\n" + 
			"                            \"@id\": \"119\",\r\n" + 
			"                            \"cdTpCartao\": 3,\r\n" + 
			"                            \"dsTpCartao\": \"LARANJA PERSONALIZADO\",\r\n" + 
			"                            \"tipoAtendimento\": {\r\n" + 
			"                                \"@id\": \"120\",\r\n" + 
			"                                \"cdTpAtendimento\": 1,\r\n" + 
			"                                \"dsTpAtendimento\": \"CLIENTE DIRETO NO AUTORIZADOR\"\r\n" + 
			"                            },\r\n" + 
			"                            \"contratoAcessos\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"123\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 3,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"127\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_CARTAO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_CARTAO.CD_TP_CARTAO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"128\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_TP_CARTAO\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_TP_CARTAO\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"130\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_TP_CARTAO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_CARTAO.DS_TP_CARTAO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"128\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"131\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_ATENDIMENTO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_CARTAO.CD_TP_ATENDIMENTO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"128\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 2,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 3,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                3,\r\n" + 
			"                                                                \"LARANJA PERSONALIZADO\",\r\n" + 
			"                                                                1\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                3\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"contratoAcessos\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoElegibilidadeCliente\": {\r\n" + 
			"                            \"@id\": \"134\",\r\n" + 
			"                            \"cdTpElegCliente\": 5,\r\n" + 
			"                            \"dsTpElegCliente\": \"NAO INFORMADO\",\r\n" + 
			"                            \"contratoAcessos\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"137\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"141\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_ELEG_CLIENTE\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ELEGIBILIDADE_CLIENTE.CD_TP_ELEG_CLIENTE\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"142\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_TP_ELEGIBILIDADE_CLIENTE\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_TP_ELEGIBILIDADE_CLIENTE\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"144\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_TP_ELEG_CLIENTE\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ELEGIBILIDADE_CLIENTE.DS_TP_ELEG_CLIENTE\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"142\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                5,\r\n" + 
			"                                                                \"NAO INFORMADO\"\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                2\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"contratoAcessos\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoEntregaCartao\": {\r\n" + 
			"                            \"@id\": \"147\",\r\n" + 
			"                            \"cdTpEntregaCartao\": 3,\r\n" + 
			"                            \"dsTpEntregaCartao\": \"ENDERECO MATRIZ\",\r\n" + 
			"                            \"flEmpresa\": true,\r\n" + 
			"                            \"contratoAcessos\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"150\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 3,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"154\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_ENTREGA_CARTAO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ENTREGA_CARTAO.CD_TP_ENTREGA_CARTAO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"155\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_TP_ENTREGA_CARTAO\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_TP_ENTREGA_CARTAO\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"157\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"FL_EMPRESA\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ENTREGA_CARTAO.FL_EMPRESA\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"155\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"158\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_TP_ENTREGA_CARTAO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ENTREGA_CARTAO.DS_TP_ENTREGA_CARTAO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"155\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 2,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 3,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                3,\r\n" + 
			"                                                                1,\r\n" + 
			"                                                                \"ENDERECO MATRIZ\"\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                3\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"contratoAcessos\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoEmissaoCartao\": {\r\n" + 
			"                            \"@id\": \"161\",\r\n" + 
			"                            \"cdTpCartaoDependente\": 3,\r\n" + 
			"                            \"dsTpCartaoDependente\": \"NAO\",\r\n" + 
			"                            \"contratoAcessos\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"164\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"168\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_CARTAO_DEPENDENTE\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_EMISSAO_CARTAO.CD_TP_CARTAO_DEPENDENTE\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"169\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_TP_EMISSAO_CARTAO\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_TP_EMISSAO_CARTAO\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"171\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_TP_CARTAO_DEPENDENTE\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_EMISSAO_CARTAO.DS_TP_CARTAO_DEPENDENTE\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"169\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                3,\r\n" + 
			"                                                                \"NAO\"\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                2\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"contratoAcessos\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoAutentVenda\": {\r\n" + 
			"                            \"@id\": \"174\",\r\n" + 
			"                            \"cdTpAutenticacaoVenda\": 1,\r\n" + 
			"                            \"dsTpAutenticacaoVenda\": \"BIOMETRIA\"\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoAtendimento\": {\r\n" + 
			"                            \"@reference\": \"120\"\r\n" + 
			"                        },\r\n" + 
			"                        \"contrato\": {\r\n" + 
			"                            \"@reference\": \"54\"\r\n" + 
			"                        },\r\n" + 
			"                        \"flCadastroTc\": false,\r\n" + 
			"                        \"flDuplicaCpf\": false\r\n" + 
			"                    },\r\n" + 
			"                    \"contratoContatos\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"177\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"contratoContatos\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"contratoFinanceiro\": {\r\n" + 
			"                        \"@id\": \"178\",\r\n" + 
			"                        \"cdContrato\": 10130,\r\n" + 
			"                        \"cdOperadorCadastro\": 31903,\r\n" + 
			"                        \"dtCadastro\": {\r\n" + 
			"                            \"@id\": \"179\",\r\n" + 
			"                            \"$\": \"2017-08-17 03:00:00.0 UTC\"\r\n" + 
			"                        },\r\n" + 
			"                        \"flTarifa\": false,\r\n" + 
			"                        \"flTaxa\": false,\r\n" + 
			"                        \"pcJurosMora\": 1,\r\n" + 
			"                        \"pcMultaAtraso\": 1,\r\n" + 
			"                        \"qtMesesVigenciaMinima\": 3,\r\n" + 
			"                        \"qtReemissaoCartaoGratuita\": 1,\r\n" + 
			"                        \"vlCartaoPosCarencia\": 0,\r\n" + 
			"                        \"vlCorrecaoMonetaria\": 0,\r\n" + 
			"                        \"vlMultaRecisao\": 1,\r\n" + 
			"                        \"vlTarifaBancaria\": 0,\r\n" + 
			"                        \"vlTaxaAdministrativa\": 0,\r\n" + 
			"                        \"contrato\": {\r\n" + 
			"                            \"@reference\": \"54\"\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoTaxa\": {\r\n" + 
			"                            \"@id\": \"180\",\r\n" + 
			"                            \"cdTpTaxa\": 3,\r\n" + 
			"                            \"dsTpTaxa\": \"NAO SE APLICA\",\r\n" + 
			"                            \"tbBfContratoFinanceiros\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"183\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"187\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_TAXA\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_TAXA.CD_TP_TAXA\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"188\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_TP_TAXA\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_TP_TAXA\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"190\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_TP_TAXA\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_TAXA.DS_TP_TAXA\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"188\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                3,\r\n" + 
			"                                                                \"NAO SE APLICA\"\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                2\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"tbBfContratoFinanceiros\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoCorrecao\": {\r\n" + 
			"                            \"@id\": \"193\",\r\n" + 
			"                            \"cdTpCorrecao\": 2,\r\n" + 
			"                            \"dsTpCorrecao\": \"IGPM\",\r\n" + 
			"                            \"tbBfContratoFinanceiros\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"196\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"200\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_CORRECAO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_CORRECAO.CD_TP_CORRECAO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"201\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_TP_CORRECAO\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_TP_CORRECAO\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"203\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_TP_CORRECAO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_CORRECAO.DS_TP_CORRECAO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"201\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                2,\r\n" + 
			"                                                                \"IGPM\"\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                2\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"tbBfContratoFinanceiros\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoTarifa\": {\r\n" + 
			"                            \"@id\": \"206\",\r\n" + 
			"                            \"cdTpTarifa\": 3,\r\n" + 
			"                            \"dsTpTarifa\": \"NAO SE APLICA\",\r\n" + 
			"                            \"tbBfContratoFinanceiros\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"209\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"213\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_TARIFA\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_TARIFA.CD_TP_TARIFA\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"214\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_TP_TARIFA\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_TP_TARIFA\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"216\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_TP_TARIFA\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_TARIFA.DS_TP_TARIFA\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"214\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                3,\r\n" + 
			"                                                                \"NAO SE APLICA\"\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                2\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"tbBfContratoFinanceiros\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        }\r\n" + 
			"                    },\r\n" + 
			"                    \"contratoRedes\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"221\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"contratoRedes\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"empresaContratos\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"224\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"empresaContratos\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"faturaUnidades\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"227\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"faturaUnidades\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"planos\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"230\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"planos\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"politicaComercial\": {\r\n" + 
			"                        \"@id\": \"231\",\r\n" + 
			"                        \"cdContrato\": 10130,\r\n" + 
			"                        \"flOfertaVigenteFilial\": true,\r\n" + 
			"                        \"flListaProduto\": false,\r\n" + 
			"                        \"pcDescFinanceiro\": 0,\r\n" + 
			"                        \"pcDescMedNaoTarj\": 0,\r\n" + 
			"                        \"pcDescMedNaoTarjGenerico\": 15,\r\n" + 
			"                        \"pcDescMedTarjGenerico\": 15,\r\n" + 
			"                        \"pcDescMedTarjMarca\": 15,\r\n" + 
			"                        \"pcDescPerfOutros\": 0,\r\n" + 
			"                        \"dtCadastro\": {\r\n" + 
			"                            \"@id\": \"232\",\r\n" + 
			"                            \"$\": \"2017-08-17 21:30:44.0 UTC\"\r\n" + 
			"                        },\r\n" + 
			"                        \"cdOperadorCadastro\": 31903,\r\n" + 
			"                        \"contrato\": {\r\n" + 
			"                            \"@reference\": \"54\"\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoDescontoFinanceiro\": {\r\n" + 
			"                            \"@id\": \"233\",\r\n" + 
			"                            \"cdTipoDescontoFinanceiro\": 1,\r\n" + 
			"                            \"dsTipoDescontoFinanceiro\": \"FIXO\"\r\n" + 
			"                        }\r\n" + 
			"                    },\r\n" + 
			"                    \"dtCadastro\": {\r\n" + 
			"                        \"@id\": \"234\",\r\n" + 
			"                        \"$\": \"2017-08-17 21:30:44.0 UTC\"\r\n" + 
			"                    },\r\n" + 
			"                    \"cdOperadorCadastro\": 31903,\r\n" + 
			"                    \"contratoEspecifico\": {\r\n" + 
			"                        \"@id\": \"235\",\r\n" + 
			"                        \"cdTpContrato\": 1,\r\n" + 
			"                        \"dsTpContrato\": \"PADRAO\"\r\n" + 
			"                    },\r\n" + 
			"                    \"flCriaContaDependente\": false\r\n" + 
			"                },\r\n" + 
			"                \"clienteContas\": [\r\n" + 
			"                    {},\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"capacityIncrement\": 0,\r\n" + 
			"                            \"elementCount\": 0,\r\n" + 
			"                            \"elementData\": []\r\n" + 
			"                        }\r\n" + 
			"                    },\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"initialCapacity\": 10,\r\n" + 
			"                            \"isListOrderBrokenInDb\": false,\r\n" + 
			"                            \"isRegistered\": true,\r\n" + 
			"                            \"valueHolder\": {\r\n" + 
			"                                \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                \"@id\": \"238\",\r\n" + 
			"                                \"isInstantiated\": false,\r\n" + 
			"                                \"row\": [\r\n" + 
			"                                    [\r\n" + 
			"                                        {},\r\n" + 
			"                                        {\r\n" + 
			"                                            \"default\": {\r\n" + 
			"                                                \"capacityIncrement\": 0,\r\n" + 
			"                                                \"elementCount\": 32,\r\n" + 
			"                                                \"elementData\": [\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"242\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 4,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_TP_BLOQUEIO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_TP_BLOQUEIO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@id\": \"243\",\r\n" + 
			"                                                            \"name\": \"TB_BF_PLANO\",\r\n" + 
			"                                                            \"tableQualifier\": \"\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_PLANO\",\r\n" + 
			"                                                            \"uniqueConstraints\": [],\r\n" + 
			"                                                            \"useDelimiters\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 0,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"245\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_REUTILIZA_RECEITA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_REUTILIZA_RECEITA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 1,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"246\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 50,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"NM_PLANO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.NM_PLANO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                        \"sqlType\": 12,\r\n" + 
			"                                                        \"index\": 2,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"247\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"DT_FIM_VIGENCIA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.DT_FIM_VIGENCIA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                        \"sqlType\": 93,\r\n" + 
			"                                                        \"index\": 3,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"248\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_SUBSIDIO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_SUBSIDIO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 4,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"249\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 12,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 5,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"250\",\r\n" + 
			"                                                        \"scale\": 2,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 9,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"VL_LIMITE_MINIMO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.VL_LIMITE_MINIMO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 6,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"251\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_APLICAR_DESCONTO_CONTRATO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_APLICAR_DESCONTO_CONTRATO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 7,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"252\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 6,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_RECEITA_ACID_TRABALHO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_RECEITA_ACID_TRABALHO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 8,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"253\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 3,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"QT_DIAS_AVISO_TERMINO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.QT_DIAS_AVISO_TERMINO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 9,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"254\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"DT_ULT_ALTERACAO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.DT_ULT_ALTERACAO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                        \"sqlType\": 93,\r\n" + 
			"                                                        \"index\": 10,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"255\",\r\n" + 
			"                                                        \"scale\": 2,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 9,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"VL_LIMITE_MAXIMO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.VL_LIMITE_MAXIMO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 11,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"256\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 4,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"QT_DIAS_VALIDADE_RECEITA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.QT_DIAS_VALIDADE_RECEITA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 12,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"257\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_PROVISORIO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_PROVISORIO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 13,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"258\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_EXIGE_RECEITA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_EXIGE_RECEITA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 14,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"259\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"DT_CADASTRO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.DT_CADASTRO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                        \"sqlType\": 93,\r\n" + 
			"                                                        \"index\": 15,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"260\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 9,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_PLANO_MIGRA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_PLANO_MIGRA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 16,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"261\",\r\n" + 
			"                                                        \"scale\": 2,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 9,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"VL_EMISSAO_CARTAO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.VL_EMISSAO_CARTAO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 17,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"262\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_UNIVERSO_AMPLO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_UNIVERSO_AMPLO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 18,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"263\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_BLOQUEIA_VENDA_VISTA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_BLOQUEIA_VENDA_VISTA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 19,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"264\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_VIGENCIA_IDENTICA_CONTRATO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_VIGENCIA_IDENTICA_CONTRATO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 20,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"265\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_VINC_NOME_RECEITA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_VINC_NOME_RECEITA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 21,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"266\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 12,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 22,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"267\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_EXIGE_LISTA_ESPEC_PROD\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_EXIGE_LISTA_ESPEC_PROD\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 23,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"268\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"FL_NAO_UTILIZA_LIMITE \",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.FL_NAO_UTILIZA_LIMITE \",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 24,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"269\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"DT_INI_VIGENCIA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.DT_INI_VIGENCIA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                        \"sqlType\": 93,\r\n" + 
			"                                                        \"index\": 25,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"270\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_PLANO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_PLANO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 26,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"271\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": false,\r\n" + 
			"                                                        \"isInsertable\": false,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_CONTRATO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_CONTRATO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 27,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"272\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_TP_PAGAMENTO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_TP_PAGAMENTO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 28,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"273\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"NR_UNIDADE\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.NR_UNIDADE\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 29,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"274\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_TP_RETENCAO_RECEITA\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_TP_RETENCAO_RECEITA\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 30,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"275\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": false,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_PROCESSA_SALDO_NEGATIVO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_PLANO.CD_PROCESSA_SALDO_NEGATIVO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"243\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 31,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null\r\n" + 
			"                                                ]\r\n" + 
			"                                            }\r\n" + 
			"                                        },\r\n" + 
			"                                        {\r\n" + 
			"                                            \"default\": {}\r\n" + 
			"                                        }\r\n" + 
			"                                    ],\r\n" + 
			"                                    [\r\n" + 
			"                                        {},\r\n" + 
			"                                        {\r\n" + 
			"                                            \"default\": {\r\n" + 
			"                                                \"capacityIncrement\": 0,\r\n" + 
			"                                                \"elementCount\": 32,\r\n" + 
			"                                                \"elementData\": [\r\n" + 
			"                                                    1,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    \"PLANO BASICO\",\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    1,\r\n" + 
			"                                                    1,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    1500,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"278\",\r\n" + 
			"                                                        \"$\": \"2017-08-17 21:30:44\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    1,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    1,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    31903,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    0,\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"279\",\r\n" + 
			"                                                        \"$\": \"2009-08-03\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    3847,\r\n" + 
			"                                                    10130,\r\n" + 
			"                                                    3,\r\n" + 
			"                                                    2892,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    1\r\n" + 
			"                                                ]\r\n" + 
			"                                            }\r\n" + 
			"                                        },\r\n" + 
			"                                        {\r\n" + 
			"                                            \"default\": {}\r\n" + 
			"                                        }\r\n" + 
			"                                    ],\r\n" + 
			"                                    32\r\n" + 
			"                                ],\r\n" + 
			"                                \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                \"sourceAttributeName\": \"clienteContas\"\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"                ],\r\n" + 
			"                \"planoDescAplicaveis\": [\r\n" + 
			"                    {},\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"capacityIncrement\": 0,\r\n" + 
			"                            \"elementCount\": 0,\r\n" + 
			"                            \"elementData\": []\r\n" + 
			"                        }\r\n" + 
			"                    },\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"initialCapacity\": 10,\r\n" + 
			"                            \"isListOrderBrokenInDb\": false,\r\n" + 
			"                            \"isRegistered\": true,\r\n" + 
			"                            \"valueHolder\": {\r\n" + 
			"                                \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                \"@id\": \"282\",\r\n" + 
			"                                \"isInstantiated\": false,\r\n" + 
			"                                \"row\": [],\r\n" + 
			"                                \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                \"sourceAttributeName\": \"planoDescAplicaveis\"\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"                ],\r\n" + 
			"                \"planoMsgAutorizadores\": [\r\n" + 
			"                    {},\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"capacityIncrement\": 0,\r\n" + 
			"                            \"elementCount\": 0,\r\n" + 
			"                            \"elementData\": []\r\n" + 
			"                        }\r\n" + 
			"                    },\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"initialCapacity\": 10,\r\n" + 
			"                            \"isListOrderBrokenInDb\": false,\r\n" + 
			"                            \"isRegistered\": true,\r\n" + 
			"                            \"valueHolder\": {\r\n" + 
			"                                \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                \"@id\": \"285\",\r\n" + 
			"                                \"isInstantiated\": false,\r\n" + 
			"                                \"row\": [],\r\n" + 
			"                                \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                \"sourceAttributeName\": \"planoMsgAutorizadores\"\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"                ],\r\n" + 
			"                \"planoRegras\": [\r\n" + 
			"                    {},\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"capacityIncrement\": 0,\r\n" + 
			"                            \"elementCount\": 0,\r\n" + 
			"                            \"elementData\": []\r\n" + 
			"                        }\r\n" + 
			"                    },\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"initialCapacity\": 10,\r\n" + 
			"                            \"isListOrderBrokenInDb\": false,\r\n" + 
			"                            \"isRegistered\": true,\r\n" + 
			"                            \"valueHolder\": {\r\n" + 
			"                                \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                \"@id\": \"288\",\r\n" + 
			"                                \"isInstantiated\": false,\r\n" + 
			"                                \"row\": [],\r\n" + 
			"                                \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                \"sourceAttributeName\": \"planoRegras\"\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"                ],\r\n" + 
			"                \"faturaUnidade\": {\r\n" + 
			"                    \"@id\": \"289\",\r\n" + 
			"                    \"pk\": {\r\n" + 
			"                        \"@id\": \"290\",\r\n" + 
			"                        \"cdContrato\": 10130,\r\n" + 
			"                        \"nrUnidade\": 2892\r\n" + 
			"                    },\r\n" + 
			"                    \"cdOperadorCadastro\": 31903,\r\n" + 
			"                    \"dtCadastro\": {\r\n" + 
			"                        \"@id\": \"291\",\r\n" + 
			"                        \"$\": \"2017-08-17 21:30:44.0 UTC\"\r\n" + 
			"                    },\r\n" + 
			"                    \"flEnviaContraCupom\": false,\r\n" + 
			"                    \"flEnviaNotaDebito\": true,\r\n" + 
			"                    \"flEnviaReceituario\": false,\r\n" + 
			"                    \"flLayoutPadrao\": true,\r\n" + 
			"                    \"nmUnidade\": \"DAPI DIAGNOSTICO\",\r\n" + 
			"                    \"nrCnpjCobranca\": 76689835000596,\r\n" + 
			"                    \"nrCnpjFatura\": 76689835000596,\r\n" + 
			"                    \"nrDiaFechto1\": 22,\r\n" + 
			"                    \"qtDiasEnvioFatura\": 4,\r\n" + 
			"                    \"qtDiasPagtoAposFechto\": 15,\r\n" + 
			"                    \"nrCnpjPrincipal\": 76689835000596,\r\n" + 
			"                    \"tipoEntregaBoleto\": {\r\n" + 
			"                        \"@id\": \"292\",\r\n" + 
			"                        \"cdTpEntregaBoleto\": 1,\r\n" + 
			"                        \"dsTpEntregaBoleto\": \"Via Site\",\r\n" + 
			"                        \"tbBfFaturaUnidades\": [\r\n" + 
			"                            {},\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"capacityIncrement\": 0,\r\n" + 
			"                                    \"elementCount\": 0,\r\n" + 
			"                                    \"elementData\": []\r\n" + 
			"                                }\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"initialCapacity\": 10,\r\n" + 
			"                                    \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                    \"isRegistered\": true,\r\n" + 
			"                                    \"valueHolder\": {\r\n" + 
			"                                        \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                        \"@id\": \"295\",\r\n" + 
			"                                        \"isInstantiated\": false,\r\n" + 
			"                                        \"row\": [\r\n" + 
			"                                            [\r\n" + 
			"                                                {},\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {\r\n" + 
			"                                                        \"capacityIncrement\": 0,\r\n" + 
			"                                                        \"elementCount\": 2,\r\n" + 
			"                                                        \"elementData\": [\r\n" + 
			"                                                            {\r\n" + 
			"                                                                \"@id\": \"299\",\r\n" + 
			"                                                                \"scale\": 0,\r\n" + 
			"                                                                \"length\": 0,\r\n" + 
			"                                                                \"precision\": 0,\r\n" + 
			"                                                                \"isUnique\": false,\r\n" + 
			"                                                                \"isNullable\": true,\r\n" + 
			"                                                                \"isUpdatable\": true,\r\n" + 
			"                                                                \"isInsertable\": true,\r\n" + 
			"                                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                                \"name\": \"CD_TP_ENTREGA_BOLETO\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_ENTREGA_BOLETO.CD_TP_ENTREGA_BOLETO\",\r\n" + 
			"                                                                \"table\": {\r\n" + 
			"                                                                    \"@id\": \"300\",\r\n" + 
			"                                                                    \"name\": \"TB_BF_TP_ENTREGA_BOLETO\",\r\n" + 
			"                                                                    \"tableQualifier\": \"\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ENTREGA_BOLETO\",\r\n" + 
			"                                                                    \"uniqueConstraints\": [],\r\n" + 
			"                                                                    \"useDelimiters\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                \"sqlType\": 2,\r\n" + 
			"                                                                \"index\": 0,\r\n" + 
			"                                                                \"useDelimiters\": false,\r\n" + 
			"                                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            {\r\n" + 
			"                                                                \"@id\": \"302\",\r\n" + 
			"                                                                \"scale\": 0,\r\n" + 
			"                                                                \"length\": 0,\r\n" + 
			"                                                                \"precision\": 0,\r\n" + 
			"                                                                \"isUnique\": false,\r\n" + 
			"                                                                \"isNullable\": true,\r\n" + 
			"                                                                \"isUpdatable\": true,\r\n" + 
			"                                                                \"isInsertable\": true,\r\n" + 
			"                                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                                \"name\": \"DS_TP_ENTREGA_BOLETO\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_ENTREGA_BOLETO.DS_TP_ENTREGA_BOLETO\",\r\n" + 
			"                                                                \"table\": {\r\n" + 
			"                                                                    \"@reference\": \"300\"\r\n" + 
			"                                                                },\r\n" + 
			"                                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                \"sqlType\": 12,\r\n" + 
			"                                                                \"index\": 1,\r\n" + 
			"                                                                \"useDelimiters\": false,\r\n" + 
			"                                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null\r\n" + 
			"                                                        ]\r\n" + 
			"                                                    }\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {}\r\n" + 
			"                                                }\r\n" + 
			"                                            ],\r\n" + 
			"                                            [\r\n" + 
			"                                                {},\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {\r\n" + 
			"                                                        \"capacityIncrement\": 0,\r\n" + 
			"                                                        \"elementCount\": 2,\r\n" + 
			"                                                        \"elementData\": [\r\n" + 
			"                                                            1,\r\n" + 
			"                                                            \"Via Site\"\r\n" + 
			"                                                        ]\r\n" + 
			"                                                    }\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {}\r\n" + 
			"                                                }\r\n" + 
			"                                            ],\r\n" + 
			"                                            2\r\n" + 
			"                                        ],\r\n" + 
			"                                        \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                        \"sourceAttributeName\": \"tbBfFaturaUnidades\"\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        ]\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoFechto\": {\r\n" + 
			"                        \"@id\": \"305\",\r\n" + 
			"                        \"cdTpFechto\": 1,\r\n" + 
			"                        \"dsTpFechto\": \"MENSAL\",\r\n" + 
			"                        \"tbBfFaturaUnidades\": [\r\n" + 
			"                            {},\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"capacityIncrement\": 0,\r\n" + 
			"                                    \"elementCount\": 0,\r\n" + 
			"                                    \"elementData\": []\r\n" + 
			"                                }\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"initialCapacity\": 10,\r\n" + 
			"                                    \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                    \"isRegistered\": true,\r\n" + 
			"                                    \"valueHolder\": {\r\n" + 
			"                                        \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                        \"@id\": \"308\",\r\n" + 
			"                                        \"isInstantiated\": false,\r\n" + 
			"                                        \"row\": [\r\n" + 
			"                                            [\r\n" + 
			"                                                {},\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {\r\n" + 
			"                                                        \"capacityIncrement\": 0,\r\n" + 
			"                                                        \"elementCount\": 2,\r\n" + 
			"                                                        \"elementData\": [\r\n" + 
			"                                                            {\r\n" + 
			"                                                                \"@id\": \"312\",\r\n" + 
			"                                                                \"scale\": 0,\r\n" + 
			"                                                                \"length\": 0,\r\n" + 
			"                                                                \"precision\": 0,\r\n" + 
			"                                                                \"isUnique\": false,\r\n" + 
			"                                                                \"isNullable\": true,\r\n" + 
			"                                                                \"isUpdatable\": true,\r\n" + 
			"                                                                \"isInsertable\": true,\r\n" + 
			"                                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                                \"name\": \"CD_TP_FECHTO\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_FECHTO.CD_TP_FECHTO\",\r\n" + 
			"                                                                \"table\": {\r\n" + 
			"                                                                    \"@id\": \"313\",\r\n" + 
			"                                                                    \"name\": \"TB_BF_TP_FECHTO\",\r\n" + 
			"                                                                    \"tableQualifier\": \"\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_FECHTO\",\r\n" + 
			"                                                                    \"uniqueConstraints\": [],\r\n" + 
			"                                                                    \"useDelimiters\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                \"sqlType\": 2,\r\n" + 
			"                                                                \"index\": 0,\r\n" + 
			"                                                                \"useDelimiters\": false,\r\n" + 
			"                                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            {\r\n" + 
			"                                                                \"@id\": \"315\",\r\n" + 
			"                                                                \"scale\": 0,\r\n" + 
			"                                                                \"length\": 0,\r\n" + 
			"                                                                \"precision\": 0,\r\n" + 
			"                                                                \"isUnique\": false,\r\n" + 
			"                                                                \"isNullable\": true,\r\n" + 
			"                                                                \"isUpdatable\": true,\r\n" + 
			"                                                                \"isInsertable\": true,\r\n" + 
			"                                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                                \"name\": \"DS_TP_FECHTO\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_FECHTO.DS_TP_FECHTO\",\r\n" + 
			"                                                                \"table\": {\r\n" + 
			"                                                                    \"@reference\": \"313\"\r\n" + 
			"                                                                },\r\n" + 
			"                                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                \"sqlType\": 12,\r\n" + 
			"                                                                \"index\": 1,\r\n" + 
			"                                                                \"useDelimiters\": false,\r\n" + 
			"                                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null\r\n" + 
			"                                                        ]\r\n" + 
			"                                                    }\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {}\r\n" + 
			"                                                }\r\n" + 
			"                                            ],\r\n" + 
			"                                            [\r\n" + 
			"                                                {},\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {\r\n" + 
			"                                                        \"capacityIncrement\": 0,\r\n" + 
			"                                                        \"elementCount\": 2,\r\n" + 
			"                                                        \"elementData\": [\r\n" + 
			"                                                            1,\r\n" + 
			"                                                            \"MENSAL\"\r\n" + 
			"                                                        ]\r\n" + 
			"                                                    }\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {}\r\n" + 
			"                                                }\r\n" + 
			"                                            ],\r\n" + 
			"                                            2\r\n" + 
			"                                        ],\r\n" + 
			"                                        \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                        \"sourceAttributeName\": \"tbBfFaturaUnidades\"\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        ]\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoPrazoPgto\": {\r\n" + 
			"                        \"@id\": \"318\",\r\n" + 
			"                        \"cdTpPrazoPgto\": 2,\r\n" + 
			"                        \"dsTpPrazoPgto\": \"PRÓXIMO MES\",\r\n" + 
			"                        \"tbBfFaturaUnidades\": [\r\n" + 
			"                            {},\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"capacityIncrement\": 0,\r\n" + 
			"                                    \"elementCount\": 0,\r\n" + 
			"                                    \"elementData\": []\r\n" + 
			"                                }\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"initialCapacity\": 10,\r\n" + 
			"                                    \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                    \"isRegistered\": true,\r\n" + 
			"                                    \"valueHolder\": {\r\n" + 
			"                                        \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                        \"@id\": \"321\",\r\n" + 
			"                                        \"isInstantiated\": false,\r\n" + 
			"                                        \"row\": [\r\n" + 
			"                                            [\r\n" + 
			"                                                {},\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {\r\n" + 
			"                                                        \"capacityIncrement\": 0,\r\n" + 
			"                                                        \"elementCount\": 2,\r\n" + 
			"                                                        \"elementData\": [\r\n" + 
			"                                                            {\r\n" + 
			"                                                                \"@id\": \"325\",\r\n" + 
			"                                                                \"scale\": 0,\r\n" + 
			"                                                                \"length\": 0,\r\n" + 
			"                                                                \"precision\": 0,\r\n" + 
			"                                                                \"isUnique\": false,\r\n" + 
			"                                                                \"isNullable\": true,\r\n" + 
			"                                                                \"isUpdatable\": true,\r\n" + 
			"                                                                \"isInsertable\": true,\r\n" + 
			"                                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                                \"name\": \"CD_TP_PRAZO_PGTO\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_PRAZO_PGTO.CD_TP_PRAZO_PGTO\",\r\n" + 
			"                                                                \"table\": {\r\n" + 
			"                                                                    \"@id\": \"326\",\r\n" + 
			"                                                                    \"name\": \"TB_BF_TP_PRAZO_PGTO\",\r\n" + 
			"                                                                    \"tableQualifier\": \"\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_PRAZO_PGTO\",\r\n" + 
			"                                                                    \"uniqueConstraints\": [],\r\n" + 
			"                                                                    \"useDelimiters\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                \"sqlType\": 2,\r\n" + 
			"                                                                \"index\": 0,\r\n" + 
			"                                                                \"useDelimiters\": false,\r\n" + 
			"                                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            {\r\n" + 
			"                                                                \"@id\": \"328\",\r\n" + 
			"                                                                \"scale\": 0,\r\n" + 
			"                                                                \"length\": 0,\r\n" + 
			"                                                                \"precision\": 0,\r\n" + 
			"                                                                \"isUnique\": false,\r\n" + 
			"                                                                \"isNullable\": true,\r\n" + 
			"                                                                \"isUpdatable\": true,\r\n" + 
			"                                                                \"isInsertable\": true,\r\n" + 
			"                                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                                \"name\": \"DS_TP_PRAZO_PGTO\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_PRAZO_PGTO.DS_TP_PRAZO_PGTO\",\r\n" + 
			"                                                                \"table\": {\r\n" + 
			"                                                                    \"@reference\": \"326\"\r\n" + 
			"                                                                },\r\n" + 
			"                                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                \"sqlType\": 12,\r\n" + 
			"                                                                \"index\": 1,\r\n" + 
			"                                                                \"useDelimiters\": false,\r\n" + 
			"                                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null\r\n" + 
			"                                                        ]\r\n" + 
			"                                                    }\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {}\r\n" + 
			"                                                }\r\n" + 
			"                                            ],\r\n" + 
			"                                            [\r\n" + 
			"                                                {},\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {\r\n" + 
			"                                                        \"capacityIncrement\": 0,\r\n" + 
			"                                                        \"elementCount\": 2,\r\n" + 
			"                                                        \"elementData\": [\r\n" + 
			"                                                            2,\r\n" + 
			"                                                            \"PRÓXIMO MES\"\r\n" + 
			"                                                        ]\r\n" + 
			"                                                    }\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {}\r\n" + 
			"                                                }\r\n" + 
			"                                            ],\r\n" + 
			"                                            2\r\n" + 
			"                                        ],\r\n" + 
			"                                        \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                        \"sourceAttributeName\": \"tbBfFaturaUnidades\"\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        ]\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoDia\": {\r\n" + 
			"                        \"@id\": \"331\",\r\n" + 
			"                        \"cdTpDia\": 3,\r\n" + 
			"                        \"dsTpDia\": \"QTDE DE DIAS CORRIDOS APOS DIA DE CORTE\",\r\n" + 
			"                        \"tbBfFaturaUnidades\": [\r\n" + 
			"                            {},\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"capacityIncrement\": 0,\r\n" + 
			"                                    \"elementCount\": 0,\r\n" + 
			"                                    \"elementData\": []\r\n" + 
			"                                }\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"initialCapacity\": 10,\r\n" + 
			"                                    \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                    \"isRegistered\": true,\r\n" + 
			"                                    \"valueHolder\": {\r\n" + 
			"                                        \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                        \"@id\": \"334\",\r\n" + 
			"                                        \"isInstantiated\": false,\r\n" + 
			"                                        \"row\": [\r\n" + 
			"                                            [\r\n" + 
			"                                                {},\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {\r\n" + 
			"                                                        \"capacityIncrement\": 0,\r\n" + 
			"                                                        \"elementCount\": 2,\r\n" + 
			"                                                        \"elementData\": [\r\n" + 
			"                                                            {\r\n" + 
			"                                                                \"@id\": \"338\",\r\n" + 
			"                                                                \"scale\": 0,\r\n" + 
			"                                                                \"length\": 0,\r\n" + 
			"                                                                \"precision\": 0,\r\n" + 
			"                                                                \"isUnique\": false,\r\n" + 
			"                                                                \"isNullable\": true,\r\n" + 
			"                                                                \"isUpdatable\": true,\r\n" + 
			"                                                                \"isInsertable\": true,\r\n" + 
			"                                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                                \"name\": \"CD_TP_DIA\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_DIA.CD_TP_DIA\",\r\n" + 
			"                                                                \"table\": {\r\n" + 
			"                                                                    \"@id\": \"339\",\r\n" + 
			"                                                                    \"name\": \"TB_BF_TP_DIA\",\r\n" + 
			"                                                                    \"tableQualifier\": \"\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_DIA\",\r\n" + 
			"                                                                    \"uniqueConstraints\": [],\r\n" + 
			"                                                                    \"useDelimiters\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                \"sqlType\": 2,\r\n" + 
			"                                                                \"index\": 0,\r\n" + 
			"                                                                \"useDelimiters\": false,\r\n" + 
			"                                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            {\r\n" + 
			"                                                                \"@id\": \"341\",\r\n" + 
			"                                                                \"scale\": 0,\r\n" + 
			"                                                                \"length\": 0,\r\n" + 
			"                                                                \"precision\": 0,\r\n" + 
			"                                                                \"isUnique\": false,\r\n" + 
			"                                                                \"isNullable\": true,\r\n" + 
			"                                                                \"isUpdatable\": true,\r\n" + 
			"                                                                \"isInsertable\": true,\r\n" + 
			"                                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                                \"name\": \"DS_TP_DIA\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_TP_DIA.DS_TP_DIA\",\r\n" + 
			"                                                                \"table\": {\r\n" + 
			"                                                                    \"@reference\": \"339\"\r\n" + 
			"                                                                },\r\n" + 
			"                                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                \"sqlType\": 12,\r\n" + 
			"                                                                \"index\": 1,\r\n" + 
			"                                                                \"useDelimiters\": false,\r\n" + 
			"                                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null,\r\n" + 
			"                                                            null\r\n" + 
			"                                                        ]\r\n" + 
			"                                                    }\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {}\r\n" + 
			"                                                }\r\n" + 
			"                                            ],\r\n" + 
			"                                            [\r\n" + 
			"                                                {},\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {\r\n" + 
			"                                                        \"capacityIncrement\": 0,\r\n" + 
			"                                                        \"elementCount\": 2,\r\n" + 
			"                                                        \"elementData\": [\r\n" + 
			"                                                            3,\r\n" + 
			"                                                            \"QTDE DE DIAS CORRIDOS APOS DIA DE CORTE\"\r\n" + 
			"                                                        ]\r\n" + 
			"                                                    }\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"default\": {}\r\n" + 
			"                                                }\r\n" + 
			"                                            ],\r\n" + 
			"                                            2\r\n" + 
			"                                        ],\r\n" + 
			"                                        \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                        \"sourceAttributeName\": \"tbBfFaturaUnidades\"\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        ]\r\n" + 
			"                    },\r\n" + 
			"                    \"contrato\": {\r\n" + 
			"                        \"@reference\": \"54\"\r\n" + 
			"                    },\r\n" + 
			"                    \"faturaUnidadeDocs\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"346\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 32,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"350\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_ENVIA_NOTA_DEBITO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.FL_ENVIA_NOTA_DEBITO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@id\": \"351\",\r\n" + 
			"                                                                \"name\": \"TB_BF_FATURA_UNIDADE\",\r\n" + 
			"                                                                \"tableQualifier\": \"\",\r\n" + 
			"                                                                \"qualifiedName\": \"TB_BF_FATURA_UNIDADE\",\r\n" + 
			"                                                                \"uniqueConstraints\": [],\r\n" + 
			"                                                                \"useDelimiters\": false\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 0,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"353\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_AGENCIA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_AGENCIA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 1,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"354\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 14,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_CNPJ_COBRANCA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_CNPJ_COBRANCA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 2,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"355\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_ENVIA_RECEITUARIO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.FL_ENVIA_RECEITUARIO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 3,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"356\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": true,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_CNPJ_PRINCIPAL\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_CNPJ_PRINCIPAL\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 4,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"357\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"QT_DIAS_PAGTO_APOS_FECHTO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.QT_DIAS_PAGTO_APOS_FECHTO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 5,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"358\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": true,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_UNIDADE\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_UNIDADE\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 6,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"359\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_DIA_FECHTO_4\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_DIA_FECHTO_4\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 7,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"360\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_DIA_FECHTO_3\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_DIA_FECHTO_3\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 8,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"361\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 9,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"362\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_DIA_FECHTO_2\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_DIA_FECHTO_2\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 10,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"363\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_DIA_VCTO_FATURA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_DIA_VCTO_FATURA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 11,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"364\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_DIA_FECHTO_1\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_DIA_FECHTO_1\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 12,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"365\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_LAYOUT_PADRAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.FL_LAYOUT_PADRAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 13,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"366\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"QT_DIAS_ENVIO_FATURA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.QT_DIAS_ENVIO_FATURA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 14,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"367\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"FL_ENVIA_CONTRA_CUPOM\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.FL_ENVIA_CONTRA_CUPOM\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 15,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"368\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_ULT_ALTERACAO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.DT_ULT_ALTERACAO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 16,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"369\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"DT_CADASTRO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.DT_CADASTRO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                            \"sqlType\": 93,\r\n" + 
			"                                                            \"index\": 17,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"370\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CDS_CONTA_CORRENTE\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CDS_CONTA_CORRENTE\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                            \"sqlType\": 12,\r\n" + 
			"                                                            \"index\": 18,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"371\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 14,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_CNPJ_FATURA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_CNPJ_FATURA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 19,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"372\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 12,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 20,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"373\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 2,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NR_DIA_EMISSAO_FATURA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NR_DIA_EMISSAO_FATURA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 21,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"374\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 50,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"NM_UNIDADE\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.NM_UNIDADE\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                            \"sqlType\": 12,\r\n" + 
			"                                                            \"index\": 22,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"375\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": true,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": false,\r\n" + 
			"                                                            \"isInsertable\": false,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_CONTRATO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_CONTRATO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 23,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"376\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_FECHTO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_TP_FECHTO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 24,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"377\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_DIA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_TP_DIA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 25,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"378\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_AGRUPA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_TP_AGRUPA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 26,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"379\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_DOC_RECEBIMENTO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_TP_DOC_RECEBIMENTO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 27,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"380\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_ENTREGA_BOLETO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_TP_ENTREGA_BOLETO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 28,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"381\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CDS_BANCO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CDS_BANCO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                            \"sqlType\": 12,\r\n" + 
			"                                                            \"index\": 29,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"382\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": false,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_TP_PRAZO_PGTO\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_TP_PRAZO_PGTO\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 30,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"383\",\r\n" + 
			"                                                            \"scale\": 0,\r\n" + 
			"                                                            \"length\": 0,\r\n" + 
			"                                                            \"precision\": 0,\r\n" + 
			"                                                            \"isUnique\": false,\r\n" + 
			"                                                            \"isNullable\": true,\r\n" + 
			"                                                            \"isUpdatable\": true,\r\n" + 
			"                                                            \"isInsertable\": true,\r\n" + 
			"                                                            \"columnDefinition\": \"\",\r\n" + 
			"                                                            \"name\": \"CD_EMPRESA\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_FATURA_UNIDADE.CD_EMPRESA\",\r\n" + 
			"                                                            \"table\": {\r\n" + 
			"                                                                \"@reference\": \"351\"\r\n" + 
			"                                                            },\r\n" + 
			"                                                            \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                            \"sqlType\": 2,\r\n" + 
			"                                                            \"index\": 31,\r\n" + 
			"                                                            \"useDelimiters\": false,\r\n" + 
			"                                                            \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        [\r\n" + 
			"                                            {},\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {\r\n" + 
			"                                                    \"capacityIncrement\": 0,\r\n" + 
			"                                                    \"elementCount\": 32,\r\n" + 
			"                                                    \"elementData\": [\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        76689835000596,\r\n" + 
			"                                                        0,\r\n" + 
			"                                                        76689835000596,\r\n" + 
			"                                                        15,\r\n" + 
			"                                                        2892,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        22,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        4,\r\n" + 
			"                                                        0,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        {\r\n" + 
			"                                                            \"@id\": \"386\",\r\n" + 
			"                                                            \"$\": \"2017-08-17 21:30:44\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        76689835000596,\r\n" + 
			"                                                        31903,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        \"DAPI DIAGNOSTICO\",\r\n" + 
			"                                                        10130,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        3,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        1,\r\n" + 
			"                                                        null,\r\n" + 
			"                                                        2,\r\n" + 
			"                                                        13028\r\n" + 
			"                                                    ]\r\n" + 
			"                                                }\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"default\": {}\r\n" + 
			"                                            }\r\n" + 
			"                                        ],\r\n" + 
			"                                        32\r\n" + 
			"                                    ],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"faturaUnidadeDocs\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"faturaCentroCustos\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"389\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"faturaCentroCustos\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"tipoFaturaAgrupa\": {\r\n" + 
			"                        \"@id\": \"390\",\r\n" + 
			"                        \"cdTpAgrupa\": 1,\r\n" + 
			"                        \"dsTpAgrupa\": \"CNPJ PRINCIPAL\"\r\n" + 
			"                    },\r\n" + 
			"                    \"planos\": [\r\n" + 
			"                        {},\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"capacityIncrement\": 0,\r\n" + 
			"                                \"elementCount\": 0,\r\n" + 
			"                                \"elementData\": []\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        {\r\n" + 
			"                            \"default\": {\r\n" + 
			"                                \"initialCapacity\": 10,\r\n" + 
			"                                \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                \"isRegistered\": true,\r\n" + 
			"                                \"valueHolder\": {\r\n" + 
			"                                    \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                    \"@id\": \"393\",\r\n" + 
			"                                    \"isInstantiated\": false,\r\n" + 
			"                                    \"row\": [],\r\n" + 
			"                                    \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                    \"sourceAttributeName\": \"planos\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    ],\r\n" + 
			"                    \"empresa\": {\r\n" + 
			"                        \"@id\": \"394\",\r\n" + 
			"                        \"cdEmpresa\": 13028,\r\n" + 
			"                        \"cdOperadorAlteracao\": 41,\r\n" + 
			"                        \"cdOperadorCadastro\": 3481,\r\n" + 
			"                        \"dtCadastro\": {\r\n" + 
			"                            \"@id\": \"395\",\r\n" + 
			"                            \"$\": \"2015-03-26 18:51:07.0 UTC\"\r\n" + 
			"                        },\r\n" + 
			"                        \"dtUltAlteracao\": {\r\n" + 
			"                            \"@id\": \"396\",\r\n" + 
			"                            \"$\": \"2017-08-17 21:28:54.0 UTC\"\r\n" + 
			"                        },\r\n" + 
			"                        \"flCnpjPrincipal\": true,\r\n" + 
			"                        \"nmFantasia\": \"DAPI DIAGNOSTICO A POR IMAGEM\",\r\n" + 
			"                        \"nmRazaoSocial\": \"LIGA DAS SENHORAS CATOLICAS DE CURITIBA\",\r\n" + 
			"                        \"nmSiteInstitucional\": \"WWW.DAPI.COM.BR\",\r\n" + 
			"                        \"nrCnpjEmpresa\": 76689835000596,\r\n" + 
			"                        \"nrInscrEstadual\": \"NA\",\r\n" + 
			"                        \"nrInscrMunicipal\": \"NA\",\r\n" + 
			"                        \"qtBeneficiarios\": 398,\r\n" + 
			"                        \"qtDependentes\": 0,\r\n" + 
			"                        \"qtTitular\": 398,\r\n" + 
			"                        \"empresaSituacao\": {\r\n" + 
			"                            \"@id\": \"397\",\r\n" + 
			"                            \"cdSitEmpresa\": 2,\r\n" + 
			"                            \"dsSitEmpresa\": \"ATIVA\",\r\n" + 
			"                            \"empresas\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"400\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"404\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_SIT_EMPRESA\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_EMPRESA_SITUACAO.CD_SIT_EMPRESA\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"405\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_EMPRESA_SITUACAO\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_EMPRESA_SITUACAO\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"407\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_SIT_EMPRESA\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_EMPRESA_SITUACAO.DS_SIT_EMPRESA\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"405\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                2,\r\n" + 
			"                                                                \"ATIVA\"\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                2\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"empresas\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"tipoAtividade\": {\r\n" + 
			"                            \"@id\": \"410\",\r\n" + 
			"                            \"cdTpAtividade\": 5,\r\n" + 
			"                            \"dsTpAtividade\": \"SERVICO\",\r\n" + 
			"                            \"cdOperadorCadastro\": 1,\r\n" + 
			"                            \"dtCadastro\": {\r\n" + 
			"                                \"@id\": \"411\",\r\n" + 
			"                                \"$\": \"1900-01-01 03:06:28.0 UTC\"\r\n" + 
			"                            },\r\n" + 
			"                            \"empresas\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"414\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 6,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"418\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 2,\r\n" + 
			"                                                                    \"isUnique\": true,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_TP_ATIVIDADE\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ATIVIDADE.CD_TP_ATIVIDADE\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"419\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_TP_ATIVIDADE\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_TP_ATIVIDADE\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"421\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DT_ULT_ALTERACAO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ATIVIDADE.DT_ULT_ALTERACAO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"419\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                                    \"sqlType\": 93,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"422\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_TP_ATIVIDADE\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ATIVIDADE.DS_TP_ATIVIDADE\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"419\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 2,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"423\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ATIVIDADE.CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"419\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 3,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"424\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DT_CADASTRO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ATIVIDADE.DT_CADASTRO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"419\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                                    \"sqlType\": 93,\r\n" + 
			"                                                                    \"index\": 4,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"425\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": false,\r\n" + 
			"                                                                    \"isNullable\": true,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_TP_ATIVIDADE.CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"419\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 5,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 6,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                5,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                \"SERVICO\",\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"428\",\r\n" + 
			"                                                                    \"$\": \"1900-01-01\"\r\n" + 
			"                                                                },\r\n" + 
			"                                                                1\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                6\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"empresas\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"planoSaude\": {\r\n" + 
			"                            \"@id\": \"429\",\r\n" + 
			"                            \"cdPlanoSaude\": 1,\r\n" + 
			"                            \"dsPlanoSaude\": \"NAO INFORMADO\",\r\n" + 
			"                            \"empresas\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 10,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                            \"@id\": \"432\",\r\n" + 
			"                                            \"isInstantiated\": false,\r\n" + 
			"                                            \"row\": [\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"436\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 0,\r\n" + 
			"                                                                    \"precision\": 5,\r\n" + 
			"                                                                    \"isUnique\": true,\r\n" + 
			"                                                                    \"isNullable\": false,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"CD_PLANO_SAUDE\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_PLANO_SAUDE.CD_PLANO_SAUDE\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@id\": \"437\",\r\n" + 
			"                                                                        \"name\": \"TB_BF_PLANO_SAUDE\",\r\n" + 
			"                                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                                        \"qualifiedName\": \"TB_BF_PLANO_SAUDE\",\r\n" + 
			"                                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                                        \"useDelimiters\": false\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                                    \"sqlType\": 2,\r\n" + 
			"                                                                    \"index\": 0,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                {\r\n" + 
			"                                                                    \"@id\": \"439\",\r\n" + 
			"                                                                    \"scale\": 0,\r\n" + 
			"                                                                    \"length\": 100,\r\n" + 
			"                                                                    \"precision\": 0,\r\n" + 
			"                                                                    \"isUnique\": true,\r\n" + 
			"                                                                    \"isNullable\": false,\r\n" + 
			"                                                                    \"isUpdatable\": true,\r\n" + 
			"                                                                    \"isInsertable\": true,\r\n" + 
			"                                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                                    \"name\": \"DS_PLANO_SAUDE\",\r\n" + 
			"                                                                    \"qualifiedName\": \"TB_BF_PLANO_SAUDE.DS_PLANO_SAUDE\",\r\n" + 
			"                                                                    \"table\": {\r\n" + 
			"                                                                        \"@reference\": \"437\"\r\n" + 
			"                                                                    },\r\n" + 
			"                                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                                    \"sqlType\": 12,\r\n" + 
			"                                                                    \"index\": 1,\r\n" + 
			"                                                                    \"useDelimiters\": false,\r\n" + 
			"                                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                                },\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null,\r\n" + 
			"                                                                null\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                [\r\n" + 
			"                                                    {},\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {\r\n" + 
			"                                                            \"capacityIncrement\": 0,\r\n" + 
			"                                                            \"elementCount\": 2,\r\n" + 
			"                                                            \"elementData\": [\r\n" + 
			"                                                                1,\r\n" + 
			"                                                                \"NAO INFORMADO\"\r\n" + 
			"                                                            ]\r\n" + 
			"                                                        }\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"default\": {}\r\n" + 
			"                                                    }\r\n" + 
			"                                                ],\r\n" + 
			"                                                2\r\n" + 
			"                                            ],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"sourceAttributeName\": \"empresas\"\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ]\r\n" + 
			"                        },\r\n" + 
			"                        \"empresaEnderecos\": [\r\n" + 
			"                            {\r\n" + 
			"                                \"@id\": \"443\",\r\n" + 
			"                                \"pk\": {\r\n" + 
			"                                    \"@id\": \"444\",\r\n" + 
			"                                    \"cdEmpresa\": 13028,\r\n" + 
			"                                    \"nrSeqEndereco\": 2\r\n" + 
			"                                },\r\n" + 
			"                                \"cdUf\": \"SP\",\r\n" + 
			"                                \"cdOperadorCadastro\": 3641,\r\n" + 
			"                                \"dsBairro\": \"VILA BUTANTA\",\r\n" + 
			"                                \"dsCidade\": \"SAO PAULO\",\r\n" + 
			"                                \"dsEndereco\": \"DROGASIL S.A.\",\r\n" + 
			"                                \"dtCadastro\": {\r\n" + 
			"                                    \"@id\": \"445\",\r\n" + 
			"                                    \"$\": \"2015-04-02 19:25:41.0 UTC\"\r\n" + 
			"                                },\r\n" + 
			"                                \"flAtivo\": false,\r\n" + 
			"                                \"cdCep\": \"05339900\",\r\n" + 
			"                                \"nrEndereco\": \"3097\",\r\n" + 
			"                                \"empresa\": {\r\n" + 
			"                                    \"@reference\": \"394\"\r\n" + 
			"                                },\r\n" + 
			"                                \"tipoEndereco\": {\r\n" + 
			"                                    \"@id\": \"446\",\r\n" + 
			"                                    \"cdTpEndereco\": 1,\r\n" + 
			"                                    \"dsTpEndereco\": \"SEDE\"\r\n" + 
			"                                }\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"@id\": \"447\",\r\n" + 
			"                                \"pk\": {\r\n" + 
			"                                    \"@id\": \"448\",\r\n" + 
			"                                    \"cdEmpresa\": 13028,\r\n" + 
			"                                    \"nrSeqEndereco\": 1\r\n" + 
			"                                },\r\n" + 
			"                                \"cdUf\": \"PR\",\r\n" + 
			"                                \"cdOperadorAlteracao\": 3641,\r\n" + 
			"                                \"cdOperadorCadastro\": 3481,\r\n" + 
			"                                \"dsBairro\": \"MERCES\",\r\n" + 
			"                                \"dsCidade\": \"CURITIBA\",\r\n" + 
			"                                \"dsEndereco\": \"RUA BRIGADEIRO FRANCO  ATE 13931394\",\r\n" + 
			"                                \"dtCadastro\": {\r\n" + 
			"                                    \"@id\": \"449\",\r\n" + 
			"                                    \"$\": \"2015-03-26 18:51:07.0 UTC\"\r\n" + 
			"                                },\r\n" + 
			"                                \"dtUltAlteracao\": {\r\n" + 
			"                                    \"@id\": \"450\",\r\n" + 
			"                                    \"$\": \"2015-04-02 19:27:11.0 UTC\"\r\n" + 
			"                                },\r\n" + 
			"                                \"flAtivo\": false,\r\n" + 
			"                                \"cdCep\": \"80430210\",\r\n" + 
			"                                \"nrEndereco\": \"122 285\",\r\n" + 
			"                                \"empresa\": {\r\n" + 
			"                                    \"@reference\": \"394\"\r\n" + 
			"                                },\r\n" + 
			"                                \"tipoEndereco\": {\r\n" + 
			"                                    \"@id\": \"451\",\r\n" + 
			"                                    \"cdTpEndereco\": 3,\r\n" + 
			"                                    \"dsTpEndereco\": \"COBRANCA\"\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        ]\r\n" + 
			"                    },\r\n" + 
			"                    \"tipoDocumentoRecebimento\": {\r\n" + 
			"                        \"@id\": \"452\",\r\n" + 
			"                        \"cdTpDocRecebimento\": 1,\r\n" + 
			"                        \"dsTpDocRecebimento\": \"BOLETO BANCÁRIO\"\r\n" + 
			"                    },\r\n" + 
			"                    \"nrUnidade\": 2892\r\n" + 
			"                },\r\n" + 
			"                \"tipoProcessaSaldoNegativo\": {\r\n" + 
			"                    \"@id\": \"453\",\r\n" + 
			"                    \"cdProcessaSaldoNegativo\": 1,\r\n" + 
			"                    \"dsProcessaSaldoNegativo\": \"FOLHA DE PAGAMENTO\"\r\n" + 
			"                },\r\n" + 
			"                \"flReutilizaReceita\": false\r\n" + 
			"            },\r\n" + 
			"            \"contaSituacao\": {\r\n" + 
			"                \"@id\": \"454\",\r\n" + 
			"                \"cdContaSituacao\": 1,\r\n" + 
			"                \"dsContaSituacao\": \"ATIVA\",\r\n" + 
			"                \"clienteContas\": [\r\n" + 
			"                    {},\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"capacityIncrement\": 0,\r\n" + 
			"                            \"elementCount\": 0,\r\n" + 
			"                            \"elementData\": []\r\n" + 
			"                        }\r\n" + 
			"                    },\r\n" + 
			"                    {\r\n" + 
			"                        \"default\": {\r\n" + 
			"                            \"initialCapacity\": 10,\r\n" + 
			"                            \"isListOrderBrokenInDb\": false,\r\n" + 
			"                            \"isRegistered\": true,\r\n" + 
			"                            \"valueHolder\": {\r\n" + 
			"                                \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                \"@id\": \"457\",\r\n" + 
			"                                \"isInstantiated\": false,\r\n" + 
			"                                \"row\": [\r\n" + 
			"                                    [\r\n" + 
			"                                        {},\r\n" + 
			"                                        {\r\n" + 
			"                                            \"default\": {\r\n" + 
			"                                                \"capacityIncrement\": 0,\r\n" + 
			"                                                \"elementCount\": 2,\r\n" + 
			"                                                \"elementData\": [\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"461\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 0,\r\n" + 
			"                                                        \"precision\": 2,\r\n" + 
			"                                                        \"isUnique\": true,\r\n" + 
			"                                                        \"isNullable\": false,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"CD_CONTA_SITUACAO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_CONTA_SITUACAO.CD_CONTA_SITUACAO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@id\": \"462\",\r\n" + 
			"                                                            \"name\": \"TB_BF_CONTA_SITUACAO\",\r\n" + 
			"                                                            \"tableQualifier\": \"\",\r\n" + 
			"                                                            \"qualifiedName\": \"TB_BF_CONTA_SITUACAO\",\r\n" + 
			"                                                            \"uniqueConstraints\": [],\r\n" + 
			"                                                            \"useDelimiters\": false\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                        \"sqlType\": 2,\r\n" + 
			"                                                        \"index\": 0,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    {\r\n" + 
			"                                                        \"@id\": \"464\",\r\n" + 
			"                                                        \"scale\": 0,\r\n" + 
			"                                                        \"length\": 20,\r\n" + 
			"                                                        \"precision\": 0,\r\n" + 
			"                                                        \"isUnique\": true,\r\n" + 
			"                                                        \"isNullable\": true,\r\n" + 
			"                                                        \"isUpdatable\": true,\r\n" + 
			"                                                        \"isInsertable\": true,\r\n" + 
			"                                                        \"columnDefinition\": \"\",\r\n" + 
			"                                                        \"name\": \"DS_CONTA_SITUACAO\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_CONTA_SITUACAO.DS_CONTA_SITUACAO\",\r\n" + 
			"                                                        \"table\": {\r\n" + 
			"                                                            \"@reference\": \"462\"\r\n" + 
			"                                                        },\r\n" + 
			"                                                        \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                        \"sqlType\": 12,\r\n" + 
			"                                                        \"index\": 1,\r\n" + 
			"                                                        \"useDelimiters\": false,\r\n" + 
			"                                                        \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null,\r\n" + 
			"                                                    null\r\n" + 
			"                                                ]\r\n" + 
			"                                            }\r\n" + 
			"                                        },\r\n" + 
			"                                        {\r\n" + 
			"                                            \"default\": {}\r\n" + 
			"                                        }\r\n" + 
			"                                    ],\r\n" + 
			"                                    [\r\n" + 
			"                                        {},\r\n" + 
			"                                        {\r\n" + 
			"                                            \"default\": {\r\n" + 
			"                                                \"capacityIncrement\": 0,\r\n" + 
			"                                                \"elementCount\": 2,\r\n" + 
			"                                                \"elementData\": [\r\n" + 
			"                                                    1,\r\n" + 
			"                                                    \"ATIVA\"\r\n" + 
			"                                                ]\r\n" + 
			"                                            }\r\n" + 
			"                                        },\r\n" + 
			"                                        {\r\n" + 
			"                                            \"default\": {}\r\n" + 
			"                                        }\r\n" + 
			"                                    ],\r\n" + 
			"                                    2\r\n" + 
			"                                ],\r\n" + 
			"                                \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                \"sourceAttributeName\": \"clienteContas\"\r\n" + 
			"                            }\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"                ]\r\n" + 
			"            },\r\n" + 
			"            \"clienteContaSaldos\": [\r\n" + 
			"                {},\r\n" + 
			"                {\r\n" + 
			"                    \"default\": {\r\n" + 
			"                        \"capacityIncrement\": 0,\r\n" + 
			"                        \"elementCount\": 0,\r\n" + 
			"                        \"elementData\": []\r\n" + 
			"                    }\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"default\": {\r\n" + 
			"                        \"initialCapacity\": 10,\r\n" + 
			"                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                        \"isRegistered\": true,\r\n" + 
			"                        \"delegate\": [],\r\n" + 
			"                        \"valueHolder\": {\r\n" + 
			"                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                            \"@id\": \"470\",\r\n" + 
			"                            \"value\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 0,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": true,\r\n" + 
			"                                        \"delegate\": [],\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.indirection.ValueHolder\",\r\n" + 
			"                                            \"@id\": \"473\",\r\n" + 
			"                                            \"value\": [],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"isNewlyWeavedValueHolder\": false\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ],\r\n" + 
			"                            \"isInstantiated\": true,\r\n" + 
			"                            \"row\": [\r\n" + 
			"                                [\r\n" + 
			"                                    {},\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {\r\n" + 
			"                                            \"capacityIncrement\": 0,\r\n" + 
			"                                            \"elementCount\": 14,\r\n" + 
			"                                            \"elementData\": [\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"477\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"DT_ULT_ALTERACAO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.DT_ULT_ALTERACAO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@id\": \"478\",\r\n" + 
			"                                                        \"name\": \"TB_BF_CLIENTE_CONTA\",\r\n" + 
			"                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_CLIENTE_CONTA\",\r\n" + 
			"                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                        \"useDelimiters\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                    \"sqlType\": 93,\r\n" + 
			"                                                    \"index\": 0,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"480\",\r\n" + 
			"                                                    \"scale\": 2,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 12,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"VL_LIMITE_ADICIONAL\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.VL_LIMITE_ADICIONAL\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 1,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"481\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"DT_CADASTRO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.DT_CADASTRO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                    \"sqlType\": 93,\r\n" + 
			"                                                    \"index\": 2,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"482\",\r\n" + 
			"                                                    \"scale\": 2,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 12,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"VL_LIMITE\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.VL_LIMITE\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 3,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"483\",\r\n" + 
			"                                                    \"scale\": 2,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 12,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"VL_SUBLIMITE\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.VL_SUBLIMITE\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 4,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"484\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": true,\r\n" + 
			"                                                    \"isNullable\": false,\r\n" + 
			"                                                    \"isUpdatable\": false,\r\n" + 
			"                                                    \"isInsertable\": false,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"ID_CLIENTE\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.ID_CLIENTE\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 5,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"485\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 50,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CDS_CUSTO_LOTACAO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.CDS_CUSTO_LOTACAO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                    \"sqlType\": 12,\r\n" + 
			"                                                    \"index\": 6,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"486\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"FL_INADIMPLENTE\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.FL_INADIMPLENTE\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.Boolean\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 7,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"487\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 12,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 8,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"488\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 12,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 9,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"489\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 9,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CD_CENTRO_CUSTO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.CD_CENTRO_CUSTO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 10,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"490\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": false,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CD_CONTRATO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.CD_CONTRATO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 11,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"491\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": false,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CD_PLANO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.CD_PLANO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 12,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"492\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": false,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CD_CONTA_SITUACAO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE_CONTA.CD_CONTA_SITUACAO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"478\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 13,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null\r\n" + 
			"                                            ]\r\n" + 
			"                                        }\r\n" + 
			"                                    },\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {}\r\n" + 
			"                                    }\r\n" + 
			"                                ],\r\n" + 
			"                                [\r\n" + 
			"                                    {},\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {\r\n" + 
			"                                            \"capacityIncrement\": 0,\r\n" + 
			"                                            \"elementCount\": 14,\r\n" + 
			"                                            \"elementData\": [\r\n" + 
			"                                                null,\r\n" + 
			"                                                0,\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"495\",\r\n" + 
			"                                                    \"$\": \"2015-03-27\"\r\n" + 
			"                                                },\r\n" + 
			"                                                100,\r\n" + 
			"                                                0,\r\n" + 
			"                                                18117,\r\n" + 
			"                                                null,\r\n" + 
			"                                                0,\r\n" + 
			"                                                null,\r\n" + 
			"                                                31903,\r\n" + 
			"                                                null,\r\n" + 
			"                                                10130,\r\n" + 
			"                                                3847,\r\n" + 
			"                                                1\r\n" + 
			"                                            ]\r\n" + 
			"                                        }\r\n" + 
			"                                    },\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {}\r\n" + 
			"                                    }\r\n" + 
			"                                ],\r\n" + 
			"                                14\r\n" + 
			"                            ],\r\n" + 
			"                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                            \"sourceAttributeName\": \"clienteContaSaldos\"\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"                }\r\n" + 
			"            ],\r\n" + 
			"            \"flInadimplente\": false\r\n" + 
			"        },\r\n" + 
			"        \"clienteEnderecos\": [],\r\n" + 
			"        \"tipoParentesco\": {\r\n" + 
			"            \"@id\": \"497\",\r\n" + 
			"            \"cdTpParentesco\": 1,\r\n" + 
			"            \"dsTpParentesco\": \"N/A\",\r\n" + 
			"            \"clientes\": [\r\n" + 
			"                {},\r\n" + 
			"                {\r\n" + 
			"                    \"default\": {\r\n" + 
			"                        \"capacityIncrement\": 0,\r\n" + 
			"                        \"elementCount\": 0,\r\n" + 
			"                        \"elementData\": []\r\n" + 
			"                    }\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"default\": {\r\n" + 
			"                        \"initialCapacity\": 10,\r\n" + 
			"                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                        \"isRegistered\": true,\r\n" + 
			"                        \"valueHolder\": {\r\n" + 
			"                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                            \"@id\": \"500\",\r\n" + 
			"                            \"isInstantiated\": false,\r\n" + 
			"                            \"row\": [\r\n" + 
			"                                [\r\n" + 
			"                                    {},\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {\r\n" + 
			"                                            \"capacityIncrement\": 0,\r\n" + 
			"                                            \"elementCount\": 2,\r\n" + 
			"                                            \"elementData\": [\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"504\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CD_TP_PARENTESCO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_TP_PARENTESCO.CD_TP_PARENTESCO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@id\": \"505\",\r\n" + 
			"                                                        \"name\": \"TB_BF_TP_PARENTESCO\",\r\n" + 
			"                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_TP_PARENTESCO\",\r\n" + 
			"                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                        \"useDelimiters\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 0,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"507\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"DS_TP_PARENTESCO\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_TP_PARENTESCO.DS_TP_PARENTESCO\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"505\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                    \"sqlType\": 12,\r\n" + 
			"                                                    \"index\": 1,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null\r\n" + 
			"                                            ]\r\n" + 
			"                                        }\r\n" + 
			"                                    },\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {}\r\n" + 
			"                                    }\r\n" + 
			"                                ],\r\n" + 
			"                                [\r\n" + 
			"                                    {},\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {\r\n" + 
			"                                            \"capacityIncrement\": 0,\r\n" + 
			"                                            \"elementCount\": 2,\r\n" + 
			"                                            \"elementData\": [\r\n" + 
			"                                                1,\r\n" + 
			"                                                \"N/A\"\r\n" + 
			"                                            ]\r\n" + 
			"                                        }\r\n" + 
			"                                    },\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {}\r\n" + 
			"                                    }\r\n" + 
			"                                ],\r\n" + 
			"                                2\r\n" + 
			"                            ],\r\n" + 
			"                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                            \"sourceAttributeName\": \"clientes\"\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"                }\r\n" + 
			"            ]\r\n" + 
			"        },\r\n" + 
			"        \"tipoTitularidade\": {\r\n" + 
			"            \"@id\": \"510\",\r\n" + 
			"            \"cdTpTitularidade\": 1,\r\n" + 
			"            \"dsTpTitularidade\": \"TITULAR\",\r\n" + 
			"            \"clientes\": [\r\n" + 
			"                {},\r\n" + 
			"                {\r\n" + 
			"                    \"default\": {\r\n" + 
			"                        \"capacityIncrement\": 0,\r\n" + 
			"                        \"elementCount\": 0,\r\n" + 
			"                        \"elementData\": []\r\n" + 
			"                    }\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"default\": {\r\n" + 
			"                        \"initialCapacity\": 10,\r\n" + 
			"                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                        \"isRegistered\": true,\r\n" + 
			"                        \"valueHolder\": {\r\n" + 
			"                            \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                            \"@id\": \"513\",\r\n" + 
			"                            \"isInstantiated\": false,\r\n" + 
			"                            \"row\": [\r\n" + 
			"                                [\r\n" + 
			"                                    {},\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {\r\n" + 
			"                                            \"capacityIncrement\": 0,\r\n" + 
			"                                            \"elementCount\": 2,\r\n" + 
			"                                            \"elementData\": [\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"517\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"CD_TP_TITULARIDADE\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_TP_TITULARIDADE.CD_TP_TITULARIDADE\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@id\": \"518\",\r\n" + 
			"                                                        \"name\": \"TB_BF_TP_TITULARIDADE\",\r\n" + 
			"                                                        \"tableQualifier\": \"\",\r\n" + 
			"                                                        \"qualifiedName\": \"TB_BF_TP_TITULARIDADE\",\r\n" + 
			"                                                        \"uniqueConstraints\": [],\r\n" + 
			"                                                        \"useDelimiters\": false\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                    \"sqlType\": 2,\r\n" + 
			"                                                    \"index\": 0,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                {\r\n" + 
			"                                                    \"@id\": \"520\",\r\n" + 
			"                                                    \"scale\": 0,\r\n" + 
			"                                                    \"length\": 0,\r\n" + 
			"                                                    \"precision\": 0,\r\n" + 
			"                                                    \"isUnique\": false,\r\n" + 
			"                                                    \"isNullable\": true,\r\n" + 
			"                                                    \"isUpdatable\": true,\r\n" + 
			"                                                    \"isInsertable\": true,\r\n" + 
			"                                                    \"columnDefinition\": \"\",\r\n" + 
			"                                                    \"name\": \"DS_TP_TITULARIDADE\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_TP_TITULARIDADE.DS_TP_TITULARIDADE\",\r\n" + 
			"                                                    \"table\": {\r\n" + 
			"                                                        \"@reference\": \"518\"\r\n" + 
			"                                                    },\r\n" + 
			"                                                    \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                    \"sqlType\": 12,\r\n" + 
			"                                                    \"index\": 1,\r\n" + 
			"                                                    \"useDelimiters\": false,\r\n" + 
			"                                                    \"useUpperCaseForComparisons\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null,\r\n" + 
			"                                                null\r\n" + 
			"                                            ]\r\n" + 
			"                                        }\r\n" + 
			"                                    },\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {}\r\n" + 
			"                                    }\r\n" + 
			"                                ],\r\n" + 
			"                                [\r\n" + 
			"                                    {},\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {\r\n" + 
			"                                            \"capacityIncrement\": 0,\r\n" + 
			"                                            \"elementCount\": 2,\r\n" + 
			"                                            \"elementData\": [\r\n" + 
			"                                                1,\r\n" + 
			"                                                \"TITULAR\"\r\n" + 
			"                                            ]\r\n" + 
			"                                        }\r\n" + 
			"                                    },\r\n" + 
			"                                    {\r\n" + 
			"                                        \"default\": {}\r\n" + 
			"                                    }\r\n" + 
			"                                ],\r\n" + 
			"                                2\r\n" + 
			"                            ],\r\n" + 
			"                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                            \"sourceAttributeName\": \"clientes\"\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"                }\r\n" + 
			"            ]\r\n" + 
			"        },\r\n" + 
			"        \"clienteTelefones\": [\r\n" + 
			"            {},\r\n" + 
			"            {\r\n" + 
			"                \"default\": {\r\n" + 
			"                    \"capacityIncrement\": 0,\r\n" + 
			"                    \"elementCount\": 0,\r\n" + 
			"                    \"elementData\": []\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"default\": {\r\n" + 
			"                    \"initialCapacity\": 10,\r\n" + 
			"                    \"isListOrderBrokenInDb\": false,\r\n" + 
			"                    \"isRegistered\": true,\r\n" + 
			"                    \"delegate\": [],\r\n" + 
			"                    \"valueHolder\": {\r\n" + 
			"                        \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                        \"@id\": \"526\",\r\n" + 
			"                        \"value\": [\r\n" + 
			"                            {},\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"capacityIncrement\": 0,\r\n" + 
			"                                    \"elementCount\": 0,\r\n" + 
			"                                    \"elementData\": []\r\n" + 
			"                                }\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"default\": {\r\n" + 
			"                                    \"initialCapacity\": 0,\r\n" + 
			"                                    \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                    \"isRegistered\": false,\r\n" + 
			"                                    \"delegate\": [],\r\n" + 
			"                                    \"valueHolder\": {\r\n" + 
			"                                        \"@class\": \"org.eclipse.persistence.indirection.ValueHolder\",\r\n" + 
			"                                        \"@id\": \"529\",\r\n" + 
			"                                        \"value\": [],\r\n" + 
			"                                        \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                        \"isNewlyWeavedValueHolder\": false\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            }\r\n" + 
			"                        ],\r\n" + 
			"                        \"isInstantiated\": true,\r\n" + 
			"                        \"row\": [\r\n" + 
			"                            [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 21,\r\n" + 
			"                                        \"elementData\": [\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"533\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"ID_CLIENTE\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.ID_CLIENTE\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@id\": \"534\",\r\n" + 
			"                                                    \"name\": \"TB_BF_CLIENTE\",\r\n" + 
			"                                                    \"tableQualifier\": \"\",\r\n" + 
			"                                                    \"qualifiedName\": \"TB_BF_CLIENTE\",\r\n" + 
			"                                                    \"uniqueConstraints\": [],\r\n" + 
			"                                                    \"useDelimiters\": false\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 0,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"536\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"CD_OPERADOR_ALTER_BLOQ\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.CD_OPERADOR_ALTER_BLOQ\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 1,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"537\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"NM_CLIENTE\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.NM_CLIENTE\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                \"sqlType\": 12,\r\n" + 
			"                                                \"index\": 2,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"538\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"NR_RG\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.NR_RG\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                \"sqlType\": 12,\r\n" + 
			"                                                \"index\": 3,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"539\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"NR_CPF\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.NR_CPF\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 4,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"540\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 1,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"FL_NOTIFICACOES_APP\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.FL_NOTIFICACOES_APP\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 5,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"541\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.CD_OPERADOR_ALTERACAO\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 6,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"542\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"DT_NASCIMENTO\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.DT_NASCIMENTO\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.sql.Date\",\r\n" + 
			"                                                \"sqlType\": 93,\r\n" + 
			"                                                \"index\": 7,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"543\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"DT_ULT_ALTERACAO\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.DT_ULT_ALTERACAO\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                \"sqlType\": 93,\r\n" + 
			"                                                \"index\": 8,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"544\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 1,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"FL_TERMO_USO_APP\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.FL_TERMO_USO_APP\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.Integer\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 9,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"545\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"DT_CADASTRO\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.DT_CADASTRO\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                \"sqlType\": 93,\r\n" + 
			"                                                \"index\": 10,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"546\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"DS_EMAIL\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.DS_EMAIL\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                \"sqlType\": 12,\r\n" + 
			"                                                \"index\": 11,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"547\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"DT_ALTERACAO_BLOQ\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.DT_ALTERACAO_BLOQ\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.sql.Timestamp\",\r\n" + 
			"                                                \"sqlType\": 93,\r\n" + 
			"                                                \"index\": 12,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"548\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"ID_CLIENTE_TITULAR\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.ID_CLIENTE_TITULAR\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 13,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"549\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"DS_SEXO\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.DS_SEXO\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                \"sqlType\": 12,\r\n" + 
			"                                                \"index\": 14,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"550\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.CD_OPERADOR_CADASTRO\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.math.BigDecimal\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 15,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"551\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"NR_IDENT_CLIENTE\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.NR_IDENT_CLIENTE\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                \"sqlType\": 12,\r\n" + 
			"                                                \"index\": 16,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"552\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"CDS_MATRICULA\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.CDS_MATRICULA\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.String\",\r\n" + 
			"                                                \"sqlType\": 12,\r\n" + 
			"                                                \"index\": 17,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"553\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": false,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"CD_TP_TITULARIDADE\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.CD_TP_TITULARIDADE\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 18,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"554\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": false,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"CD_TP_PARENTESCO\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.CD_TP_PARENTESCO\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 19,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"555\",\r\n" + 
			"                                                \"scale\": 0,\r\n" + 
			"                                                \"length\": 0,\r\n" + 
			"                                                \"precision\": 0,\r\n" + 
			"                                                \"isUnique\": false,\r\n" + 
			"                                                \"isNullable\": true,\r\n" + 
			"                                                \"isUpdatable\": true,\r\n" + 
			"                                                \"isInsertable\": true,\r\n" + 
			"                                                \"columnDefinition\": \"\",\r\n" + 
			"                                                \"name\": \"CD_TP_BLOQUEIO\",\r\n" + 
			"                                                \"qualifiedName\": \"TB_BF_CLIENTE.CD_TP_BLOQUEIO\",\r\n" + 
			"                                                \"table\": {\r\n" + 
			"                                                    \"@reference\": \"534\"\r\n" + 
			"                                                },\r\n" + 
			"                                                \"typeName\": \"java.lang.Long\",\r\n" + 
			"                                                \"sqlType\": 2,\r\n" + 
			"                                                \"index\": 20,\r\n" + 
			"                                                \"useDelimiters\": false,\r\n" + 
			"                                                \"useUpperCaseForComparisons\": false\r\n" + 
			"                                            },\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null\r\n" + 
			"                                        ]\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {}\r\n" + 
			"                                }\r\n" + 
			"                            ],\r\n" + 
			"                            [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 21,\r\n" + 
			"                                        \"elementData\": [\r\n" + 
			"                                            18117,\r\n" + 
			"                                            null,\r\n" + 
			"                                            \"WILLIAN WALLACE DE SOUZA\",\r\n" + 
			"                                            null,\r\n" + 
			"                                            0,\r\n" + 
			"                                            0,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            0,\r\n" + 
			"                                            {\r\n" + 
			"                                                \"@id\": \"558\",\r\n" + 
			"                                                \"$\": \"2015-03-27 13:52:22\"\r\n" + 
			"                                            },\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            null,\r\n" + 
			"                                            31903,\r\n" + 
			"                                            \"500\",\r\n" + 
			"                                            null,\r\n" + 
			"                                            1,\r\n" + 
			"                                            1,\r\n" + 
			"                                            1\r\n" + 
			"                                        ]\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {}\r\n" + 
			"                                }\r\n" + 
			"                            ],\r\n" + 
			"                            21\r\n" + 
			"                        ],\r\n" + 
			"                        \"isCoordinatedWithProperty\": false,\r\n" + 
			"                        \"backupValueHolder\": {\r\n" + 
			"                            \"@class\": \"org.eclipse.persistence.internal.indirection.BackupValueHolder\",\r\n" + 
			"                            \"@id\": \"559\",\r\n" + 
			"                            \"value\": [\r\n" + 
			"                                {},\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"capacityIncrement\": 0,\r\n" + 
			"                                        \"elementCount\": 0,\r\n" + 
			"                                        \"elementData\": []\r\n" + 
			"                                    }\r\n" + 
			"                                },\r\n" + 
			"                                {\r\n" + 
			"                                    \"default\": {\r\n" + 
			"                                        \"initialCapacity\": 0,\r\n" + 
			"                                        \"isListOrderBrokenInDb\": false,\r\n" + 
			"                                        \"isRegistered\": false,\r\n" + 
			"                                        \"delegate\": [],\r\n" + 
			"                                        \"valueHolder\": {\r\n" + 
			"                                            \"@class\": \"org.eclipse.persistence.indirection.ValueHolder\",\r\n" + 
			"                                            \"@id\": \"563\",\r\n" + 
			"                                            \"value\": [],\r\n" + 
			"                                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                                            \"isNewlyWeavedValueHolder\": false\r\n" + 
			"                                        }\r\n" + 
			"                                    }\r\n" + 
			"                                }\r\n" + 
			"                            ],\r\n" + 
			"                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                            \"isNewlyWeavedValueHolder\": false,\r\n" + 
			"                            \"unitOfWorkValueHolder\": {\r\n" + 
			"                                \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                \"@reference\": \"526\"\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        \"sourceAttributeName\": \"clienteTelefones\"\r\n" + 
			"                    }\r\n" + 
			"                }\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"nrOrdem\": 0,\r\n" + 
			"        \"saldoDevedor\": 0.0,\r\n" + 
			"        \"clienteCartaoCobrancas\": [\r\n" + 
			"            {},\r\n" + 
			"            {\r\n" + 
			"                \"default\": {\r\n" + 
			"                    \"capacityIncrement\": 0,\r\n" + 
			"                    \"elementCount\": 0,\r\n" + 
			"                    \"elementData\": []\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"default\": {\r\n" + 
			"                    \"initialCapacity\": 10,\r\n" + 
			"                    \"isListOrderBrokenInDb\": false,\r\n" + 
			"                    \"isRegistered\": true,\r\n" + 
			"                    \"valueHolder\": {\r\n" + 
			"                        \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                        \"@id\": \"566\",\r\n" + 
			"                        \"isInstantiated\": false,\r\n" + 
			"                        \"row\": [],\r\n" + 
			"                        \"isCoordinatedWithProperty\": false,\r\n" + 
			"                        \"backupValueHolder\": {\r\n" + 
			"                            \"@class\": \"org.eclipse.persistence.internal.indirection.BackupValueHolder\",\r\n" + 
			"                            \"@id\": \"567\",\r\n" + 
			"                            \"isCoordinatedWithProperty\": false,\r\n" + 
			"                            \"isNewlyWeavedValueHolder\": false,\r\n" + 
			"                            \"unitOfWorkValueHolder\": {\r\n" + 
			"                                \"@class\": \"org.eclipse.persistence.internal.indirection.UnitOfWorkQueryValueHolder\",\r\n" + 
			"                                \"@reference\": \"566\"\r\n" + 
			"                            }\r\n" + 
			"                        },\r\n" + 
			"                        \"sourceAttributeName\": \"clienteCartaoCobrancas\"\r\n" + 
			"                    }\r\n" + 
			"                }\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"camposEspecificos\": [],\r\n" + 
			"        \"flNotificacoesApp\": 0,\r\n" + 
			"        \"flTermoUsoApp\": 0,\r\n" + 
			"        \"solicitaSegundaVia\": false,\r\n" + 
			"        \"solicitaPrimeiraVia\": false,\r\n" + 
			"        \"cdContrato\": 0\r\n" + 
			"    }\r\n" + 
			"}";

}
