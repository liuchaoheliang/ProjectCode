package com.froad.util;

import java.util.Random;

/**
 * 类描述：生成验证码的工具类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: 2014年10月29日 下午5:54:46
 */
public class RandomCodeUtils {

	private static char[] letters = new char[52];
	  //初始化种子
	private static	String letterNumber = "2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,U,V,W,X,Y,Z";  
	
	private static int[] numbers = new int[10];

	static {
		int index = 0;
		for (int i = 65; i < 91; i++) {
			char a = (char) i;
			letters[index] = a;
			index = index + 2;
		}
		index = 1;
		for (int i = 97; i < 123; i++) {
			char a = (char) i;
			letters[index] = a;
			index = index + 2;
		}
		for (int i = 0; i < 10; i++) {
			numbers[i] = i;
		}
	}

	/**
	 * 方法描述：生成字母加数字的随机验证码
	 * 
	 * @param: length 验证码的长度
	 * @return: 验证码
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年10月29日 下午5:58:16
	 */
	public static String makeCheckCode(int length) {
		StringBuilder checkCode = new StringBuilder();
		Random random = new Random();
		int i = 0;
		while (i < length) {
			int choice = random.nextInt(10);
			if (choice % 2 == 0) {
				char letter = letters[random.nextInt(52)];
				checkCode.append(letter);
			} else {
				int number = numbers[random.nextInt(10)];
				checkCode.append(number);
			}
			i++;
		}
		return checkCode.toString();
	}

	/**
	 * 生成纯数字随机码
	* <p>Function: makeCheckCodeAllNumber</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年1月9日 下午2:26:14
	* @version 1.0
	* @param length
	* @return
	 */
	public static String makeCheckCodeAllNumber(int length) {
		StringBuilder checkCode = new StringBuilder();
		Random random = new Random();
		while (length != 0) {
			checkCode.append(random.nextInt(10));
			length--;
		}
		return checkCode.toString();
	}
	
	/**
	 * 方法描述：随机生成包含验证码字符串
	 * 
	 * @param: length 验证码的长度
	 * @return: 验证码
	 * @version: 1.0
	 * @author: wuhelian@f-road.com.cn
	 * @time: 2015年5月29日 下午5:58:16
	 */
      public static String makeCheckRandom(int length){
		//创建Random对象
		 Random random=new Random();
		 int index = 0;  
	   String str[] = letterNumber.split(",");//将字符串以,分割 
		  String text = "";
		  //随机产生4个字符的字符串
		  for(int i=0;i<length;i++){
		    index = random.nextInt(str.length-1);//在0到str.length-1生成一个伪随机数赋值给index  
		    text += str[index];//将对应索引的数组与str的变量值相连接  
		  }
		     return text;
		 }
}
