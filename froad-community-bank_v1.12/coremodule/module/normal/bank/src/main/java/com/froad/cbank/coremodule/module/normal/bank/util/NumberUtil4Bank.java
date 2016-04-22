package com.froad.cbank.coremodule.module.normal.bank.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * 类名: NumberUtil 
 * 描述: 数字转成百分比形式相关的类 
 * 作者: 明灿 chenmingcan@f-road.com.cn
 * 日期: 2015年7月29日 下午5:02:55 
 *
 */
public class NumberUtil4Bank {

	private static BigDecimal bd = null;
	private static String REGEX = "######0.00";
	/**
	 * 
	 * 方法名称: toPercentageShow 
	 * 简要描述: double类型转成百分比数字输出
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月29日 下午5:04:20
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param number
	 * 方法参数: @return
	 * 返回类型: String
	 * @throws
	 */
	public static String toPercentageShow(double number) {
		String result = "";
		number = number * 100;
		String temp = String.valueOf(number);
		String[] strs = temp.split("\\.");
		if (isExist(strs) && strs[1].matches("0{1,}")) {
			result = Math.round(number) + "%";
		} else {
			bd = new BigDecimal(number);
			double value = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			result = value + "%";
		}
		return result;
	}
	
	/**
	 * 
	 * 方法名称: bigDecimalToDouble 
	 * 简要描述: BigDecimal类型转成double类型 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月29日 下午5:13:14
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param number
	 * 方法参数: @return
	 * 返回类型: double
	 * @throws
	 */
	public static double bigDecimalToDouble(BigDecimal number) {
		String temp = number.toString();
		return Double.parseDouble(temp);
	}
	
	/**
	 * 
	 * 方法名称: isExist 
	 * 简要描述: 判断数组的第二元素是否存在
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月30日 上午10:16:22
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param str
	 * 方法参数: @return
	 * 返回类型: boolean
	 * @throws
	 */
	private static boolean isExist(String[] str) {
		return str != null & str.length > 0 && str[1] != null;
	}

	/**
	 * 
	 * 方法名称: amountToString 
	 * 简要描述: 将double类型金额转成string保留2位小数,举个栗子:0.00-->"0.00";0.1-->"0.10";0.111-->"0.11"
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月31日 下午2:20:59
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param number
	 * 方法参数: @return
	 * 返回类型: String
	 * @throws
	 */
	public static String amountToString(double number) {
		DecimalFormat df = new DecimalFormat(REGEX);
		return df.format(number);
	}
	
	/**
	 * 
	 * 方法名称: percentToString 
	 * 简要描述: 将double转成百分比类型且保留2位小数 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月31日 下午2:39:27
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param number
	 * 方法参数: @return
	 * 返回类型: String
	 * @throws
	 */
	public static String percentToString(double number) {
		number = number * 100;
		DecimalFormat df = new DecimalFormat(REGEX);
		return df.format(number) + "%";
	}
	public static void main(String[] args) {
		double d = bigDecimalToDouble(new BigDecimal(0.3400));
		String percent = toPercentageShow(d);
		System.out.println(percent);
		System.err.println(amountToString(0.1151));
	}
}
