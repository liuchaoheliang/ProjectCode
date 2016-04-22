package com.froad.CB.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.froad.CB.common.Command;
import com.froad.CB.common.RuleDetailType;
import com.froad.CB.common.SmsLogType;
import com.froad.CB.common.SpringContextUtil;
import com.froad.CB.common.constant.LotteryCommand;
import com.froad.CB.common.constant.PayState;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.impl.PayDaoImpl;
import com.froad.CB.po.Pay;
import com.froad.CB.po.SmsLog;
import com.froad.CB.po.Trans;
import com.froad.CB.po.TransGoodsAttribute;
import com.froad.CB.service.impl.MessageServiceImpl;
import com.froad.CB.service.impl.TransServiceImpl;
import com.froad.util.MessageSourceUtil;


	/**
	 * 类描述：接收彩票的发货通知(购买成功或失败)
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 26, 2013 8:32:37 PM 
	 */
public class LotteryShipmentsServlet extends HttpServlet {

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private static final Logger logger=Logger.getLogger(LotteryShipmentsServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("==========收到彩票发货通知============");
		request.setCharacterEncoding("UTF-8");
		String spid=request.getParameter("SPID");//分分通传递的订单号
		String respCode=request.getParameter("respCode");//响应码
		String respMsg=request.getParameter("respMsg");//响应消息
		//成功：修改彩票的pay记录  失败：退(款|积分)
		if(spid==null||!spid.contains("fbu")||!spid.contains("x")){
			logger.error("无效的SPID: "+spid);
			return;
		}
		String[] tmp=spid.substring(3).split("x");
		Integer transId=Integer.parseInt(tmp[0]);
		Integer payId=Integer.parseInt(tmp[1]);
		TransServiceImpl transService=(TransServiceImpl)SpringContextUtil.getBean("transService");
		Trans trans = transService.getTransById(transId);
		if(trans!=null){
			List<Pay> payList=trans.getPayList();
			Pay lotteryPay=this.findPay(payList, payId);
			if(lotteryPay==null){
				logger.error("购买彩票的pay记录不存在，交易号： "+transId+"，payId: "+payId);
				return;
			}
			if(PayState.LOTTERY_SUCCESS.equals(lotteryPay.getState())){
				logger.error("重复的彩票发货通知");
				return;
			}
			Pay pointsPay=null,cashPay=null,tmpPay=null;
			String detail=null;
			for (int i = 0; i <payList.size(); i++) {
				tmpPay=payList.get(i);
				detail=tmpPay.getTypeDetail();
				if(RuleDetailType.PAY_POINTS.getValue().equals(detail)||
						RuleDetailType.PAY_BANK_POINTS.getValue().equals(detail)){
					pointsPay=tmpPay;
				}else if(RuleDetailType.PAY_COLLECT.getValue().equals(detail)){
					cashPay=tmpPay;
				}
			}
			if(cashPay==null&&pointsPay==null){
				logger.error("该笔彩票交易的支付记录为空，交易号： "+transId);
				return;
			}
			if(cashPay!=null&&!PayState.PAY_SUCCESS.equals(cashPay.getState())){
				logger.error("该笔彩票交易还没有付款成功，交易号："+transId+" 当前支付状态为: "+cashPay.getState());
				return;
			}
			if(pointsPay!=null&&!PayState.POINT_PAY_SUCCESS.equals(pointsPay.getState())){
				logger.error("该笔彩票交易，积分还没有抵扣，交易号："+transId+" 当前积分的抵扣状态为： "+pointsPay.getState());
				return;
			}
			PayDaoImpl payDao=(PayDaoImpl)SpringContextUtil.getBean("payDaoImpl");
			if(Command.respCode_SUCCESS.equals(respCode)){
				logger.info("==========彩票购买成功，交易号："+transId+"=========");
				lotteryPay.setState(PayState.LOTTERY_SUCCESS);
				lotteryPay.setResultCode(respCode);
				lotteryPay.setResultDesc("彩票购买成功");
				payDao.updatePay(lotteryPay);
				transService.updateStateById(transId, TranCommand.TRAN_SUCCESS);
				//彩票购买成功,给用户发短信
				this.sendMsg(trans.getTransGoodsAttrList());
				//修改库存和销量
				transService.updateStore(trans);
			}else if(Command.respCode_FAIL.equals(respCode)){
				logger.info("==========彩票购买失败，交易号："+transId+"=========");
				lotteryPay.setState(PayState.LOTTERY_FAIL);
				lotteryPay.setResultCode(respCode);
				lotteryPay.setResultDesc(respMsg==null?"彩票购买失败":respMsg);
				payDao.updatePay(lotteryPay);
				transService.updateStateById(transId, TranCommand.TRAN_FAIL);
				//退款
				if(cashPay!=null){
					transService.refund(cashPay, "彩票购买失败");
				}
				//退积积分
				if(pointsPay!=null){
					transService.refundPoints(pointsPay, trans.getPointBillNo());
				}
			}else{
				logger.error("购买彩票通知中出现未知的响应码(respCode):"+respCode);
			}
			response.getWriter().write("200");
		}else{
			logger.error("交易不存在，交易编号： "+transId);
		}
	}
	
	
	/**
	  * 方法描述：彩票购买成功后发短信
	  * @param: phone
	  * @param: msg字段的顺序[period,lotteryNo,buyamount,numCount,amount,transId]
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 18, 2013 5:56:16 PM
	  */
	private void sendMsg(List<TransGoodsAttribute> attrList){
		String phone=null;
		
		String period=null;
		String lotteryNo=null;
		String buyamount=null;
		String numCount=null;
		String amount=null;
		String transId=attrList.get(0).getTransId();
		
		TransGoodsAttribute attr=null;
		String rackAttrId=null;
		String element=null;
		for (int i = 0; i <attrList.size(); i++) {
			attr=attrList.get(i);
			rackAttrId=attr.getGoodsRackAttributeId();
			element=attr.getElement();
			if(TranCommand.ID_LOTTERY_PHONE.equals(rackAttrId)){
				phone=element;
			}else if(TranCommand.ID_PERIOAD.equals(rackAttrId)){
				period=element;
			}else if(TranCommand.ID_LOTTERY_NO.equals(rackAttrId)){
				lotteryNo=element;
			}else if(TranCommand.ID_BUY_AMOUNT.equals(rackAttrId)){
				buyamount=element;
			}else if(TranCommand.ID_NUM_COUNT.equals(rackAttrId)){
				numCount=element;
			}else if(TranCommand.ID_AMOUNT.equals(rackAttrId)){
				amount=element;
			}
		}
		lotteryNo=LotteryCommand.LOTTERY_NO.get(lotteryNo);//彩票编码对应的中文
		String[] msg={period,lotteryNo,buyamount,numCount,amount,transId};
		String content=MessageSourceUtil.getSource().getMessage("lottery", msg, null);
		SmsLog smsLog=new SmsLog();
		smsLog.setMobile(phone);
		smsLog.setMessage(content);
		smsLog.setType(SmsLogType.SMS_LOG_LOTTERY);
		MessageServiceImpl messageService=(MessageServiceImpl)SpringContextUtil.getBean("messageService");
		messageService.sendMessage(smsLog);
	}
	
	
	private Pay findPay(List<Pay> payList,Integer payId){
		for (int i = 0; i <payList.size(); i++) {
			if(payList.get(i).getId().equals(payId)){
				return payList.get(i);
			}
		}
		return null;
	}
}
