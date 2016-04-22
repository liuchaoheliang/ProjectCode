package com.froad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

public class TestExprotTxt extends TestCase{

	public static void main(String[] args) throws IOException {
		FileOutputStream fs = new FileOutputStream(new File("E:\\workdata\\HB\\QM-400000.txt"));
		PrintStream p = new PrintStream(fs);
		String base = "123456789";   
		Set<String> set=new HashSet<String>();
		for(int i = 0; i < 400000; i++) {
			Random random = new Random();   
		    StringBuffer sb = new StringBuffer();   
		    for (int j = 0; j < 3; j++) {   
		        int number = random.nextInt(base.length());   
		       /* if(j > 0 && j%4 ==0) {
		        	sb.append("-");	  
		        }*/
		        
		        sb.append(base.charAt(number));	     
		    }  
		    set.add(sb.toString());			
		}		
		
		for(String aString : set) {
			p.println(aString);
		}
		
		p.close();

	}
}