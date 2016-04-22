package com.froad.thrift.service.impl.validation;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.CashType;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.thrift.vo.payment.DoPayOrdersVoReq;
import com.froad.util.payment.BaseSubassembly;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: PaymentValidation
 * @Description: 用于对支付参数的基本校验
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月20日 上午10:33:10
 */
public class PaymentValidation {

	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: baseParamVerifyBeforPay
	 * @Description: 支付订单前基础参数校验  
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param doPayOrdersVoReq
	 * @return
	 * @Return: DoPayOrdersVoRes
	 */
	public static ResultBean baseParamVerifyBeforPay(DoPayOrdersVoReq doPayOrdersVoReq){
		
		//对参数进行常规校验
		if(!doPayOrdersVoReq.isSetPayType() || !doPayOrdersVoReq.isSetClientId() || !doPayOrdersVoReq.isSetOrderId()){ //校验申明的非空字段
			return new ResultBean(ResultCode.payment_params_error, "必要参数不能为空");
		}
		
		PaymentMethod paymentMethod = BaseSubassembly.codeToPaymentMethod(doPayOrdersVoReq.getPayType());
		int cashTypeCode = doPayOrdersVoReq.getCashType();
		
		//校验组合参数是否有正确的依赖存在性
		if(BaseSubassembly.isPurePointsPayment(paymentMethod)){
			//采用纯积分支付
			if(!doPayOrdersVoReq.isSetPointAmount() || !doPayOrdersVoReq.isSetPointOrgNo()){
				//纯积分支付必要参数校验失败
				return new ResultBean(ResultCode.payment_params_error, "纯积分支付未指定必要参数");
			}
		}else if(BaseSubassembly.isPureCashPayment(paymentMethod)){
			//采用纯现金支付
			if(!doPayOrdersVoReq.isSetCashAmount() || !doPayOrdersVoReq.isSetCashOrgNo() || !doPayOrdersVoReq.isSetCashType()){
				return new ResultBean(ResultCode.payment_params_error, "纯现金支付未指定必要参数");
			}
			if(CashType.foil_card.code() == cashTypeCode){ //如果是贴膜卡支付现金需要确认是否指定贴膜卡手机号
				if(StringUtils.isEmpty(doPayOrdersVoReq.getFoilCardNum())){
					return new ResultBean(ResultCode.payment_params_error, "贴膜卡支付未指定贴膜卡手机号");
				}
			}
		}else if(BaseSubassembly.isCombinePayment(paymentMethod)){
			//采用积分&现金支付
			if(!doPayOrdersVoReq.isSetPointAmount() || !doPayOrdersVoReq.isSetPointOrgNo()){
				//纯积分支付必要参数校验失败
				return new ResultBean(ResultCode.payment_params_error, "包含积分支付未指定必要参数");
			}
			if(!doPayOrdersVoReq.isSetCashAmount() || !doPayOrdersVoReq.isSetCashOrgNo() || !doPayOrdersVoReq.isSetCashType()){
				return new ResultBean(ResultCode.payment_params_error, "包含现金支付未指定必要参数");
			}
			if(CashType.foil_card.code() == cashTypeCode){ //如果是贴膜卡支付现金需要确认是否指定贴膜卡手机号
				if(StringUtils.isEmpty(doPayOrdersVoReq.getFoilCardNum())){
					return new ResultBean(ResultCode.payment_params_error, "包含现金贴膜卡支付未指定贴膜卡手机号");
				}
			}
		}else{
			return new ResultBean(ResultCode.payment_params_error, "支付类型无效");
		}
		return new ResultBean(ResultCode.success);
	}
}
