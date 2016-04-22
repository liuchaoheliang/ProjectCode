package com.froad.fft.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.service.SysClientService;

/**
 * 编号生成类
 * 
 * @author FQ
 * 
 */
public class SerialNumberUtil {

	public static final String PRODUCT_SN_PREFIX = "SN";// 货号前缀
	public static final String TRANS_SN_PREFIX = "";// 交易编号前缀
	public static final String PAY_SN_PREFIX = "";// 支付编号前缀
	public static final String REFUND_SN_PREFIX = "";// 退款编号前缀
	public static final String RETURN_SN_PREFIX = "";// 退\换货编号前缀
	
	private final static SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
	private final static SimpleDateFormat sf_yyMMdd = new SimpleDateFormat("yyMMdd");
	
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    public static final String numberChar = "1234567890"; 
	
	private final static int pixLen = numberChar.length();
	private static volatile int pixOne = 0;
	private static volatile int pixTwo = 0;
	private static volatile int pixThree = 0;
	
	public synchronized static String generate() {

		String dateString = sf.format(new Date());
		dateString = dateString.substring(0, dateString.length());

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

		return dateString + numberChar.charAt(pixOne) + numberChar.charAt(pixTwo)
				+ numberChar.charAt(pixThree); // + str.charAt(pixFour);
	}

	/**
	 * 生成商品编号
	 * 
	 * @return 编号
	 */
	public static String buildProductSn() {
		String uuid = UUID.randomUUID().toString();
		return PRODUCT_SN_PREFIX
				+ (uuid.substring(0, 8) + uuid.substring(9, 13)).toUpperCase();
	}

	/**
	 * 生成交易编号
	 * 
	 * @param transSnPrefix
	 * @return 交易编号
	 */
	public synchronized static String buildTransSn() {
		return  TRANS_SN_PREFIX + generate();
	}

	/**
	 * 生成支付编号
	 * 
	 * @return 支付编号
	 */
	public synchronized static String buildPaySn() {
		return PAY_SN_PREFIX + generate();
	}
	
	/**
	 * 生成退款编号
	 * 
	 * @return 退款编号
	 */
	public synchronized static String buildRefundSn() {
		return REFUND_SN_PREFIX + generate();
	}
	
	/**
	 * 生成退\换货编号
	 * 
	 * @return 退款编号
	 */
	public synchronized static String buildReturnSn() {
		return RETURN_SN_PREFIX + generate();
	}
	
	
	/**
	 * 生成兑换券码
	 * @param clientNumber
	 * @return
	 */
	public synchronized static String buildSecuritiesNo(String clientNumber) {
		
		if(clientNumber == null){
			return null;
		}
		
		SysClientService sysClientService = (SysClientService) SpringUtil.getBean("sysClientServiceImpl");
		SysClient sysClient=sysClientService.findSysClientByNumber(clientNumber);
		
		if(sysClient == null){
			return null;
		}
		
		String SecuritiesNo_PREFIX=sysClient.getShortLetter();
		String dateString = sf_yyMMdd.format(new Date());
		
		return SecuritiesNo_PREFIX + dateString + generateString(6).toUpperCase();
	}
	
	
	/** 
     * 返回一个定长的随机字符串(只包含大小写字母、数字) 
     * 
     * @param length 随机字符串长度 
     * @return 随机字符串 
     */ 
    public static String generateString(int length) { 
            StringBuffer sb = new StringBuffer(); 
            Random random = new Random(); 
            for (int i = 0; i < length; i++) { 
                    sb.append(allChar.charAt(random.nextInt(allChar.length()))); 
            } 
            return sb.toString(); 
    } 
    
    
    /** 
     * 返回一个定长的随机纯字母字符串(只包含大小写字母) 
     * 
     * @param length 随机字符串长度 
     * @return 随机字符串 
     */ 
    public static String generateMixString(int length) { 
            StringBuffer sb = new StringBuffer(); 
            Random random = new Random(); 
            for (int i = 0; i < length; i++) { 
                    sb.append(allChar.charAt(random.nextInt(letterChar.length()))); 
            } 
            return sb.toString(); 
    } 
    
    /** 
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母) 
     * 
     * @param length 随机字符串长度 
     * @return 随机字符串 
     */ 
    public static String generateLowerString(int length) { 
            return generateMixString(length).toLowerCase(); 
    } 
    
    /** 
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母) 
     * 
     * @param length 随机字符串长度 
     * @return 随机字符串 
     */ 
    public static String generateUpperString(int length) { 
            return generateMixString(length).toUpperCase(); 
    } 
    
    /** 
     * 生成一个定长的纯0字符串 
     * 
     * @param length 字符串长度 
     * @return 纯0字符串 
     */ 
    public static String generateZeroString(int length) { 
            StringBuffer sb = new StringBuffer(); 
            for (int i = 0; i < length; i++) { 
                    sb.append('0'); 
            } 
            return sb.toString(); 
    } 
    
    /** 
     * 根据数字生成一个定长的字符串，长度不够前面补0 
     * 
     * @param num             数字 
     * @param fixdlenth 字符串长度 
     * @return 定长的字符串 
     */ 
    public static String toFixdLengthString(long num, int fixdlenth) { 
            StringBuffer sb = new StringBuffer(); 
            String strNum = String.valueOf(num); 
            if (fixdlenth - strNum.length() >= 0) { 
                    sb.append(generateZeroString(fixdlenth - strNum.length())); 
            } else { 
                    throw new RuntimeException("将数字" + num + "转化为长度为"+ fixdlenth + "的字符串发生异常！"); 
            } 
            sb.append(strNum); 
            return sb.toString(); 
    } 


	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(SerialNumberUtil.buildProductSn());
//		System.out.println(SerialNumberUtil.buildTransSn());
//		System.out.println(SerialNumberUtil.buildPaySn());
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context/**/*.xml");
		System.out.println(SerialNumberUtil.generateString(6));
		System.out.println(SerialNumberUtil.generateMixString(6));
		System.out.println(SerialNumberUtil.generateLowerString(6));
		System.out.println(SerialNumberUtil.generateUpperString(6));
		System.out.println(SerialNumberUtil.generateZeroString(6));
		System.out.println(SerialNumberUtil.toFixdLengthString(6,6));
		System.out.println(SerialNumberUtil.buildSecuritiesNo("243"));
		
//		SerialNumberUtil.concurrentTest();
//		System.out.println("数量："+set.size());
	}
	
	
	/**
	 * 并发测试
	 */
	static Set set=new HashSet();
	private static void concurrentTest(){
		final CyclicBarrier cdl = new CyclicBarrier(100);
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						cdl.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
					String sn=buildSecuritiesNo("243");
					System.out.println(sn);
					set.add(sn);
				}
			}).start();
		}
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
