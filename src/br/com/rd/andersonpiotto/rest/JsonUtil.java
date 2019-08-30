package br.com.rd.andersonpiotto.rest;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/** Classe responsável por disponibilizar métodos utilitários Json
 * 
 * @author Anderson Piotto
 * @since 24/07/2019
 * @version 1.0.0
 */
public class JsonUtil {
	
	public static String getJsonTo(Object object) throws ClassNotFoundException, JsonProcessingException, IOException {
		
		String jsonResult = null;
		
		XStream xstream = getXStreamFormatado();
		
		String jsonString = xstream.toXML(object);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
        JsonNode jsonNodes = objectMapper.readTree(jsonString);
		
        clearJsonNodes(jsonNodes);
        
        jsonResult = objectMapper.writeValueAsString(jsonNodes);
        
        
        return jsonResult;
        
	}
	
	private static XStream getXStreamFormatado() {
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		
		/*XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
		    public HierarchicalStreamWriter createWriter(Writer writer) {
		    	return new JsonWriter(writer, JsonWriter.STRICT_MODE, new JsonWriter.Format(new char[0], new char[0], 0));
		    }
		});*/
				
		xstream.setMode(XStream.ID_REFERENCES);
		
		return xstream;	
	}


	private static void clearJsonNodes(JsonNode jsonNodes) {
		
		for (JsonNode node : jsonNodes) {
			
			clearDataEclipseLinkIn(node);
        }
	}
	
	private static void clearInfoClassIn(ObjectNode objNode) {
		
		objNode.remove("@class");
	}
	
	private static void clearDataEclipseLinkIn(JsonNode jsonNode) {
		
		if(jsonNode instanceof ObjectNode) {
			
			ObjectNode objNode = (ObjectNode) jsonNode;
			
			// se existe, remove node típico do EclipseLink
			if(objNode.has("default")) {
				objNode.removeAll();
				
			}else {
				
				clearInfoClassIn(objNode);
				
				Iterator<JsonNode> elements = objNode.getElements();
				
				while(elements.hasNext()){
					JsonNode jsonNodeInternal = elements.next();
					
					// recursividade, para que navegar nos nodes internos
					clearDataEclipseLinkIn(jsonNodeInternal);
				}
				
			}
			
		}
		
		
		if(jsonNode instanceof ArrayNode) {
			
			ArrayNode arrayNode = (ArrayNode) jsonNode;
			
			Iterator<JsonNode> elements = arrayNode.getElements();
			
			while(elements.hasNext()){
				JsonNode jsonNodeInternal = elements.next();
				
				// recursividade, para que navegar nos nodes internos
				clearDataEclipseLinkIn(jsonNodeInternal);
			}
		}
		
	}
	
}