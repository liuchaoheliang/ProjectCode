package com.froad.util;

import java.util.Date;

public class FormatDate {

	
	public static boolean  compareDate(Date startDate,Date endDate){
		if(endDate.getTime()-startDate.getTime()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public static long expireOpt(int type,String isPrePay,String perDay,String perCount){
		if(type==0){
			if("0".equals(isPrePay)){//按小时计算
				long expireTime=DateUtil.parse(DateUtil.DATE_TIME_FROMAT6, DateUtil.formatDateTime(DateUtil.DATE_TIME_FROMAT6, System.currentTimeMillis())).getTime();
				expireTime=expireTime+Integer.parseInt(perDay)*60*60*1000;
				expireTime=(expireTime-System.currentTimeMillis())/1000;
				return expireTime;
			}else{//按日计算
				long expireTime=DateUtil.parse(DateUtil.DATE_FORMAT1, DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, System.currentTimeMillis())).getTime();
				expireTime=expireTime+Integer.parseInt(perDay)*24*60*60*1000;
				expireTime=(expireTime-System.currentTimeMillis())/1000;
				return expireTime;
			}
		}else{
			if("0".equals(isPrePay)){//按小时计算
				long expireTime=System.currentTimeMillis();
				expireTime=expireTime+Integer.parseInt(perDay)*60*60*1000;
				expireTime=(expireTime-System.currentTimeMillis())/1000;
				return expireTime;
			}else{//按日计算
				long expireTime=System.currentTimeMillis();
				expireTime=expireTime+Integer.parseInt(perDay)*24*60*60*1000;
				expireTime=(expireTime-System.currentTimeMillis())/1000;
				return expireTime;
			}
			
		}
	}
	
	
	
	
	public static void main(String[] args) {
		//String isPrePay,String perDay,String perCount
		
		System.out.println(DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT1,System.currentTimeMillis()+expireOpt(0,"0","1","")*1000));
	}
	

}
