package com.froad.action.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.bus.BusState;
import org.apache.log4j.Logger;

import com.froad.client.buyers.BuyerChannel;

import com.froad.client.buyers.Buyers;

import com.froad.client.fundsChannel.FundsChannel;
import com.froad.client.fundsChannel.FundsChannelService;

import com.froad.client.pay.Pay;
import com.froad.client.pay.PayService;
import com.froad.client.pointCashRule.PointCashRule;
import com.froad.client.pointCashRule.PointCashRuleService;
import com.froad.client.trans.AppException_Exception;
import com.froad.client.trans.BusinessException_Exception;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransService;
import com.froad.client.transRule.TransRule;
import com.froad.client.transRule.TransRuleService;
import com.froad.common.PayState;
import com.froad.common.TrackNoGenerator;
import com.froad.common.TranCommand;
import com.froad.server.tran.TranService;
import com.froad.util.Assert;
import com.froad.util.Result;
import com.froad.util.command.PayCommand;
import com.froad.util.command.RuleDetailType;
import com.froad.util.command.State;
import com.froad.util.command.TransState;
import com.opensymphony.xwork2.ActionContext;

/**
 * 类描述：交易工具类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Apr 8, 2013 8:36:03 PM
 */
public class TransActionSupport {
	private static Logger logger = Logger.getLogger(TransActionSupport.class);
	private TransService transService;
	private PayService payService;
	private SellersActionSupport sellerActionSupport;
	private BuyersActionSupport buyersActionSupport;
	private PointActionSupport pointActionSupport;

	private PointCashRuleService pointCashRuleService;
	private TransRuleService transRuleService;
	private FundsChannelService fundsChannelService;

	public String generateTrackNo() {
		return TrackNoGenerator.getTrackNoString32();
	}

	public List<Trans> queryTranListByMerchantID(String merchantID) {
		return transService.getTransByMerchantId(merchantID);
	}

	/**
	 * 方法描述：添加交易，不做支付操作
	 * 
	 * @param: Trans
	 * @return: Trans
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 8, 2013 9:13:08 PM
	 */
	public Trans addTrans(Trans trans) {
		try {
			return transService.addTrans(trans);
		} catch (Exception e) {
			logger.error("交易添加失败!", e);
			return null;
		}
	}

	public Trans clientTranAdd(Trans trans) {
		String buyersId = trans.getBuyersId();

		BuyerChannel buyerDefaultChannel = null;
		Buyers buyers = buyersActionSupport.getBuyerById(buyersId);
		if (buyers.getBuyerChannelList() != null) {
			for (int i = 0; i < buyers.getBuyerChannelList().size(); i++) {
				if ("1".equals(buyers.getBuyerChannelList().get(i)
						.getIsDefault()))
					buyerDefaultChannel = buyers.getBuyerChannelList().get(i);
			}
		}
		// if(seller.getSellerChannelList()!=null){
		// for(int j=0;j<seller.getSellerChannelList().size();j++){
		// if("1".equals(seller.getSellerChannelList().get(j).getIsDefault()))
		// sellerDefaultChannel =seller.getSellerChannelList().get(j);
		// }
		// }
		trans.setPayChannel("20");
		trans.setBuyerChannelId(buyerDefaultChannel.getChannelId());
		trans.setSellerChannelId("100001005");
		trans.setPayMethod("3");
		trans.setCostpriceTotal(trans.getTotalCount() + "");
		trans.setState("10");
		trans.setTrackNo(new Date().getTime() + "");
		trans.setClientType("20");
		trans.setCurrency("RMB");
		return addTrans(trans);
	}

	public PointCashRule getPointCashRulesByPointType(String pointType) {
		Map<String, PointCashRule> allPointCashRules = new HashMap<String, PointCashRule>();
		if (!Assert.empty(allPointCashRules))
			return allPointCashRules.get(pointType);
		else
			return null;
	}

	public Map<String, PointCashRule> getAllPointCashRules() {
		Map<String, PointCashRule> result = new HashMap<String, PointCashRule>();
		List<PointCashRule> pointCashRuleList = pointCashRuleService
				.getAllPointCashRule();
		if (Assert.empty(pointCashRuleList))
			return result;
		for (PointCashRule pointCashRule : pointCashRuleList) {
			result.put(pointCashRule.getPointType(), pointCashRule);
		}
		return result;
	}

	/**
	 * 方法描述：下推交易
	 * 
	 * @param: transId
	 * @return: Result
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 17, 2013 10:37:32 AM
	 */
	public Result pay(Integer transId) {
		Trans trans = this.getTransById(transId);
		return this.pay(trans);
	}

	/**
	 * 方法描述：下推交易
	 * 
	 * @param: transId
	 * @return: boolean
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 17, 2013 10:37:32 AM
	 */
	public Result pay(Trans trans) {
		boolean isOk = false;
		String paymentUrl=null;
		try {
			if (trans == null || trans.getId() == null
					|| trans.getTransType() == null) {
				return new Result(Result.FAIL, "交易不存在");
			}
			Integer transId = trans.getId();
			String transType = trans.getTransType();
			if (transType.equals(TranCommand.POINTS_EXCH_CASH)) {
				isOk = transService.applyWithdrawPoints(transId);
			} else if (transType.equals(TranCommand.POINTS_EXCH_PRODUCT)
					|| transType.equals(TranCommand.GROUP)
					|| transType.equals(TranCommand.PRE_SELL)) {
				com.froad.client.trans.Result result = transService.doPay(transId);
				isOk=Result.SUCCESS.equals(result.getCode());
				if(isOk){
					if(TranCommand.PAY_CHANNEL_ALIPAY.equals(trans.getPayChannel())){
						paymentUrl=result.getMsg();
					}
				}
			} else if (transType.equals(TranCommand.PRESENT_POINTS)) {
				isOk = transService.doDeduct(transId);
			} else if (transType.equals(TranCommand.POINTS_REBATE)
					|| transType.equals(TranCommand.COLLECT)) {
				isOk = transService.doCollect(transId);
			}
		} catch (BusinessException_Exception e) {
			logger.error("支付异常", e);
			return new Result(Result.FAIL, e.getMessage());
		} catch (Exception e) {
			logger.error("未知异常，异常信息如下：", e);
			return new Result(Result.FAIL, TranCommand.EXCEPTION_PREFIX
					+ "响应超时");
		}
		if (isOk) {
			Map<String, Object> session = ActionContext.getContext()
			.getSession();
			session.put("changedPoints", true);
			return new Result(Result.SUCCESS, paymentUrl);
		}
		return new Result(Result.FAIL, "失败");
	}

	/**
	 * 方法描述：添加并下推交易
	 * 
	 * @param: Trans
	 * @return: Trans
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 8, 2013 8:37:57 PM
	 */
	public Trans doTrans(Trans trans) {
		Trans transRes = new Trans();
		transRes.setVirtualType(trans.getVirtualType());
		boolean isValid = this.checkParam(trans);// 校验交易参数
		if (!isValid) {
			transRes.setRespCode(TranCommand.RESP_CODE_ADD_FAIL);
			transRes.setRespMsg("交易参数校验不通过");
			return transRes;
		}
		try {
			transRes = transService.doTrans(trans);
			// 如果下单成功，更新session里的积分账户
			if (TranCommand.RESP_CODE_PAY_REQ_OK.equals(transRes.getRespCode())) {
				Map<String, Object> session = ActionContext.getContext()
						.getSession();
				session.put("changedPoints", true);
			}
		} catch (BusinessException_Exception e) {
			logger.error("交易异常", e);
			transRes.setVirtualType(trans.getVirtualType());
			transRes.setRespCode(TranCommand.RESP_CODE_EXCEPTION);
			transRes.setRespMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("未知异常，异常信息如下：", e);
			transRes.setVirtualType(trans.getVirtualType());
			transRes.setRespCode(TranCommand.RESP_CODE_EXCEPTION);
			transRes.setRespMsg(TranCommand.EXCEPTION_PREFIX + "响应超时");
		}
		return transRes;
	}

	/**
	 * 方法描述：校验必选的交易参数
	 * 
	 * @param: Trans
	 * @return: boolean
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 3, 2013 7:27:30 PM
	 */
	private boolean checkParam(Trans trans) {
		if (trans == null) {
			logger.error("交易对象为空");
			return false;
		}
		if (!TranCommand.TRAN_PROCESSING.equals(trans.getState())) {
			logger.error("未知的交易状态：" + trans.getState());
			return false;
		}
		if (!PayCommand.CLIENT_TYPES.contains(trans.getClientType())) {
			logger.error("未知的客户端类型：" + trans.getClientType());
			return false;
		}
		if (!TranCommand.TRANS_TYPES.contains(trans.getTransType())) {
			logger.error("未知交易类型：" + trans.getTransType());
			return false;
		}
		String payMethod=trans.getPayMethod();
		if(!TranCommand.PAY_METHODS.contains(payMethod)){
			logger.error("未知的支付方式(payMethod)："+payMethod);
			return false;
		}
		if(!TranCommand.POINTS_FFT.equals(payMethod)&&!TranCommand.POINTS_BANK.equals(payMethod)){
			if(!TranCommand.PAY_CHANNELS.contains(trans.getPayChannel())){
				logger.error("未知的支付渠道(payChannel)："+trans.getPayChannel());
				return false;
			}
		}
		if (trans.getSellerId() == null || "".equals(trans.getSellerId())) {
			logger.error("卖家编号为空");
			return false;
		}
		if (trans.getSellerChannelId() == null
				|| "".equals(trans.getSellerChannelId())) {
			logger.error("卖家渠道编号为空");
			return false;
		}
		if (trans.getUserId() == null || "".equals(trans.getUserId())) {
			logger.error("用户编号为空");
			return false;
		}
		logger.info("========交易参数校验通过========");
		return true;
	}

	public List<Pay> getPayByTransId(String transId) {
		if (transId == null || "".equals(transId)) {
			return null;
		}
		return payService.getPayByTransId(transId);
	}

	public String getTransStateById(Integer transId) {
		if (transId == null) {
			logger.error("传入的交易编号为空");
			return null;
		}
		return transService.getTransStateById(transId);
	}

	/**
	 * 方法描述：查询交易状态
	 * 
	 * @param: transId
	 * @return: 结果标识
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 18, 2013 12:35:56 PM
	 */
	public String queryTransResult(Integer transId) {
		String state = this.getTransStateById(transId);
		if (TranCommand.TRAN_SUCCESS.equals(state)) {
			return "success";
		} else if (TranCommand.TRAN_PROCESSING.equals(state)) {
			return "doing";
		} else if (TranCommand.TRAN_FAIL.equals(state)) {
			return "fail";
		} else if (TranCommand.TRAN_CLOSE.equals(state)) {
			return "close";
		} else {
			return "notExist";
		}
	}

	/**
	 * 方法描述：查询收银台收款结果
	 * 
	 * @param: transId 交易号
	 * @return: 收款结果标识
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: May 8, 2013 3:14:53 PM
	 */
	public String queryCollectResult(String transId) {
		List<Pay> payList = this.getPayByTransId(transId);
		if (payList == null || payList.size() == 0) {
			logger.error("交易对应的pay记录不存在，交易号： " + transId);
			return "notExist";
		}
		Pay pay = null;// 正在执行的pay
		for (int i = 0; i < payList.size(); i++) {
			pay = payList.get(i);
			if (!PayState.PAY_WAIT.equals(pay.getState())
					&& !RuleDetailType.POINTS_Factorage.getValue().equals(
							pay.getTypeDetail())) {
				break;
			}
		}
		String typeDetail = pay.getTypeDetail();
		String state = pay.getState();
		if (RuleDetailType.PAY_Currency.getValue().equals(typeDetail)) {
			return this.makeResult(state, PayCommand.DESC_COLLECT);
		} else if (RuleDetailType.BUY_POINTS.getValue().equals(typeDetail)) {
			return this.makeResult(state, PayCommand.DESC_DEDUCT);
		} else if (RuleDetailType.SEND_POINTS.getValue().equals(typeDetail)) {
			return this.makeResult(state, PayCommand.DESC_PRESENT);
		} else {
			logger.error("未知的typeDetail字段值： " + typeDetail);
			return "notExist";
		}
	}

	/**
	 * 方法描述：组织收银台收款结果
	 * 
	 * @param: state 交易状态
	 * @param: prefix 返回结果的前缀
	 * @return: 收款结果
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: May 8, 2013 6:01:45 PM
	 */
	private String makeResult(String state, String prefix) {
		if (PayState.PAY_SUCCESS.equals(state)
				|| PayState.POINT_REBATE_SUCCESS.equals(state)) {
			return prefix + "success";
		} else if (PayState.PAY_FAIL.equals(state)
				|| PayState.PAY_REQUEST_FAIL.equals(state)
				|| PayState.POINT_REBATE_FAIL.equals(state)) {
			return prefix + "fail";
		} else if (PayState.PAY_REQUEST_SUCCESS.equals(state)
				|| PayState.PAY_WAIT.equals(state)) {
			return prefix + "doing";
		} else {
			logger.error("未知的交易状态(state): " + state);
			return "notExist";
		}
	}

	/**
	 * 方法描述：查找指定Pay的state
	 * 
	 * @param: transId 交易号
	 * @param: typeDetail 见RuleDetailType类
	 * @return: state 见PayState类
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: May 8, 2013 3:10:03 PM
	 */
	public String findPayState(String transId, String typeDetail) {
		List<Pay> payList = this.getPayByTransId(transId);
		if (payList == null || payList.size() == 0) {
			return null;
		}
		if (typeDetail == null || "".equals(typeDetail)) {
			return null;
		}
		for (int i = 0; i < payList.size(); i++) {
			if (typeDetail.equals(payList.get(i).getTypeDetail())) {
				return payList.get(i).getState();
			}
		}
		return null;
	}

	// public String queryPayStatusOfTran(Trans tranHanlded){
	// String content="";
	// if(tranHanlded==null || tranHanlded.getId()==null){
	// return "notExists";
	// }
	// Integer transId=tranHanlded.getId();
	// tranHanlded=this.getTransById(transId);
	// if(tranHanlded!=null)
	// logger.info("交易号为："+transId+",该交易状态为："+tranHanlded.getState());
	// if(tranHanlded!=null&&tranHanlded.getState().equals(TransState.tran_success)){
	// content="success";
	// // content="支付成功！";
	// logger.info("交易号为："+transId+",该交易支付成功！");
	// }else
	// if(tranHanlded!=null&&tranHanlded.getState().equals(TransState.doing)){
	// content="";
	// logger.info("交易号为："+transId+",该交易的信息为："+tranHanlded);
	// }else
	// if(tranHanlded!=null&&tranHanlded.getState().equals(TransState.tran_fail)){
	// // content="支付失败";
	// content="fail";
	// logger.info("交易号为："+transId+",该交易的信息为："+tranHanlded);
	// }else if(tranHanlded==null){
	// content="notExists";
	// // content="系统不存在交易号为："+transId+"的交易信息";
	// logger.info("系统不存在交易号为："+transId+"的交易信息");
	// }
	// return content;
	// }

	public String queryPayStatusOfTran(Integer transId) {
		String content = "";
		if (transId == null) {
			return "notExists";
		}
		Trans tranHanlded = this.getTransById(transId);
		if (tranHanlded != null)
			logger
					.info("交易号为：" + transId + ",该交易状态为："
							+ tranHanlded.getState());
		if (tranHanlded != null
				&& tranHanlded.getState().equals(TransState.tran_success)) {
			content = "success";
			// content="支付成功！";
			logger.info("交易号为：" + transId + ",该交易支付成功！");
		} else if (tranHanlded != null
				&& tranHanlded.getState().equals(TransState.doing)) {
			content = "";
			logger.info("交易号为：" + transId + ",该交易的信息为：" + tranHanlded);
		} else if (tranHanlded != null
				&& tranHanlded.getState().equals(TransState.tran_fail)) {
			// content="支付失败";
			content = "fail";
			logger.info("交易号为：" + transId + ",该交易的信息为：" + tranHanlded);
		} else if (tranHanlded == null) {
			content = "notExists";
			// content="系统不存在交易号为："+transId+"的交易信息";
			logger.info("系统不存在交易号为：" + transId + "的交易信息");
		}
		return content;
	}

	// 仅供临时使用
	public Map getRulesFromCache() {
		Map<String, Map> cache = new HashMap();
		Map<String, List<TransRule>> rules_transType = new HashMap();
		Map<String, List<TransRule>> rules_ruleType = new HashMap();
		Map<String, TransRule> rules_ruleId = new HashMap();
		TransRule ruleQueryCon = new TransRule();
		ruleQueryCon.setState(State.AVAILIABLE.getValue());
		List<TransRule> allTransRules = transRuleService
				.getTransRules(ruleQueryCon);
		if (Assert.empty(allTransRules))
			return cache;
		for (TransRule transRule : allTransRules) {
			List<TransRule> rule_transType = rules_transType.get(transRule
					.getTransType());
			if (rule_transType == null) {
				rule_transType = new ArrayList();
				rule_transType.add(transRule);
				rules_transType.put(transRule.getTransType(), rule_transType);
			} else {
				rule_transType.add(transRule);
			}

			List<TransRule> rule_ruleType = rules_ruleType.get(transRule
					.getRuleType());
			if (rule_ruleType == null) {
				rule_ruleType = new ArrayList();
				rule_ruleType.add(transRule);
				rules_ruleType.put(transRule.getRuleType(), rule_transType);
			} else {
				rule_ruleType.add(transRule);
			}
			rules_ruleId.put(String.valueOf(transRule.getId()), transRule);
		}
		cache.put("rules-transType", rules_transType);
		cache.put("rules-ruleId", rules_ruleId);
		cache.put("rules-ruleType", rules_ruleType);
		return cache;
	}

	public List<FundsChannel> queryFundsChannel(FundsChannel fundsChannel) {
		List<FundsChannel> fundsChannelList = fundsChannelService
				.getFundsChannels(fundsChannel);
		return fundsChannelList;
	}

	/**
	 * 更新交易信息
	 * 
	 * @param tran
	 * @return
	 */
	public void updateByTransId(Trans tran) {
		try {
			transService.updateById(tran);
		} catch (Exception e) {
			logger.error("TransActionSupport.updateByTransId error", e);
		}
	}

	public void updateStateById(Integer id, String state) {
		try {
			transService.updateStateById(id, state);
		} catch (Exception e) {
			logger.error("修改交易状态出现异常", e);
		}
	}

	public Trans getTransById(Integer transId) {

		return transService.getTransById(transId);
	}

	public Trans getTransByPager(Trans trans) {

		return transService.getTransByPager(trans);
	}

	/**
	 * *******************************************************
	 * <p>
	 * 描述: *-- <b>财务查询</b> --*
	 * </p>
	 * <p>
	 * 作者: 赵肖瑶
	 * </p>
	 * <p>
	 * 时间: 2013-11-22 下午05:16:29
	 * </p>
	 * <p>
	 * 版本: 1.0.1
	 * </p>
	 * ********************************************************
	 */
	public Trans getTransByPagerFinance(Trans trans) {

		return transService.getTransByPagerFinance(trans);
	}

	public Trans getGroupOrExchangeFinance(Trans trans) {

		return transService.getGroupOrExchangeTransByPagerFinance(trans);
	}

	public Trans getDataToRepExcel(Trans trans) {
		return transService.getDataToRepExcel(trans);
	}

	public Trans getGroupOrExchangeTransByPager(Trans trans) {

		return transService.getGroupOrExchangeTransByPager(trans);
	}


    public String aliPayResultCheck(String transId)
    {
        return payService.aliPayResultCheck(transId);
    }

	public TransService getTransService() {
		return transService;
	}

	public void setTransService(TransService transService) {
		this.transService = transService;
	}

	public SellersActionSupport getSellerActionSupport() {
		return sellerActionSupport;
	}

	public void setSellerActionSupport(SellersActionSupport sellerActionSupport) {
		this.sellerActionSupport = sellerActionSupport;
	}

	public PointCashRuleService getPointCashRuleService() {
		return pointCashRuleService;
	}

	public void setPointCashRuleService(
			PointCashRuleService pointCashRuleService) {
		this.pointCashRuleService = pointCashRuleService;
	}

	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}

	public TransRuleService getTransRuleService() {
		return transRuleService;
	}

	public void setTransRuleService(TransRuleService transRuleService) {
		this.transRuleService = transRuleService;
	}

	public FundsChannelService getFundsChannelService() {
		return fundsChannelService;
	}

	public void setFundsChannelService(FundsChannelService fundsChannelService) {
		this.fundsChannelService = fundsChannelService;
	}

	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}

	public void setPayService(PayService payService) {
		this.payService = payService;
	}

}
