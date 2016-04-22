package com.froad.thirdparty.util;

import java.util.Random;


	/**
	 * 类描述：生成验证码的工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年10月29日 下午5:54:46 
	 */
public class CheckCodeUtils {

	private static char[] letters=new char[52];
	
	private static int[] numbers=new int[10];
	
	static{
		int index=0;
		for (int i = 65; i < 91; i++) {
			char a=(char)i;
			letters[index]=a;
			index=index+2;
		}
		index=1;
		for (int i = 97; i < 123; i++) {
			char a=(char)i;
			letters[index]=a;
			index=index+2;
		}
		for (int i = 0; i < 10; i++) {
			numbers[i]=i;
		}
	}
	
	
	/**
	  * 方法描述：生成字母加数字的随机验证码
	  * @param: length 验证码的长度
	  * @return: 验证码
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年10月29日 下午5:58:16
	  */
	public static String makeCheckCode(int length){
		StringBuilder checkCode=new StringBuilder();
		Random random=new Random();
		int i=0;
		while(i<length){
			int choice=random.nextInt(10);
			if(choice%2==0){
				char letter=letters[random.nextInt(52)];
				checkCode.append(letter);
			}else{
				int number=numbers[random.nextInt(10)];
				checkCode.append(number);
			}
			i++;
		}
		return checkCode.toString();
	}
	/**
	 * 生成纯数字的随机验证码
	* <p>Function: makeCheckCodeAllNumber</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2014年11月28日 下午5:10:34
	* @version 1.0
	* @param length
	* @return
	 */
	public static String makeCheckCodeAllNumber(int length){
		StringBuilder checkCode=new StringBuilder();
		Random random = new Random();
		while(length != 0){
			checkCode.append(random.nextInt(10));
			length --;
		}
		return checkCode.toString();
	}
}
