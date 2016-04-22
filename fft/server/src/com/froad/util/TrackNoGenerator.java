package com.froad.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TrackNoGenerator {

	private TrackNoGenerator(){}
	
	public static String getTrackNoString32(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	
	private static String getDateTime(){
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
		String d=dateformat.format(new Date());
		// y mm dd hh mm 
		String ss =d.substring(3);
		
		return ss;
	}
	
	public static String getUniqueNumber16(){
		return TrackNoGenerator.getDateTime() + 
				TrackNoGenerator.getTimeMillis() ;
	}
	
	private static String getTimeMillis(){
		String sss = System.currentTimeMillis()+"";
		sss =sss.substring(8);
		
		return sss;
	}
	
	
		  
		
	
	public static void main(String args[]){
//			String s = TrackNoGenerator.getNanoTime();
//			System.err.println(s);
//			
//			String ss = TrackNoGenerator.getDateTime();
//			System.err.println(ss);
//			
//			String sss = TrackNoGenerator.getTimeMillis();
//			System.err.println(sss);
//			for(int i=0;i<100;i++){
//				System.out.println(getTrackNoString32());
//				
//			}
		
	}
	
}
