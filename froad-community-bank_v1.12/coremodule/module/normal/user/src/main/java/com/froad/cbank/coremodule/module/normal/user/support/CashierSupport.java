package com.froad.cbank.coremodule.module.normal.user.support;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.enums.CreateSource;
import com.froad.cbank.coremodule.module.normal.user.enums.PaymentChannel;
import com.froad.cbank.coremodule.module.normal.user.enums.PaymentMethod;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.utils.AmountUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.PayOrdersVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PointAmountResVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PointsVo;
import com.froad.cbank.coremodule.module.normal.user.vo.SmartCardVo;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.ClientPaymentChannelService;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.vo.ClientPaymentChannelVo;
import com.froad.thrift.vo.bankcard.BankCardResponse;
import com.froad.thrift.vo.member.UserEnginePointsVo;
import com.froad.thrift.vo.order.CloseOrderVoReq;
import com.froad.thrift.vo.order.CloseOrderVoRes;
import com.froad.thrift.vo.order.DeleteOrderVoReq;
import com.froad.thrift.vo.order.DeleteOrderVoRes;
import com.froad.thrift.vo.order.GetOrderDetailVoReq;
import com.froad.thrift.vo.order.GetOrderDetailVoRes;
import com.froad.thrift.vo.payment.DoPayOrdersVoReq;
import com.froad.thrift.vo.payment.DoPayOrdersVoRes;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.UserStatus;

/**
 * 订单支付服务类
 */
@Service
public class CashierSupport {

	@Resource
	private MemberInformationService.Iface memberInformationService;

	@Resource
	private ClientPaymentChannelService.Iface clientPaymentChannelService;

	@Resource
	private PaymentService.Iface paymentService;

	@Resource
	private OrderService.Iface orderService;
	
	@Resource
	private BankCardService.Iface bankCardService;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private ClientConfigSupport clientConfigSupport;
	
	@Resource
	private IntegralSupport integralSupport;

	@Resource
	private RedPacketSupport redPacketSupport;
	
	/**
	 * 纯积分支付- 方付通积分 or 银行积分
	 * 
	 * @param payOrdersVo
	 * @return
	 */
	public Map<String, Object> pointsPay(PayOrdersVo payOrdersVo, String code) {

		Map<String, Object> result = new HashMap<String, Object>();
		DoPayOrdersVoReq payOrdersVoReq = new DoPayOrdersVoReq();
		payOrdersVoReq.setPayType(Integer.parseInt(code));
		payOrdersVoReq.setClientId(payOrdersVo.getClientId());
		payOrdersVoReq.setOrderId(payOrdersVo.getOrderId());

		DoPayOrdersVoRes doPayOrdersVoRes = new DoPayOrdersVoRes();
		try {

			UserEnginePointsVo userPoints = memberInformationService
					.selectMemberPointsInfoByLoginID(payOrdersVo.getClientId(),payOrdersVo.getUserName());

			// 支付渠道列表
			ClientPaymentChannelVo payChannelVo = new ClientPaymentChannelVo();
			payChannelVo.setClientId(payOrdersVo.getClientId());
			
			List<ClientPaymentChannelVo> payChannelList = clientPaymentChannelService.getClientPaymentChannel(payChannelVo);
			String froadPointRate = "";
			String bankPointRate = "";

			for (ClientPaymentChannelVo payChannel : payChannelList) {
				if (PaymentChannel.froad_point.getCode().equals(payChannel.getType())) {
					froadPointRate = payChannel.getPointRate();
				} else if (PaymentChannel.bank_point.getCode().equals(payChannel.getType())) {
					bankPointRate = payChannel.getPointRate();
				}
			}

			// 订单总金额查询
			GetOrderDetailVoReq orderDetailVoReq = new GetOrderDetailVoReq();
			orderDetailVoReq.setOrderId(payOrdersVo.getOrderId());
			orderDetailVoReq.setClientId(payOrdersVo.getClientId());

			GetOrderDetailVoRes orderDetail = orderService.getOrderDetail(orderDetailVoReq);
			double totalPrice = orderDetail.getOrderDetailVo().getTotalPrice();

			if (PaymentMethod.froadPoints.getCode().equals(code)) {
				BigDecimal payPoints = AmountUtils.getPayFroadPoint(totalPrice,froadPointRate);
				String froadPoints=userPoints.getFroadPoints() == null ? "0":userPoints.getFroadPoints();
				if (payPoints.compareTo(new BigDecimal(froadPoints)) <= 0) {
					payOrdersVoReq.setPointAmount(payPoints.doubleValue());
				} else {
					result.put(Results.code, "1001");
					result.put(Results.msg, "支付积分不足");
					return result;
				}

				payOrdersVoReq.setPointOrgNo(userPoints.getFroadOrgNo());

			} else if (PaymentMethod.bankPoints.getCode().equals(code)) {
				
				BigDecimal payPoints = AmountUtils.getPayBankPoint(totalPrice,bankPointRate);
				
				String bankPoint=userPoints.getBankPoints() == null ? "0" : userPoints.getBankPoints();
				if (payPoints.compareTo(new BigDecimal(bankPoint)) <= 0) {
					payOrdersVoReq.setPointAmount(payPoints.doubleValue());
				} else {
					result.put(Results.code, "1001");
					result.put(Results.msg, "支付积分不足");
					return result;
				}

				payOrdersVoReq.setPointOrgNo(payOrdersVo.getPointOrgNo());// 积分机构号
			}

			
		} catch (TException e) {
			LogCvt.error("支付失败：", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}

		try{
			doPayOrdersVoRes = paymentService.doPayOrders(payOrdersVoReq);
		}catch(TException e){
			return paymentExceptionHandler(result, e);
		}
		
		
		result.put(Results.code, doPayOrdersVoRes.getResultCode());
		result.put(Results.msg, doPayOrdersVoRes.getResultDesc());
		
		return result;
	}

	/**
	 * 快捷支付-积分加现金支付
	 * 
	 * @return
	 */
	public Map<String, Object> fastPointsAndCashPay(PayOrdersVo payOrdersVo,String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		DoPayOrdersVoReq req = new DoPayOrdersVoReq();
		req.setClientId(payOrdersVo.getClientId());
		req.setOrderId(payOrdersVo.getOrderId());

		req.setPayType(Integer.parseInt(code));
		req.setCashType(Integer.parseInt(PaymentChannel.fast_pay.getCode()));//快捷支付

		DoPayOrdersVoRes doPayOrdersVoRes = new DoPayOrdersVoRes();
		try {

			// 支付渠道列表
			ClientPaymentChannelVo payChannelVo = new ClientPaymentChannelVo();
			payChannelVo.setClientId(payOrdersVo.getClientId());
			
			List<ClientPaymentChannelVo> payChannelList = clientPaymentChannelService.getClientPaymentChannel(payChannelVo);
			String froadPointRate = "";
			String bankPointRate = "";

			for (ClientPaymentChannelVo payChannel : payChannelList) {
				if (PaymentChannel.froad_point.getCode().equals(payChannel.getType())) {
					froadPointRate = payChannel.getPointRate();
				} else if (PaymentChannel.bank_point.getCode().equals(payChannel.getType())) {
					bankPointRate = payChannel.getPointRate();
				}
			}
						
			UserEnginePointsVo userPoints = memberInformationService
					.selectMemberPointsInfoByLoginID(payOrdersVo.getClientId(),payOrdersVo.getUserName());

			// 订单总金额查询
			GetOrderDetailVoReq orderDetailVoReq = new GetOrderDetailVoReq();
			orderDetailVoReq.setOrderId(payOrdersVo.getOrderId());
			orderDetailVoReq.setClientId(payOrdersVo.getClientId());

			GetOrderDetailVoRes orderDetail = orderService.getOrderDetail(orderDetailVoReq);
			double totalPrice = orderDetail.getOrderDetailVo().getTotalPrice();

			if (PaymentMethod.froadPointsAndCash.getCode().equals(code)) {
				req.setPointAmount(new Double(userPoints.getFroadPoints()));
				req.setPointOrgNo(userPoints.getFroadOrgNo());

				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(userPoints.getFroadPoints(),froadPointRate);

				if (BigDecimal.valueOf(totalPrice).compareTo(pointAmount) > 0) {
					req.setCashAmount(BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue());
				} else {
					result.put(Results.code, "1001");
					result.put(Results.msg, "非法的支付方式");
					return result;
				}
				req.setCashOrgNo(payOrdersVo.getCashOrgNo());
			} else if (PaymentMethod.bankPointsAndCash.getCode().equals(code)) {
				req.setPointOrgNo(payOrdersVo.getPointOrgNo());// 银行机构号
				
				/*BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(userPoints.getBankPoints(),userPoints.getBankPointsExchageRate());
				
				Double point=pointAmount.multiply( new BigDecimal(userPoints.getBankPointsExchageRate())).doubleValue();
				req.setPointAmount(point);*/
				
				
				//2015 11 19银行积分使用规则修改
				//银行积分舍弃小数部分计算
				String bankPointVal = new BigDecimal(userPoints.getBankPoints()).setScale(0, BigDecimal.ROUND_DOWN).toString();
				//计算银行积分兑换金额，舍弃小数点后两位
				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(bankPointVal,bankPointRate);
				//积分兑换金额再乘于积分兑换比例得出实际支付积分,小数部分进位取整
				BigDecimal finalPayPoint = pointAmount.multiply(new BigDecimal(bankPointRate)).setScale(0, BigDecimal.ROUND_UP);;
				//支付积分
				req.setPointAmount(finalPayPoint.doubleValue());
				//不足部分现金补全
				if (BigDecimal.valueOf(totalPrice).compareTo(pointAmount) > 0) {
					req.setCashAmount(BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue());
					req.setCashOrgNo(payOrdersVo.getCashOrgNo());
				} else {
					result.put(Results.code, "1001");
					result.put(Results.msg, "非法的支付方式");
					return result;
				}

			}


		} catch (TException e) {
			LogCvt.error("支付失败：", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}

		try{
			doPayOrdersVoRes = paymentService.doPayOrders(req);
		}catch(TException e){
			return paymentExceptionHandler(result, e);
		}
		
		result.put(Results.code, doPayOrdersVoRes.getResultCode());
		result.put(Results.msg, doPayOrdersVoRes.getResultDesc());

		return result;
	}
	
	
	
	
	/**
	 * CamiPointsAndCashPay:卡密支付+积分加现金支付
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2016年1月20日 下午9:17:49
	 * @param payOrdersVo
	 * @param code
	 * @return
	 * 
	 */
	public Map<String, Object> CamiPointsAndCashPay(PayOrdersVo payOrdersVo,String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		DoPayOrdersVoReq req = new DoPayOrdersVoReq();
		req.setClientId(payOrdersVo.getClientId());
		req.setOrderId(payOrdersVo.getOrderId());

		req.setPayType(Integer.parseInt(code));
		req.setCashType(Integer.parseInt(PaymentChannel.cyberbank_pay.getCode()));//卡密网银支付

		DoPayOrdersVoRes doPayOrdersVoRes = new DoPayOrdersVoRes();
		try {
			// 支付渠道列表
			ClientPaymentChannelVo payChannelVo = new ClientPaymentChannelVo();
			payChannelVo.setClientId(payOrdersVo.getClientId());
			
			List<ClientPaymentChannelVo> payChannelList = clientPaymentChannelService.getClientPaymentChannel(payChannelVo);
			String froadPointRate = "";
			String bankPointRate = "";

			for (ClientPaymentChannelVo payChannel : payChannelList) {
				if (PaymentChannel.froad_point.getCode().equals(payChannel.getType())) {
					froadPointRate = payChannel.getPointRate();
				} else if (PaymentChannel.bank_point.getCode().equals(payChannel.getType())) {
					bankPointRate = payChannel.getPointRate();
				}
			}
			UserEnginePointsVo userPoints = memberInformationService.selectMemberPointsInfoByLoginID(payOrdersVo.getClientId(),payOrdersVo.getUserName());
			// 订单总金额查询
			GetOrderDetailVoReq orderDetailVoReq = new GetOrderDetailVoReq();
			orderDetailVoReq.setOrderId(payOrdersVo.getOrderId());
			orderDetailVoReq.setClientId(payOrdersVo.getClientId());
			GetOrderDetailVoRes orderDetail = orderService.getOrderDetail(orderDetailVoReq);
			double totalPrice = orderDetail.getOrderDetailVo().getTotalPrice();

			if (PaymentMethod.froadPointsAndCash.getCode().equals(code)) {
				req.setPointAmount(new Double(userPoints.getFroadPoints()));
				req.setPointOrgNo(userPoints.getFroadOrgNo());
				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(userPoints.getFroadPoints(),froadPointRate);

				if (BigDecimal.valueOf(totalPrice).compareTo(pointAmount) > 0) {
					req.setCashAmount(BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue());
				} else {
					result.put(Results.code, "1001");
					result.put(Results.msg, "非法的支付方式");
					return result;
				}
				req.setCashOrgNo(payOrdersVo.getCashOrgNo());
			} else if (PaymentMethod.bankPointsAndCash.getCode().equals(code)) {
				req.setPointOrgNo(payOrdersVo.getPointOrgNo());// 银行机构号				
				//2015 11 19银行积分使用规则修改
				//银行积分舍弃小数部分计算
				String bankPointVal = new BigDecimal(userPoints.getBankPoints()).setScale(0, BigDecimal.ROUND_DOWN).toString();
				//计算银行积分兑换金额，舍弃小数点后两位
				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(bankPointVal,bankPointRate);
				//积分兑换金额再乘于积分兑换比例得出实际支付积分,小数部分进位取整
				BigDecimal finalPayPoint = pointAmount.multiply(new BigDecimal(bankPointRate)).setScale(0, BigDecimal.ROUND_UP);;
				//支付积分
				req.setPointAmount(finalPayPoint.doubleValue());
				//不足部分现金补全
				if (BigDecimal.valueOf(totalPrice).compareTo(pointAmount) > 0) {
					req.setCashAmount(BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue());
					req.setCashOrgNo(payOrdersVo.getCashOrgNo());
				} else {
					result.put(Results.code, "1001");
					result.put(Results.msg, "非法的支付方式");
					return result;
				}
			}
		} catch (TException e) {
			LogCvt.error("支付失败：", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}
		try{
			doPayOrdersVoRes = paymentService.doPayOrders(req);
		}catch(TException e){
			return paymentExceptionHandler(result, e);
		}
		result.put(Results.code, doPayOrdersVoRes.getResultCode());
		result.put(Results.msg, doPayOrdersVoRes.getResultDesc());
		if(doPayOrdersVoRes.getResultForm() != null){
			result.put("form", doPayOrdersVoRes.getResultForm());
		}
		return result;
	}
	

	/**
	 * 贴膜卡积分支付-方付通积分 or 银行积分
	 * 
	 * @return
	 */
	public Map<String, Object> foilCardPointsPay(PayOrdersVo payOrdersVo,String code) {

		Map<String, Object> result = new HashMap<String, Object>();
		DoPayOrdersVoReq req = new DoPayOrdersVoReq();

		req.setClientId(payOrdersVo.getClientId());
		req.setOrderId(payOrdersVo.getOrderId());
		req.setFoilCardNum(payOrdersVo.getFoilCardNum());

		req.setPayType(Integer.parseInt(code));
		req.setCashType(Integer.parseInt(PaymentChannel.foil_card.getCode()));

		DoPayOrdersVoRes doPayOrdersVoRes = null;
		try {

			// 支付渠道列表
			ClientPaymentChannelVo payChannelVo = new ClientPaymentChannelVo();
			payChannelVo.setClientId(payOrdersVo.getClientId());
			
			List<ClientPaymentChannelVo> payChannelList = clientPaymentChannelService.getClientPaymentChannel(payChannelVo);
			String froadPointRate = "";
			String bankPointRate = "";

			for (ClientPaymentChannelVo payChannel : payChannelList) {
				if (PaymentChannel.froad_point.getCode().equals(payChannel.getType())) {
					froadPointRate = payChannel.getPointRate();
				} else if (PaymentChannel.bank_point.getCode().equals(payChannel.getType())) {
					bankPointRate = payChannel.getPointRate();
				}
			}
			
			// 积分查询
			UserEnginePointsVo userPoints = memberInformationService
					.selectMemberPointsInfoByLoginID(payOrdersVo.getClientId(),payOrdersVo.getUserName());
			// 订单总金额查询
			GetOrderDetailVoReq orderDetailVoReq = new GetOrderDetailVoReq();
			orderDetailVoReq.setOrderId(payOrdersVo.getOrderId());
			orderDetailVoReq.setClientId(payOrdersVo.getClientId());

			GetOrderDetailVoRes orderDetail = orderService.getOrderDetail(orderDetailVoReq);
			double totalPrice = orderDetail.getOrderDetailVo().getTotalPrice();

			if (PaymentMethod.froadPointsAndCash.getCode().equals(code)) {
				req.setPointAmount(new Double(userPoints.getFroadPoints()));
				req.setPointOrgNo(userPoints.getFroadOrgNo());

				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(userPoints.getFroadPoints(),froadPointRate);

				req.setCashAmount(BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue());

				req.setCashOrgNo(payOrdersVo.getCashOrgNo());
			} else if (PaymentMethod.bankPointsAndCash.getCode().equals(code)) {
				req.setPointOrgNo(payOrdersVo.getPointOrgNo());// 机构号

				/*BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(userPoints.getBankPoints(),userPoints.getBankPointsExchageRate());

				Double point=pointAmount.multiply( new BigDecimal(userPoints.getBankPointsExchageRate())).doubleValue();
				req.setPointAmount(point);
				
				req.setCashAmount(BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue());
				req.setCashOrgNo(payOrdersVo.getCashOrgNo());*/
				
				//2015 11 19银行积分使用规则修改
				//银行积分舍弃小数部分计算
				String bankPointVal = new BigDecimal(userPoints.getBankPoints()).setScale(0, BigDecimal.ROUND_DOWN).toString();
				//计算银行积分兑换金额，舍弃小数点后两位
				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(bankPointVal,bankPointRate);
				//积分兑换金额再乘于积分兑换比例得出实际支付积分,小数部分进位取整
				BigDecimal finalPayPoint = pointAmount.multiply(new BigDecimal(bankPointRate)).setScale(0, BigDecimal.ROUND_UP);
				//支付积分	
				req.setPointAmount(finalPayPoint.doubleValue());
				//不足部分现金补全
				if (BigDecimal.valueOf(totalPrice).compareTo(pointAmount) > 0) {
					req.setCashAmount(BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue());
					req.setCashOrgNo(payOrdersVo.getCashOrgNo());
				} else {
					result.put(Results.code, "1001");
					result.put(Results.msg, "非法的支付方式");
					return result;
				}
			}


		} catch (TException e) {
			LogCvt.error("支付失败：", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}

		
		try{
			doPayOrdersVoRes = paymentService.doPayOrders(req);
		}catch(TException e){
			return paymentExceptionHandler(result, e);
		}
		
		
		result.put(Results.code, doPayOrdersVoRes.getResultCode());
		result.put(Results.msg, doPayOrdersVoRes.getResultDesc());
	
		return result;
	}

	/**
	 * 纯现金支付 - 快捷 or贴膜卡
	 * （惠付和普通商品收银台，现金支付方式需根据订单是否记录有使用积分，做积分+现金的支付方式组合）
	 * @param cashType 现金所属支付渠道   cashierType 收银台渠道（1 普通商品收银台   2 面对面收银台）
	 * 
	 * @return
	 */
	public Map<String, Object> cashPay(PayOrdersVo payOrdersVo, String cashType,int cashierType) {
		Map<String, Object> result = new HashMap<String, Object>();

		DoPayOrdersVoReq req = new DoPayOrdersVoReq();

		req.setClientId(payOrdersVo.getClientId());
		req.setOrderId(payOrdersVo.getOrderId());

		if (PaymentChannel.foil_card.getCode().equals(cashType)) {
			req.setFoilCardNum(payOrdersVo.getFoilCardNum());
		}

		req.setCashType(Integer.parseInt(cashType));

		DoPayOrdersVoRes doPayOrdersVoRes = null;
		try {

			GetOrderDetailVoReq orderDetailVoReq = new GetOrderDetailVoReq();
			orderDetailVoReq.setOrderId(payOrdersVo.getOrderId());
			orderDetailVoReq.setClientId(payOrdersVo.getClientId());

			GetOrderDetailVoRes orderDetail = orderService.getOrderDetail(orderDetailVoReq);
			
			//由于普通商品创建订单时有可能使用积分，所以添加积分逻辑处理			
			int payCode = Integer.parseInt(PaymentMethod.cash.getCode());
			Double totalPrice = orderDetail.getOrderDetailVo().getTotalPrice();
			Double froadPoint = orderDetail.getOrderDetailVo().getFftPoints();
			Double bankPoint = orderDetail.getOrderDetailVo().getBankPoints();
			//String cashOrgNo = payOrdersVo.getCashOrgNo();
			
			//只有是普通商品购买的时候，才去拿订单上记录的积分值使用，并且组合成新的支付方式
			if(cashierType == 1){
				if(bankPoint != null && bankPoint > 0 ){
					payCode = Integer.parseInt(PaymentMethod.bankPointsAndCash.getCode());
				}else if (froadPoint != null && froadPoint > 0 ){
					payCode = Integer.parseInt(PaymentMethod.froadPointsAndCash.getCode());
				}			
				//组合出来支付方式，走不同的逻辑
				if (PaymentMethod.froadPointsAndCash.getCode().equals(String.valueOf(payCode))) {
					// 积分查询
					UserEnginePointsVo userPoints = memberInformationService.selectMemberPointsInfoByLoginID(payOrdersVo.getClientId(),payOrdersVo.getUserName());
					//设置为之前订单上记录的积分
					req.setPointAmount(froadPoint);
					req.setPointOrgNo(userPoints.getFroadOrgNo());
					//积分值多少钱
					BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(String.valueOf(froadPoint),userPoints.getFroadPointsExchageRate());
					if(BigDecimal.valueOf(totalPrice).compareTo(pointAmount) > 0){
						//应付现金额
						totalPrice = BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue();
					}else{
						result.put(Results.code, "1001");
						result.put(Results.msg, "非法的支付请求");
						return result;
					}

				} else if (PaymentMethod.bankPointsAndCash.getCode().equals(String.valueOf(payCode))) {
					// 积分查询
					UserEnginePointsVo userPoints = memberInformationService.selectMemberPointsInfoByLoginID(payOrdersVo.getClientId(),payOrdersVo.getUserName());
					//2015 11 19银行积分使用规则修改
					//银行积分舍弃小数部分计算
					String bankPointVal = new BigDecimal(bankPoint).setScale(0, BigDecimal.ROUND_DOWN).toString();
					//计算银行积分兑换金额，舍弃小数点后两位
					BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(bankPointVal,userPoints.getBankPointsExchageRate());
					//积分兑换金额再乘于积分兑换比例得出实际支付积分,小数部分进位取整
					BigDecimal finalPayPoint = pointAmount.multiply(new BigDecimal(userPoints.getBankPointsExchageRate())).setScale(0, BigDecimal.ROUND_UP);
					//支付积分	
					req.setPointAmount(finalPayPoint.doubleValue());
					//设置银行积分机构号
					req.setPointOrgNo(userPoints.getBankOrgNO());// 机构号
					//不足部分现金补全
					if (BigDecimal.valueOf(totalPrice).compareTo(pointAmount) > 0) {
						totalPrice = BigDecimal.valueOf(totalPrice).subtract(pointAmount).doubleValue();
					} else {
						result.put(Results.code, "1001");
						result.put(Results.msg, "非法的支付请求");
						return result;
					}
				}
			}	

			//设置支付方式+现金支付金额+机构号
			req.setPayType(payCode);
			req.setCashAmount(totalPrice);
			req.setCashOrgNo(payOrdersVo.getCashOrgNo());
		} catch (TException e) {
			LogCvt.error("支付失败：", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}
		
		try{
			doPayOrdersVoRes = paymentService.doPayOrders(req);
		}catch(TException e){
			return paymentExceptionHandler(result, e);
		}
		result.put(Results.code, doPayOrdersVoRes.getResultCode());
		result.put(Results.msg, doPayOrdersVoRes.getResultDesc());
		if(doPayOrdersVoRes.getResultForm() != null){
			result.put("form", doPayOrdersVoRes.getResultForm());
		}
		return result;
	}

	/**
	 * (针对面对面收银台)
	 * 获取银行卡列表，支付渠道列表，联合积分
	 * 
	 * @param clientId
	 * @param userName
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> getPayChannelList(String clientId, String userName, Long memberCode, Boolean isSign) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//查询当前用户支持面对面付款的红包数量
			Map<String, Object>  map = redPacketSupport.canUsePackets(clientId, memberCode, true, true, null, null, new PagePojo());
			PagePojo page = (PagePojo) (map.get("page")!=null ? map.get("page"): new PagePojo());
			result.put("canUsePacketCount", page.getTotalCount());
			//查询当前用户支持面对面付款的红包数量
			
			if (StringUtil.isNotBlank(userName)) {
				//用户积分信息
				Map<String,Object> point =integralSupport.getUserPointsAmount(clientId, userName);
				result.putAll(point);
				
				// 支付渠道列表
				Map<String,Object> payChannelMap = clientConfigSupport.getClientPaymentChannel(clientId);
				if(isSign != null && isSign){//用户已签约，查询签约信息
					//查询用户快捷卡尾号 (作显示用途)
					BankCardResponse res = bankCardService.selectSignedBankCardByClientId(clientId, memberCode);
					if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultCode())){
						if(res.isSetCardList() && res.getCardListSize() > 0){
							String cardNo = res.getCardList().get(0).getCardNo();
							if(StringUtil.isNotBlank(cardNo) && cardNo.length() > 4){
								//装入快捷卡尾号
								SmartCardVo bankCard = (SmartCardVo) payChannelMap.get("bankCard");
								bankCard.setFastCardNo(cardNo.substring(cardNo.length() - 4));
								payChannelMap.put("bankCard", bankCard);
							}
						}
					}else{
						LogCvt.error("查询会员绑定快捷银行卡号失败:" + res.getResultDesc());
					}
				}
				
				result.putAll(payChannelMap);
				return result;
			} else {
				LogCvt.info(String.format("缺少必要的请求参数:{userName=%s;memberCode=%s}", userName,memberCode));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}

		} catch (TException e) {
			LogCvt.error("查询会员信息失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		} catch (Exception e) {
			LogCvt.error("查询会员信息失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}
	}
	
	
	/**
	 * shoppingPayChannelList:购物商品收银台接口
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月26日 下午12:30:40
	 * @param clientId
	 * @param memberCode
	 * @param isSign
	 * @return
	 * 
	 */
	public Map<String, Object> shoppingPayChannelList(String clientId, Long memberCode, Boolean isSign) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (memberCode != null ) {
				// 支付渠道列表
				Map<String,Object> payChannelMap = clientConfigSupport.getClientPaymentChannel(clientId);
				
				if(isSign != null && isSign){//用户已签约，查询签约信息
					//查询用户快捷卡尾号 (作显示用途)
					BankCardResponse res = bankCardService.selectSignedBankCardByClientId(clientId, memberCode);
					if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultCode())){
						if(res.isSetCardList() && res.getCardListSize() > 0){
							String cardNo = res.getCardList().get(0).getCardNo();
							if(StringUtil.isNotBlank(cardNo) && cardNo.length() > 4){
								//装入快捷卡尾号
								SmartCardVo bankCard = (SmartCardVo) payChannelMap.get("bankCard");
								bankCard.setFastCardNo(cardNo.substring(cardNo.length() - 4));
								payChannelMap.put("bankCard", bankCard);
							}
						}
					}else{
						LogCvt.error("查询会员绑定快捷银行卡号失败:" + res.getResultDesc());
					}
				}
				
				result.putAll(payChannelMap);
				return result;
			} else {
				LogCvt.info(String.format("缺少必要的请求参数:{memberCode=%s}",memberCode));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}
		} catch (Exception e) {
			LogCvt.error("查询会员绑定快捷银行卡号异常", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}
	}
	
	
	/**
	 * 订单支付（面对面订单支付接口）
	 * 含有积分,需要验证支付密码
	 * @param clientId
	 * @param payOrdersVo
	 * @return
	 */
	public Map<String, Object> payQrcodeOrders(PayOrdersVo payOrdersVo,boolean isSimplePayPwd) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (payOrdersVo == null || StringUtil.isEmpty(payOrdersVo.getOrderId())) {
			LogCvt.info(String.format("缺少必要的请求参数:{%s}", JSON.toJSONString(payOrdersVo)));
			result.put(Results.code, "1001");
			result.put(Results.msg, "缺少必要的请求参数");
			return result;
		}
		
		// 支付方式
		int type = payOrdersVo.getType();
		
		//订单支付通用检验
		boolean isValidatePass = validatePayOrder(payOrdersVo , type);
		if(!isValidatePass){
			result.put(Results.code, "1001");
			result.put(Results.msg, "缺少必要的请求参数");
			return result;
		}

		LogCvt.info(String.format("订单支付>> 用户:%s, 订单号：%s, 支付方式:%s", payOrdersVo.getMemberCode(), payOrdersVo.getOrderId(), type));
		
		// 订单支付
		boolean unSupportType = false;
		Map<String, Object> payResult = null;
		switch (type) {
		case 1:// 纯积分 - 方付通积分支付
			payResult = this.pointsPay(payOrdersVo, PaymentMethod.froadPoints.getCode());
			break;
		case 2:// 纯积分 - 银行积分支付
			payResult = this.pointsPay(payOrdersVo, PaymentMethod.bankPoints.getCode());
			break;
		case 3:// 快捷 - 方付通积分vs现金支付
			payResult = this.fastPointsAndCashPay(payOrdersVo, PaymentMethod.froadPointsAndCash.getCode());
			break;
		case 4:// 快捷 - 银行积分vs现金支付
			payResult = this.fastPointsAndCashPay(payOrdersVo, PaymentMethod.bankPointsAndCash.getCode());
			break;
		case 5:// 贴膜卡 - 方付通积分支付
			payResult = this.foilCardPointsPay(payOrdersVo, PaymentMethod.froadPointsAndCash.getCode());
			break;
		case 6:// 贴膜卡 - 银行积分支付
			payResult = this.foilCardPointsPay(payOrdersVo, PaymentMethod.bankPointsAndCash.getCode());
			break;
		case 7:// 快捷- 现金支付 (2 面对面收银台渠道 )
			payResult = this.cashPay(payOrdersVo, PaymentChannel.fast_pay.getCode(),2);
			break;
		case 8:// 贴膜卡- 现金支付(2 面对面收银台渠道 )
			payResult = this.cashPay(payOrdersVo, PaymentChannel.foil_card.getCode(),2);
			break;
		case 9:// 网银支付(2 面对面收银台渠道 )
			payResult = this.cashPay(payOrdersVo, PaymentChannel.cyberbank_pay.getCode(),2);
			break;
		case 10:// 卡密-联盟积分 支付
			payResult = this.CamiPointsAndCashPay(payOrdersVo, PaymentMethod.froadPointsAndCash.getCode());
			break;
		case 11:// 卡密-银行积分 支付
			payResult = this.CamiPointsAndCashPay(payOrdersVo, PaymentMethod.bankPointsAndCash.getCode());
			break;
		default:
			unSupportType = true;
		}

		if (unSupportType) {
			LogCvt.info(String.format("不支持的支付方式:%s", JSON.toJSONString(payOrdersVo.getType())));
			result.put(Results.code, "1001");
			result.put(Results.msg, "不支持的支付方式");
			//关闭订单
			closeOrder(payOrdersVo.getClientId(), payOrdersVo.getOrderId());
			return result;
		}

		if (payResult == null) {
			LogCvt.error("支付失败，支付结果为空");
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			//关闭订单
			closeOrder(payOrdersVo.getClientId(), payOrdersVo.getOrderId());
			return result;
		}else{
			LogCvt.info(String.format("订单%s支付结果>> %s", payOrdersVo.getOrderId(), JSON.toJSONString(payResult)));
			if( Constants.RESULT_CODE_SUCCESS.equals(payResult.get(Results.code))){
				if(isSimplePayPwd){
					LogCvt.info(String.format("支付密码规则验证为[简单密码],返回前端标识"));
					payResult.put("isSimplePayPwd", isSimplePayPwd);
				}
			}else{
				//关闭订单
				closeOrder(payOrdersVo.getClientId(), payOrdersVo.getOrderId());
			}
		}
		return payResult;
	}


	/**
	 * 普通商品订单支付接口
	 * 只存在现金支付,不需要验证支付密码
	 * @param clientId
	 * @param payOrdersVo
	 * @return
	 */
	public Map<String, Object> payOrders(PayOrdersVo payOrdersVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (payOrdersVo == null || StringUtil.isEmpty(payOrdersVo.getOrderId())) {
			LogCvt.info(String.format("缺少必要的请求参数:{%s}", JSON.toJSONString(payOrdersVo)));
			result.put(Results.code, "1001");
			result.put(Results.msg, "缺少必要的请求参数");
			return result;
		}
		
		// 支付方式
		int type = payOrdersVo.getType();
		LogCvt.info("支付方式："+type);
		
		//是否简单密码标识
		boolean isSimplePayPwd = false ;
		
		//订单支付通用检验
		boolean isValidatePass = validatePayOrder(payOrdersVo , type);
		if(!isValidatePass){
			result.put(Results.code, "1001");
			result.put(Results.msg, "缺少必要的请求参数");
			return result;
		}
		
		// 支付密码校验
		if (type == 1 || type == 2 || type == 3 || type == 4 || type == 7 ) {
			if (StringUtil.isNotBlank(payOrdersVo.getCiphertextPwd()) || StringUtil.isNotBlank(payOrdersVo.getCreateSource())) {
				// 支付密码验证
				CreateSource cs = SimpleUtils.codeToCreateSource(payOrdersVo.getCreateSource());
//				String paypwd = SimpleUtils.decodePwd(payOrdersVo.getCiphertextPwd(), cs);
				String paypwd = payOrdersVo.getCiphertextPwd();
				if (CreateSource.pc.equals(cs) || cs == null) {
		            LogCvt.info("[密码加密] >> 渠道：PC，支付密码加密");
		            paypwd = userEngineSupport.encryptPwd(paypwd);
		            if (StringUtil.isBlank(paypwd)) {
		            	LogCvt.info("[密码加密] >> 支付密码加密失败");
		            	result.put(Results.code, "9999");
						result.put(Results.msg, "支付密码加密失败");
						return result;
					}
		        }
				ResultBean verifyPwd = userEngineSupport.verifyMemberPayPwd(payOrdersVo.getMemberCode(), paypwd, payOrdersVo.getClientId());
				if(!verifyPwd.isSuccess()){
					LogCvt.info(String.format("支付密码验证失败:%s",JSON.toJSONString(verifyPwd)));
					result.put(Results.code, StringUtil.isBlank(verifyPwd.getCode()) ? "9999" : verifyPwd.getCode() );
					result.put(Results.msg, verifyPwd.getMsg());
					return result;
				}else{
					LogCvt.info(String.format("支付密码验证成功! orderId:%s, memberCode:%s",payOrdersVo.getOrderId(), payOrdersVo.getMemberCode()));
					ResultBean validPwd = userEngineSupport.validationPayPwdRule(payOrdersVo.getMemberCode(), paypwd);
					if(!validPwd.isSuccess()){
						isSimplePayPwd = true;
					}
				}
			} else {
				LogCvt.info(String.format("支付密码验证缺少必要参数:{ciphertextPwd=%s,createSource=%s}",payOrdersVo.getCiphertextPwd(),payOrdersVo.getCreateSource()));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
			}
		}
		//用户状态判断
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(payOrdersVo.getMemberCode());
		if(UserStatus.UNAVAILABLE.getValue() == user.getStatus()){
			//未激活用户不可支付
			result.put(Results.code, "9999");
			result.put(Results.msg, "当前用户处于未激活状态，有疑问请致电客服。");
			return result;
		}
		
		//针对用户名为身份证加密带*的情况,重新查询用户名
		payOrdersVo.setUserName(user.getLoginID());		
		LogCvt.info(String.format("订单支付>> 用户:%s, 订单号：%s, 支付方式:%s", payOrdersVo.getMemberCode(), payOrdersVo.getOrderId(), type));
		
		// 订单支付
		boolean unSupportType = false;
		Map<String, Object> payResult = null;
		switch (type) {
		case 1:// 纯积分 - 方付通积分支付
			payResult = this.pointsPay(payOrdersVo, PaymentMethod.froadPoints.getCode());
			break;
		case 2:// 纯积分 - 银行积分支付
			payResult = this.pointsPay(payOrdersVo, PaymentMethod.bankPoints.getCode());
			break;
		case 3:// 快捷 - 方付通积分vs现金支付
			payResult = this.fastPointsAndCashPay(payOrdersVo, PaymentMethod.froadPointsAndCash.getCode());
			break;
		case 4:// 快捷 - 银行积分vs现金支付
			payResult = this.fastPointsAndCashPay(payOrdersVo, PaymentMethod.bankPointsAndCash.getCode());
			break;
		case 5:// 贴膜卡 - 方付通积分支付
			payResult = this.foilCardPointsPay(payOrdersVo, PaymentMethod.froadPointsAndCash.getCode());
			break;
		case 6:// 贴膜卡 - 银行积分支付
			payResult = this.foilCardPointsPay(payOrdersVo, PaymentMethod.bankPointsAndCash.getCode());
			break;
		case 7:// 快捷- 现金支付(1 普通商品收银台渠道 )
			payResult = this.cashPay(payOrdersVo, PaymentChannel.fast_pay.getCode(),1);
			break;
		case 8:// 贴膜卡- 现金支付(1 普通商品收银台渠道 )
			payResult = this.cashPay(payOrdersVo, PaymentChannel.foil_card.getCode(),1);
			break;
		case 9:// 卡密支付(1 普通商品收银台渠道 )
			payResult = this.cashPay(payOrdersVo, PaymentChannel.cyberbank_pay.getCode(),1);
			break;
		case 10:// 卡密-联盟积分 支付
			payResult = this.CamiPointsAndCashPay(payOrdersVo, PaymentMethod.froadPointsAndCash.getCode());
			break;
		case 11:// 卡密-银行积分 支付
			payResult = this.CamiPointsAndCashPay(payOrdersVo, PaymentMethod.bankPointsAndCash.getCode());
			break;
		default:
			unSupportType = true;
		}

		if (unSupportType) {
			LogCvt.info(String.format("不支持的支付方式:%s", JSON.toJSONString(payOrdersVo.getType())));
			result.put(Results.code, "1001");
			result.put(Results.msg, "不支持的支付方式");
			return result;
		}

		if (payResult == null) {
			LogCvt.error("支付失败，支付结果为空");
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}else{
			LogCvt.info(String.format("订单%s支付结果>> %s", payOrdersVo.getOrderId(), JSON.toJSONString(payResult)));
			if( Constants.RESULT_CODE_SUCCESS.equals(payResult.get(Results.code))){
				if(isSimplePayPwd){
					LogCvt.info(String.format("支付密码规则验证为[简单密码],返回前端标识"));
					payResult.put("isSimplePayPwd", isSimplePayPwd);
				}
			}
		}
		return payResult;
	}
	
	

	
	/**
	 * 支付异常处理
	 * <p>请求超时异常，返回给前端固定的结果码以便优化体验
	 * @param result
	 * @param e
	 * @return
	 */
	public Map<String, Object> paymentExceptionHandler(Map<String, Object> result, TException e){
		
		if(e.getCause() instanceof SocketTimeoutException){
			LogCvt.error("支付请求超时：", e);
			result.put(Results.code, ResultCode.payTimeout.getCode());
			result.put(Results.msg, ResultCode.payTimeout.getMsg());
			return result;
		}else{
			LogCvt.error("支付失败：", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}

	}
	
	/**
	 * 
	 * shoppingPayVipChannel:vip支付.
	 *
	 * @author wm
	 * 2015年12月11日 上午11:50:35
	 * @param clientId
	 * @param memberCode
	 * @param isSign
	 * @return
	 *
	 */
	public Map<String, Object> shoppingPayVipChannel(String clientId,String userName, Long memberCode, Boolean isSign) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (memberCode != null ) {
				//用户积分信息
				Map<String,Object> point =integralSupport.getUserPointsAmount(clientId, userName);
				result.putAll(point);
				
				// 支付渠道列表
				Map<String,Object> payChannelMap = clientConfigSupport.getClientPaymentChannel(clientId);
				payChannelMap.put("payChannel",point.get("displayChannel"));
				
				if(isSign != null && isSign){//用户已签约，查询签约信息
					//查询用户快捷卡尾号 (作显示用途)
					BankCardResponse res = bankCardService.selectSignedBankCardByClientId(clientId, memberCode);
					if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultCode())){
						if(res.isSetCardList() && res.getCardListSize() > 0){
							String cardNo = res.getCardList().get(0).getCardNo();
							if(StringUtil.isNotBlank(cardNo) && cardNo.length() > 4){
								//装入快捷卡尾号
								SmartCardVo bankCard = (SmartCardVo) payChannelMap.get("bankCard");
								bankCard.setFastCardNo(cardNo.substring(cardNo.length() - 4));
								payChannelMap.put("bankCard", bankCard);
							}
						}
					}else{
						LogCvt.error("查询会员绑定快捷银行卡号失败:" + res.getResultDesc());
					}
				}
				
				result.putAll(payChannelMap);
				return result;
			} else {
				LogCvt.info(String.format("缺少必要的请求参数:{memberCode=%s}",memberCode));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}
		} catch (Exception e) {
			LogCvt.error("查询会员绑定快捷银行卡号异常", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统繁忙");
			return result;
		}
	}
	
	
	/**
	 * closeOrder:面对面订单支付失败或者支付异常，需关闭订单
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年12月21日 下午4:32:28
	 * @param clientId
	 * @param orderId
	 * 
	 */
	public void closeOrder(String clientId ,String orderId){
		//面对面订单支付失败或者支付异常，需关闭订单
		CloseOrderVoReq req = new CloseOrderVoReq();
		req.setClientId(clientId);
		req.setOrderId(orderId);
		try {
			CloseOrderVoRes res = orderService.closeOrder(req);
			LogCvt.info("面对面支付异常或者失败关闭订单结果：");res.getResultVo();
		} catch (TException e) {
			LogCvt.error("面对面支付异常关闭订单接口异常",e);
		}
	}
	
	/**
	 * validatePayOrder:订单支付通用校验
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2016年1月20日 下午10:43:36
	 * @param payOrdersVo
	 * @param type
	 * @return false 未通过    true 通过
	 * 
	 */
	public boolean validatePayOrder(PayOrdersVo payOrdersVo,int type){
		boolean flag = true ;
		// 支付参数验证
		if (type == 2 && StringUtil.isEmpty(payOrdersVo.getPointOrgNo())) {
			LogCvt.info(String.format("银行积分支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if (type == 3 && StringUtil.isEmpty(payOrdersVo.getCashOrgNo())) {
			LogCvt.info(String.format("快捷方付通积分vs现金支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if (type == 4 && (StringUtil.isEmpty(payOrdersVo.getCashOrgNo()) || StringUtil.isEmpty(payOrdersVo.getPointOrgNo()))) {
			LogCvt.info(String.format("快捷银行积分vs现金支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if (type == 5 && (StringUtil.isEmpty(payOrdersVo.getFoilCardNum()) || StringUtil.isEmpty(payOrdersVo.getCashOrgNo()))) {
			LogCvt.info(String.format("贴膜卡方付通积分支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if (type == 6 && ( StringUtil.isEmpty(payOrdersVo.getFoilCardNum())|| StringUtil.isEmpty(payOrdersVo.getCashOrgNo()) || StringUtil.isEmpty(payOrdersVo.getPointOrgNo()))) {
			LogCvt.info(String.format("贴膜卡银行积分支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if (type == 7 && StringUtil.isEmpty(payOrdersVo.getCashOrgNo())) {
			LogCvt.info(String.format("快捷现金支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if (type == 8 && (StringUtil.isEmpty(payOrdersVo.getFoilCardNum()) || StringUtil.isEmpty(payOrdersVo.getCashOrgNo()))) {
			LogCvt.info(String.format("贴膜卡现金支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if(type == 9 && StringUtil.isEmpty(payOrdersVo.getCashOrgNo())){
			LogCvt.info(String.format("卡密现金支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if(type == 10 && StringUtil.isEmpty(payOrdersVo.getCashOrgNo()) ){
			LogCvt.info(String.format("卡密方付通积分支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		} else if(type == 11 && (StringUtil.isEmpty(payOrdersVo.getCashOrgNo()) || StringUtil.isEmpty(payOrdersVo.getPointOrgNo()) ) ){
			LogCvt.info(String.format("卡密银行积分支付缺少必要参数:%s",JSON.toJSONString(payOrdersVo)));
			flag = false ;
		}
		return flag;
	}
	
	

}
