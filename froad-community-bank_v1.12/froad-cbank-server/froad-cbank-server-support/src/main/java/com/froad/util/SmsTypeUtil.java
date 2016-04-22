package com.froad.util;

import java.util.ArrayList;
import java.util.List;

public class SmsTypeUtil {
	
	/**
	 *  PAYMENT         - 1支付验证码   PAYMENT
		REMINDER 		- 2消息提醒    REMINDER
		REGISTRATION 	- 3注册验证码  REGISTRATION
		VERIFICATION 	- 4普通验证码 VERIFICATION
		ANNOUNCEMENT 	- 5业务通告   ANNOUNCEMENT
		MARKETING 	    - 6营销短信  MARKETING
		BULK 			- 7批量短信   BULK
		
		详情可对照枚举smsType,若暂无此类型的短信默认值为3888
	 */
	static	final String PAYMENT="1";
	static	final String REMINDER="2";
	static final String REGISTRATION="3";
	static	final String VERIFICATION="4";
	static	final String ANNOUNCEMENT="5";
	static	final String MARKETING="6";
	static	final String BULK="7";
	static	final int Default=3888;
	static final Integer[] PAYMENTS={1305};
	static final Integer[] REMINDERS={1100,1310,1311,1312,1101,1102,1103,1104,1106,1108,1109,1110,1111};
	static final Integer[] REGISTRATIONS={1000};
	static final Integer[] VERIFICATIONS={1001,1002,1300,1301,1302,1304,1305,1306,1307,1308,1309};
	static final Integer[] ANNOUNCEMENTS={Default};
	static final Integer[] MARKETINGS={Default};
	static final Integer[] BULKS={Default};
	static  Integer[] TOTALS=
		{1305,1100,1310,1311,1312,1101,1102,1103,1104,1106,1108,1109,1110,1111,1000,1001,1002,1300,1301,1302,1304,1305,1306,1307,1308,1309};
	/**
	 */
	
	
	public static Integer[] getSmstypeCondition(String name){
		if(name!=null){
			if(name.equals(PAYMENT)){
				return returnPAYMENT();
			}else if(name.equals(REMINDER)){
				return returnREMINDER();
			}else if(name.equals(REGISTRATION)){
				return returnREGISTRATION();
			}else if(name.equals(VERIFICATION)){
				return returnVERIFICATION();
			}else if(name.equals(ANNOUNCEMENT)){
				return returnANNOUNCEMENT();
			}else if(name.equals(MARKETING)){
				return returnMARKETING();
			}else if(name.equals(BULK)){
				return returnBULK();
			}else if(name.equals("")){
				return null;
			}
		}
			return TOTALS;
	}
	
	public static String setSmsTypeMark(Integer smsType){
		if(smsType!=null){
		  if(IsHasSmsType(PAYMENTS,smsType)){
			  return PAYMENT;
		  }else if(IsHasSmsType(REMINDERS,smsType)){
			  return REMINDER;
		  }else if(IsHasSmsType(REGISTRATIONS,smsType)){
			  return REGISTRATION;
		  }else if(IsHasSmsType(VERIFICATIONS,smsType)){
			  return VERIFICATION;
		  }else if(IsHasSmsType(ANNOUNCEMENTS,smsType)){
			  return ANNOUNCEMENT;
		  }else if(IsHasSmsType(MARKETINGS,smsType)){
			  return MARKETING;
		  }else if(IsHasSmsType(BULKS,smsType)){
			  return BULK;
		  }
		}
		
		return "";
	}
	public static boolean IsHasSmsType(Integer[] smsTypes,Integer smsType){
		for(int i=0;i<smsTypes.length;i++){
			if(smsType.intValue()==smsTypes[i].intValue()){
				return true;
			}
		}
		return false;
	}
//	public static List<Integer> addTotals(Integer[] orgin,List<Integer> dest){
//		for(int i=0;i<orgin.length;i++){
//			dest.add(orgin[i]);
//		}
//		return dest;
//	}
	public static Integer[] returnPAYMENT(){
		return PAYMENTS; 
	}
	public static Integer[] returnREMINDER(){
		return REMINDERS; 
	}
	public static Integer[] returnREGISTRATION(){
		return REGISTRATIONS; 
	}
	public static Integer[] returnVERIFICATION(){
		return VERIFICATIONS; 
	}
	public static Integer[] returnANNOUNCEMENT(){
		return ANNOUNCEMENTS; 
	}
	public static Integer[] returnBULK(){
		return BULKS; 
	}
	public static Integer[] returnMARKETING(){
		return MARKETINGS; 
	}
	public static Integer[] returnTOTAL(){
//	    int lenght=PAYMENTS.length+REMINDERS.length+REGISTRATIONS.length+VERIFICATIONS.length+ANNOUNCEMENTS.length+BULKS.length+MARKETINGS.length;
//		TOTALS=new Integer[lenght];
//		List<Integer> list=new ArrayList<Integer>();
//		addTotals(PAYMENTS,list);
//		addTotals(REMINDERS,list);
//		addTotals(REGISTRATIONS,list);
//		addTotals(VERIFICATIONS,list);
//		addTotals(ANNOUNCEMENTS,list);
//		addTotals(BULKS,list);
//		addTotals(MARKETINGS,list);
//		Object[] objs=list.toArray();
//		TOTALS=new Integer[objs.length];
//		for(int i=0;i<objs.length;i++){
//			TOTALS[i]=(Integer)objs[i];
//		}
		return TOTALS; 
	}

}
