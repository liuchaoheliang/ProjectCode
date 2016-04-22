package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.eclipse.jetty.util.ajax.JSON;

/**
 * 商户活跃度统计
 * @author kevin
 *
 */
public class MainMerchantActive {

	public static void main(String args[]) throws Exception{
	    FileWriter fw = new FileWriter("C:\\Users\\FQ\\Desktop\\青峰\\商户活跃度-11.csv");
//	    OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream("C:\\Users\\FQ\\Desktop\\青峰\\商户活跃度.csv"),"GBK"); 
		//String header = "机构号,二级法人行社,商户累计开户量,商户累计销户量,交易0笔商户数,交易1-5笔商户数,交易5笔以上商\r\n";
	    String header = "机构号,二级法人行社,商户累计开户量,商户累计销户量,交易0笔商户数,交易1-5笔商户数,交易5-10笔商户数,交易10-20笔商户数,交易20-50笔商户数,交易50笔以上商户数\r\n";
		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		fw.write(header);
		Map<String, Map<String, Integer>> orgMerchantMapAll = new HashMap<String, Map<String, Integer>>(); 
		Map<String, String> orgMap = new HashMap<String, String>(); 
		Map<String, String> mantQMap = new HashMap<String, String>(); 
		Map<String, String> mantJMap = new HashMap<String, String>(); 
		Map<String, String> mantCMap = new HashMap<String, String>(); 
		
		
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
		
		//获取审核通过的商户信息(累计签约的商户信息)
		String mantQSql = "SELECT t.merchant_id, t.city_org_code FROM cb_merchant t WHERE t.merchant_status = '0'";
		System.out.println("mantQSql: " + mantQSql); 
		Connection con = dbHelp.conn;
		ResultSet mantQRs = null;
		PreparedStatement mantQPst = con.prepareStatement(mantQSql);
		mantQRs = mantQPst.executeQuery();
		while(mantQRs.next()) {
			mantQMap.put(mantQRs.getString("merchant_id").trim() ,  mantQRs.getString("city_org_code").trim());
		}
		
		//获取解约的商户信息
		String mantJSql = "SELECT t.merchant_id, t.city_org_code FROM cb_merchant t WHERE t.disable_status ='2' and t.merchant_status = '0'";
		System.out.println("mantJSql: " + mantJSql); 
		ResultSet mantJRs = null;
		PreparedStatement mantJPst = con.prepareStatement(mantJSql);
		mantJRs = mantJPst.executeQuery();
		while(mantJRs.next()) {
			mantJMap.put(mantJRs.getString("merchant_id").trim() ,  mantJRs.getString("city_org_code").trim());
		}
		
		//获取二级法人行社下的非银行商户数
		String mantCSql = "SELECT t.city_org_code, count(t.merchant_id) AS merchant_count FROM cb_merchant t WHERE t.merchant_status = '0' AND t.audit_state = '1' GROUP BY t.city_org_code";
		System.out.println("mantCSql: " + mantCSql); 
		ResultSet mantCRs = null;
		PreparedStatement mantCPst = con.prepareStatement(mantCSql);
		mantCRs = mantCPst.executeQuery();
		while(mantCRs.next()) {
			mantCMap.put(mantCRs.getString("city_org_code").trim() ,  mantCRs.getString("merchant_count").trim());
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
		System.out.println("支付成功的非面对面第三方商户订单数量："+subList.size());
		if(subList != null && subList.size() > 0) {
			//遍历二级法人行社机构非面对面有交易成功的会员信息
			for(DBObject subOrder : subList) {
				//System.out.println("subOrder== "+subOrder.toString());
				JSONObject subJson = JSON.parseObject(subOrder.toString());
				String corgCode = subJson.get("s_org_code").toString();
				String merchantId = subJson.get("merchant_id").toString();
				//获取当前机构的当前会员交易统计信息
				Map<String, Integer> memMap = orgMerchantMapAll.get(corgCode);
				if(memMap == null) {
					memMap = new HashMap<String, Integer>();
					memMap.put(merchantId, 1);
				} else {
					Integer count = memMap.get(merchantId);
					if(count == null) {
						//如果当前商户统计信息不存在
						memMap.put(merchantId, 1);
					} else {
						//如果当前商户统计信息存在
						memMap.put(merchantId, count + 1);
					}
				}
				orgMerchantMapAll.put(corgCode, memMap);
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
				String merchantId = ordJson.get("merchant_id").toString();
				String tmOrgCode = mantQMap.get(merchantId);
				if(StringUtils.isEmpty(tmOrgCode)) {
					throw new RuntimeException("当前商户下没有关联机构号,memberId:"+ merchantId);
				}
				Map<String, Integer> memMap = orgMerchantMapAll.get(tmOrgCode);
				if(memMap == null) {
					memMap = new HashMap<String, Integer>();
					memMap.put(merchantId, 1);
				} else {
					Integer count = memMap.get(merchantId);
					if(count == null) {
						//如果当前商户统计信息不存在
						memMap.put(merchantId, 1);
						
					} else {
						//如果当前商户统计信息存在
						memMap.put(merchantId, count + 1);
					}
				}
				orgMerchantMapAll.put(tmOrgCode, memMap);
			}
		}
		
		for(Map.Entry<String, String> orgEntry : orgMap.entrySet()) {
			String oc = orgEntry.getKey();
			Map<String, Integer> tmMerMap = orgMerchantMapAll.get(oc);
			StringBuffer str = new StringBuffer();
            str.append(oc+",");
            str.append(orgEntry.getValue()+",");
			
            //计算商户累计开户量
            int qcount = 0;
            for(Map.Entry<String, String> qentry : mantQMap.entrySet()) {
            	if(oc.equals(qentry.getValue())) {
            		qcount++;
            	}
            }
            str.append(qcount + ",");
            //计算商户累计销户量
            int jcount = 0;
            for(Map.Entry<String, String> jentry : mantJMap.entrySet()) {
            	if(oc.equals(jentry.getValue())) {
            		jcount++;
            	}
            }
            str.append(jcount + ","); 
			
			if(tmMerMap == null) {
				//说明此二级法人行社机构没有商户交易交易信息
				
				if(mantCMap.get(oc) == null) {
					str.append("0,");
				} else {
					str.append(mantCMap.get(oc)+",");
				}
	            
	            str.append("0"+",");
	            str.append("0");
	            str.append("\r\n");
	            fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
				fw.write(str.toString());
			    fw.flush();
			} else {
				//统计交易0笔的商户数
				str.append((Integer.valueOf(mantCMap.get(oc)) - tmMerMap.size() )+",");
				
				//统计每个机构交易1-5笔商户数以及5笔以上的商户数
				int count0 = 0;
				int count1_5 = 0;
				int count5_10 = 0;
				int count10_20 = 0;
				int count20_50 = 0;
				int count50 = 0;
				for (Map.Entry<String, Integer> entry : tmMerMap.entrySet()) {
					Integer ct =  entry.getValue();
				    if(ct > 5) {
				    	count1_5++;
				    } else if(1 <= ct && ct <= 5) {
				    	count0++;
				    } else if(5 < ct && ct <= 10) {
				    	count5_10++;
				    } else if(10 < ct && ct <= 20) {
				    	count10_20++;
				    } else if(20 < ct && ct <=50) {
				    	count20_50++;
				    } else if(50 < ct) {
				    	count50++;
				    }
			    }
	            str.append(count0+",");
	            str.append(count1_5 + ",");
	            str.append(count5_10 + ",");
	            str.append(count10_20 + ",");
	            str.append(count20_50 + ",");
	            str.append(count50);
	            
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
