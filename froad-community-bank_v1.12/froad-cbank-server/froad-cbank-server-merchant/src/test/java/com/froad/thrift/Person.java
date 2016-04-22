package com.froad.thrift;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.froad.po.MerchantType;

public class Person {
	String name;
	 int age;
	 
	 public Person(String name,int age){
	  this.name = name;
	  this.age = age;
	  
	 }

	 public int getAge() {
	  return age;
	 }

	 public void setAge(int age) {
	  this.age = age;
	 }

	 public String getName() {
	  return name;
	 }

	 public void setName(String name) {
	  this.name = name;
	 }
	 
	 public static void main(String[] args) {
		 Set<Long> set = new HashSet<Long>();
		 set.add(111111l);
		 
		 System.out.println(set.contains(1111112));
		 
		 MerchantType m = new MerchantType();
		 m.setId(1000000001l);
		 m.setType("直接优惠");
		 m.setType("1");
		 
//		 System.out.println(JSON.);
		 
		 
	}
}

