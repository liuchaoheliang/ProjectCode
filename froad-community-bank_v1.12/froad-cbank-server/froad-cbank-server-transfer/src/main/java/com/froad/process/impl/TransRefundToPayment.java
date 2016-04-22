package com.froad.process.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONObject;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.PaymentMongoEntity;
import com.froad.db.ahui.mysql.AhMyBatisManager;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ModuleID;
import com.froad.process.AbstractProcess;
import com.froad.process.Process;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class TransRefundToPayment extends AbstractProcess{
	public TransRefundToPayment(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	private MongoManager manager = new MongoManager();
	private SimpleID simpleID = new SimpleID(ModuleID.payment);
	Jedis jedisRead = redis.getJedis(RedisManager.read);
	Jedis jedisWrite = redis.getJedis(RedisManager.write);

	@Override
	public void begin() {
		SqlSessionFactory factory = AhMyBatisManager.getSqlSessionFactory();
		Connection conn = null;
		ResultSet rs = null;
		try {
			int count = 0;
			conn = factory.getConfiguration().getEnvironment().getDataSource()
					.getConnection();
			PreparedStatement ps = conn
					.prepareCall("select id,pay_channel from fft_trans");
			ps.setFetchSize(Integer.MIN_VALUE);
			rs = ps.executeQuery();
			String value=null,pc;
			long s = System.currentTimeMillis();
			
			while(rs.next()){
				count++;
				if(count%5000==0){
					System.out.println("count:"+count);
					System.out.println("耗时："+(System.currentTimeMillis() - s));
				}
				value =  rs.getString(1);
				jedisWrite.set("trans:id:"+value,value);
				pc = rs.getString(2);
				if(pc!=null){
					jedisWrite.set("trans:pc:"+value, pc);
				}
			
			}
		}catch(Exception e){
			
		}
	}

	public int getStep(String state) {
		if (state.equals("40") || state.equals("30")|| state.equals("50")){
			return 4;
		}

		if (state.equals("10")) {
			return 1;
		}

		if (state.equals("20")) {
			return 3;
		}
		
		return -1;
	}

	public String getStatus(String state) {
		if (state.equals("1004") || state.equals("1012")) {
			return "4";
		}
		if (state.equals("1013") || state.equals("1005")) {
			return "5";
		}
		if (state.equals("1003") || state.equals("1002")
				|| state.equals("1010") || state.equals("1011")) {
			return "3";
		}

		if (state.equals("1000")) {
			return "1";
		}

		return "";
	}

	public String getPaymentReason(String reason) {
		if (reason.equals("104")) {//结算
			return "0";
		}
		return "2";//支付
	}


	@Override
	public void process() {
		SqlSessionFactory factory = AhMyBatisManager.getSqlSessionFactory();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = factory.getConfiguration().getEnvironment().getDataSource()
					.getConnection();
			PreparedStatement ps = conn
					.prepareCall("select create_time,if(pay_type='10',bill_no,point_bill_no),pay_type,pay_value,pay_type_details,enabled,pay_state,pay_org,from_account_name,from_account_no,to_account_name,to_account_no,from_phone,to_phone,from_user_name,to_user_name,result_code,result_desc,remark,trans_id from fft_pay limit 10000");
			ps.setFetchSize(Integer.MIN_VALUE);
			rs = ps.executeQuery();
			Object value = null;
			String id = null, strValue = null;
			;
			int intValue = 0;
			PaymentMongoEntity entity = null,refund = null;
			long s = System.currentTimeMillis();
			int count = 0;
			DBObject[] object = new BasicDBObject[200];
			DBCollection mongo = super.mongo.getWriteDBCollection("cb_payment");
			while (rs.next()) {
				entity = new PaymentMongoEntity();
				id = rs.getString(20);
//				entity.setOrder_id(id);
				// 查询新ID
				// FIXME
				strValue = jedisRead.get("trans:id:"+id);
				entity.setOrder_id(strValue);

				entity.setAuto_refund("0");
				entity.setClient_id("anhui");
				entity.setCreate_time(rs.getTimestamp(1));
				entity.setPayment_id(simpleID.nextId());
				
				
				entity.setBill_no(rs.getString(2));

				if (rs.getString(3).equals("30")) {
					entity.setPayment_type(2);//现金
				} else {
					entity.setPayment_type(3);//银行积分
				}

				entity.setPayment_value(Double.valueOf(rs.getDouble(4) * 1000)
						.intValue());

				// 支付详情-》贴膜卡等支付渠道
				entity.setPayment_reason("1");
				
				//FIXME get type
				//strValue = type
//				if(strValue.equals("000")){//积分支付
//					entity.setPayment_type_details(0);
//				}else{//redis 查询货币支付渠道
//					strValue = jedisRead.get("trans:pc:"+id);
//					if(strValue!=null){
//						if(strValue.equals("51")){
//							entity.setPayment_type_details(51);
//						}else if(strValue.equals("20")){
//							entity.setPayment_type_details(20);
//						}
//					}
//				}
				
				entity.setIs_enable(true);

				strValue = rs.getString(7);
				intValue = getStep(strValue);
				
				entity.setStep(intValue);
				entity.setPayment_status(getStatus(strValue));
//				entity.setPayment_org_no(rs.getString(8));
				entity.setFrom_account_name(rs.getString(9));
				entity.setFrom_account_no(rs.getString(10));
				entity.setTo_account_name(rs.getString(11));
				entity.setTo_account_no(rs.getString(12));
				entity.setFrom_phone(rs.getString(13));
				entity.setTo_phone(rs.getString(14));
				entity.setFrom_user_name(rs.getString(15));
				entity.setTo_user_name(rs.getString(16));
				entity.setResult_code(rs.getString(17));
				entity.setResult_desc(rs.getString(18));
				entity.setRemark(rs.getString(19));
				entity.setPoint_rate(200);
				object[count%200]=(BasicDBObject)com.mongodb.util.JSON.parse(JSONObject.toJSONString(entity)) ;
				count++;
//				strValue = rs.getString(21);
//				if(!StringUtils.isEmpty(strValue)){
//					refund = new PaymentMongoEntity();
//					try {
//						BeanUtils.copyProperties(refund, entity);
//						refund.setBill_no(strValue);
//						refund.setPayment_reason("1");//退款
//						object[count%200]=(BasicDBObject)com.mongodb.util.JSON.parse(JSONObject.toJSONString(refund)) ;
//						count++;
//						System.out.println("-----");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
				
				if(count%200==0 && count>0){
					mongo.insert(object);
				}
				
				if(entity.getStep()==-1){
					System.out.println(JSONObject.toJSON(entity));
				}
			}
			
			if(count%200!=0){
				mongo.insert(Arrays.copyOfRange(object, 0, count%200));
			}
			
			System.out.println(System.currentTimeMillis() - s);
			System.out.println(count);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exception(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void next() {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		System.setProperty("CONFIG_PATH", "./config");
	}

}
