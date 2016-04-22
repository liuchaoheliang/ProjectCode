package com.froad.CB.common.constant;

import java.util.HashMap;
import java.util.Map;

public class LotteryCommand {
	
	//业务代码
	public static String APICODE_QUERY_PERIDLISTNOW="60001";//查询当前期数
	public static String APICODE_CALORDER="60002";//计算彩票注数
	public static String APICODE_QUERY_ZCINFOS="60003";//查询足彩信息
	public static String APICODE_QUERY_LASTPERIOD_REWARD="60004";//查询上期中奖情况
	public static String APICODE_CREATEORDER="60005";//创建订单
	public static String APICODE_QUERY_REWARDBYTRANID="60006";//通过TRANID查询彩票中奖情况

	//响应码
	public static String CODE_SUCCESS="0000";//成功
	public static String CODE_FAIL="0001";//失败
	
	//彩票响应指令
	public static final Map<String, String> LOTTERY_RESPCODE;
	   static {
		   LOTTERY_RESPCODE= new HashMap<String, String>();
	   LOTTERY_RESPCODE.put("0000", "操作成功");
	   LOTTERY_RESPCODE.put("0100", "客户未授权");
	   LOTTERY_RESPCODE.put("0101", "账号已过期无效");
	   LOTTERY_RESPCODE.put("0102", "该业务不存在或未授权");
	   LOTTERY_RESPCODE.put("0103", "业务数据非法");
	   LOTTERY_RESPCODE.put("0044", "不存在此业务");	   
	   LOTTERY_RESPCODE.put("0011", "投注倍数小于一倍");
	   LOTTERY_RESPCODE.put("0012", "输入投注倍数大于99倍");	   
	   LOTTERY_RESPCODE.put("0013", "输入追号期数小于1期");	   
	   LOTTERY_RESPCODE.put("1014", "输入追号期数大于50期");
	   LOTTERY_RESPCODE.put("0103", "客户ID标识错误");	   
	   LOTTERY_RESPCODE.put("0003", "销售期为空");
	   LOTTERY_RESPCODE.put("0004", "彩种编码为空");	   
	   LOTTERY_RESPCODE.put("0005", "彩种类型为空");
	   LOTTERY_RESPCODE.put("0006", "选号为空");
	   LOTTERY_RESPCODE.put("0017", "对不起，暂时不能购买(可能期号不正确)");	   
	   LOTTERY_RESPCODE.put("0144", "未知错误");	   
	   LOTTERY_RESPCODE.put("0104", "预存金额不足");
	   LOTTERY_RESPCODE.put("0016", "其他错误提示");	   
	   }
	   
	   
	   public static final Map<String, String> LOTTERY_TYPE;//彩票类型
	   static {
		   LOTTERY_TYPE= new HashMap<String, String>();
		   LOTTERY_TYPE.put("1", "双色球");
		   LOTTERY_TYPE.put("2", "3D");
		   LOTTERY_TYPE.put("3", "七乐彩");
		   LOTTERY_TYPE.put("4", "大乐透");
		   LOTTERY_TYPE.put("5", "22选5");	   
		   LOTTERY_TYPE.put("6", "七星彩");
		   LOTTERY_TYPE.put("7", "排列3");	   
		   LOTTERY_TYPE.put("8", "排列5");	   
		   LOTTERY_TYPE.put("9", "足彩六场半");
		   LOTTERY_TYPE.put("10", "足彩4进球");	   
		   LOTTERY_TYPE.put("11", "足彩胜平负");	   
	   }
	   
	   
	   public static final Map<String, String> LOTTERY_NO;//彩种编码
	   static {
		   LOTTERY_NO= new HashMap<String, String>();
		   LOTTERY_NO.put("FC_SSQ", "双色球");
		   LOTTERY_NO.put("FC_3D", "3D");
		   LOTTERY_NO.put("FC_QLC", "七乐彩");
		   LOTTERY_NO.put("TC_CJDLR", "大乐透");
		   LOTTERY_NO.put("TC_22_5", "22选5");	   
		   LOTTERY_NO.put("TC_QXC", "七星彩");
		   LOTTERY_NO.put("TC_PL3", "排列3");	   
		   LOTTERY_NO.put("TC_PL5", "排列5");	   
		   LOTTERY_NO.put("TC_6CBQCSF", "足彩六场半");
		   LOTTERY_NO.put("TC_4CJQ", "足彩4进球");	   
		   LOTTERY_NO.put("TC_ZQSPF", "足彩胜平负");	   
	   }
	   
	   
	   public static final Map<String, String> LOTTERY_PLAYTYPE;//玩法
	   static {
		   LOTTERY_PLAYTYPE= new HashMap<String, String>();
		   LOTTERY_PLAYTYPE.put("1", "直选");
		   LOTTERY_PLAYTYPE.put("2", "组选3  适合3D、排列三");
		   LOTTERY_PLAYTYPE.put("3", "组选6  适合3D、排列三");
		   LOTTERY_PLAYTYPE.put("4", "组选");
	   	   
	   }
	   
	   public static final Map<String, String> LOTTERY_NUMTYPE;//单复和合胆
	   static {
		   LOTTERY_NUMTYPE= new HashMap<String, String>();
		   LOTTERY_NUMTYPE.put("1", "单式");
		   LOTTERY_NUMTYPE.put("2", "复式");
		   LOTTERY_NUMTYPE.put("3", "包号");
		   LOTTERY_NUMTYPE.put("4", "和值");
		   LOTTERY_NUMTYPE.put("5", "胆拖");
		   LOTTERY_NUMTYPE.put("9", "9场");		   
		   LOTTERY_NUMTYPE.put("14", "14场");
	   }
	   
	   
	   public static final Map<String, String> LOTTERY_STATUS;//订单状态
	   static {
		   LOTTERY_STATUS= new HashMap<String, String>();
		   LOTTERY_STATUS.put("1", "已受理");
		   LOTTERY_STATUS.put("2", "受理失败");
		   LOTTERY_STATUS.put("3", "出票成功");
		   LOTTERY_STATUS.put("4", "已退票");
		   LOTTERY_STATUS.put("5", "未中奖");	   
		   LOTTERY_STATUS.put("6", "已派奖");
		   LOTTERY_STATUS.put("7", "未派奖");	   
		   LOTTERY_STATUS.put("8", "异常");	   
		     
	   }
	   
	   public static final Map<String, String> LOTTERY_REWARDSTATUS;//订单状态
	   static {
		   LOTTERY_REWARDSTATUS= new HashMap<String, String>();
		   LOTTERY_REWARDSTATUS.put("00", "派奖成功");
		   LOTTERY_REWARDSTATUS.put("01", "派奖中");
		   LOTTERY_REWARDSTATUS.put("02", "审核中");
	   }
	   public static final Map<String, String> LOTTERY_TYPE_NO;//彩票类型
	   static {
		   LOTTERY_TYPE_NO= new HashMap<String, String>();
		   LOTTERY_TYPE_NO.put("FC_SSQ", "1");
		   LOTTERY_TYPE_NO.put("FC_3D", "2");
		   LOTTERY_TYPE_NO.put("FC_QLC", "3");
		   LOTTERY_TYPE_NO.put("TC_CJDLR", "4");
		   LOTTERY_TYPE_NO.put("TC_22_5", "5");	   
		   LOTTERY_TYPE_NO.put("TC_QXC", "6");
		   LOTTERY_TYPE_NO.put("TC_PL3", "7");	   
		   LOTTERY_TYPE_NO.put("TC_PL5", "8");	   
		   LOTTERY_TYPE_NO.put("TC_6CBQCSF", "9");
		   LOTTERY_TYPE_NO.put("TC_4CJQ", "10");	   
		   LOTTERY_TYPE_NO.put("TC_ZQSPF", "11");	   
	   }
}