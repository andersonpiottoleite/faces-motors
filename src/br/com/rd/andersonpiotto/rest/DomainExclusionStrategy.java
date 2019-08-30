package br.com.rd.andersonpiotto.rest;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class DomainExclusionStrategy implements ExclusionStrategy {
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if (f.getName().equals("idCar")) {
			return true;
		}
		if (f.getName().equals("modelo")) {
			return true;
		}
		if (f.getName().equals("automovel")) {
			return true;
		}
		if (f.getName().equals("automoveis")) {
			return true;
		}
		
		return false;
	}

}