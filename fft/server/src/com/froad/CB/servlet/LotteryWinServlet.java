package com.froad.CB.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.frontend.spring.ClientProxyFactoryBeanDefinitionParser.SpringClientProxyFactoryBean;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.froad.CB.common.SmsLogType;
import com.froad.CB.common.SpringContextUtil;
import com.froad.CB.common.constant.PayState;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.impl.PayDaoImpl;
import com.froad.CB.dao.impl.TransGoodsAttributeDaoImpl;
import com.froad.CB.dao.impl.UserCertificationDaoImpl;
import com.froad.CB.dao.user.impl.UserDaoImpl;
import com.froad.CB.po.Pay;
import com.froad.CB.po.SmsLog;
import com.froad.CB.po.Trans;
import com.froad.CB.po.TransGoodsAttribute;
import com.froad.CB.po.UserCertification;
import com.froad.CB.po.user.User;
import com.froad.CB.service.impl.MessageServiceImpl;
import com.froad.CB.service.impl.TransServiceImpl;
import com.froad.util.MessageSourceUtil;

/**
 * 类描述：接收彩票的中奖通知
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Mar 26, 2013 8:33:27 PM
 */
public class LotteryWinServlet extends HttpServlet {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(LotteryWinServlet.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("==========收到彩票[中奖|派奖]通知============");
		request.setCharacterEncoding("UTF-8");
		String tranID=request.getParameter("tranID");//第三方彩票订单号
		String awdAmount = request.getParameter("awdAmount");// 中奖金额
		String prizeGrade = request.getParameter("prizeGrade");// 中奖等级
		String prizeCount = request.getParameter("prizeCount");// 中奖注数
		String status=request.getParameter("status");//0：中奖  00：已派奖
		logger.info("tranID="+tranID+",awdAmount="+awdAmount+",prizeGrade="+prizeGrade+
				",prizeCount="+prizeCount+",status="+status);

		if(tranID==null||"".equals(tranID)){
			logger.error("第三方彩票订单号为空");
			return;
		}
		if (awdAmount == null || awdAmount.trim().equals("")) {
			logger.error("中奖金额为空");
			return;
		}
		if(!"0".equals(status)&&!"00".equals(status)){
			logger.error("无效的status: "+status);
			return;
		}
		
		TransServiceImpl transService = (TransServiceImpl) SpringContextUtil
				.getBean("transService");
		Trans trans = transService.getTransByLotteryBillNo(tranID);
		if(trans==null){
			logger.error("交易不存在，第三方彩票订单号： " + tranID);
			return;
		}
		Integer transId=trans.getId();
		List<Pay> payList = trans.getPayList();
		Pay lotteryPay = this.findLotteryPay(payList);
		if (lotteryPay == null) {
			logger.error("彩票的pay记录不存在，交易号： " + transId + "第三方彩票订单号："+tranID);
			return;
		}
		if ("0".equals(status)&&
				PayState.LOTTERY_WIN.equals(lotteryPay.getState())) {
			logger.error("重复的彩票中奖通知");
			return;
		}
		if ("00".equals(status)&&
				PayState.LOTTERY_SEND.equals(lotteryPay.getState())) {
			logger.error("重复的彩票派奖通知");
			return;
		}
		if ("0".equals(status)&&
				!PayState.LOTTERY_SUCCESS.equals(lotteryPay.getState())) {
			logger.error("彩票没有购买成功，当前彩票pay记录的状态为：" + lotteryPay.getState()
					+ "交易号：" + transId + "，第三方彩票订单号: " + tranID);
			return;
		}
		if ("00".equals(status)&&
				!PayState.LOTTERY_WIN.equals(lotteryPay.getState())) {
			logger.error("彩票没有中奖，当前彩票pay记录的状态为：" + lotteryPay.getState()
					+ "交易号：" + transId + "，第三方彩票订单号: " + tranID);
			return;
		}
		// 更新购买彩票的pay记录
		PayDaoImpl payDao = (PayDaoImpl) SpringContextUtil
				.getBean("payDaoImpl");
		if("0".equals(status)){//中奖通知
			lotteryPay.setState(PayState.LOTTERY_WIN);
			lotteryPay.setResultDesc("中奖");
			payDao.updatePay(lotteryPay);
			// 向TransGoodsAttribute表中添加中奖属性(中奖金额、中奖等级、中奖注数)
			List<TransGoodsAttribute> attrList = new ArrayList<TransGoodsAttribute>();

			TransGoodsAttribute awdAmountAttr = new TransGoodsAttribute();// 中奖金额
			awdAmountAttr.setTransId(transId + "");
			awdAmountAttr.setGoodsRackAttributeId(TranCommand.ID_AWD_AMOUNT);
			awdAmountAttr.setElement(awdAmount);
			attrList.add(awdAmountAttr);

			if (prizeGrade != null && !"".equals(prizeGrade)) {// 中奖等级
				TransGoodsAttribute prizeGradeAttr = new TransGoodsAttribute();
				prizeGradeAttr.setTransId(transId + "");
				prizeGradeAttr
						.setGoodsRackAttributeId(TranCommand.ID_PRIZE_GRADE);
				prizeGradeAttr.setElement(prizeGrade);
				attrList.add(prizeGradeAttr);
			}

			if (prizeCount != null && !"".equals(prizeCount)) {// 中奖注数
				TransGoodsAttribute prizeCountAttr = new TransGoodsAttribute();
				prizeCountAttr.setTransId(transId + "");
				prizeCountAttr
						.setGoodsRackAttributeId(TranCommand.ID_PRIZE_COUNT);
				prizeCountAttr.setElement(prizeCount);
				attrList.add(prizeCountAttr);
			}

			TransGoodsAttributeDaoImpl attrDaoImpl = (TransGoodsAttributeDaoImpl) SpringContextUtil
					.getBean("transGoodsAttributeDaoImpl");
			attrDaoImpl.batchInsert(attrList);
			
			//查询用户是否为绑定体现认证的账户
			UserCertification userCertification=new UserCertification();
			userCertification.setUserId(trans.getUserId());
			userCertification.setCertificationResult("1");			
			UserCertificationDaoImpl certificationDaoImpl=(UserCertificationDaoImpl) SpringContextUtil.getBean("userCertificationDaoImpl");
			List<UserCertification> list=certificationDaoImpl.getUserCertBySelective(userCertification);
			if(list.size()==0||list==null){
				sendMessage(trans.getUserId());
			}
		}else{//派奖通知
			lotteryPay.setState(PayState.LOTTERY_SEND);
			lotteryPay.setResultDesc("已派奖");
			payDao.updatePay(lotteryPay);
		}
		response.getWriter().write("200");
	}

	
	private Pay findLotteryPay(List<Pay> payList) {
		for (int i = 0; i < payList.size(); i++) {
			if (TranCommand.CATEGORY_LOTTORY.equals(payList.get(i).getTypeDetail())) {
				return payList.get(i);
			}
		}
		return null;
	}
	
	private void sendMessage(String userId){
		//给用户发短信(彩票派奖未绑定提现认证的账户)
		UserDaoImpl userDaoImpl=(UserDaoImpl) SpringContextUtil.getBean("userDaoImpl");
		User user=userDaoImpl.queryUserAllByUserID(userId);
		String content=MessageSourceUtil.getSource().getMessage("withdraw_certification", null, null);
		SmsLog smsLog=new SmsLog();
		smsLog.setMobile(user.getMobilephone());
		smsLog.setMessage(content);
		smsLog.setType(SmsLogType.WITHDRAW_CERTIFICATION);
		MessageServiceImpl messageServiceImpl=(MessageServiceImpl) SpringContextUtil.getBean("messageService");
		messageServiceImpl.sendMessage(smsLog);
	}

}
