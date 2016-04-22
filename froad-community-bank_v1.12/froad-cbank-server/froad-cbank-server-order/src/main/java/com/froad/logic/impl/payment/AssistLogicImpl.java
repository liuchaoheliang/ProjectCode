package com.froad.logic.impl.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.PointInfo;
import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.PayThriftVoBean;
import com.froad.enums.CashType;
import com.froad.enums.OrderState;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.exceptions.PaymentException;
import com.froad.handler.OrderLogHandler;
import com.froad.handler.impl.OrderLogHandlerImpl;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrderLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.LogBeanClone;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.payment.AssistLogic;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.MerchantAccount;
import com.froad.po.Org;
import com.froad.po.Payment;
import com.froad.po.Store;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.support.impl.payment.AWIPSThirdpartyImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.AWIPSThirdparty;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.util.Arith;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.froad.util.payment.ThirdpartyRequestBuilder;
import com.pay.user.dto.MemberBankSpecDto;
import com.pay.user.dto.UserSpecDto;

public class AssistLogicImpl implements AssistLogic {
	
	/**
	 * 必须操作无法完成
	 */
	private final String NECESSITY_ACTION_NOT_DONE = "系统暂时无法完成支付，必要操作无法完成";
	
	/**
	 * 无效的金额参数
	 */
	private final String PAY_AMOUNT_VOID = "支付金额无效";
	
	/**
	 * 支付金额超出
	 */
	private final String PAY_AMOUNT_MORE = "支付金额超出";
	
	/**
	 * 支付金额不足
	 */
	private final String PAY_AMOUNT_LESS = "支付金额不足";
	
	/**
	 * 使用贴膜卡支付但未指定贴膜卡号码
	 */
	private final String FOIL_CARD_NOT_FIND = "未指定支付的贴膜手机号";
	
	/**
	 * 查询用户的积分信息返回空信息
	 */
	private final String GET_POINT_INF_FAILED = "未能成功获取用户积分信息";
	
	
	private AWIPSThirdparty aWIPSThirdparty = new AWIPSThirdpartyImpl();
	private DataWrap dataWrap = new DataWrapImpl();
	private OrderLogic orderLogic = new OrderLogicImpl();
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	private OrderLogHandler orderLogHandler = new OrderLogHandlerImpl();
	
	/**
	 * 校验支付方式和贴膜卡号码
	* <p>Function: validationPayMethodAndFoilNum</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午11:30:50
	* @version 1.0
	* @param payThriftVoBean
	* @return
	 */
	private ResultBean validationPayMethodAndFoilNum(PayThriftVoBean payThriftVoBean){
		
		List<ClientPaymentChannel> channels = BaseSubassembly.acquireClientPaymentChannelFromRedis(payThriftVoBean.getClientId());
		if(channels == null || channels.size() == 0){
			return new ResultBean(ResultCode.payment_paytype_notfound);//未找到当前客户端有效的支付方式记录
		}
		
		boolean isPointsOrgExist = false;
		boolean isCashOrgExist = false;
		
		//--------确认传入的机构号能对应查出的有效的机构号
		if(BaseSubassembly.isCombinePayment(String.valueOf(payThriftVoBean.getPayType()))){ //对比积分和现金的积分机构号
			for (ClientPaymentChannel channel : channels) {
				if(channel.getPaymentOrgNo().equals(payThriftVoBean.getCashOrgNo())){
					isCashOrgExist = true;
					break;
				}
			}
			for (ClientPaymentChannel channel : channels) {
				if(channel.getPaymentOrgNo().equals(payThriftVoBean.getPointOrgNo())){
					payThriftVoBean.setPointRatio(Integer.valueOf((channel.getPointRate()))); //含有积分的需要从渠道中获取积分比例
					isPointsOrgExist = true;
					break;
				}
			}
		}else{
			if(BaseSubassembly.isPurePointsPayment(String.valueOf(payThriftVoBean.getPayType()))){ //纯积分
				for (ClientPaymentChannel channel : channels) {
					if(channel.getPaymentOrgNo().equals(payThriftVoBean.getPointOrgNo())){
						payThriftVoBean.setPointRatio(Integer.valueOf(channel.getPointRate()));
						isPointsOrgExist = true;
						break;
					}
				}
				isCashOrgExist = true;
			}else{ //纯现金
				for (ClientPaymentChannel channel : channels) {
					if(channel.getPaymentOrgNo().equals(payThriftVoBean.getCashOrgNo())){
						isCashOrgExist = true;
						break;
					}
				}
				isPointsOrgExist = true;
			}
		}
		
		if(!BaseSubassembly.isPurePointsPayment(String.valueOf(payThriftVoBean.getPayType()))){//如果使用了现金支付
			if(CashType.foil_card.code() == payThriftVoBean.getCashType()){//用户选择现金使用贴膜卡支付
				if(StringUtils.isEmpty(payThriftVoBean.getFoilCardNum())){
					return new ResultBean(ResultCode.payment_paytype_unsupport,FOIL_CARD_NOT_FIND);
				}
				
				ResultBean resultBean = aWIPSThirdparty.verifyFoilCardNumOfOpenAPI(payThriftVoBean.getClientId(), payThriftVoBean.getFoilCardNum());
				if(!EsyT.isResultBeanSuccess(resultBean)){
					Logger.logInfo("贴膜卡支付指定手机非有效贴膜卡手机号","foilCardNum",payThriftVoBean.getFoilCardNum());
					return resultBean;
				}
			}
		}
		
		if(isPointsOrgExist && isCashOrgExist){//自此检验完毕
			return new ResultBean(ResultCode.success);
		}
		
		Logger.logInfo("指定的支付方式机构号在对应客户端支付渠道配置信息中不存在","clientId",payThriftVoBean.getClientId());
		return new ResultBean(ResultCode.payment_paytype_unsupport); // 指定的支付机构号不符
	}
	
	private ResultBean validationOrderPrice(Integer totalPrice, PayThriftVoBean paythriftVoBean){

		double orderAmount = Arith.div(totalPrice, Const.HDOP_1000); //订单总价值（元）
		double payAmount = 0.00;

		if (BaseSubassembly.isCombinePayment(String.valueOf(paythriftVoBean.getPayType()))) { //组合支付
			if (paythriftVoBean.getPointAmount() <= 0 || paythriftVoBean.getCashAmount() <= 0) {
				return new ResultBean(ResultCode.payment_params_error,PAY_AMOUNT_VOID);
			}
		} else if (BaseSubassembly.isPurePointsPayment(String.valueOf(paythriftVoBean.getPayType()))) {//纯积分支付
			if (paythriftVoBean.getPointAmount() <= 0) {
				return new ResultBean(ResultCode.payment_params_error,PAY_AMOUNT_VOID);
			}
		} else { //纯现金支付
			if (paythriftVoBean.getCashAmount() <= 0) {
				return new ResultBean(ResultCode.payment_params_error,PAY_AMOUNT_VOID);
			}
		}

		// 验证价格是否足以支付订单
		if (BaseSubassembly.isCombinePayment(String.valueOf(paythriftVoBean.getPayType()))) {
			payAmount = Arith.add(
					paythriftVoBean.getCashAmount(),//现金总值
					Arith.divFloor(paythriftVoBean.getPointAmount(),paythriftVoBean.getPointRatio())
					);
		} else if (BaseSubassembly.isPurePointsPayment(String.valueOf(paythriftVoBean.getPayType()))) {
			payAmount = Arith.divFloor(paythriftVoBean.getPointAmount(),paythriftVoBean.getPointRatio());
		} else {
			// 纯现金不涉及比例
			payAmount = paythriftVoBean.getCashAmount();
		}

		if (payAmount < orderAmount) {
			return new ResultBean(ResultCode.payment_payamount_notequal,PAY_AMOUNT_LESS);
		} else if (payAmount > orderAmount) {
			return new ResultBean(ResultCode.payment_payamount_notequal,PAY_AMOUNT_MORE);
		}

		return new ResultBean(ResultCode.success);
	}

	@Override
	public ResultBean validationAllBeforePay(OrderMongo order,PayThriftVoBean payThriftVoBean) {
		//验证支付方式，检查贴膜卡的有效性（如果使用）
		ResultBean resultBean = validationPayMethodAndFoilNum(payThriftVoBean);
		if (!EsyT.isResultBeanSuccess(resultBean)) {
			return resultBean;
		}
		Integer totalPrice = order.getTotalPrice();
		if (totalPrice == null || totalPrice <= 0) {
			Logger.logInfo("支付订单没有总价参数", "orderId",payThriftVoBean.getOrderId());
			return new ResultBean(ResultCode.payment_order_error);
		}
		resultBean = validationOrderPrice(totalPrice, payThriftVoBean);
		if(EsyT.isResultBeanSuccess(resultBean)){
			UserSpecDto userSpecDto = aWIPSThirdparty.queryUserByMemberCode(order.getMemberCode());
			if(userSpecDto == null){ //用户信息不存在
				return new ResultBean(ResultCode.payment_member_lack);
			}else{
				PointInfo pointInfo = null;
				if(!BaseSubassembly.isPureCashPayment(String.valueOf(payThriftVoBean.getPayType()))){
					
					//如果非纯现金支付则需要验证积分值
					pointInfo = aWIPSThirdparty.queryUserPoints(userSpecDto.getLoginID(), payThriftVoBean.getClientId());
					
					if(pointInfo == null){
						return new ResultBean(ResultCode.failed,GET_POINT_INF_FAILED);
					}
					
					if(Const.FROAD_POINT_ORG_NO.equals(payThriftVoBean.getPointOrgNo())){ //方付通积分
						if(payThriftVoBean.getPointAmount() > pointInfo.getFroadPoint()){
							//方付通积分不足
							return new ResultBean(ResultCode.payment_point_lack);
						}
					}else{//银行积分
						if(payThriftVoBean.getPointAmount() > pointInfo.getBankPoint()){
							//银行积分不足
							return new ResultBean(ResultCode.payment_point_lack);
						}
					}
				}
				
				Object[] dataRes = new Object[2];
				dataRes[0] = userSpecDto;
				dataRes[1] = pointInfo;
				
				return new ResultBean(ResultCode.success, dataRes);
			}
		}else{
			return resultBean;
		}
	}

	@Override
	public ResultBean savePaymentAndDisableOldPaymentOfUserPay(PayThriftVoBean payThriftVoBean,Object[] dataValidationRes) {
		List<Payment> payments = dataWrap.queryEnableOfUserPayByOrderId(payThriftVoBean.getOrderId());
		if(payments != null && payments.size() != 0){ //该笔订单存在历史流水，这次支付为再支付，需要作废历史支付流水
			for (Payment payment : payments) {
				if(!dataWrap.modifyDisablePayment(payment.getPaymentId())){
					//作废历史支付订单失败
					Logger.logError("作废支付流水失败","paymentId",payment.getPaymentId());
					return new ResultBean(ResultCode.failed, NECESSITY_ACTION_NOT_DONE);
				}
			}
		}
		
		List<Payment> paymentsLog = new ArrayList<Payment>();
		
		Logger.logInfo("作废历史订单完成,生成新的支付流水","paythriftVoBean",payThriftVoBean);
		payments = new ArrayList<Payment>();
		boolean flag = false;
		UserSpecDto userSpecDto = (UserSpecDto) dataValidationRes[0];
		String fromUserName = userSpecDto.getLoginID();
		Long fromUserMemberCode = userSpecDto.getMemberCode();
		if(BaseSubassembly.isCombinePayment(String.valueOf(payThriftVoBean.getPayType()))){ //如果是组合支付
			PointInfo pointInfo = (PointInfo) dataValidationRes[1];
			
			Payment paymentPoint = null;
			//--------------组合支付 先判断是哪种积分---------------
			if(Const.FROAD_POINT_ORG_NO.equals(payThriftVoBean.getPointOrgNo())){ //方付通积分
				paymentPoint = BaseSubassembly.loadyToPaymentFroadPoint(payThriftVoBean);
				paymentPoint.setFromUserName(fromUserName);
				paymentPoint.setFromUserMemberCode(fromUserMemberCode);
				paymentPoint.setFromAccountNo(pointInfo.getFroadAccountId());
				payments.add(paymentPoint);
				flag = dataWrap.initializePayment(paymentPoint);
			}else{//银行积分
				paymentPoint = BaseSubassembly.loadyToPaymentBankPoint(payThriftVoBean);
				paymentPoint.setFromUserName(fromUserName);
				paymentPoint.setFromUserMemberCode(fromUserMemberCode);
				paymentPoint.setFromAccountNo(pointInfo.getBankAccountId());
				payments.add(paymentPoint);
				flag = dataWrap.initializePayment(paymentPoint);
			}
			//--------------组合支付 先判断是哪种积分---------------
			
			if(!flag){
				Logger.logError("保存支付流水失败： 组合支付中（保存积分流水已经失败，现金流水放弃保存）","payment",payments.get(0));
				return new ResultBean(ResultCode.failed, NECESSITY_ACTION_NOT_DONE);
			}
			
			paymentsLog.add(paymentPoint);
			
			Payment paymentCash = BaseSubassembly.loadyToPaymentCash(payThriftVoBean);
			paymentCash.setFromUserName(fromUserName);
			paymentCash.setFromPhone(userSpecDto.getMobile());
			paymentCash.setFromUserMemberCode(fromUserMemberCode);
			//如果使用的是贴膜卡支付 则设置fromPhone字段
			if(CashType.foil_card.code() == payThriftVoBean.getCashType()){ //使用贴膜卡
				paymentCash.setFromPhone(payThriftVoBean.getFoilCardNum());
			}else if(CashType.bank_fast_pay.code() == payThriftVoBean.getCashType()){ //如果使用的快捷支付
				MemberBankSpecDto bankSpecDto = aWIPSThirdparty.queryUserSignatoryBankCardOfClient(payThriftVoBean.getClientId(), userSpecDto.getMemberCode());
				if(bankSpecDto != null){
					paymentCash.setFromAccountName(bankSpecDto.getCardHostName());
					paymentCash.setFromAccountNo(bankSpecDto.getCardNo());
				}
			}
			payments.add(paymentCash);
			flag = dataWrap.initializePayment(paymentCash);
			
			if(!flag){
				Logger.logError("保存支付流水失败","payment",payments.get(0));
				return new ResultBean(ResultCode.failed, NECESSITY_ACTION_NOT_DONE);
			}
			
			paymentsLog.add(paymentCash);
			
			//记录日志
			orderLogHandler.paymentLogCreate(paymentsLog, null, LogBeanClone.getPaymentType(payThriftVoBean.getPointOrgNo(), payThriftVoBean.getCashType()), 1);
			
			return new ResultBean(ResultCode.success,payments);
		}else{ //纯支付方式
			if(BaseSubassembly.isPurePointsPayment(String.valueOf(payThriftVoBean.getPayType()))){ //纯积分
				PointInfo pointInfo = (PointInfo) dataValidationRes[1];
				
				Payment paymentPoint = null;
				if(Const.FROAD_POINT_ORG_NO.equals(payThriftVoBean.getPointOrgNo())){
					paymentPoint = BaseSubassembly.loadyToPaymentFroadPoint(payThriftVoBean);
					paymentPoint.setFromUserName(fromUserName);
					paymentPoint.setFromUserMemberCode(fromUserMemberCode);
					paymentPoint.setFromAccountNo(pointInfo.getFroadAccountId());
					payments.add(paymentPoint);
					flag = dataWrap.initializePayment(paymentPoint);
				}else{
					paymentPoint = BaseSubassembly.loadyToPaymentBankPoint(payThriftVoBean);
					paymentPoint.setFromUserName(fromUserName);
					paymentPoint.setFromUserMemberCode(fromUserMemberCode);
					paymentPoint.setFromAccountNo(pointInfo.getBankAccountId());
					payments.add(paymentPoint);
					flag = dataWrap.initializePayment(paymentPoint);
				}
				
				if(!flag){
					Logger.logError("保存支付流水失败","payment",payments.get(0));
					return new ResultBean(ResultCode.failed, NECESSITY_ACTION_NOT_DONE);
				}
				
				paymentsLog.add(paymentPoint);
				
				//记录日志
				orderLogHandler.paymentLogCreate(paymentsLog, null, LogBeanClone.getPaymentType(payThriftVoBean.getPointOrgNo(), 0), 1);

				return new ResultBean(ResultCode.success,payments);
			}else{//纯现金
				Payment paymentCash = BaseSubassembly.loadyToPaymentCash(payThriftVoBean);
				paymentCash.setFromPhone(userSpecDto.getMobile());
				paymentCash.setFromUserName(fromUserName);
				paymentCash.setFromUserMemberCode(fromUserMemberCode);
				
				//如果使用的是贴膜卡支付 则设置fromPhone字段
				if(CashType.foil_card.code() == payThriftVoBean.getCashType()){ //使用贴膜卡
					paymentCash.setFromPhone(payThriftVoBean.getFoilCardNum());
				}else if(CashType.bank_fast_pay.code() == payThriftVoBean.getCashType()){ //如果使用的快捷支付
					MemberBankSpecDto bankSpecDto = aWIPSThirdparty.queryUserSignatoryBankCardOfClient(payThriftVoBean.getClientId(), userSpecDto.getMemberCode());
					if(bankSpecDto != null){
						paymentCash.setFromAccountName(bankSpecDto.getCardHostName());
						paymentCash.setFromAccountNo(bankSpecDto.getCardNo());
					}
				}
				payments.add(paymentCash);
				flag = dataWrap.initializePayment(paymentCash);
				if(!flag){
					Logger.logError("保存支付流水失败","payment",payments.get(0));
					return new ResultBean(ResultCode.failed, NECESSITY_ACTION_NOT_DONE);
				}
				
				paymentsLog.add(paymentCash);
				
				//记录日志
				orderLogHandler.paymentLogCreate(paymentsLog, null, LogBeanClone.getPaymentType(null, payThriftVoBean.getCashType()), 1);
				
				return new ResultBean(ResultCode.success,payments);
			}
		}
	}

	
	@Override
	public ResultBean completeOrderOfPaymentInfo(OrderMongo order,PayThriftVoBean payThriftVoBean) {

		String orderId = order.getOrderId();
		OrderMongo orderUpdate = new OrderMongo();
		orderUpdate.setOrderId(orderId);
		orderUpdate.setClientId(payThriftVoBean.getClientId());
		int paymentMethodCode = payThriftVoBean.getPayType();

		orderUpdate.setPaymentMethod(String.valueOf(paymentMethodCode));// 设置订单支付类型
		order.setPaymentMethod(String.valueOf(paymentMethodCode));
		
		Date payTime = new Date();
		
		orderUpdate.setPaymentTime(payTime.getTime()); // 设置支付时间
		order.setPaymentTime(payTime.getTime());
		
		switch (paymentMethodCode) {
		case 2: // 方付通积分支付
			orderUpdate.setFftPoints(Arith.mul(payThriftVoBean.getPointAmount(), Const.HDOP_1000)); //设置方付通积分值
			orderUpdate.setPointRate(Integer.toString(payThriftVoBean.getPointRatio()));
			break;
			
		case 3: //银行积分支付
			orderUpdate.setBankPoints(Arith.mul(payThriftVoBean.getPointAmount(), Const.HDOP_1000)); //设置银行积分值
			orderUpdate.setPointRate(Integer.toString(payThriftVoBean.getPointRatio()));
			break;
			
		case 1: //现金
			orderUpdate.setRealPrice(Arith.mul(payThriftVoBean.getCashAmount(), Const.HDOP_1000)); //设置方付通积分值
			break;
			
		case 4: //方付通积分&现金
			orderUpdate.setFftPoints(Arith.mul(payThriftVoBean.getPointAmount(), Const.HDOP_1000)); //设置方付通积分值
			orderUpdate.setPointRate(Integer.toString(payThriftVoBean.getPointRatio()));
			orderUpdate.setRealPrice(Arith.mul(payThriftVoBean.getCashAmount(), Const.HDOP_1000)); 
			break;
			
		case 5: //方付通积分&现金
			orderUpdate.setBankPoints(Arith.mul(payThriftVoBean.getPointAmount(), Const.HDOP_1000)); //设置银行积分值
			orderUpdate.setPointRate(Integer.toString(payThriftVoBean.getPointRatio()));
			orderUpdate.setRealPrice(Arith.mul(payThriftVoBean.getCashAmount(), Const.HDOP_1000)); 
			break;
			
		default:
			Logger.logError("订单上的paymentMethod.code不存在","paymentMethod.code",paymentMethodCode);
			return new ResultBean(ResultCode.failed,NECESSITY_ACTION_NOT_DONE);
		}

		ResultBean resultBean = orderLogic.updateOrderForPay(orderUpdate);
		if (!EsyT.isResultBeanSuccess(resultBean)) {
			Logger.logError("在补全订单的支付信息时更新订单失败","orderId",orderUpdate.getOrderId());
			return new ResultBean(ResultCode.failed, NECESSITY_ACTION_NOT_DONE);
		}
		
		//补全拆分规则信息
		boolean flag = orderLogic.updateUnitProductCutPoint(payThriftVoBean.getClientId(),orderId);
		if(!flag){
			Logger.logError("补全积分拆分规则数据失败");
			return new ResultBean(ResultCode.failed, NECESSITY_ACTION_NOT_DONE);
		}
		return resultBean;
	}
	
	
	@Override
	public OrderMongo completeOrderOfPaymentInfoForSeckilling(OrderMongo order,PayThriftVoBean payThriftVoBean) {

		int paymentMethodCode = payThriftVoBean.getPayType();

		order.setPaymentMethod(String.valueOf(paymentMethodCode));// 设置订单支付类型
		order.setPaymentTime(new Date().getTime()); // 设置支付时间

		switch (paymentMethodCode) {
		case 2: // 方付通积分支付
			order.setFftPoints(Arith.mul(payThriftVoBean.getPointAmount(), Const.HDOP_1000)); //设置方付通积分值
			order.setPointRate(Integer.toString(payThriftVoBean.getPointRatio()));
			break;
			
		case 3: //银行积分支付
			order.setBankPoints(Arith.mul(payThriftVoBean.getPointAmount(), Const.HDOP_1000)); //设置银行积分值
			order.setPointRate(Integer.toString(payThriftVoBean.getPointRatio()));
			break;
			
		case 1: //现金
			order.setRealPrice(Arith.mul(payThriftVoBean.getCashAmount(), Const.HDOP_1000)); //设置方付通积分值
			break;
			
		case 4: //方付通积分&现金
			order.setFftPoints(Arith.mul(payThriftVoBean.getPointAmount(), Const.HDOP_1000)); //设置方付通积分值
			order.setPointRate(Integer.toString(payThriftVoBean.getPointRatio()));
			order.setRealPrice(Arith.mul(payThriftVoBean.getCashAmount(), Const.HDOP_1000)); 
			break;
			
		case 5: //方付通积分&现金
			order.setBankPoints(Arith.mul(payThriftVoBean.getPointAmount(), Const.HDOP_1000)); //设置银行积分值
			order.setPointRate(Integer.toString(payThriftVoBean.getPointRatio()));
			order.setRealPrice(Arith.mul(payThriftVoBean.getCashAmount(), Const.HDOP_1000)); 
			break;
			
		default:
			Logger.logError("订单上的paymentMethod.code不存在","paymentMethod.code",paymentMethodCode);
		}
		return order;
	}

	@Override
	public ResultBean checkAndOperateStore(OrderMongo order, boolean isFreeStore) {
		
		if(!isFreeStore){ //如果指明不是释放退还库存（则是扣除库存）但订单上也不是表明库存已退则不进行任何操作
			if (!OrderState.RETURNED.getCode().equals(order.getState())) {
				return new ResultBean(ResultCode.success);
			}
		}

		ResultBean resultBean = orderLogic.getSubOrderListByOrderId(order.getClientId(), order.getOrderId());

		@SuppressWarnings("unchecked")
		List<SubOrderMongo> subOrderMongoList = (List<SubOrderMongo>) resultBean.getData();
		List<Store> storeList = new ArrayList<Store>();
		if (EsyT.isResultBeanSuccess(resultBean)) {
			for (SubOrderMongo sOrder : subOrderMongoList) {
				storeList.addAll(getStoreList(sOrder));
			}
		} else {
			Logger.logError("查询库存失败: " + resultBean.getMsg());
			return new ResultBean(ResultCode.failed, resultBean.getMsg());
		}

		ResultBean storeResult;
		if (isFreeStore) {
			storeResult = orderLogic.increaseStore(storeList);
			if (!EsyT.isResultBeanSuccess(storeResult)) {
				Logger.logError("释放库存失败: " + storeResult.getMsg());
				return new ResultBean(ResultCode.failed, storeResult.getMsg());
			}
			storeResult = orderLogic.updateProductStore(storeList);
		} else {
			storeResult = orderLogic.reduceStore(storeList);
			if (!EsyT.isResultBeanSuccess(storeResult)) {
				Logger.logError("主动扣除库存失败:" + storeResult.getMsg());
				return new ResultBean(ResultCode.failed, "商品库存不足");
			}
			storeResult = orderLogic.updateProductStore(storeList);
		}
		
		if (!EsyT.isResultBeanSuccess(storeResult)) {
			Logger.logError("同步库存到mysql失败:" + storeResult.getMsg());
			return new ResultBean(ResultCode.failed, storeResult.getMsg());
		}
		
		//此处解决扣除库存后，需要立即更新订单上库存已退为正常情况
		if(!isFreeStore){//正在进行扣除库存动作
			if(OrderState.RETURNED.getCode().equals(order.getState())){
				//如果当前订单的状态为库存已退，则重新扣除后需要更新为正常
				Logger.logInfo("重扣库存后更新订单库存状态为正常","order_id",order.getOrderId());
				OrderMongo orderUpdate = new OrderMongo();
				orderUpdate.setOrderId(order.getOrderId());
				orderUpdate.setClientId(order.getClientId());
				orderUpdate.setState(OrderState.NORMAL.getCode());
				orderLogic.updateOrderForPay(orderUpdate);
			}
		}
		
		return new ResultBean(ResultCode.success);
	}
	
	private List<Store> getStoreList(SubOrderMongo order){
		String merchantId = order.getMerchantId();
		if (!(StringUtils.equals(order.getType(),SubOrderType.special_merchant.getCode()) || StringUtils.equals(order.getType(), SubOrderType.group_merchant.getCode())  ||  StringUtils.equals(order.getType(), SubOrderType.boutique.getCode()))) {
			Org org = new CommonLogicImpl().getOrgByOrgCode(merchantId,order.getClientId());
			merchantId = org.getMerchantId();
		}
		List<Store> storeList = new ArrayList<Store>();
		Store store = null;
		for (ProductMongo product : order.getProducts()) {
			store = new Store();
			store.setClientId(order.getClientId());
			store.setMerchantId(merchantId);
			store.setProductId(product.getProductId());
			store.setReduceStore(product.getQuantity());
			store.setStore(product.getQuantity());
			storeList.add(store);
		}
		return storeList;
	}

	public ResultBean returnStoreOfSeckilling(OrderMongo order){

		ResultBean resultBean = orderLogic.getSubOrderListByOrderId(order.getClientId(), order.getOrderId());

		@SuppressWarnings("unchecked")
		List<SubOrderMongo> subOrderMongoList = (List<SubOrderMongo>) resultBean.getData();
		List<Store> storeList = new ArrayList<Store>();
		if (EsyT.isResultBeanSuccess(resultBean)) {
			for (SubOrderMongo sOrder : subOrderMongoList) {
				storeList.addAll(getStoreList(sOrder));
			}
		} else {
			Logger.logError("查询库存失败: " + resultBean.getMsg());
			return new ResultBean(ResultCode.failed, resultBean.getMsg());
		}

		ResultBean storeResult;
		storeResult = orderLogic.increaseSeckillStore(storeList);
		if (!EsyT.isResultBeanSuccess(storeResult)) {
			Logger.logError("释放库存失败: " + storeResult.getMsg());
			return new ResultBean(ResultCode.failed, storeResult.getMsg());
		}
		storeResult = orderLogic.updateSeckillProductStore(storeList);
		
		if (!EsyT.isResultBeanSuccess(storeResult)) {
			Logger.logError("同步库存到mysql失败:" + storeResult.getMsg());
			return new ResultBean(ResultCode.failed, storeResult.getMsg());
		}
		
		return new ResultBean(ResultCode.success);
	}

	@Override
	public ResultBean settleToMerchantCapital(String orderId, String clientId, double settleCash, String merchantId,String merchantOutletId) {
		ResultBean resultBean = orderLogic.getOrderByOrderId(clientId, orderId);
		OrderMongo order = null;
		if(!EsyT.isResultBeanSuccess(resultBean)){
			return new ResultBean(ResultCode.failed,"查询对应订单失败");
		}
		order = (OrderMongo) resultBean.getData();
		
		UserSpecDto userSpecDto = aWIPSThirdparty.queryUserByMemberCode(order.getMemberCode());
		
		Payment payment = new Payment();
		payment.setPaymentId(EsyT.simpleID());
		payment.setOrderId(order.getOrderId());
		payment.setClientId(order.getClientId());
		payment.setPaymentReason("0");
		payment.setPaymentType(2);
		payment.setPaymentTypeDetails(CashType.timely_pay.code());
		payment.setPaymentOrgNo(BaseSubassembly.acquireSettlePaymentOrgNo(clientId));
		payment.setFromUserMemberCode(userSpecDto.getMemberCode());
		payment.setFromUserName(userSpecDto.getLoginID());
		payment.setSettleMerchantAndOutletId(merchantId + "|" + merchantOutletId);
		payment.setPaymentValue(Arith.mul(settleCash, Const.HDOP_1000));
		MerchantAccount merchantAccount = commonLogic.getMerchantAccoutByMerchantIdOrOutletId(merchantId, merchantOutletId);
		if(merchantAccount == null){
			Logger.logError("准备结算资金到商户，未能获得商户收款信息",new String[]{"merchantId","merchantOutletId"},new Object[]{merchantId,merchantOutletId});
			return new ResultBean(ResultCode.failed,"商户收款信息查询为空");
		}
		payment.setSettleToAccountName(merchantAccount.getAcctName());
		payment.setSettleToAccountNo(merchantAccount.getAcctNumber());
		
		dataWrap.initializePayment(payment);
		
		OpenApiReq req = ThirdpartyRequestBuilder.builderCashSettleReq(payment);
		OpenApiRes res = null;
		String paymentId = payment.getPaymentId();
		
		try {
			res = aWIPSThirdparty.cashConsume(req);
		} catch (PaymentException e) {
			dataWrap.addPaymentTimeFromRedis(paymentId); //发送请求异常的流水交由定时任务去接管
			dataWrap.modifyPaymentToRequestException(paymentId, "请求支付系统OpenAPI异常: " + e.getMessage());
			Logger.logError("发送现金结算请求异常",e);
			return new ResultBean(ResultCode.success,"现金结算请求发送完毕",paymentId);
		}
		dataWrap.modifyPaymentToRequestSuccess(paymentId);
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			dataWrap.addPaymentTimeFromRedis(paymentId); //受理成功的订单需要定时任务去定期查询通知
			dataWrap.modifyPaymentToPayAccessSuccess(paymentId,res.getFroadBillNo(),"现金结算请求受理成功");
			return new ResultBean(ResultCode.success,"现金结算请求受理成功",paymentId);
		}else{
			dataWrap.modifyPaymentToPayAccessFailed(paymentId, res.getResultCode(),res.getResultDesc(),"现金结算请求受理失败");
			return new ResultBean(ResultCode.failed,res.getResultDesc(),paymentId);
		}
	}
	
}
