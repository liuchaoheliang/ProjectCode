package com.froad.fft.support;

import java.util.List;

import com.froad.fft.persistent.common.enums.PayChannel;
import com.froad.fft.persistent.common.enums.PayMethod;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.FundsChannel;

public class TransHelper {

	/**
	  * 方法描述：是否为商户发起的交易
	  * @param: TransType
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月28日 下午2:32:37
	  */
	public static boolean isMerchantTrans(TransType transType){
		return TransType.collect.equals(transType)||
				TransType.points_rebate.equals(transType)||
				TransType.present_points.equals(transType);
	}
	
	
	/**
	  * 方法描述：是否为用户发起的交易
	  * @param: TransType
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月3日 下午4:00:27
	  */
	public static boolean isUserTrans(TransType transType){
		return isProductTrans(transType)||
				TransType.points_withdraw.equals(transType);
	}
	
	
	/**
	  * 方法描述：是否为购买实物商品的交易
	  * @param: TransType
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月28日 下午3:02:17
	  */
	public static boolean isProductTrans(TransType transType){
		return TransType.points_exchange.equals(transType)||
				TransType.group.equals(transType)||
				TransType.presell.equals(transType);
	}
	
	
	/**
	  * 方法描述：是否为纯积分支付
	  * @param: PayMethod
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月28日 下午3:08:41
	  */
	public static boolean isPointsPay(PayMethod payMethod){
		return PayMethod.points_fft.equals(payMethod)||
				PayMethod.points_bank.equals(payMethod);
	}

	
	/**
	  * 方法描述：是否有支付宝支付
	  * @param: PayMethod
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月2日 上午11:33:23
	  */
	public static boolean hasAlipay(PayMethod payMethod){
		return PayMethod.alipay.equals(payMethod)||
				PayMethod.alipay_bank_points.equals(payMethod)||
				PayMethod.alipay_fft_points.equals(payMethod);
	}
	
	
	/**
	  * 方法描述：是否有贴膜卡支付
	  * @param: PayMethod
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月2日 上午11:37:54
	  */
	public static boolean hasCardPay(PayMethod payMethod){
		return PayMethod.film_card.equals(payMethod)||
				PayMethod.points_bank_film_card.equals(payMethod)||
				PayMethod.points_fft_film_card.equals(payMethod);
	}
	
	
	/**
	  * 方法描述：查找资金机构号
	  * @param: List<FundsChannel> 资金渠道列表
	  * @param: PayChannel 支付渠道
	  * @return: 资金机构号
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月30日 上午10:59:37
	  */
	public static String findPayOrg(List<FundsChannel> list,PayChannel channel){
		FundsChannel fundsChannel=null;
		for (int i = 0; i < list.size(); i++) {
			fundsChannel=list.get(i);
			if(channel.equals(fundsChannel.getChannelType())){
				return fundsChannel.getPayOrg();
			}
		}
		return null;
	}
}
