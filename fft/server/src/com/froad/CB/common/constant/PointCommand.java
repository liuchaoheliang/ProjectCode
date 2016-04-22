package com.froad.CB.common.constant;


	/**
	 * 类描述：积分相关的常量
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 15, 2013 4:26:55 PM 
	 */
public class PointCommand {
	
	/** 会员类型 01：手机号 02：username **/
	public static String ACCOUNT_MARKED_TYPE="02";
	
	/**证件类型：身份证**/
	public static String CERT_TYPE="011";
	
	/**消费积分的对象类型 1：积分消费 2：积分提现**/
	public static String OBJECT_TYPE_CONSUME="1";
	
	public static String OBJECT_TYPE_WITHDRAW="2";
	
	/**积分系统中积分分类的Id**/
	public static String POINTS_CATE_NO="pointsCate_0001";
	
	/**珠海农行积分分类编号：pointsCate_0002**/
	public static String POINTS_ZH_CATE_NO="pointsCate_0002";
	
	public static final String RESP_CODE_NO_EXIST="5001";//积分账户不存在
}
