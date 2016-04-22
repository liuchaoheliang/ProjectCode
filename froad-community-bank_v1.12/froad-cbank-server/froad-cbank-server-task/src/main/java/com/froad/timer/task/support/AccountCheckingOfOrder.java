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
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.froad.cons.AccountCheckingConst;
import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.PaymentMongoService;
import com.froad.db.mongo.impl.OrderMongoServiceImpl;
import com.froad.db.mongo.impl.PaymentMongoServiceImpl;
import com.froad.enums.PaymentMethod;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.PaymentMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.IOTXTAppend;

/**
 * ClassName:AccountCheckingOfDetails
 * Reason:	 生成 社区银行详细支付文件
 * Date:     2015年10月27日 上午11:07:40
 * @author   open
 * @version  
 * @see 	 
 */
public class AccountCheckingOfOrder {
	
	private PaymentMongoService paymentMongoService = new PaymentMongoServiceImpl();
	private OrderMongoService orderMongoService = new  OrderMongoServiceImpl();

	/**
	 * 支付详情对账处理
	 * doCheck:(这里用一句话描述这个方法的作用).
	 *
	 * @author open
	 * 2015年10月27日 上午11:08:36
	 * @param orderList 订单集合（不重复）
	 * @param uri 输出的文件路径
	 *
	 */
	public void doCheck(List<String> orderList,String uri){
		
		if(Checker.isEmpty(orderList)){
			return;
		}
		
		List<PaymentMongo> pmList = null;
		PaymentMongo pm = null;
			
		//定义“对账日期”转换工具类，格式：YYYYMMDD
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		//16 对账日期
		String accountDate = sdf2.format(new Date());
		
		//遍历每一个订单号
		for(String orderId : orderList){
			
			//查询一个订单号对应的支付流水
			pmList = paymentMongoService.queryPaymentOfDaily(orderId);
			pm = pmList.get(0);
			
			//0 现金账单号 TODO
			String cashBillNo = "";
			//1 积分账单号
			String pointBillNo = "";
			
			//17 现金银行组号  
			String paymentOrgNo_bank = "";
			//18 积分机构号  
			String paymentOrgNo_point = "";
			
			//遍历订单号对应的支付流水集合，来确定以上四个字段的值
			for(PaymentMongo tpm : pmList){
				if(tpm.getPaymentType() == 2){//现金 支付
					cashBillNo = tpm.getBillNo();
					paymentOrgNo_bank = tpm.getPaymentOrgNo();
				}else{
					pointBillNo = tpm.getBillNo();
					paymentOrgNo_point = tpm.getPaymentOrgNo();
				}
			}
			
			//根据订单号查询订单
			OrderMongo order =  orderMongoService.findByOrderId(orderId);
			
			//4 支付类型
			String paymentMethod = getPaymentMethodCode(order.getPaymentMethod(),pm);
			
			//如果是面对面订单  或 开通VIP
			if(order.getIsQrcode() == 1 || order.getIsVipOrder() == 1){
				//3 订单时间
				String createTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, order.getCreateTime());
				//6 产品名称
				String productName = order.getProductName();
				//7 产品编码
				String productId = order.getProductId()+order.getOrderId();
				//9 普通价购买数量
				int count = 1;
				//10 VIP价数量
				int vipCount = 0;
				//11 产品普通价
				double price = Arith.div(order.getTotalPrice(), 1000);
				//12 产品VIP价
				double vipPrice = 0;
				//13 产品总售价
				double totalMoney = Arith.div(order.getTotalPrice(), 1000);
				
				//15 会员标识
				Long memberCode = order.getMemberCode();
				
				//构造数据行数组
				String[] sbArray = new String[19];
				sbArray[0] = cashBillNo;//0 现金账单号
				sbArray[1] = pointBillNo;//1 积分账单号
				sbArray[2] = orderId;
				sbArray[3] = createTime;
				sbArray[4] = paymentMethod;//
				sbArray[5] = "";//5 供应商编号 (暂无内容)
				sbArray[6] = productName;
				sbArray[7] = productId;
				sbArray[8] = "";//8 产品单位  (暂无内容)
				sbArray[9] = String.valueOf(count);
				sbArray[10] = String.valueOf(vipCount);
				sbArray[11] = String.valueOf(price);
				sbArray[12] = String.valueOf(vipPrice);
				sbArray[13] = String.valueOf(totalMoney);
				sbArray[14] = pm.getResultCode();//14 状态码 
				sbArray[15] = String.valueOf(memberCode);
				sbArray[16] = accountDate;//16 对账日期
				sbArray[17] = paymentOrgNo_bank;//17 现金银行组号 
				sbArray[18] = paymentOrgNo_point;//18 积分机构号
				
				//将内容追加到指定的文件中
				IOTXTAppend.appendData(uri, sbArray);
				
				continue;
			}
			
			//查询子订单
			List<SubOrderMongo> subOrderList = orderMongoService.findSubOrderListByOrderId(orderId);
			
			//判断子订单是否为空
			if(Checker.isEmpty(subOrderList)){
				continue;
			}
			
			//遍历子订单
			for(SubOrderMongo subOrder : subOrderList){
				//3 订单时间
				String createTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, subOrder.getCreateTime());
				
				//判断订单中的商品信息是否为空
				if(Checker.isEmpty(subOrder.getProducts())){
					continue;
				}
				
				//遍历订单中的商品信息
				for(ProductMongo product : subOrder.getProducts()){
					//6 产品名称
					String productName = product.getProductName();
					//7 产品编码
					String productId = product.getProductId()+subOrder.getSubOrderId();
					
					//9 普通价购买数量
					int count = product.getQuantity();
					//10 VIP价数量
					int vipCount = product.getVipQuantity();
					//11 产品普通价
					double price = Arith.div(product.getMoney(), 1000);
					//12 产品VIP价
					double vipPrice = Arith.div(product.getVipMoney(), 1000);
					//13 产品总售价
					double totalMoney = Arith.add(Arith.mul(price, count), Arith.mul(vipPrice, vipCount));
					
					//15 会员标识
					Long memberCode = subOrder.getMemberCode();
					
					//构造数据行数组
					String[] sbArray = new String[19];
					sbArray[0] = cashBillNo;//0 现金账单号
					sbArray[1] = pointBillNo;//1 积分账单号
					sbArray[2] = orderId;
					sbArray[3] = createTime;
					sbArray[4] = paymentMethod;//
					sbArray[5] = "";//5 供应商编号 (暂无内容)
					sbArray[6] = productName;
					sbArray[7] = productId;
					sbArray[8] = "";//8 产品单位  (暂无内容)
					sbArray[9] = String.valueOf(count);
					sbArray[10] = String.valueOf(vipCount);
					sbArray[11] = String.valueOf(price);
					sbArray[12] = String.valueOf(vipPrice);
					sbArray[13] = String.valueOf(totalMoney);
					sbArray[14] = pm.getResultCode();//14 状态码 
					sbArray[15] = String.valueOf(memberCode);
					sbArray[16] = accountDate;//16 对账日期
					sbArray[17] = paymentOrgNo_bank;//17 现金银行组号 
					sbArray[18] = paymentOrgNo_point;//18 积分机构号
					
					//将内容追加到指定的文件中
					IOTXTAppend.appendData(uri, sbArray);
				}
			}
		}
	}
	
	/**
	 * 转换支付类型
	 * @param paymentMethod
	 * @return
	 */
	public String getPaymentMethodCode(String paymentMethod,PaymentMongo pm ){
		String code = null;
		if(StringUtils.equals(PaymentMethod.cash.getCode(), paymentMethod)){
			//现金支付：贴膜卡支付
			if (pm.getPaymentType() == 2 && pm.getPaymentReason().equals("2") && pm.getPaymentTypeDetails() == 2) {
				code = AccountCheckingConst.FOILCARD_PAY;
			}
			// 现金支付：快捷支付
			else if (pm.getPaymentType() == 2 && pm.getPaymentReason().equals("2") && pm.getPaymentTypeDetails() == 51) {
				code = AccountCheckingConst.SHORTCUT_PAY;
			}
			// 现金支付：现金退款
			else if (pm.getPaymentType() == 2 && pm.getPaymentReason().equals("1")) {
				code = AccountCheckingConst.CASHRETURN_PAY;
			}
			//现金支付：即时支付
			else if (pm.getPaymentType() == 2 && pm.getPaymentReason().equals("0") && pm.getPaymentTypeDetails() == 55) {
				code = AccountCheckingConst.IMMEDIATE_PAY;
			}
		}else if(StringUtils.equals(PaymentMethod.froadPoints.getCode(), paymentMethod)){
			code = AccountCheckingConst.LMJFXF_POINT;
		}else if(StringUtils.equals(PaymentMethod.bankPoints.getCode(), paymentMethod)){
			code = AccountCheckingConst.YHJFZF_POINT;
		}else if(StringUtils.equals(PaymentMethod.froadPointsAndCash.getCode(), paymentMethod)){
			code = AccountCheckingConst.LMJFJXJ;
		}else if(StringUtils.equals(PaymentMethod.bankPointsAndCash.getCode(), paymentMethod)){
			code = AccountCheckingConst.YHJFJXJ;
		}else if(StringUtils.equals(PaymentMethod.creditPoints.getCode(), paymentMethod)){
			
		}
		return code;
	}
}
