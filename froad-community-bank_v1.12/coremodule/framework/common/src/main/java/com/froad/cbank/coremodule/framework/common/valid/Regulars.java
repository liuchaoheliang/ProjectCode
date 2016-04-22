package com.froad.cbank.coremodule.framework.common.valid;

/**
 * 正则表达式
 * @ClassName Regulars
 * @author zxl
 * @date 2015年5月16日 上午10:05:53
 */
public class Regulars {
	
	//数字
	public static final String NUMBER = "^[0-9]*$";
	
	//身份证号
	public static final String IDCARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
	
	//手机号
//	public static final String MOBILE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
	public static final String MOBILE = "^(1[3-8])\\d{9}$";
	
	//邮箱
	public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	
	//中文
	public static final String CHINESE = "^[\u4e00-\u9fa5]{0,}$";
	
	//电话号码
	public static final String TEL = "(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$";
	
	//金额
	public static final String MONEY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";

}
