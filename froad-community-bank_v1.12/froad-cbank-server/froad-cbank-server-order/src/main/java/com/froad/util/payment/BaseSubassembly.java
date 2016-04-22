package com.froad.util.payment;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.payment.PayThriftVoBean;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.PayType;
import com.froad.enums.PaymentChannel;
import com.froad.enums.PaymentMethod;
import com.froad.po.Client;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.Payment;
import com.froad.po.PaymentMongoEntity;
import com.froad.support.impl.payment.DataPersistentImpl;
import com.froad.support.payment.DataPersistent;
import com.froad.thrift.vo.payment.DoPayOrdersVoReq;
import com.froad.thrift.vo.payment.PaymentQueryVo;
import com.froad.util.Arith;


/**
 * 基础的对支付模块的支持服务类
* <p>Function: BaseSubassembly</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-20 上午9:43:26
* @version 1.0
 */
public class BaseSubassembly {

	private BaseSubassembly(){};
	
	//-----------------------------------beans---------------------------------------
	private static DataPersistentImpl dataPersistent;
	//-----------------------------------beans--------------------------------------
	
	
	//---------------------------------实体转换---------------------------------------
	/**
	 * MongoEntity TO Vo
	* <p>Function: loadyPaymentQueryVo</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 上午9:58:58
	* @version 1.0
	* @param mongoEntity
	* @return
	 */
	public static PaymentQueryVo loadyPaymentQueryVo(PaymentMongoEntity mongoEntity){
		if(mongoEntity == null){
			return null;
		}
		PaymentQueryVo paymentQueryVo = new PaymentQueryVo();
		paymentQueryVo.setId(mongoEntity.getId() == null ? 0 : mongoEntity.getId());
		paymentQueryVo.setCreateTime(mongoEntity.getCreate_time() != null ? mongoEntity.getCreate_time().getTime() : 0);
		paymentQueryVo.setPaymentId(mongoEntity.getPayment_id());
		paymentQueryVo.setClientId(mongoEntity.getClient_id());
		paymentQueryVo.setOrderId(mongoEntity.getOrder_id());
		paymentQueryVo.setBillNo(mongoEntity.getBill_no());
		paymentQueryVo.setPaymentType(mongoEntity.getPayment_type());
		paymentQueryVo.setPaymentValue(mongoEntity.getPayment_value() == null ? 0d : Arith.div(mongoEntity.getPayment_value(),Const.HDOP_1000));
		paymentQueryVo.setPaymentTypeDetails(mongoEntity.getPayment_type_details() == null ? -1 : mongoEntity.getPayment_type_details());
		paymentQueryVo.setStep(mongoEntity.getStep());
		paymentQueryVo.setIsEnable(mongoEntity.getIs_enable());
		paymentQueryVo.setPaymentStatus(mongoEntity.getPayment_status());
		paymentQueryVo.setPaymentOrgNo(mongoEntity.getPayment_org_no());
		paymentQueryVo.setFromAccountName(mongoEntity.getFrom_account_name());
		paymentQueryVo.setFromAccountNo(mongoEntity.getFrom_account_no());
		paymentQueryVo.setToAccountName(mongoEntity.getTo_account_name());
		paymentQueryVo.setToAccountNo(mongoEntity.getTo_account_no());
		paymentQueryVo.setFromPhone(mongoEntity.getFrom_phone());
		paymentQueryVo.setToPhone(mongoEntity.getTo_phone());
		paymentQueryVo.setFromUserName(mongoEntity.getFrom_user_name());
		paymentQueryVo.setToUserName(mongoEntity.getTo_user_name());
		paymentQueryVo.setResultCode(mongoEntity.getResult_code());
		paymentQueryVo.setResultDesc(mongoEntity.getResult_desc());
		paymentQueryVo.setRemark(mongoEntity.getRemark());
		paymentQueryVo.setAutoRefund(mongoEntity.getAuto_refund());
		paymentQueryVo.setPointRate(mongoEntity.getPoint_rate() == null ? 0 : mongoEntity.getPoint_rate());
		paymentQueryVo.setPaymentReason(mongoEntity.getPayment_reason());
		paymentQueryVo.setIsDisposeException(mongoEntity.getIs_dispose_exception());
		paymentQueryVo.setRefundPointsBillNo(mongoEntity.getRefund_points_bill_no());
		return paymentQueryVo;
	}
	
	/**
	 * DoPayOrdersVoReq(Thrift) --> PayThriftVoBean
	* <p>Function: loadyToPayThriftVoBean</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 下午12:34:49
	* @version 1.0
	* @param doPayOrdersVoReq
	* @return
	 */
	public static PayThriftVoBean loadyToPayThriftVoBean(DoPayOrdersVoReq doPayOrdersVoReq){
		PayThriftVoBean payThriftVoBean = new PayThriftVoBean();
		payThriftVoBean.setClientId(doPayOrdersVoReq.getClientId()); 
		payThriftVoBean.setOrderId(doPayOrdersVoReq.getOrderId()); 
		payThriftVoBean.setPointOrgNo(doPayOrdersVoReq.getPointOrgNo()); 
		payThriftVoBean.setCashOrgNo(doPayOrdersVoReq.getCashOrgNo()); 
		payThriftVoBean.setPayType(doPayOrdersVoReq.getPayType()); 
		payThriftVoBean.setCashType(doPayOrdersVoReq.getCashType()); 
		payThriftVoBean.setPointAmount(doPayOrdersVoReq.getPointAmount()); 
		payThriftVoBean.setCashAmount(doPayOrdersVoReq.getCashAmount()); 
		payThriftVoBean.setFoilCardNum(doPayOrdersVoReq.getFoilCardNum()); 
		return payThriftVoBean;
	}
	
	/**
	 * payThriftVoBean转方付通积分流水
	* <p>Function: loadyToPaymentFroadPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午2:31:45
	* @version 1.0
	* @param payThriftVoBean
	* @return
	 */
	public static Payment loadyToPaymentFroadPoint(PayThriftVoBean payThriftVoBean){
		if(payThriftVoBean == null){
			return null;
		}
		Payment payment = new Payment();
		payment.setPaymentId(EsyT.simpleID());
		payment.setClientId(payThriftVoBean.getClientId());
		payment.setOrderId(payThriftVoBean.getOrderId());
		payment.setPaymentTypeDetails(0);
		payment.setPaymentOrgNo(payThriftVoBean.getPointOrgNo());
		payment.setPaymentType(1);
		payment.setPaymentValue(Arith.mul(payThriftVoBean.getPointAmount(),Const.HDOP_1000));
		payment.setPointRate(payThriftVoBean.getPointRatio());
		payment.setPaymentReason("2");
		return payment;
	}
	
	
	/**
	 * payThriftVoBean转银行积分流水
	* <p>Function: loadyToPaymentBankPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午2:32:15
	* @version 1.0
	* @param payThriftVoBean
	* @return
	 */
	public static Payment loadyToPaymentBankPoint(PayThriftVoBean payThriftVoBean){
		if(payThriftVoBean == null){
			return null;
		}
		Payment payment = new Payment();
		payment.setPaymentId(EsyT.simpleID());
		payment.setClientId(payThriftVoBean.getClientId());
		payment.setOrderId(payThriftVoBean.getOrderId());
		payment.setPaymentTypeDetails(0);
		payment.setPaymentOrgNo(payThriftVoBean.getPointOrgNo());
		payment.setPaymentType(3);
		payment.setPaymentValue(Arith.mul(payThriftVoBean.getPointAmount(),Const.HDOP_1000));
		payment.setPointRate(payThriftVoBean.getPointRatio());
		payment.setPaymentReason("2");
		return payment;
	}
	
	/**
	 * payThriftVoBean转现金流水
	* <p>Function: loadyToPaymentCash</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 下午5:38:53
	* @version 1.0
	* @param payThriftVoBean
	* @return
	 */
	public static Payment loadyToPaymentCash(PayThriftVoBean payThriftVoBean){
		if(payThriftVoBean == null){
			return null;
		}
		Payment payment = new Payment();
		payment.setPaymentId(EsyT.simpleID());
		payment.setClientId(payThriftVoBean.getClientId());
		payment.setOrderId(payThriftVoBean.getOrderId());
		payment.setPaymentTypeDetails(payThriftVoBean.getCashType());
		payment.setPaymentOrgNo(payThriftVoBean.getCashOrgNo());
		payment.setPaymentType(2);
		payment.setPaymentValue(Arith.mul(payThriftVoBean.getCashAmount(),Const.HDOP_1000));
		payment.setPaymentReason("2");
		return payment;
	}
	
	//---------------------------------实体转换---------------------------------------
	
	//---------------------------------交易类型---------------------------------------
	
	/**
	 * 是否为纯积分交易（判断PaymentMethod）
	* <p>Function: isPurePointsPayment</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 上午10:03:08
	* @version 1.0
	* @param paymentMethod
	* @return
	 */
	public static boolean isPurePointsPayment(PaymentMethod paymentMethod) {
		switch (paymentMethod) {
		case bankPoints:
			return true;
		case froadPoints:
			return true;
		default:
			return false;
		}
	}

    /**
     * 是否为纯积分交易（判断PaymentMethod.code）
    * <p>Function: isPurePointsPayment</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-22 下午2:49:18
    * @version 1.0
    * @param code
    * @return
     */
	public static boolean isPurePointsPayment(String code) {
		return PaymentMethod.bankPoints.getCode().equals(code) || PaymentMethod.froadPoints.getCode().equals(code);
	}

	/**
	 * 是否为组合支付（判断PaymentMethod.code）
	* <p>Function: isCombinePayment</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 上午10:08:18
	* @version 1.0
	* @param code
	* @return
	 */
	public static boolean isCombinePayment(String code) {
		return code.equals(PaymentMethod.bankPointsAndCash.getCode())
				|| code.equals(PaymentMethod.froadPointsAndCash.getCode());
	}

    /**
     * 判断是否是积分支付类型的支付流水（判断PayType.code）
    * <p>Function: isPaymentTypeOfPurePoints</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-21 下午4:29:34
    * @version 1.0
    * @param paymentType
    * @return
     */
	public static boolean isPaymentTypeOfPurePoints(Integer paymentType) {
		if (PayType.bankPoint.code == paymentType || PayType.froadPoint.code == paymentType) {
			return true;
		}
		return false;
	}

    /**
     * 是否为组合支付（判断PaymentMethod）
    * <p>Function: isCombinePayment</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-20 上午10:11:18
    * @version 1.0
    * @param paymentMethod
    * @return
     */
	public static boolean isCombinePayment(PaymentMethod paymentMethod) {
		switch (paymentMethod) {
		case bankPointsAndCash:
			return true;
		case froadPointsAndCash:
			return true;
		default:
			return false;
		}
	}

    /**
     * 是否为纯现金支付（判断PaymentMethod）
    * <p>Function: isPureCashPayment</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-20 上午10:14:20
    * @version 1.0
    * @param paymentMethod
    * @return
     */
	public static boolean isPureCashPayment(PaymentMethod paymentMethod) {
		return PaymentMethod.cash.equals(paymentMethod);
	}
    
    /**
     * 判断是否是纯现金支付
     * isPureCashPayment:(这里用一句话描述这个方法的作用).
     *
     * @author Zxy
     * 2015年9月11日 下午2:05:15
     * @param code
     * @return
     *
     */
	public static boolean isPureCashPayment(String code) {
		return code.equals(PaymentMethod.cash.getCode());
	}

    /**
     * PaymentMethod.code TO PaymentMethod
    * <p>Function: getPaymentMethodByIntValue</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-20 上午10:19:42
    * @version 1.0
    * @param type
    * @return
     */
	public static PaymentMethod codeToPaymentMethod(int type) {
		switch (type) {
		case 1:
			return PaymentMethod.cash;
		case 2:
			return PaymentMethod.froadPoints;
		case 3:
			return PaymentMethod.bankPoints;
		case 4:
			return PaymentMethod.froadPointsAndCash;
		case 5:
			return PaymentMethod.bankPointsAndCash;
		default:
			return PaymentMethod.invalid;
		}
	}
	// ---------------------------------交易类型---------------------------------------

	// ---------------------------------第三方公共信息---------------------------------------
	private static DataPersistent initDataPersistent() {
		if (dataPersistent == null) {
			dataPersistent = new DataPersistentImpl();
		}
		return dataPersistent;
	}
    
    /**
     * 从缓存中获取客户端信息
    * <p>Function: acquireClientFromRedis</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-20 下午5:00:54
    * @version 1.0
    * @param clientId
    * @return
     */
	public static Client acquireClientFromRedis(String clientId) {
		Client client = initDataPersistent().acquireClientFromRedis(clientId);
		if (client == null) {
			EsyT.sendPoint(MonitorPointEnum.order_pay_query_redis_failed);
		}
		return client;
	}
    
    /**
     * 从缓存中获取客户端支付渠道信息
    * <p>Function: acquireClientPaymentChannelFromRedis</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-20 下午5:48:55
    * @version 1.0
    * @param clientId
    * @return
     */
	public static List<ClientPaymentChannel> acquireClientPaymentChannelFromRedis(String clientId) {
		List<ClientPaymentChannel> channel = initDataPersistent().acquireClientPaymentChannelFromRedis(clientId);
		if (channel == null || channel.size() == 0) {
			EsyT.sendPoint(MonitorPointEnum.order_pay_query_redis_failed);
		}
		return channel;
	}
    
    /**
     * 从缓存中获取openapi_partner_no
    * <p>Function: acquireOpenAPIPartnerNo</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-20 下午7:02:03
    * @version 1.0
    * @param clientId
    * @return
     */
	public static String acquireOpenAPIPartnerNo(String clientId) {
		return acquireClientFromRedis(clientId).getOpenapiPartnerNo();
	}
    
    /**
     * 从缓存中获取acquirePointPartnerNo
    * <p>Function: acquirePointPartnerNo</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-20 下午7:04:35
    * @version 1.0
    * @param clientId
    * @return
     */
	public static String acquirePointPartnerNo(String clientId) {
		return acquireClientFromRedis(clientId).getPointPartnerNo();
	}

    
    /**
     * 从缓存中获取支付渠道中银行积分类型的支付机构号
    * <p>Function: acquireBankPointPaymentOrgNo</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-21 上午9:19:29
    * @version 1.0
    * @param clientId
    * @return
     */
	public static String acquireBankPointPaymentOrgNo(String clientId) {
		List<ClientPaymentChannel> channels = acquireClientPaymentChannelFromRedis(clientId);
		if (channels == null || channels.size() == 0) {
			return null;
		}
		for (ClientPaymentChannel channel : channels) {
			if (channel == null) {
				return null;
			}
			if (String.valueOf(PaymentChannel.bank_point.getCode()).equals(channel.getType())) {
				return channel.getPaymentOrgNo();
			}
		}
		return null;
	}

    /**
     * 获取方付通积分积分比例
    * <p>Function: acquireFroadPointPointRate</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-26 上午11:31:50
    * @version 1.0
    * @param clientId
    * @return
     */
	public static Integer acquireFroadPointPointRate(String clientId) {
		List<ClientPaymentChannel> channels = acquireClientPaymentChannelFromRedis(clientId);
		if (channels == null || channels.size() == 0) {
			return null;
		}
		for (ClientPaymentChannel channel : channels) {
			if (channel == null) {
				return null;
			}
			if (String.valueOf(PaymentChannel.froad_point.getCode()).equals(channel.getType())) {
				return StringUtils.isEmpty(channel.getPointRate()) ? null : Integer.valueOf(channel.getPointRate());
			}
		}
		return null;
	}

	/**
	 * 获取银行积分积分比例
	 * acquireBankPointPointRate:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年9月15日 上午10:41:33
	 * @param clientId
	 * @return
	 *
	 */
	public static Integer acquireBankPointPointRate(String clientId) {
		List<ClientPaymentChannel> channels = acquireClientPaymentChannelFromRedis(clientId);
		if (channels == null || channels.size() == 0) {
			return null;
		}
		for (ClientPaymentChannel channel : channels) {
			if (channel == null) {
				return null;
			}
			if (String.valueOf(PaymentChannel.bank_point.getCode()).equals(channel.getType())) {
				return StringUtils.isEmpty(channel.getPointRate()) ? null : Integer.valueOf(channel.getPointRate());
			}
		}
		return null;
	}

    /**
     * 从缓存中获取支付渠道中贴膜卡类型的支付机构号
    * <p>Function: acquireFoilCardPointPaymentOrgNo</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-22 上午11:47:51
    * @version 1.0
    * @param clientId
    * @return
     */
	public static String acquireFoilCardPointPaymentOrgNo(String clientId) {
		List<ClientPaymentChannel> channels = acquireClientPaymentChannelFromRedis(clientId);
		if (channels == null || channels.size() == 0) {
			return null;
		}
		for (ClientPaymentChannel channel : channels) {
			if (channel == null) {
				return null;
			}
			if (String.valueOf(PaymentChannel.foil_card.getCode()).equals(channel.getType())) {
				return channel.getPaymentOrgNo();
			}
		}
		return null;
	}

	/**
	 * 从缓存中获取结算支付机构号
	 * acquireSettlePaymentOrgNo:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年9月15日 上午10:36:18
	 * @param clientId
	 * @return
	 *
	 */
	public static String acquireSettlePaymentOrgNo(String clientId) {
		List<ClientPaymentChannel> channels = acquireClientPaymentChannelFromRedis(clientId);
		if (channels == null || channels.size() == 0) {
			return null;
		}
		for (ClientPaymentChannel channel : channels) {
			if (channel == null) {
				return null;
			}
			if (String.valueOf(PaymentChannel.timely_pay.getCode()).equals(channel.getType())) {
				return channel.getPaymentOrgNo();
			}
		}
		return null;
	}
	
	
    /**
     * 获取客户端对应的bankOrg VIP 所属银行标签
    * <p>Function: acquireBankOrg</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015年6月24日 下午2:18:49
    * @version 1.0
    * @param clientId
    * @return
     */
	public static String acquireBankOrg(String clientId) {
		return acquireClientFromRedis(clientId).getBankOrg();
	}

	// ---------------------------------第三方公共信息---------------------------------------
}
