package br.com.rd.andersonpiotto.dto;

import org.modelmapper.ModelMapper;

public class MapperObjectToObjectUtils {
	
	private static ModelMapper mapper = new ModelMapper();
	
	public static void objetoToObjectDTO(Object object, Object objectDTO) {
		mapper.map(object, objectDTO);
	}

}
