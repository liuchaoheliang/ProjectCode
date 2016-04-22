package com.froad.log;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.froad.log.vo.HeadKey;
import com.froad.log.vo.MerchantLog;
import com.froad.log.vo.OutletLog;



public class MerchantLogs {
	private static Logger logger = LoggerFactory.getLogger(MerchantLogs.class);
	 
	public static void main(String[] args) {
		MerchantLogs log= new MerchantLogs();

	}
	
	private static void merchantHead(MerchantLog log) {
		HeadKey key = new HeadKey();
		key.setMerchant_id(log.getData().getMerchant_id());
		log.setKey(key);
		log.setTime(System.currentTimeMillis());
	}
	
	private static void outletHead(OutletLog log) {
		HeadKey key = new HeadKey();
		key.setOutlet_id(log.getData().getOutlet_id());
		log.setKey(key);
		log.setTime(System.currentTimeMillis());
	} 
	
	//商户添加
	public static Boolean addMerchant(MerchantLog log){
		merchantHead(log);
		log.setAction("MERCHANTADD");
		logger.info(JSON.toJSONString(log));
		return true;
	}
	
	//商户修改
	public static Boolean updateMerchant(MerchantLog log){
		merchantHead(log);
		log.setAction("MERCHANTMODIFY");
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	
	//商户录入审核
	public static Boolean auditMerchant(MerchantLog log){
		merchantHead(log);
		log.setAction("MERCHANTAUDIT");
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	
	//商户解约
	public static Boolean deleteMerchant(String productId,String clientId,Long create_time,
			String action){
		StringBuffer deleteStr = new StringBuffer();
		deleteStr = new StringBuffer();
		deleteStr.append("{");
		deleteStr.append("\"key\": { \"merchant_id\": \"");
		deleteStr.append(productId);
		deleteStr.append("\"},");
		deleteStr.append("\"action\": \"MERCHANTDELETE\",");
		deleteStr.append("\"client_id\": \"");
		deleteStr.append(clientId);
		deleteStr.append("\",");
		deleteStr.append("\"time\": ");
		deleteStr.append(new Date().getTime());
		deleteStr.append(",");
		deleteStr.append("\"data\": {");
		deleteStr.append("\"merchant_id\":\"");
		deleteStr.append(productId);
		deleteStr.append("\",");
		deleteStr.append("\"create_time\":\"");
		deleteStr.append(create_time);
		deleteStr.append("\"");
		deleteStr.append("}");
		deleteStr.append("}");
		deleteStr.append("\n");
		logger.info(deleteStr.toString());
		return true;
	}  
	//门店添加
	public static Boolean addOutlet(OutletLog log){
		outletHead(log);
		log.setAction("OUTLETADD");
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	//门店修改
	public static Boolean updateOutlet(OutletLog log){
		outletHead(log);
		log.setAction("OUTLETMODIFY");
		logger.info(JSON.toJSONString(log));
		return true;
	}
	//门店录入审核
	public static Boolean auditOutlet(OutletLog log){
		outletHead(log);
		log.setAction("OUTLETAUDIT");
		logger.info(JSON.toJSONString(log));
		return true;
	} 
	//门店删除
	public static Boolean deleteOutlet(String productId,String clientId,Long create_time,
			String action){
		StringBuffer deleteStr = new StringBuffer();
		deleteStr = new StringBuffer();
		deleteStr.append("{");
		deleteStr.append("\"key\": { \"outlet_id\": \"");
		deleteStr.append(productId);
		deleteStr.append("\"},");
		deleteStr.append("\"action\": \"OUTLETDELETE\",");
		deleteStr.append("\"client_id\": \"");
		deleteStr.append(clientId);
		deleteStr.append("\",");
		deleteStr.append("\"time\": ");
		deleteStr.append(new Date().getTime());
		deleteStr.append(",");
		deleteStr.append("\"data\": {");
		deleteStr.append("\"outlet_id\":\"");
		deleteStr.append(productId);
		deleteStr.append("\",");
		deleteStr.append("\"create_time\":\"");
		deleteStr.append(create_time);
		deleteStr.append("\"");
		deleteStr.append("}");
		deleteStr.append("}");
		deleteStr.append("\n");
		logger.info(deleteStr.toString());
		return true;
	}  
}
