/**
 * Project Name:froad-cbank-server-task
 * File Name:AccountCheckingOfPoint.java
 * Package Name:com.froad.timer.task.support
 * Date:2015年8月25日上午11:40:33
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.timer.task.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.froad.cons.AccountCheckingConst;
import com.froad.cons.AccountCheckingConst.AccountFileName;
import com.froad.db.mongo.PaymentMongoService;
import com.froad.db.mongo.impl.PaymentMongoServiceImpl;
import com.froad.enums.PaymentMethod;
import com.froad.po.mongo.PaymentMongo;
import com.froad.util.Arith;
import com.froad.util.IOTXTAppend;

/**
 * ClassName:AccountCheckingOfPoint
 * Reason:	 TODO ADD REASON.
 * Date:     2015年8月25日 上午11:40:33
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class AccountCheckingOfPoint {

	private PaymentMongoService paymentMongoService = new  PaymentMongoServiceImpl();
	
	/**
	 * 
	 * doCheck:进行流水对账数据处理.
	 *
	 * @author Zxy
	 * 2015年8月25日 上午11:41:06
	 * @param pm
	 *
	 */
	public void doCheck(PaymentMongo pm,String uri){
		//交易编码
		String tranCode = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		//银行积分支付
		if(pm.getPaymentType() == 3 && pm.getPaymentReason().equals("2")){
			String paymentMethod = paymentMongoService.queryByOrderId(pm.getOrderId()).getPaymentMethod();
			if(PaymentMethod.bankPointsAndCash.getCode().equals(paymentMethod)){
				tranCode = AccountCheckingConst.YHJFJXJ;
			}else{
				tranCode = AccountCheckingConst.YHJFZF_POINT;
			}
			
		}
		//银行积分兑换
//		if(pm.getPaymentType() == 3 && pm.getPaymentReason().equals("") && pm.getPaymentTypeDetails()==null){
//			tranCode = AccountCheckingConst.YHJFDH_POINT;
//		}
		//银行积分赠送
//		if(pm.getPaymentType() == 3 && pm.getPaymentReason().equals("3")){
//			tranCode = AccountCheckingConst.YHJFZS_POINT;
//		}
		//银行积分退分
		else if(pm.getPaymentType() == 3 && (pm.getPaymentReason().equals("1") || pm.getPaymentReason().equals("5"))){
			tranCode = AccountCheckingConst.YHJFTF_POINT;
		}
		//联盟积分消费
		else if(pm.getPaymentType() == 1 && pm.getPaymentReason().equals("2")){
			String paymentMethod = paymentMongoService.queryByOrderId(pm.getOrderId()).getPaymentMethod();
			if(PaymentMethod.froadPointsAndCash.getCode().equals(paymentMethod)){
				tranCode = AccountCheckingConst.LMJFJXJ;
			}else{
				tranCode = AccountCheckingConst.LMJFXF_POINT;
			}
		}
		//联盟积分退分
		else if(pm.getPaymentType() == 1 &&  (pm.getPaymentReason().equals("1") || pm.getPaymentReason().equals("5"))){
			tranCode = AccountCheckingConst.LMJFTF_POINT;
		}
		//联盟积分赠送
		else if(pm.getPaymentType() == 1 && pm.getPaymentReason().equals("3")){
			tranCode = AccountCheckingConst.LMJFZS_POINT;
		}
		//联盟积分充值
//		if(pm.getPaymentType() == 1 && pm.getPaymentReason().equals("") && pm.getPaymentTypeDetails()==null){
//			tranCode = AccountCheckingConst.LMJFCZ_POINT;
//		}
		//联盟积分转增
//		if(pm.getPaymentType() == 1 && pm.getPaymentReason().equals("") && pm.getPaymentTypeDetails()==null){
//			tranCode = AccountCheckingConst.LMJFZZ_POINT;
//		}
		
		//需要采集的字段
		// (请求流水)payment_id,(账单号)bill_no,(交易额)payment_value,(订单时间)create_time,(交易类型),
		// 供应商编号,(结果码)result_code,会员标识,对账日期,(银行组号)payment_org_no,银行名称,积分机构号,前端平台号,前端平台名称
		String[] sbArray = new String[14];
		Map<String, String> client = AccountCheckingConst.getClientInfo(pm.getClientId());
		sbArray[0]= pm.getPaymentId();
		sbArray[1] = pm.getBillNo();
		sbArray[2] = String.valueOf(Arith.div(Double.parseDouble(pm.getPaymentValue().toString()), 1000));
		sbArray[3] = sdf.format(pm.getCreateTime());
		sbArray[4] = tranCode;
		sbArray[5] = "";	//供应商编号
		sbArray[6] = pm.getResultCode();
		sbArray[7] = pm.getFromUserName();//paymentMongoService.queryMemberCodeByOrderId(pm.getOrderId());  //会员标示
		sbArray[8] = sdf2.format(new Date());
		sbArray[9] = client.get("bank_id");
		sbArray[10] = client.get("bank_name");
		sbArray[11] = pm.getPaymentOrgNo(); //积分机构号
		sbArray[12] = client.get("point_partner_no");//前端平台号
		sbArray[13] = client.get("bank_name");
		IOTXTAppend.appendData(uri, sbArray);
	}
}
