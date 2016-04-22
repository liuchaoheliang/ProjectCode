package com.froad;

import java.util.Random;

import org.junit.Test;

import junit.framework.TestCase;

public class TestRamCode extends TestCase{
	@Test
	public void testCode(){
		 String base = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM0123456789";   
	     Random random = new Random();   
	     StringBuffer sb = new StringBuffer();   
	     for (int i = 0; i < 20; i++) {   
	    	if(i > 0 && i%4 == 0) {
	    		sb.append("-");
	    	} 
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	     }   
	     System.out.println(sb.toString());
	}
}
