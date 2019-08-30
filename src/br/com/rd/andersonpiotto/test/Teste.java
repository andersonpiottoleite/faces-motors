package br.com.rd.andersonpiotto.test;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Teste {
	
	private String name1;
	
	private List<String> list1 = new ArrayList<String>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String name2;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> list2 = new ArrayList<String>();

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public List<String> getList1() {
		return list1;
	}

	public void setList1(List<String> list1) {
		this.list1 = list1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public List<String> getList2() {
		return list2;
	}

	public void setList2(List<String> list2) {
		this.list2 = list2;
	}
	
	
	
	
	
	

}
