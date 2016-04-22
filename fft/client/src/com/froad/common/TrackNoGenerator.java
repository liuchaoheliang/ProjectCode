package com.froad.common;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TrackNoGenerator {

	private TrackNoGenerator(){}
	
	public static String getTrackNoString32(){
		
		
//		return UUID.randomUUID().toString();
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	private static String getNanoTime(){
		long x = System.nanoTime();
		System.out.println( System.currentTimeMillis());
//		String s = x+"";
//		s = s.substring(9, s.length());
		
		return ""+x;
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
	
	
	
//	public static String getUniqueNumber16(){
//		return TrackNoGenerator.getDateTime() + 
//				TrackNoGenerator.getTimeMillis() ;
//	}
	
	private static String getTimeMillis(){
		String sss = System.currentTimeMillis()+"";
		sss =sss.substring(8);
		
		return sss;
	}
	
	public static String getUniqueNumber45(){
		String time=System.currentTimeMillis()+"";
		if(time.length()>13)
			time=time.substring(0, 12);
		return TrackNoGenerator.getTrackNoString32() + 
				time ;
	}
		  
		
	
	public static void main(String args[]){
			String s = TrackNoGenerator.getNanoTime();
			System.out.println(s);
			
			String ss = TrackNoGenerator.getDateTime();
			System.out.println(ss);
			
			String sss = TrackNoGenerator.getTimeMillis();
			System.out.println(sss);
			
			System.out.println("==3333333333====");
			String uuid=TrackNoGenerator.getTrackNoString32();
			System.out.println(uuid);
			
			System.out.println("===================");
			for(int i=1;i<100;i++){
				System.out.println(getUniqueNumber16());
			}
	}
	
}
