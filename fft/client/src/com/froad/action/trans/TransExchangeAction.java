package com.froad.action.trans;

import org.apache.log4j.Logger;

import com.froad.action.support.TransActionSupport;
import com.froad.action.support.trans.ExchangeActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.bean.ExchangeTempBean;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.trans.Trans;
import com.froad.client.user.User;
import com.froad.common.PayState;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Result;
import com.opensymphony.xwork2.Action;


	/**
	 * 类描述：PC端积分兑换
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 6, 2013 10:15:01 PM 
	 */
public class TransExchangeAction extends BaseActionSupport{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final Logger logger=Logger.getLogger(TransExchangeAction.class);
	
	private TransActionSupport transActionSupport;
	
	private ExchangeActionSupport exchangeActionSupport;
	
	/**页面提交过来的数据**/
	private String transId;
	private ExchangeTempBean exchangeTempBean;
	
	/**进入商品兑换页面前，赋值 暂未使用**/
	private GoodsExchangeRack goodsExchangeRack;
	
	/*****交易完成后传递到页面上的对象***/
	private Trans trans;
	private String payResult;
	private String errorMsg;
	
    private String paymentUrl;
    private String msg;
	
//	public String queryBeforeExchange(){
//		log.info("话费充值商品ID:"+id);
//		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(id);
//		return "";
//	}
	
	public String doExchange(){
		if(transId==null||"".equals(transId)){
			logger.error("===========交易编号为空，无效的请求==========");
			payResult="error";
			errorMsg = "页面已过期";
			return Action.SUCCESS;
		}
		Integer id=null;
		try {
			id=Integer.parseInt(transId);
		} catch (Exception e) {
			logger.error("===========交易编号格式不正确，无效的请求==========",e);
			payResult="error";
			errorMsg = "无效的交易";
			return Action.SUCCESS;
		}
		String userId=(String)getSession("userId");
		User user=getLoginUser();
		if(user == null ||userId==null){
			logger.info("============没有登陆===========");
			return "login_page";
		}
		logger.info("==========正在下推交易==========");
		trans=transActionSupport.getTransById(id);
		Result result=transActionSupport.pay(trans);
		logger.info("交易下推完成，返回消息： "+result.getMsg());	
		if(Result.SUCCESS.equals(result.getCode())){
			String payMethod=trans.getPayMethod();
			if(TranCommand.POINTS_FFT.equals(payMethod)||TranCommand.POINTS_BANK.equals(payMethod)){
				payResult="success";
			}else if(TranCommand.ALPAY.equals(payMethod)){
                this.paymentUrl = result.getMsg();
                return "alipay";
			}else{
				payResult="waiting";
			}
		}else{
			payResult="error";
			errorMsg = "兑换失败，请稍后重试。";
		}
		return Action.SUCCESS;
	}
	
	
    /**
     * 支付宝支付结果检查
     *
     * @return
     */
    public String aliPayCheck()
    {
        User user = getLoginUser();
        if (user == null || Assert.empty(user.getUserID()))
        {
            logger.info("没有登陆");
            return "login_page";
        }
        if (trans == null || trans.getId() == null)
        {
            logger.info("===交易查询参数为空===");
            payResult = "error";
            errorMsg = "页面已过期";
            return Action.SUCCESS;
        }
        logger.info("查询支付结果，交易ID："+ trans.getId());
        String aliPayCheckResult = transActionSupport.aliPayResultCheck(String.valueOf(trans.getId()));
        if (PayState.PAY_SUCCESS.equals(aliPayCheckResult))
        {
        	trans=transActionSupport.getTransById(trans.getId());
        	String virtualType=trans.getVirtualType();
        	if(TranCommand.CATEGORY_HFCZ.equals(virtualType)||
        			TranCommand.CATEGORY_LOTTORY.equals(virtualType)){
        		msg="提示信息：您已成功参与积分兑换，请稍后到我的订单中确认发货信息！";
        	}
            return Action.SUCCESS;
        }
        else if (PayState.PAY_REQUEST_SUCCESS.equals(aliPayCheckResult))
        {
            return "wait";
        }
        else
        {
            errorMsg = "支付失败！";
            return "failse";
        }
    }
	
	
	
	
	public ExchangeTempBean getExchangeTempBean() {
		return exchangeTempBean;
	}

	public void setExchangeTempBean(ExchangeTempBean exchangeTempBean) {
		this.exchangeTempBean = exchangeTempBean;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}

	public void setExchangeActionSupport(ExchangeActionSupport exchangeActionSupport) {
		this.exchangeActionSupport = exchangeActionSupport;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}


	public ExchangeActionSupport getExchangeActionSupport() {
		return exchangeActionSupport;
	}

	public GoodsExchangeRack getGoodsExchangeRack() {
		return goodsExchangeRack;
	}

	public void setGoodsExchangeRack(GoodsExchangeRack goodsExchangeRack) {
		this.goodsExchangeRack = goodsExchangeRack;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}



	public String getPaymentUrl() {
		return paymentUrl;
	}



	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}



	public String getTransId() {
		return transId;
	}



	public void setTransId(String transId) {
		this.transId = transId;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

}
