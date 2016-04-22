package com.froad.db.mongo.test;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;


/**
 * 统计银行会员活跃度
 * @author kevin
 *
 */
public class MainMemberActive {

	
	public static void main(String args[]) throws Exception {

		FileWriter fw = new FileWriter("C:\\Users\\FQ\\Desktop\\青峰\\会员活跃度-11.csv");
//		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream("C:\\Users\\FQ\\Desktop\\青峰\\会员活跃度.csv"),"GB2312"); 
		String header = "机构号,二级法人行社,交易1-5笔用户数,5笔以上用户数\r\n";
		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		fw.write(header);
		Map<String, Map<String, Integer>> orgMemberMapAll = new HashMap<String, Map<String, Integer>>(); 
		Map<String, String> orgMap = new HashMap<String, String>(); 
		Map<String, String> mantMap = new HashMap<String, String>(); 
		
		//获取系统所有二级法人行社信息
		String orgSql = "SELECT t.org_code, t.org_name, t.province_agency FROM cb_org t WHERE t.org_level='2' and t.org_name not like '%村镇%' and t.org_name not like '%测试%'";
		System.out.println("orgSql: " + orgSql);
		DBHelper dbHelp = new DBHelper(orgSql);
		ResultSet orgRs = null;
		PreparedStatement orgPst = dbHelp.pst;
		orgRs = orgPst.executeQuery();
		while (orgRs.next()) {
			String orgCode = orgRs.getString("org_code");
			orgMap.put(orgCode, orgRs.getString("org_name"));
		}
		
		//获取审核通过的商户信息
		String mantSql = "SELECT t.merchant_id, t.city_org_code FROM cb_merchant t WHERE  t.merchant_status = '0'";
		System.out.println("mantSql: " + mantSql); 
		Connection con = dbHelp.conn;
		ResultSet mantRs = null;
		PreparedStatement mantPst = con.prepareStatement(mantSql);
		mantRs = mantPst.executeQuery();
		while(mantRs.next()) {
			mantMap.put(mantRs.getString("merchant_id") ,  mantRs.getString("city_org_code"));
		}
		
		String start_create_time="2014-07-30 00:00:00";
		String end_create_time="2015-08-10 23:59:59";
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Date statrt_time= sdf.parse(start_create_time);
	    Date end_time= sdf.parse(end_create_time);
		//获取支付成功的非面对面支付子订单集合
		DBCollection sub_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_suborder_product");
		BasicDBObject subWhere = new BasicDBObject();
		//时间 2015-07-26 06:38:24
	    
	    BasicDBList subValues1 = new BasicDBList();
	    //subValues.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,statrt_time.getTime())));
	    //subValues.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,end_time.getTime())));
		//subWhere.put(QueryOperators.AND,subValues);
	    
	    subValues1.add(new BasicDBObject("type", "1"));
	    subValues1.add(new BasicDBObject("type", "3"));
	    subWhere.put(QueryOperators.OR,subValues1);
	    subWhere.put("order_status", "6");
	    
		List<DBObject> subList = sub_dbConnection.find(subWhere).toArray();
		System.out.println("支付成功的非面对面的第三方商户订单数量："+subList.size());
		if(subList != null && subList.size() > 0) {
			//遍历二级法人行社机构非面对面有交易成功的会员信息
			for(DBObject subOrder : subList) {
				//System.out.println("subOrder== "+subOrder.toString());
				JSONObject subJson = JSON.parseObject(subOrder.toString());
				String corgCode = subJson.get("s_org_code").toString();
				String memberCode = subJson.get("member_code").toString();
				//获取当前机构的当前会员交易统计信息
				Map<String, Integer> memMap = orgMemberMapAll.get(corgCode);
				if(memMap == null) {
					memMap = new HashMap<String, Integer>();
					memMap.put(memberCode, 1);
				} else {
					Integer count = memMap.get(memberCode);
					if(count == null) {
						//如果当前会员统计信息不存在
						memMap.put(memberCode, 1);
					} else {
						//如果当前会员统计信息存在
						memMap.put(memberCode, count + 1);
					}
				}
				orgMemberMapAll.put(corgCode, memMap);
			}
		}
		
		
		
		//获取所有支付成功的面对面订单
		DBCollection ord_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_order");
		BasicDBObject ordWhere = new BasicDBObject();
	    BasicDBList ordValues = new BasicDBList();
	    //ordValues.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,statrt_time.getTime())));
	    //ordValues.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,end_time.getTime())));
	    //ordWhere.put(QueryOperators.AND,ordValues);
	    ordWhere.put("is_qrcode", 1);
	    ordWhere.put("order_status", "6");
		List<DBObject> orderList = ord_dbConnection.find(ordWhere).toArray();
		System.out.println("支付成功的面对面订单数量："+orderList.size());
		//System.out.println(orgRs.getString("org_code") + ":" + orgRs.getString("org_name"));
		if(orderList != null && orderList.size() > 0) {
			for(DBObject order : orderList) {
				JSONObject ordJson = JSON.parseObject(order.toString());
				String memberCode = ordJson.get("member_code").toString();
				String merchantId = ordJson.get("merchant_id").toString();
				String tmOrgCode = mantMap.get(merchantId);
				if(StringUtils.isEmpty(tmOrgCode)) {
					throw new RuntimeException("当前商户下没有关联机构号,memberId:"+ merchantId);
				}
				Map<String, Integer> memMap = orgMemberMapAll.get(tmOrgCode);
				if(memMap == null) {
					memMap = new HashMap<String, Integer>();
					memMap.put(memberCode, 1);
				} else {
					Integer count = memMap.get(memberCode);
					if(count == null) {
						//如果当前会员统计信息不存在
						memMap.put(memberCode, 1);
						
					} else {
						//如果当前会员统计信息存在
						memMap.put(memberCode, count + 1);
					}
				}
				orgMemberMapAll.put(tmOrgCode, memMap);
			}
		}
		
		
		
		for(Map.Entry<String, String> orgEntry : orgMap.entrySet()) {
			String oc = orgEntry.getKey();
			Map<String, Integer> tmMemMap = orgMemberMapAll.get(oc);
			//说明此二级法人行社机构没有会员交易信息
			StringBuffer str = new StringBuffer();
            str.append(oc+",");
            str.append(orgEntry.getValue()+",");
			if(tmMemMap == null) {
	            str.append("0"+",");
	            str.append("0");
	            str.append("\r\n");
	            fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
				fw.write(str.toString());
			    fw.flush();
			} else {
				//统计每个机构交易1-5笔用户数以及5笔以上用户数
				int lowCount = 0;
				int highCount = 0;
				for (Map.Entry<String, Integer> entry : tmMemMap.entrySet()) {
					Integer ct =  entry.getValue();
				    if(ct > 5) {
				    	highCount++;
				    } else if(1 <= ct && ct<= 5){
				    	lowCount++;
				    }
			    }
	            str.append(lowCount+",");
	            str.append(highCount);
	            str.append("\r\n");
	            fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
				fw.write(str.toString());
			    fw.flush();
			}
		}
		
		fw.close();
		System.out.println("成功。。。。。。。。。。。。。。。");
	}
}
