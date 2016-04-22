/**
 * Project Name:froad-cbank-server-task
 * File Name:AccountCheckingOfDetails.java
 * Package Name:com.froad.timer.task.support
 * Date:2015年8月27日上午11:07:40
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.timer.task.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.froad.cons.AccountCheckingConst;
import com.froad.db.mongo.PaymentMongoService;
import com.froad.db.mongo.SubOrderMongoService;
import com.froad.db.mongo.impl.PaymentMongoServiceImpl;
import com.froad.db.mongo.impl.SubOrderMongoServiceImpl;
import com.froad.po.mongo.PaymentMongo;
import com.froad.util.Arith;
import com.froad.util.IOTXTAppend;

/**
 * ClassName:AccountCheckingOfDetails
 * Reason:	 TODO ADD REASON.
 * Date:     2015年8月27日 上午11:07:40
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class AccountCheckingOfDetails {
	
	private PaymentMongoService paymentMongoService = new  PaymentMongoServiceImpl();

	/**
	 * 详情对账处理
	 * doCheck:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年8月27日 上午11:08:36
	 * @param pm
	 * @param uri
	 *
	 */
	public void doCheck(PaymentMongo pm,String uri){
		//交易编码
		String tranCode = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		
		//银行积分支付
		if (pm.getPaymentType() == 3 && pm.getPaymentReason().equals("2")) {
			tranCode = AccountCheckingConst.YHJFZF_POINT;
		}
		// 银行积分退分
		else if (pm.getPaymentType() == 3 && (pm.getPaymentReason().equals("1") || pm.getPaymentReason().equals("5"))) {
			tranCode = AccountCheckingConst.YHJFTF_POINT;
		}
		// 联盟积分消费
		else if (pm.getPaymentType() == 1 && pm.getPaymentReason().equals("2")) {
			tranCode = AccountCheckingConst.LMJFXF_POINT;
		}
		// 联盟积分退分
		else if (pm.getPaymentType() == 1 && (pm.getPaymentReason().equals("1") || pm.getPaymentReason().equals("5"))) {
			tranCode = AccountCheckingConst.LMJFTF_POINT;
		}
		// 联盟积分赠送
		else if (pm.getPaymentType() == 1 && pm.getPaymentReason().equals("3")) {
			tranCode = AccountCheckingConst.LMJFZS_POINT;
		}
		//现金支付：贴膜卡支付
		else if (pm.getPaymentType() == 2 && pm.getPaymentReason().equals("2") && pm.getPaymentTypeDetails() == 2) {
			tranCode = AccountCheckingConst.FOILCARD_PAY;
		}
		// 现金支付：快捷支付
		else if (pm.getPaymentType() == 2 && pm.getPaymentReason().equals("2") && pm.getPaymentTypeDetails() == 51) {
			tranCode = AccountCheckingConst.SHORTCUT_PAY;
		}
		// 现金支付：现金退款
		else if (pm.getPaymentType() == 2 && pm.getPaymentReason().equals("1")) {
			tranCode = AccountCheckingConst.CASHRETURN_PAY;
		}
		//现金支付：即时支付
		else if (pm.getPaymentType() == 2 && pm.getPaymentReason().equals("0") && pm.getPaymentTypeDetails() == 55) {
			tranCode = AccountCheckingConst.IMMEDIATE_PAY;
		}
		
		
		//需要采集的字段
		// (现金账单号)bill_no,(积分账单号)bill_no,(子订单号),(现金交易额)payment_value,(积分交易额)payment_value,(订单时间)create_time,(交易类型),
		// 供应商编号,状态码,会员标识,对账日期,(现金银行组号)payment_org_no,积分机构号
		String[] sbArray = new String[13];
		//现金支付
		if(pm.getPaymentType() == 2){
			sbArray[0] = pm.getBillNo();
			sbArray[1] = "";
			sbArray[3] = String.valueOf(Arith.div(Double.parseDouble(pm.getPaymentValue().toString()), 1000));
			sbArray[4] = String.valueOf(Arith.div(0.00, 1000));
			sbArray[11] = pm.getPaymentOrgNo();  //现金银行组号
			sbArray[12] = "";  //积分机构号
		//积分支付
		}else{
			sbArray[0] = "";
			sbArray[1] = pm.getBillNo();
			sbArray[3] = String.valueOf(Arith.div(0.00, 1000));
			sbArray[4] = String.valueOf(Arith.div(Double.parseDouble(pm.getPaymentValue().toString()), 1000));
			sbArray[11] = "";  //现金银行组号
			//银行积分
			if(pm.getPaymentType() == 3){
				sbArray[12] = pm.getPaymentOrgNo();  //积分机构号
			//联盟积分
			}else{
				sbArray[12] = "210";  //积分机构号
			}
		}
		sbArray[2] = ""; 	//子订单号
		sbArray[5] = sdf.format(pm.getCreateTime());
		sbArray[6] = tranCode;
		sbArray[7] = pm.getPaymentOrgNo();  //供应商编号
		sbArray[8] = pm.getPaymentStatus();  //状态码
		sbArray[9] = paymentMongoService.queryByOrderId(pm.getOrderId()).getMemberCode().toString();  
		sbArray[10] =  sdf2.format(new Date());  
		IOTXTAppend.appendData(uri, sbArray);
	}
}
