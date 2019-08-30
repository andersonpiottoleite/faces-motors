package br.com.rd.andersonpiotto.test;

import java.util.ArrayList;
import java.util.List;

public class TesteGeral {
	
	public static void main(String[] args) {
		
		System.out.println("Uma linha\noutra linha");
		
		
		List<String> list = new ArrayList<String>();
		
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		
		System.out.println(list);
		
		List<String> list2 = new ArrayList<String>();
		
		list2.add("E");
		list2.add("F");
		list2.add("G");
		list2.add("H");
		
		list.clear();
		
		list.addAll(list2);
		
		System.out.println(list);
		
		
	}

}
