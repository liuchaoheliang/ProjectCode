package com.froad.thirdparty.request.active;

import com.froad.common.beans.RefundOrderBean;
import com.froad.common.beans.ResultBean;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.refund.RefundHistory;


/**
 * 支付模块对营销平台的相关处理逻辑
 * Description : TODO<br/>
 * Project Name : froad-cbank-server-order<br/>
 * File Name : MarketingPaymentLogic.java<br/>
 * 
 * Date : 2015年11月9日 上午11:51:46 <br/> 
 * @author KaiweiXiang
 * @version
 */
public interface ActiveFunc {

    /**
     * 当付款成功后，对营销平台进行通知，前提：订单商品参与了营销活动，        
     * Function : doPaySuccess<br/>
     * 2015年11月9日 上午11:53:06 <br/>
     *  
     * @author KaiweiXiang
     * @param order
     * @param status 支付成功 status=true，面对面订单支付失败 status=false，非面对面订单支付失败不调用该接口
     * @return
     */
	public ResultBean noticeMarketingPlatformPaySuccess(OrderMongo order,boolean status);
	
	

    /**
     * 退款前调用，获取优惠金额，并记录优惠详情       
     * Function : noticeMarketingPlatformRefund<br/>
     * 2015年11月11日 上午10:42:48 <br/>
     *  
     * @author KaiweiXiang
     * @param order
     */
	public ResultBean noticeMarketingPlatformRefund(RefundHistory refundHis,boolean isQuery);
	
    /**
     * 退款失败(确认失败)时，通知营销平台，将优惠金额冲正回去 ，前提：订单商品参与了营销活动   
     * Function : noticeMarketingPlatformRefund<br/>
     * 2015年11月11日 上午10:42:48 <br/>
     *  
     * @author KaiweiXiang
     * @param order
     */
	public ResultBean noticeMarketingPlatformRefundFailure(RefundHistory refundHis);
	
	
	/**
	 * 退款完成时（一个订单全部退完时调用），通知营销平台，前提：订单商品参与了营销活动， 
	 * Function : noticeMarketingPlatformRefund<br/>
	 * 2015年11月11日 上午10:43:39 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param order
	 */
	public ResultBean noticeMarketingPlatformRefundFinish(RefundHistory refundHis);



	/**
	 * 满赠活动订单支付前校验
	 * Function : fullGiveCheck<br/>
	 * 2016年2月22日 下午5:37:42 <br/>
	 *  
	 * @author KaiweiXiang
	 * @return
	 */
	public ResultBean fullGiveCheck(OrderMongo order);
	
	
}
