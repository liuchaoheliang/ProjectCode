package com.froad.thrift;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Mycomparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		  Person p1=(Person)o1;
		  Person p2=(Person)o2;  
		  if(p1.age>p2.age)
		   return 1;
		  else
		   return 0;
	}
	
	
	public static void main(String[] args) {
//		  ArrayList list = new ArrayList();
//		  list.add(new Person("lcl",28));
//		  list.add(new Person("fx",23));
//		  list.add(new Person("wqx",29));
//		  Comparator comp = new Mycomparator();
//		  Collections.sort(list,comp);  
//		  for(int i = 0;i<list.size();i++){
//		   Person p = (Person)list.get(i);
//		   System.out.println(p.getName());
//		  }  
		
		Map<String,String> hash = new HashMap<String, String>();
		
	}

}

