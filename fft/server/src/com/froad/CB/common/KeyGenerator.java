package com.froad.CB.common;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sean
 * 不支持多系统同时运行，单独系统可以生成 16位，并发9999个,纯数字
 * 1305121247049314
 * 年月月日日时时分分秒秒XXXX
 * 并追加四位"0-9"的自增字符串.<br/> 
 * 支持年份为2010年-2110年<br/>
 */
public class KeyGenerator {
 
	private final static SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
	
	private static volatile BigDecimal postFix = new BigDecimal(0); // 后缀自增值
	
	private final static BigDecimal bentchMark = new BigDecimal(9999); // 最大9999 可以并发9999

	public synchronized static String generateOfSleep(){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return generate("101");
	}
	
	/**
	 * 业务上传入前缀以示区别
	 * @param prefix
	 * @return
	 */
	final public synchronized static String generate(String prefix)  {
		
		String dateString = sf.format(new Date());
		
		postFix = postFix.add(new BigDecimal(1));
		
		String strPostFix = "";
		
		if(postFix.compareTo(bentchMark) < 0) { // 小于9999
			strPostFix += postFix.toString();
			while(strPostFix.length() < 4) {
				strPostFix = "0" + strPostFix;
			}
		} else if(postFix.compareTo(bentchMark) == 0) { // =9999 - 返回9999
			strPostFix = "9999";
			postFix = new BigDecimal(0);
		} else {
			// 不会出现
		}
	
		return prefix + dateString + strPostFix;
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
		
//		long x = System.currentTimeMillis();
//		for(int i=0;i<10;i++){
//			new Thread(new MyRun("10" + i)).start();
//			Thread.sleep(10);
//		}
//		long y = System.currentTimeMillis();
//		
//		System.out.println("启动耗时："+(y-x)+"毫秒");
		System.out.println(generate("fbu").length());
	}
	
	
	static class MyRun implements Runnable{
		String x = "";
		public MyRun(String as) {
			x = as;
		}
		
		@Override
		public void run() {
			for(int i=0;i<1000;i++){
				String s = KeyGenerator.generate(x);
				System.out.println(s);
				set.add(s);
				
				try {
					Thread.currentThread().sleep(10);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			
			System.out.println(Thread.currentThread().getId() + ":" + set.size());
			
		}
		
	}
	
} 

