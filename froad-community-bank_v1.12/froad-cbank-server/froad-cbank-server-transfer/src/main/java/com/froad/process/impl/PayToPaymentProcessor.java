package com.froad.process.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.fastjson.JSONObject;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.PaymentMongoEntity;
import com.froad.db.ahui.mysql.AhMyBatisManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class PayToPaymentProcessor extends AbstractProcess {
	private static final int batchSize = 200;
	private long start;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	DBCollection mongo = super.mongo.getWriteDBCollection("cb_payment");
	
	public PayToPaymentProcessor(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	public PayToPaymentProcessor(){
		super(null,null);
	}
	
	private SimpleID orderId = new SimpleID(ModuleID.order);
	
	final Map<String,String> channel = new HashMap<String,String>(80000);//支付渠道
	final Map<String,String> exchange_rate = new HashMap<String,String>(10000);//积分兑换率
	final Map<String,String> sn_id = new HashMap<String,String>(4000);//订单sn与ID映射
	final Map<String,String> old_new_id = new HashMap<String,String>(80000);
//	final Map<String,String> closed = new HashMap<String,String>(30000);
	final Set refundSet = new HashSet();
	@Override
	public void begin() {//启动映射关系数据加载线程
		new PaymentInit().run();//交易表-支付流水表映射
		new IDMapLoader().run();//交易表ID映射关系加载
	}
	
	@Override
	public void end() {//清理资源
		new Thread(){
			@Override
			public void run() {
				channel.clear();
				exchange_rate.clear();
				sn_id.clear();
				old_new_id.clear();
				refundSet.clear();
			}
		}.start();

	}

	@Override
	public void process() {
		SqlSessionFactory factory = AhMyBatisManager.getSqlSessionFactory();
		DataSource dr = factory.getConfiguration().getEnvironment().getDataSource();
		new TransRefundProcessor(dr).run();//启动退款表fft_trans_refunds转换线程
		new PaymentProcessor(dr).run();//启动支付表fft_payment转换线程
		
	}

	public static void main(String[] args) {
		System.setProperty("CONFIG_PATH", "./config");
		PayToPaymentProcessor p = new PayToPaymentProcessor();
		p.start = System.currentTimeMillis();
		p.begin();
		p.process();
		p.end();
	}
	
	class PaymentInit extends Thread{
		
		public PaymentInit() {
			super();
			setName("订单关联数据初始化线程");
		}

		@Override
		public void run() {
			ConsoleLogger.info("PayToPayment-PaymentInit#开始处理:"+sdf.format(new Date()));
			DataSource dr = AhMyBatisManager.getSqlSessionFactory().getConfiguration().getEnvironment().getDataSource();
			int count = 0;
			String value=null;
			Statement ps = null;
			ResultSet rs  = null;
			Connection conn = null;
			long s = System.currentTimeMillis();
			try {
				conn = dr.getConnection();
				ps = conn.createStatement();
				ps.setFetchSize(Integer.MIN_VALUE);
				rs = ps.executeQuery("select id,sn,pay_channel,exchange_rate,pay_state from fft_trans");
				ConsoleLogger.info("PayToPayment-PaymentInit#查询耗时："+(System.currentTimeMillis() - s)+"毫秒");
				s = System.currentTimeMillis();
				String[] rowData = new String[6];
				while(rs.next()){
					count++;
					if(count%5000==0){
						ConsoleLogger.info("PayToPayment-PaymentInit#完成条数:"+count);
						ConsoleLogger.info("PayToPayment-PaymentInit#耗时："+(System.currentTimeMillis() - s)+"毫秒");
//						synchronized (channel) {
//							channel.notifyAll();
//						}
//						synchronized (sn_id) {
//							sn_id.notify();
//						}
					}
					for(int i=1;i<rowData.length;i++){
						rowData[i] = rs.getString(i);
					}
					value = rowData[4];
					if(value!=null){
						exchange_rate.put(rowData[1], value);
					}
					value = rowData[5];
					if(value!=null&&value.compareTo("30")>0){
						refundSet.add(rowData[1]);
					}
					if(rowData[3]!=null){
						channel.put(rowData[1], rowData[3]);//支付渠道
					}
					sn_id.put(rowData[2],rowData[1]);
				}
				ConsoleLogger.info("PayToPayment-PaymentInit#>>>>>>>>>共加载条数:"+count+",耗时："+(System.currentTimeMillis() - s)+"毫秒,time:"+sdf.format(new Date()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try{
					rs.close();
				}catch(Exception e){}
				try{
					ps.close();
				}catch(Exception e){}
				try{
					conn.close();
				}catch(Exception e){}
				
			}
		}
	}
	class IDMapLoader  extends Thread{
		
		public IDMapLoader() {
			super();
			setName("　　ID映射数据加载线程");
		}

		@Override
		public void run() {
			boolean mock = false;
			DataSource cdr = MyBatisManager.getSqlSessionFactory().getConfiguration().getEnvironment().getDataSource();
			DataSource dr = AhMyBatisManager.getSqlSessionFactory().getConfiguration().getEnvironment().getDataSource();
			
			int count = 0;
			Statement st = null;
			ResultSet rs  = null;
			long s = System.currentTimeMillis();
			Connection conn = null;
			try {
				if(mock){
					conn = dr.getConnection();
				}else{
					conn = cdr.getConnection();
				}
				st = conn.createStatement();
				st.setFetchSize(Integer.MIN_VALUE);
				if(mock){
					rs = st.executeQuery("select id from fft_trans");
				}else{
					rs = st.executeQuery("select old_id,new_id from cb_transfer where type="+TransferTypeEnum.order_id.getCode());
				}
				ConsoleLogger.info("IDMapLoader#开始处理:"+sdf.format(new Date()));
				while(rs.next()){
					count++;
					if(mock){
						old_new_id.put(rs.getString(1), orderId.nextId());
					}else{
						old_new_id.put(rs.getString(1),rs.getString(2).substring(0,12));
					}
					
//					if(count%1000==0){
//						synchronized (old_new_id) {
//							old_new_id.notifyAll();
//						}
//					}
				}
				ConsoleLogger.info("IDMapLoader#>>>>>>>>>共加载条数:"+count+",耗时："+(System.currentTimeMillis() - s)+"毫秒,time:"+sdf.format(new Date()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try{
					rs.close();
				}catch(Exception e){}
				try{
					st.close();
				}catch(Exception e){}
				try{
					conn.close();
				}catch(Exception e){}
				
			}
		}
		
	}

	/**
	 * 支付状态 pay_wait("1000","等待支付"), 
	 * pay_request_success("1002","支付请求发送成功"),
	 * pay_request_fail("1003","支付请求发送失败"), 
	 * pay_success("1004","支付成功"),
	 * pay_fail("1005","支付失败"),
	 * 
	 * refund_request_success("1010","发送退款请求成功"),
	 * refund_request_fail("1011","发送退款请求失败"), 
	 * refund_success("1012","退款或退分成功"),
	 * refund_fail("1013","退款或退分失败"),
	 * 
	 * present_success("1021","积分增送成功"), 
	 * present_failed("1022","积分增送失败");
	 */

	public int getStep(String state) {//根据支付流水状态转换支付步骤
		if (state.equals("1004") || state.equals("1005")
				|| state.equals("1012") || state.equals("1013")) {//支付成功、支付失败、退款成功、退款失败-》支付结果明确
			return 4;
		}

		if (state.equals("1003") || state.equals("1002")
				|| state.equals("1010") || state.equals("1011")) {//支付请求发起成功、支付请求发起失败，退款请求发起成功、退款请求发起失败-》请求发起完毕
			return 3;
		}

		if (state.equals("1000")) {//等待支付
			return 1;
		}
		
		return 2;
	}

	/**
	 * 支付状态 pay_wait("1000","等待支付"), 
	 * pay_request_success("1002","支付请求发送成功"),
	 * pay_request_fail("1003","支付请求发送失败"), 
	 * pay_success("1004","支付成功"),
	 * pay_fail("1005","支付失败"),
	 * 
	 * refund_request_success("1010","发送退款请求成功"),
	 * refund_request_fail("1011","发送退款请求失败"), 
	 * refund_success("1012","退款或退分成功"),
	 * refund_fail("1013","退款或退分失败"),
	 * 
	 * present_success("1021","积分增送成功"), 
	 * present_failed("1022","积分增送失败");
	 */
	public int getPaymentStatusForPayment(int value) {//顺序根据生产数据统计结果列出
		if(value==1004||value>1005){//支付状态大于1005代表发起退款，证明是支付成功的
			return 4;
		}else if(value==1005){//支付失败
			return 5;
		}else if(value==1003){//发起请求失败
			return 3;
		}else if(value==1002){//-发起请求成功
			return 2;
		}else if(value==1000){//-等待支付
			return 1;
		}
		return 0;
	}
	
	public String getRefundStatusForPayment(int value){
		if(value==1012){
			return "4";
		}else if(value==1010){
			return "2";
		}else if(value==1011){
			return "3";
		}else if(value==1013){
			return "5";
		}
		
		return "0";
	}
	
	public int getTicketRefundStepByPaymentState(String paymentState){
		if(paymentState==null||paymentState.equals("4")||paymentState.equals("5")){
			return 4;
		}else if(paymentState.equals("2")||paymentState.equals("3")){
			return 3;
		}else{
			return 4;
		}
	}
	/**
	 * wait("10","未退款"), request_success("20","退款请求成功"),
	 * request_fail("30","退款请求失败"), success("40","退款成功"), fail("50","退款失败");
	 */
	public String getTicketRefundState(String state){
		if(state.equals("40")){
			return "4";
		}else if(state.equals("30")){
			return "3";
		}else if(state.equals("50")){
			return "5";
		}else if(state.equals("20")){
			return "2";
		}else{
			return null;
		}
	}
	public String getPaymentReason(String reason) {
		if (reason.equals("104")) {//结算
			return "0";
		}
		return "2";//支付
	}


	/**
	 * 团购退券转换<br>
	 * 团购券可以单个退款，每一个券退款生成一条团购退券的退款记录插入到fft_trans_refunds，发起退款时会同步更改订单（trans)的状态，退款成功
	 * 或失败后，检查所有退款金额是否等于订单总金额，如果相等则修改订单支付状态为“全额退款”，如果不等，这修改支付状态为“部分退款”
	 * @author admin
	 *
	 */
	class TransRefundProcessor extends Thread{
		private final DataSource dr;
		TransRefundProcessor(DataSource d){
			this.dr = d;
			setName("　　　团购退款转换线程");
		}
		
		@Override
		public void run() {
			ConsoleLogger.info("PayToPayment-fft_trans_refunds#开始处理:"+sdf.format(new Date()));
			Connection conn = null;
			ResultSet rs = null;
			try {
				conn = dr.getConnection();
				PreparedStatement ps = conn
						.prepareCall("select refund.trans_sn,refund.create_time,refund.refund_order_id,refund.type,refund.refund_value,pay.pay_org,refund.account_id,refund.username,refund.result_code,refund.result_desc,refund.sn,refund.state from fft_trans_refunds refund inner join fft_pay pay on refund.pay_sn=pay.sn");
				ps.setFetchSize(Integer.MIN_VALUE);
				rs = ps.executeQuery();
				Object value = null;
				String newTransId = null, strValue = null;
				int intValue = 0;
				PaymentMongoEntity entity = null,refund = null;
				long s = System.currentTimeMillis();
				int count = 0;
				DBObject[] object = new BasicDBObject[batchSize];
				String oldTransId = null;
				while (rs.next()) {
					entity = new PaymentMongoEntity();

					entity.setAuto_refund("0");
					entity.setClient_id(Constans.clientId);
					entity.setCreate_time(rs.getTimestamp(2));
					
					entity.setBill_no(rs.getString(3));
					
					entity.setPayment_value(Double.valueOf(rs.getDouble(5) * 1000).intValue());

					entity.setPayment_reason("1");
					
					entity.setIs_enable(true);

					entity.setPayment_status(getTicketRefundState(rs.getString(12)));//refund.state
					entity.setStep(getTicketRefundStepByPaymentState(entity.getPayment_status()));
					entity.setPayment_org_no(rs.getString(6));//反查支付流水
					entity.setTo_account_no(rs.getString(7));//account_id
					entity.setTo_user_name(rs.getString(8));//user_name
					entity.setResult_code(rs.getString(9));
					entity.setResult_desc(rs.getString(10));
					entity.setPayment_id(rs.getString(11));
					strValue = null; 
					strValue = exchange_rate.get(oldTransId);
					if(strValue!=null){
						entity.setPoint_rate(Integer.valueOf(strValue));
					}
					
					strValue = rs.getString(1);//trans_sn
//					oldTransId = null;
					oldTransId = sn_id.get(strValue);//old_trans_id
					if(oldTransId==null){
						LogCvt.error("PayToPayment-fft_trans_refunds#sn->id对应数据未找到,sn:"+strValue);
//						if(oldTransId==null)
//						synchronized (sn_id) {//等待SN-ID映射数据加载
//							try {
//								sn_id.wait(50);
//							} catch (InterruptedException e) {
//							}
//						}
					}
					
//					newTransId = null;
//					while(newTransId==null){
						newTransId = old_new_id.get(oldTransId);
						if(newTransId==null){
							LogCvt.error("PayToPayment-fft_trans_refunds#old_id->new_id对应数据未找到，old_id:"+oldTransId+",data:"+JSONObject.toJSONString(entity));
//							synchronized (old_new_id) {//等旧-新ID映射加载
//								try {
//									old_new_id.wait(50);
//								} catch (InterruptedException e) {
//								}
//							}
						}
//					}
					strValue = rs.getString(4);//TYPE
					
					if(strValue.equals("20")){//积分支付
						entity.setPayment_type(3);//银行积分
						entity.setPayment_type_details(0);
					}else{//根据生产环境统计，目前资金支付的只有贴膜卡和快捷支付，故其他设计类型不做处理
						entity.setPayment_type(2);//现金
						strValue = channel.get(oldTransId);
						if(strValue.equals("51")){
							entity.setPayment_type_details(51);
						}else if(strValue.equals("20")){
							entity.setPayment_type_details(20);
						}
					}
					entity.setOrder_id(newTransId);
					object[count%batchSize]=(BasicDBObject)com.mongodb.util.JSON.parse(JSONObject.toJSONString(entity)) ;
					count++;
					
					if(count%batchSize==0 && count>0){
						mongo.insert(object);
						ConsoleLogger.info("完成条数："+count);
					}
					
					if(entity.getStep()==2){//中间状态，需要人工处理
						LogCvt.error("PayToPayment-fft_trans_refunds#退款中："+JSONObject.toJSON(entity).toString());
					}
				}
				
				if(count%batchSize!=0){
					mongo.insert(Arrays.copyOfRange(object, 0, count%batchSize));
				}
				
				ConsoleLogger.info("PayToPayment-fft_trans_refunds#>>>>>>>>>共转换条数:"+count+",耗时："+(System.currentTimeMillis() - s)+"毫秒,time:"+sdf.format(new Date()));

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
		
	}
	
	class PaymentProcessor extends Thread{
		private final DataSource dr;
		public PaymentProcessor(DataSource d) {
			this.dr = d;
			setName("　　　支付流水转换线程");
		}
		
		public void run(){
			Connection conn = null;
			ResultSet rs = null;
			try {

				ConsoleLogger.info("PayToPayment-fft_pay#开始处理:"+sdf.format(new Date()));
				conn = dr.getConnection();
				PreparedStatement ps = conn.prepareCall("select create_time,if(pay_type='10',bill_no,point_bill_no),pay_type,pay_value,pay_type_details,enabled,pay_state,pay_org,from_account_name,from_account_no,to_account_name,to_account_no,from_phone,to_phone,from_user_name,to_user_name,result_code,result_desc,remark,trans_id,if(pay_type='10',refund_order_id,refund_point_no),sn  from fft_pay");
				ps.setFetchSize(Integer.MIN_VALUE);
				rs = ps.executeQuery();
				String oldTransId = null,newTransId = null, strValue = null,strValue2=null;
				int payState = 0, payStateOld=0;
				PaymentMongoEntity entity = null,refund = null;
				long s = System.currentTimeMillis();
				int count = 0;
				DBObject[] object = new BasicDBObject[batchSize];
				while (rs.next()) {
					entity = new PaymentMongoEntity();
					
					//set common field
//					entity.setAuto_refund("0");
					entity.setClient_id(Constans.clientId);
					entity.setCreate_time(rs.getTimestamp(1));
					entity.setPayment_id(rs.getString(22));
					
					entity.setBill_no(rs.getString(2));

					//set payment type start
					if (rs.getString(3).equals("10")) {
						entity.setPayment_type(2);
					} else {
						entity.setPayment_type(3);
					}
					//set payment type end

					entity.setPayment_value(Double.valueOf(rs.getDouble(4) * 1000)
							.intValue());
					
					//set payment reason
					strValue = rs.getString(5);
					entity.setPayment_reason(getPaymentReason(strValue));
					
					//
					if(strValue.equals("000")){//积分支付
						entity.setPayment_type_details(0);
					}else{//查询货币支付渠道
						// 支付详情-》贴膜卡等支付渠道
						strValue = null;
//						while(strValue==null){
							strValue = channel.get(oldTransId);
							if(strValue==null){
								LogCvt.error("PayToPayment-fft_pay#支付渠道未找到,oldTransId:"+strValue);
//							synchronized (channel) {
//								try {
//									channel.wait(50);
//								} catch (InterruptedException e) {
//								}
//							}
							}
//						}
						
						if(strValue!=null){
							if(strValue.equals("51")){
								entity.setPayment_type_details(51);
							}else if(strValue.equals("20")){
								entity.setPayment_type_details(20);
							}
						}
					}
					
					entity.setIs_enable(rs.getBoolean(6));

					payStateOld = rs.getInt(7);//pay_state
					payState = getPaymentStatusForPayment(payStateOld);
					entity.setPayment_status(Integer.toString(payState));
					entity.setStep(payState>3?4:payState==1?1:2);
					
					entity.setPayment_org_no(rs.getString(8));
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
					strValue = exchange_rate.get(oldTransId);
					if (strValue!=null) {
						entity.setPoint_rate(Integer.valueOf(strValue));
					}

					strValue = rs.getString(21);//退款id
					if(refundSet.contains(oldTransId)&&entity.getIs_enable()&&!StringUtils.isEmpty(strValue)){//退款单号不为空
						refund = new PaymentMongoEntity();
						try {
							BeanUtils.copyProperties(refund, entity);
							refund.setPayment_status(getRefundStatusForPayment(payStateOld));
							refund.setPayment_reason("1");//退款
							refund.setTo_account_name(refund.getFrom_account_name());
							refund.setTo_account_no(refund.getFrom_account_no());
							refund.setFrom_account_name(entity.getTo_account_name());
							refund.setFrom_account_no(entity.getTo_account_no());
							refund.setAuto_refund("0");
							entity.setAuto_refund(refund.getPayment_status().equals("4")?"2":"1");
							
							refund.setFrom_user_name(entity.getTo_user_name());
							refund.setTo_user_name(entity.getFrom_user_name());
							refund.setPayment_id(strValue);
							object[count%batchSize]=(BasicDBObject)com.mongodb.util.JSON.parse(JSONObject.toJSONString(refund)) ;
							count++;

							if(count%batchSize==0&& count>0){
								mongo.insert(object);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if(!StringUtils.isEmpty(strValue)){//FIX - 
						entity.setRefund_points_bill_no(strValue);
						if(payStateOld>1005){
							if(payStateOld==1012){
								entity.setAuto_refund("2");
							}else{
								entity.setAuto_refund("1");
							}
						}else{
							entity.setAuto_refund("0");
						}
					}else{
						entity.setAuto_refund("0");
					}
					
					//set new ID
					oldTransId = rs.getString(20);
					newTransId = null;
//					while(newTransId==null){
						newTransId = old_new_id.get(oldTransId);
						if(newTransId==null){
							LogCvt.error("PayToPayment-fft_pay#old_id->new_id对应数据未找到，old_id:"+oldTransId+",data:"+JSONObject.toJSONString(entity));
//							synchronized (old_new_id) {
//								try {
//									old_new_id.wait(50);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//							}
						}
//					}
					entity.setOrder_id(newTransId);

					object[count%batchSize]=(BasicDBObject)com.mongodb.util.JSON.parse(JSONObject.toJSONString(entity)) ;
					count++;
					
					if(count%batchSize==0 && count>0){
						mongo.insert(object);
						if(count%5000==0)
						ConsoleLogger.info("PayToPayment-fft_pay#完成条数:"+count);
					}
					
					if(entity.getStep()==2){
						LogCvt.error("PayToPayment-fft_pay#支付中订单："+JSONObject.toJSON(entity).toString());
					}
				}
				
				if(count%batchSize!=0){
					mongo.insert(Arrays.copyOfRange(object, 0, count%batchSize));
				}
				
				ConsoleLogger.info("PayToPayment-fft_pay#>>>>>>>>>共转换条数:"+count+",耗时："+(System.currentTimeMillis() - s)+"毫秒,time:"+sdf.format(new Date()));

			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (Exception e) {
				}
				try{
					conn.close();
				}catch(Exception e){
				}
			}
		
			ConsoleLogger.info("PayToPayment#总计耗时:"+(System.currentTimeMillis()-start)+"毫秒");
		}
	}

}
