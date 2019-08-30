package br.com.rd.andersonpiotto.rest;

import java.lang.reflect.Type;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.com.rd.andersonpiotto.model.Automovel;

public class AutomovelSerialize implements JsonSerializer<Automovel>{
	
	 @Override
	    public JsonElement serialize(Automovel automovel, Type type, JsonSerializationContext jsc) {
	        JsonObject jObj = (JsonObject)new GsonBuilder().create().toJsonTree(automovel);
	        jObj.remove("@id");
	        return jObj;
	    }

}
