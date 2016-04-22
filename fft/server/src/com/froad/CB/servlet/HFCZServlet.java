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
import com.froad.CB.common.constant.PayState;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.impl.PayDaoImpl;
import com.froad.CB.po.Pay;
import com.froad.CB.po.SmsLog;
import com.froad.CB.po.Trans;
import com.froad.CB.service.impl.MessageServiceImpl;
import com.froad.CB.service.impl.TransServiceImpl;
import com.froad.util.MessageSourceUtil;


public class HFCZServlet extends HttpServlet{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final Logger logger=Logger.getLogger(HFCZServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("==========收到话费充值的通知============");
		request.setCharacterEncoding("UTF-8");
		String spid=request.getParameter("SPID");//分分通传递的订单号
		String respCode=request.getParameter("respCode");//响应码
		String respMsg=request.getParameter("respMsg");//响应消息
		//成功：修改充值的pay记录  失败：退(款|积分)
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
			Pay hfczPay=this.findPay(payList, payId);
			if(hfczPay==null){
				logger.error("话费充值的pay记录不存在，交易号： "+transId+"，payId: "+payId);
				return;
			}
			if(PayState.HFCZ_SUCCESS.equals(hfczPay.getState())){
				logger.error("该笔话费充值已经成功，重复的话费充值通知");
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
				logger.error("该笔话费充值交易的支付记录为空，交易号： "+transId);
				return;
			}
			if(cashPay!=null&&!PayState.PAY_SUCCESS.equals(cashPay.getState())){
				logger.error("该笔话费充值交易还没有付款成功，交易号："+transId+" 当前支付状态为: "+cashPay.getState());
				return;
			}
			if(pointsPay!=null&&!PayState.POINT_PAY_SUCCESS.equals(pointsPay.getState())){
				logger.error("该笔话费充值交易，积分还没有抵扣，交易号："+transId+" 当前积分的抵扣状态为： "+pointsPay.getState());
				return;
			}
			PayDaoImpl payDao=(PayDaoImpl)SpringContextUtil.getBean("payDaoImpl");
			if(Command.respCode_SUCCESS.equals(respCode)){
				logger.info("==========话费充值成功，交易号："+transId+"=========");
				hfczPay.setState(PayState.HFCZ_SUCCESS);
				hfczPay.setResultCode(respCode);
				hfczPay.setResultDesc("话费充值成功");
				payDao.updatePay(hfczPay);
				transService.updateStateById(transId, TranCommand.TRAN_SUCCESS);
				
				
				
				//给用户发短信
				//TODO 取消发送短信
				this.sendMsg(hfczPay.getToPhone(), hfczPay.getPayValue());
				//修改库存和销量
				transService.updateStore(trans);
			}else if(Command.respCode_FAIL.equals(respCode)){
				logger.info("==========话费充值失败，交易号："+transId+"=========");
				hfczPay.setState(PayState.HFCZ_FAIL);
				hfczPay.setResultCode(respCode);
				hfczPay.setResultDesc(respMsg==null?"话费充值失败":respMsg);
				payDao.updatePay(hfczPay);
				transService.updateStateById(transId, TranCommand.TRAN_FAIL);
				//退款
				if(cashPay!=null){
					transService.refund(cashPay, "话费充值失败");
				}
				//退积积分
				if(pointsPay!=null){
					transService.refundPoints(pointsPay, trans.getPointBillNo());
				}
			}else{
				logger.error("话费充值通知中出现未知的响应码(respCode):"+respCode);
			}
			response.getWriter().write("200");
		}else{
			logger.error("交易不存在，交易编号： "+transId);
		}
	}

	private void sendMsg(String phone,String amount){
		String[]msg={phone,amount};
		String content=MessageSourceUtil.getSource().getMessage("hfcz", msg, null);
		SmsLog smsLog=new SmsLog();
		smsLog.setMobile(phone);
		smsLog.setMessage(content);
		smsLog.setType(SmsLogType.SMS_LOG_HFCZ);
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
