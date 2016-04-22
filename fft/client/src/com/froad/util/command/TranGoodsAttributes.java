package com.froad.util.command;

import java.util.ArrayList;
import java.util.List;

/**
  * 类描述：交易品在交易时才有的一些属性（其实就是交易的附加信息）
  * @version: 1.0
  * @Copyright www.f-road.com.cn Corporation 2013 
  * @author: 刘丽 liuli@f-road.com.cn
  * @time: 2013-2-26 上午11:45:05
 */
public class TranGoodsAttributes {
	//彩票类型的交易品
//	public static String Tran_Goods_Attributes_LotteryNo="";//彩票编码
	public static String Tran_Goods_Attributes_Period="tzqh";//彩票期号
	public static String Tran_Goods_Attributes_PlayType="wf";// 玩法
	public static String Tran_Goods_Attributes_Content="tzhm";//彩票投注内容
	public static String Tran_Goods_Attributes_NumCount="tzzs";//彩票注数
	public static String Tran_Goods_Attributes_zj="zjje";//中奖金额
	
	
//	lottery.setLotteryNo("FC_SSQ");
//	lottery.setNumType("1");
//	lottery.setBuyamount("1");
//	lottery.setAmount("2");
//	lottery.setMobilephone("18616136190");
//	
//	 ALIAS_CODES.put(czhm,充值号码)
//	 ALIAS_CODES.put(czhmgsd,充值号码归属地)
//	 ALIAS_CODES.put(yys,运营商)
//	 ALIAS_CODES.put(mz,面值)
//	 ALIAS_CODES.put(tzqh,投注期号)
//	 ALIAS_CODES.put(tzzs,投注注数)
//	 ALIAS_CODES.put(tzhm,投注号码)
//	 ALIAS_CODES.put(zjje,中奖金额)
//	 ALIAS_CODES.put(cpbm,彩票编码)
//	 ALIAS_CODES.put(wf,玩法)
//	 ALIAS_CODES.put(tzbx,投注倍数)
//	 ALIAS_CODES.put(tzje,投注金额)
//	 ALIAS_CODES.put(lxdh,联系电话)
//	 ALIAS_CODES.put(zjdj,中奖等级)
//	 ALIAS_CODES.put(zjzs,中奖注数)
//	 ALIAS_CODES.put(dfhhd,单复和合胆)


	
	public static String Tran_Goods_Attributes_cpbm="cpbm";//彩票编码
	public static String Tran_Goods_Attributes_dfhhd="dfhhd";//单复和合胆
	public static String Tran_Goods_Attributes_tzbx="tzbx";//投注倍数
	public static String Tran_Goods_Attributes_tzje="tzje";//投注金额
	public static String Tran_Goods_Attributes_lxdh="lxdh";//联系电话
	
	
	
	//话费充值的交易品
	public static String Tran_Goods_Attributes_Phone="czhm";//充值号码
	public static String Tran_Goods_Attributes_Phone_local="czhmgsd";//充值号码归属地
	public static String Tran_Goods_Attributes_Phone_mz="mz";//面值
	public static String Tran_Goods_Attributes_Phone_yys="yys";//运营商
	//充值到账户的交易品
	public static String Tran_Goods_Attributes_AccountNo="";//账户号
	public static String Tran_Goods_Attributes_AccountName="";//账户名
	
	//短信通知--这个是用于发货后的通知
	public static String Tran_Goods_Attributes_Notice_Phone="";//短信通知手机号
	
	public static List<String> ALL_Tran_Goods_Attributes=new ArrayList();
	
	static{
//		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_LotteryNo);
		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_Period);
		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_PlayType);
		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_Content);
		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_NumCount);
		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_Phone);
		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_AccountNo);
		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_AccountName);
		ALL_Tran_Goods_Attributes.add(Tran_Goods_Attributes_Notice_Phone);
	}
}
