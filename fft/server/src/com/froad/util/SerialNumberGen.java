package com.froad.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sean
 * 不支持多系统同时运行，单独系统可以生成 14位，每秒1000个,纯数字
 * 21115092944000
 * 年月月日日时时分分秒秒XXX
 * 并追加三位"0-9"的自增字符串.<br/> 
 * 如果系统时间设置为大于<b>2304-6-27 7:00:26<b/>的时间，将会报错<br/>
 * 支持年份为2010年-2019年<br/>
 */
public class SerialNumberGen {
 
 
	private final static SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
	
	private final static String str = "1234567890";
 
	private final static int pixLen = str.length();
 
	private static volatile int pixOne = 0;
 
	private static volatile int pixTwo = 0;
 
	private static volatile int pixThree = 0;
 
	
	final public synchronized static String generateOfSleep(){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return generate();
	}
	
	
	final public synchronized static String generate()  {
		
		
		String dateString = sf.format(new Date());
		dateString = dateString.substring(1, dateString.length());
		
		pixThree++;
 
			if (pixThree == pixLen) {
 
				pixThree = 0;
 
				pixTwo++;
 
				if (pixTwo == pixLen) {
 
					pixTwo = 0;
 
					pixOne++;
 
					if (pixOne == pixLen) {
 
						pixOne = 0;
					}
 
				}
 
			}
 
	
		return dateString + str.charAt(pixOne) + str.charAt(pixTwo)
				+ str.charAt(pixThree); //+ str.charAt(pixFour);
	}
 
	
	static final  Set<String> set = new HashSet<String>();
	/**
	 * @author Sean
	 * FroadBullNoGen 测试说明
	 * <p>并发10个线程，间隔10毫秒启动
	 * <p>每个线程间隔10毫秒取一个ID,并加入Set
	 * <p>线程执行结束,打印线程号+Set.Size
	 * <p>正确结果为 size<=10000 <br/>
	 * 为模拟线程并发情况 Set<String> 不是线程安全，可能存在线程资源争强后无法释放
	 */
	public static void main(String[] args) throws InterruptedException {
		
		long x = System.currentTimeMillis();
		for(int i=0;i<10;i++){
			new Thread(new MyRun()).start();
			Thread.sleep(10);
		}
		long y = System.currentTimeMillis();
		
		System.out.println("启动耗时："+(y-x)+"毫秒");
	}
	
	
	static class MyRun implements Runnable{
		
		@Override
		public void run() {
			
			for(int i=0;i<1000;i++){
				String s = SerialNumberGen.generate();
				set.add(s);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(Thread.currentThread().getId() + ":" + set.size());
			
		}
		
	}
	
} 

