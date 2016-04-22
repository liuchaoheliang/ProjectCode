package com.froad.util;




	/**
	 * 类描述：主要验证 邮箱 用户名 密码 电话 等
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2011 
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-6-1 下午05:43:40 
	 */
public class Validate {
	
	
	/**
	  * 方法描述：邮箱验证
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-6-1 下午05:51:42
	  */
	public static boolean validateOfMail(String mail){
		if(mail == null) mail = "";
		return mail.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

	}
	
	
	/**
	  * 方法描述：验证手机号码
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-6-1 下午05:59:49
	  */
	public static boolean validateOfPhone(String phone) {
		if(phone == null) phone = "";
		return phone.matches("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");     

	}
	
	
	/**
	  * 方法描述：用户名验证
	  * @param: 4位4位以上的字母+数字的组合
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-6-2 上午09:17:29
	  */
	public static boolean validateOfUserName(String name){
		if(name == null) name = "";
		return name.matches("^[a-zA-Z0-9_-]{4,20}$")&& !name.matches("^[0-9]{4,20}$|^[_-]{4,20}$");
	}
	
	
	
	/**
	  * 方法描述：密码验证
	  * @param: 密码长度不能少于6位
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-6-2 上午09:19:46
	  */
	public static boolean validateOfPwd(String pwd){
		
		return pwd.length() < 6 ? false:true;
	}
	
	
	/**
	  * 方法描述：日期格式验证
	  * @param: yyyy-mm-dd
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-6-2 下午02:41:51
	  */
	public static boolean validateOfDate(String date){
		if(date == null) date = "";
		return date.matches("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
	}
	
	
	
	/**
	  * 方法描述：数字验证
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-7-28 上午10:29:53
	  */
	public static boolean validateOfNumber(String num) {
		if(num == null) num = "";
		return num.matches("^[0-9]+$");
	}
	
	public static void main(String[] args) {
		System.out.println(validateOfMail("dd@163.com"));
	}
}
